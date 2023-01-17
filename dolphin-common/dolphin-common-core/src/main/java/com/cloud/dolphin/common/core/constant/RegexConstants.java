package com.cloud.dolphin.common.core.constant;

/**
 *<p>
 * 正则表达式常量
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/4/18
 */
public interface RegexConstants {

    /**
     * 匹配网址正则表达式
     */
    String MATCHER_URL = "(((^https?:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+(?::\\d+)?|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)$";

}
