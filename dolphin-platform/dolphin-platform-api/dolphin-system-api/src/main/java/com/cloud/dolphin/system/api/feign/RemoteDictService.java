package com.cloud.dolphin.system.api.feign;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import com.cloud.dolphin.system.api.entity.DictData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 *<p>
 * 远程字典api
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@FeignClient(contextId = "remoteDictService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteDictService {

	/**
	 * 根据 type 查询字典数据
	 * @param type 字典类型
	 * @param from 内部调用标志
	 * @return 返回字典项列表
	 */
	@GetMapping(AppConstants.APP_SYSTEM + "/dictData/getDictByType/{type}")
	R<List<DictData>> getDictByType(@PathVariable("type") String type, @RequestHeader(SecurityConstants.FROM) String from);

}
