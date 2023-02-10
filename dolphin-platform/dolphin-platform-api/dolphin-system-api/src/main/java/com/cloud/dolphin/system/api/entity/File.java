package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * oss文件管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_file", excludeProperty = { "remarks" })
public class File extends CommonEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private String id;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 容器名称
	 */
	private String bucketName;

	/**
	 * 原文件名
	 */
	private String original;

	/**
	 * 文件类型
	 */
	private String type;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 有效原始路径
	 */
	private String availablePath;

	/**
	 * 视频时长
	 */
	private Long duration;

	/**
	 * 媒体资源类型
	 */
	private String mimeType;

}
