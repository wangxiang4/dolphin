package com.cloud.dolphin.common.security.xss;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.cloud.dolphin.common.core.util.HTMLFilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *<p>
 * XSS、sql过滤处理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static String[] SQL_KEYWORDS = {"master", "truncate", "insert", "select"
            , "delete", "update", "declare", "alter", "drop", "sleep"};
    //sql 替换字符
    private static String REPLACE_STR = "";

    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                // 防xss攻击和过滤前后空格
                escapseValues[i] = HtmlUtil.filter(values[i]).trim();
                //防sql注入
                escapseValues[i] = cleanSqlKeyWords(escapseValues[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    private String cleanSqlKeyWords(String value) {
        String paramValue = value;
        for (String keyword : SQL_KEYWORDS) {
            if (paramValue.length() > keyword.length() + 4
                    && (paramValue.contains(" " + keyword) || paramValue.contains(keyword + " ") || paramValue.contains(" " + keyword + " "))) {
                paramValue = StrUtil.replace(paramValue, keyword, REPLACE_STR);
            }
        }
        return paramValue;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        if (!isJsonRequest()) {
            return super.getInputStream();
        }

        // 为空，直接返回
        String json = IoUtil.read(super.getInputStream(), "utf-8");
        if (StrUtil.isEmpty(json)) {
            return super.getInputStream();
        }

        // xss过滤
        json = new HTMLFilterUtil().filter(json).trim();
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() throws IOException {
                return bis.read();
            }
        };
    }

    /**
     * 是否是Json请求
     */
    public boolean isJsonRequest() {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(header)
                || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(header);
    }
}
