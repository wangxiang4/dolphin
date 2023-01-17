package com.cloud.dolphin.system.util;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 *<p>
 * 菜单路由工具
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/2
 */
@UtilityClass
public class MenuRouteUtil {

    /**
     * 处理重定向路由路径拼接
     * @Param args 多个路径
     * @return String 返回拼接后路径
     */
    public static String routePathJoin(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            if (StrUtil.isNotBlank(arg)) {
                // 清除前置斜杆
                if(arg.startsWith("/")) {
                    arg = arg.substring(1);
                }
                // 检查拼接后置斜杠
                if(arg.endsWith("/")) {
                    sb.append(arg);
                } else {
                    sb.append(arg.concat("/"));
                }
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}
