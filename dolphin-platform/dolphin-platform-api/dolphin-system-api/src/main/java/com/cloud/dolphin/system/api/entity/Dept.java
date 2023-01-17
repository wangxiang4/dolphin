package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 *<p>
 * 部门表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_dept", excludeProperty = { "tenantId" })
public class Dept extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId
    private String deptId;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 父部门id
     */
    private String parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Dept> children = new ArrayList();
}
