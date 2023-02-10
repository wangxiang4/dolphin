package com.cloud.dolphin.system.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.log.annotation.SysLog;
import com.cloud.dolphin.common.security.annotation.Inner;
import com.cloud.dolphin.system.api.entity.File;
import com.cloud.dolphin.system.api.entity.Role;
import com.cloud.dolphin.system.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 *<p>
 * 文件管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/file")
@Api(value = "file", tags = "文件管理")
public class FileController {

	private final FileService fileService;

	private LambdaQueryWrapper<File> getQueryWrapper(File file) {
		return new LambdaQueryWrapper<File>()
				.like(StrUtil.isNotBlank(file.getFileName()), File::getFileName, file.getFileName());
	}

	@GetMapping("/list")
	@ApiOperation(value = "分页查询", notes = "分页查询")
	public R list(Page page, File file) {
		IPage<Role> fileIPage = fileService.page(page, getQueryWrapper(file));
		return R.ok(fileIPage.getRecords(), fileIPage.getTotal());
	}

	@PostMapping("/upload")
	public File upload(@RequestPart("file") MultipartFile file,
					   @RequestParam(value = "ossFile", required=false) String ossFile) {
		return fileService.uploadFile(file, Optional.ofNullable(ossFile).map(item -> JSONUtil.parseObj(ossFile).toBean(File.class)).orElse(null));
	}

	@Inner(false)
	@GetMapping("/getFile/{bucket}/{fileName}")
	public void getById(@PathVariable String bucket, @PathVariable String fileName, HttpServletResponse response) {
		fileService.getFile(bucket, fileName, response);
	}

	/** 获取上传模板 */
	@SneakyThrows
	@Inner(false)
	@GetMapping("/local/{fileName}")
	public void localFile(@PathVariable String fileName, HttpServletResponse response) {
		ClassPathResource resource = new ClassPathResource("file/" + fileName);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.copy(resource.getInputStream(), response.getOutputStream());
	}

	@SysLog("删除文件管理")
	@DeleteMapping("/remove/{ids:[\\w,]+}")
	@PreAuthorize("@pms.hasPermission('file_del')")
	@ApiOperation(value = "通过id删除文件管理", notes = "通过id删除文件管理")
	public R remove(@PathVariable String[] ids) {
		for (int i = 0; i < ids.length; ++i) {
			fileService.deleteFile(ids[i]);
		}
		return R.ok();
	}

}
