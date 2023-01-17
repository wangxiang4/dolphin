package com.cloud.dolphin.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *<p>
 * 树结构模型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/12/28
 */
@Data
public class TreeEntity<T> extends CommonEntity {

    private static final long serialVersionUID = 1L;
    /** 编号 **/
    @ApiModelProperty("编号")
    private String id;

    /** 父级编号 **/
    @ApiModelProperty("父级编号")
    private String parentId;

    /** 名称 */
    @ApiModelProperty("名称")
    protected String name;

    /** 排序 **/
    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子级集合")
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected List<T> children;

}
