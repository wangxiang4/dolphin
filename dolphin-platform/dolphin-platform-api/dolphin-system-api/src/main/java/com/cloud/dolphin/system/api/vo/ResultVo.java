package com.cloud.dolphin.system.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *<p>
 * 返回结果
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@Accessors(chain = true)
public class ResultVo<T> implements Serializable {

    /**
     * 查询结果
     */
    private T result;

    /**
     * 扩展信息
     */
    private T extend;

}
