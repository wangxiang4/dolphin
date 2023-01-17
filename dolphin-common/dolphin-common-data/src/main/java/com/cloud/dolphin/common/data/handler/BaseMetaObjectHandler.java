package com.cloud.dolphin.common.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *<p>
 * 公共字段自动填充
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
public class BaseMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }

        if (Optional.ofNullable(getUser()).isPresent()) {
            Object createById = getFieldValByName("createById", metaObject);
            if (createById == null) {
                setFieldValByName("createById", getUser().getId(), metaObject);
            }

            Object createByName = getFieldValByName("createByName", metaObject);
            if (createByName == null) {
                setFieldValByName("createByName", getUser().getUsername(), metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldValue = getFieldValByName("updateTime", metaObject);
        if (fieldValue == null) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        if (Optional.ofNullable(getUser()).isPresent()) {
            Object updateById = getFieldValByName("updateById", metaObject);
            if (updateById == null) {
                setFieldValByName("updateById", getUser().getId(), metaObject);
            }

            Object updateByName = getFieldValByName("updateByName", metaObject);
            if (updateByName == null) {
                setFieldValByName("updateByName", getUser().getUsername(), metaObject);
            }
        }
    }

    /**
     * 获取用户
     */
    protected DolphinUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Optional.ofNullable(authentication).isPresent()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof DolphinUser) {
                return (DolphinUser) principal;
            }
        }
        return null;
    }
}
