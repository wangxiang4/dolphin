package com.cloud.dolphin.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *<p>
 * 数据类型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@Getter
@AllArgsConstructor
public enum DataTypeEnum {
    /**
     * mysql
     */
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver"),

    /**
     * sqlserver
     */
    SQLSERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * oracle
     */
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver"),

    /**
     * Postgresql
     */
    POSTGRESQL("postgresql", "org.postgresql.Driver"),

    /**
     * sqlite
     */
    SQLITE("sqlite", "org.sqlite.JDBC");

    /**
     * 类型
     */
    private final String type;

    /**
     * 驱动
     */
    private final String driverClassName;

}
