package com.cloud.dolphin.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import com.cloud.dolphin.monitor.mapper.OperLogMapper;
import com.cloud.dolphin.monitor.service.OperLogService;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 操作日志记录 服务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements OperLogService {

}
