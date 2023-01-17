package com.cloud.dolphin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.dolphin.system.api.entity.OauthClientDetails;

import java.util.List;

/**
 *<p>
 * 授权客户端管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

	/**
	 * 通过ID删除客户端
	 * @param ids
	 * @return Boolean
	 */
	Boolean removeClientDetailsByIds(List<String> ids);

	/**
	 * 修改客户端信息
	 * @param oauthClientDetails
	 * @return Boolean
	 */
	Boolean updateClientDetailsById(OauthClientDetails oauthClientDetails);

	/**
	 * 清除客户端缓存
	 */
	void clearClientCache();

}
