package com.cloud.dolphin.common.core.util;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *<p>
 * Jasypt加解密单元测试
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public class JasyptUtil {

    public static void main(String[] args) {
        testEnvironmentProperties();
    }

    public static void testEnvironmentProperties() {
        System.setProperty("jasypt.encryptor.password", "dolphin");

        PasswordEncoder ENCODER = new BCryptPasswordEncoder();

        System.out.println(ENCODER.encode("123456"));

        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        //加密方法
        System.out.println(stringEncryptor.encrypt("dolphin"));

        //解密方法
        System.out.println(stringEncryptor.decrypt("dtjFNJ20ocy3dpBwUqSbfA=="));
    }

}
