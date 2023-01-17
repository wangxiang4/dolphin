package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 字典数据表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_dict_data")
public class DictData extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据编号
     */
    @TableId
    private String id;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 字典排序
     */
    private Integer sort;

}
