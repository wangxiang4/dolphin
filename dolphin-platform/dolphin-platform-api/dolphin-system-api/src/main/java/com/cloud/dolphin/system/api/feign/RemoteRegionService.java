package com.cloud.dolphin.system.api.feign;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import com.cloud.dolphin.system.api.entity.Region;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.List;

/**
 *<p>
 * 远程区域api
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@FeignClient(contextId = "remoteRegionService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteRegionService {

	/**
	 * 通过区域id查询区域信息
	 * @param regionIds 区域id
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping(AppConstants.APP_SYSTEM + "/region/selectByRegionIds/{regionIds}")
	R<List<Region>> selectByRegionIds(@PathVariable("regionIds") String[] regionIds, @RequestHeader(SecurityConstants.FROM) String from);

}
