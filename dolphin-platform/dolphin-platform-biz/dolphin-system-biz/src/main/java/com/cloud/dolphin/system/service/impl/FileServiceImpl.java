package com.cloud.dolphin.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.common.core.exception.CheckedException;
import com.cloud.dolphin.system.api.entity.File;
import com.cloud.dolphin.system.mapper.FileMapper;
import com.cloud.dolphin.system.service.FileService;
import com.pig4cloud.plugin.oss.OssProperties;
import com.pig4cloud.plugin.oss.service.OssTemplate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
/**
 *<p>
 * 文件管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

	private final OssProperties ossProperties;
	private final OssTemplate ossTemplate;

	@Override
	public Map uploadFile(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", ossProperties.getBucketName());
		resultMap.put("fileName", fileName);
		resultMap.put("url", String.format("/system_proxy/system/file/getFile/%s/%s", ossProperties.getBucketName(), fileName));
		try {
			ossTemplate.putObject(ossProperties.getBucketName(), fileName, file.getContentType(), file.getInputStream());
			// 文件管理数据记录,收集管理追踪文件
			fileLog(file, fileName);
		} catch (Exception e) {
			throw new CheckedException("上传失败");
		}
		return resultMap;
	}

	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = ossTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		} catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}

	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public void deleteFile(String id) {
		File file = this.getById(id);
		if (ObjectUtil.isNotEmpty(file)) {
			ossTemplate.removeObject(ossProperties.getBucketName(), file.getFileName());
			this.removeById(id);
		}
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 * @param file 上传文件格式
	 * @param fileName 文件名
	 */
	private void fileLog(MultipartFile file, String fileName) {
		this.save(new File()
				.setFileName(fileName)
				.setOriginal(file.getOriginalFilename())
				.setFileSize(file.getSize())
				.setType(FileUtil.extName(file.getOriginalFilename()))
				.setBucketName(ossProperties.getBucketName()));
	}

}
