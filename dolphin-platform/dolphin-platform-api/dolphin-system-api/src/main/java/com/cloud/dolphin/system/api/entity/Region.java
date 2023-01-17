package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 区域管理
 * 区域数据量太大了,采用懒加载形式处理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/7/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value="sys_region", excludeProperty = { "delFlag" })
public class Region extends TreeEntity<Region> {

    /** 区域编码 */
    private String code;

    /** 地区级别 */
    private Integer level;

}
