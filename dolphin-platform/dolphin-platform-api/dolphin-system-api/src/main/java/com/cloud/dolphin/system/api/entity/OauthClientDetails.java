package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 *<p>
 * 客户端信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_oauth_client_details", excludeProperty = { "remarks", "delFlag" })
public class OauthClientDetails extends CommonEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户端ID
	 */
	@NotBlank(message = "client_id 不能为空")
	@TableId(value = "client_id", type = IdType.INPUT)
	@ApiModelProperty(value = "客户端id")
	private String clientId;

	/**
	 * 客户端密钥
	 */
	@NotBlank(message = "client_secret 不能为空")
	@ApiModelProperty(value = "客户端密钥")
	private String clientSecret;

	/**
	 * 资源ID
	 */
	@ApiModelProperty(value = "资源id列表")
	private String resourceIds;

	/**
	 * 作用域
	 */
	@NotBlank(message = "scope 不能为空")
	@ApiModelProperty(value = "作用域")
	private String scope;

	/**
	 * 授权方式（A,B,C）
	 */
	@ApiModelProperty(value = "授权方式")
	private String authorizedGrantTypes;

	/**
	 * 回调地址
	 */
	@ApiModelProperty(value = "回调地址")
	private String webServerRedirectUri;

	/**
	 * 权限
	 */
	@ApiModelProperty(value = "权限列表")
	private String authorities;

	/**
	 * 请求令牌有效时间
	 */
	@ApiModelProperty(value = "请求令牌有效时间")
	private Integer accessTokenValidity;

	/**
	 * 刷新令牌有效时间
	 */
	@ApiModelProperty(value = "刷新令牌有效时间")
	private Integer refreshTokenValidity;

	/**
	 * 扩展信息
	 */
	@ApiModelProperty(value = "扩展信息")
	private String additionalInformation;

	/**
	 * 是否自动放行
	 */
	@ApiModelProperty(value = "是否自动放行")
	private String autoapprove;

}
