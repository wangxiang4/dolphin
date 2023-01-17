package com.cloud.dolphin.common.core.util;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import lombok.experimental.UtilityClass;

/**
 *<p>
 * 基础工具类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/21
 */
@UtilityClass
public class BaseUtil {

    /**
     * 雪花算法生成全局ID
     * @Param
     * @return
     */
    public Long snowflakeId () {
        SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
        return snowflakeGenerator.next();
    }

}
