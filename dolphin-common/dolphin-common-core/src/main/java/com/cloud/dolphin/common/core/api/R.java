package com.cloud.dolphin.common.core.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *<p>
 * 响应信息主体
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标记
	 */
	private static Integer SUCCESS = 200;
	/**
	 * 失败标记
	 */
	private static Integer FAIL = 500;
	/**
	 * 未认证
	 */
	private static Integer UNAUTH = 401;

	@Getter
	@Setter
	@ApiModelProperty("状态编码")
	private int code;

	@Getter
	@Setter
	@ApiModelProperty("提示消息")
	private String msg;

	@Getter
	@Setter
	@ApiModelProperty("结果集数量统计")
	private long total;

	@Getter
	@Setter
	@ApiModelProperty("结果集")
	private T data;

	public static <T> R<T> ok() {
		return restResult(null, SUCCESS, "成功");
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, SUCCESS, "成功");
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, SUCCESS, msg);
	}

	public static <T> R<T> ok(T data, long total) {
		return restData(data, SUCCESS, null, total);
	}

	public static <T> R<T> error() {
		return restResult(null, FAIL, "失败");
	}

	public static <T> R<T> error(String msg) {
		return restResult(null, FAIL, msg);
	}

	public static <T> R<T> error(T data) {
		return restResult(data, FAIL, null);
	}

	public static <T> R<T> error(T data, String msg) {
		return restResult(data, FAIL, msg);
	}

	public static <T> R<T> unAuth(String msg) {
		return restResult(null, UNAUTH, msg);
	}


	private static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

	private static <T> R<T> restData(T data, int code, String msg, long total) {
		R<T> apiData = new R<>();
		apiData.setCode(code);
		apiData.setMsg(msg);
		apiData.setTotal(total);
		apiData.setData(data);
		return apiData;
	}

}
