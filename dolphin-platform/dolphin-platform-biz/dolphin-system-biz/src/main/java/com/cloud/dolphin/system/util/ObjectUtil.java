package com.cloud.dolphin.system.util;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class ObjectUtil {

    /**
     * 判断对象的多个属性是否为空
     *
     * @param obj 对象
     * @param fieldNames 属性名数组
     * @return true，所有属性其中一个不为空 false，所有属性都为空
     * @throws IllegalAccessException
     */
    public static boolean isAnyFieldsNotNull(Object obj, String[] fieldNames) throws IllegalAccessException {
        for (String fieldName : fieldNames) {
            Field field = getField(obj, fieldName);
            field.setAccessible(true);
            if (field != null) {
                Object fieldValue = field.get(obj);
                if (fieldValue != null && StrUtil.isNotBlank(fieldValue.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 根据属性名获取对象的属性值
     *
     * @param obj 对象
     * @param fieldName 属性名
     * @return Field对象
     */
    private static Field getField(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

}
