package com.cloud.dolphin.common.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *<p>
 * 通用模型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/12/28
 */
@Data
public class CommonEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 创建id */
    @ApiModelProperty("创建id")
    @TableField(value = "create_by_id", fill = FieldFill.INSERT)
    protected String createById;

    /** 创建者 */
    @ApiModelProperty("创建人")
    @TableField(value = "create_by_name", fill = FieldFill.INSERT)
    protected String createByName;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;

    /** 更新id */
    @ApiModelProperty("更新id")
    @TableField(value = "update_by_id", fill = FieldFill.UPDATE)
    protected String updateById;

    /** 更新者 */
    @ApiModelProperty("更新者")
    @TableField(value = "update_by_name", fill = FieldFill.UPDATE)
    protected String updateByName;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;

    /** 备注 */
    @ApiModelProperty("备注")
    protected String remarks;

    /** 删除标志（0代表存在 1代表删除）*/
    @TableLogic
    @JsonIgnore
    protected String delFlag;

    /** 开始时间 */
    @TableField(exist = false)
    @JsonIgnore
    private String beginTime;

    /** 结束时间 */
    @TableField(exist = false)
    @JsonIgnore
    private String endTime;

}
