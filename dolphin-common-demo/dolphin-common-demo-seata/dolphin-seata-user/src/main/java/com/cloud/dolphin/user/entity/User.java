package com.cloud.dolphin.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 用户表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("demo_seata_user")
public class User {

	@TableId
	private String id;

	private String name;

	private Integer age;

}
