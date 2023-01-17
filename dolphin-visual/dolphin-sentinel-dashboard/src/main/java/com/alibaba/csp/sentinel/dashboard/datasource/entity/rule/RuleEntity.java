package com.alibaba.csp.sentinel.dashboard.datasource.entity.rule;

import com.alibaba.csp.sentinel.slots.block.Rule;

import java.util.Date;

/**
 * @author leyou
 */
public interface RuleEntity {

	Long getId();

	void setId(Long id);

	String getApp();

	String getIp();

	Integer getPort();

	Date getGmtCreate();

	Rule toRule();

}
