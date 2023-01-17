package com.cloud.dolphin.system.api.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *<p>
 * 用户信息表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private String id;

    /**
     * 用户名
     */
    @ExcelProperty("用户名")
    private String userName;

    /**
     * 昵称
     */
    @ExcelProperty("昵称")
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 机构ID
     */
    private String deptId;

    /**
     * 机构名称
     */
    @ExcelProperty("机构名称")
    private String deptName;

    /**
     * 用户邮箱
     */
    @ExcelProperty("邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ExcelProperty("手机号码")
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @ExcelProperty("性别")
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private LocalDateTime loginTime;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 角色ID集合
     */
    @TableField(exist = false)
    private String[] roleIds;

    /**
     * 权限标识集合
     */
    @TableField(exist = false)
    private String[] permissions;

    /**
     * 新密码
     */
    @TableField(exist = false)
    private String newPassword;
}
