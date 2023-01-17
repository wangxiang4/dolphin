package com.cloud.dolphin.common.datasource.config;

import com.baomidou.dynamic.datasource.provider.AbstractJdbcDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.cloud.dolphin.common.datasource.support.DataSourceConstants;
import org.jasypt.encryption.StringEncryptor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *<p>
 * 初始化多数据源
 * 从数据源中获取 配置信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
public class JdbcDynamicDataSourceProvider extends AbstractJdbcDataSourceProvider {

	private final DataSourceProperties properties;

	private final StringEncryptor stringEncryptor;

	public JdbcDynamicDataSourceProvider(StringEncryptor stringEncryptor, DataSourceProperties properties) {
		super(properties.getDriverClassName(), properties.getUrl(), properties.getUsername(), properties.getPassword());
		this.stringEncryptor = stringEncryptor;
		this.properties = properties;
	}

	/**
	 * 执行语句获得数据源参数
	 * @param statement 语句
	 * @return 数据源参数
	 * @throws SQLException sql异常
	 */
	@Override
	protected Map<String, DataSourceProperty> executeStmt(Statement statement) throws SQLException {
		ResultSet rs = statement.executeQuery(properties.getQueryDsSql());

		Map<String, DataSourceProperty> map = new HashMap<>(8);
		while (rs.next()) {
			String name = rs.getString(DataSourceConstants.DS_NAME);
			String username = rs.getString(DataSourceConstants.DS_USER_NAME);
			String password = rs.getString(DataSourceConstants.DS_USER_PWD);
			String url = rs.getString(DataSourceConstants.DS_JDBC_URL);
			DataSourceProperty property = new DataSourceProperty();
			property.setUsername(username);
			property.setLazy(true);
			property.setPassword(stringEncryptor.decrypt(password));
			property.setUrl(url);
			map.put(name, property);
		}

		// 添加默认主数据源
		DataSourceProperty property = new DataSourceProperty();
		property.setUsername(properties.getUsername());
		property.setPassword(properties.getPassword());
		property.setUrl(properties.getUrl());
		property.setLazy(true);
		map.put(DataSourceConstants.DS_MASTER, property);
		return map;
	}

}
