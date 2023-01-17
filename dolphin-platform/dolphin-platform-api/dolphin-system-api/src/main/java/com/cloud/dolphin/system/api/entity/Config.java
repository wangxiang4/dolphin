package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 参数配置表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_config")
public class Config extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 参数主键
     */
    @TableId
    private String id;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键名
     */
    @TableField("`key`")
    private String key;

    /**
     * 参数键值
     */
    @TableField("`value`")
    private String value;

    /**
     * 系统内置
     */
    private String isSys;

}
