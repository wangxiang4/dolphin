package com.cloud.dolphin.system.api.dto;
import lombok.Data;

/**
 *<p>
 * 前端Tree选中类型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/6/6
 */
@Data
public class CheckedInfo {

    private String[] checkedKeys = new String[0];

    private String[] halfCheckedKeys = new String[0];

}
