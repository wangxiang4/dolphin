package com.cloud.dolphin.point.controller;

import com.cloud.dolphin.point.entity.Point;
import com.cloud.dolphin.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 积分控制器类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@RestController
@RequiredArgsConstructor
public class PointController {

	private final PointService pointService;

	/**
	 * 创建积分
	 */
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/point")
	public void setPoint() {
		Point point = new Point();
		point.setCount(10);
		pointService.saveOrUpdate(point);
	}
}
