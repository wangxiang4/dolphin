package com.cloud.dolphin.point.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.point.entity.Point;
import com.cloud.dolphin.point.mapper.PointMapper;
import com.cloud.dolphin.point.service.PointService;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 积分业务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Service
public class PointServiceImpl extends ServiceImpl<PointMapper, Point> implements PointService {
}
