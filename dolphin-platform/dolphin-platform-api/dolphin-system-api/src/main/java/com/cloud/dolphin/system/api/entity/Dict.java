package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 字典类型表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId
    private String id;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型
     */
    private String type;

    /**
     * 是否系统
     */
    private String isSys;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

}
