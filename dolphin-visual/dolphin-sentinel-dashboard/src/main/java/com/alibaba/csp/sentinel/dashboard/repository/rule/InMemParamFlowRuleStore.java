package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowClusterConfig;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Eric Zhao
 * @since 0.2.1
 */
@Component
public class InMemParamFlowRuleStore extends InMemoryRuleRepositoryAdapter<ParamFlowRuleEntity> {

	private static AtomicLong ids = new AtomicLong(0);

	@Override
	protected long nextId() {
		return ids.incrementAndGet();
	}

	@Override
	protected ParamFlowRuleEntity preProcess(ParamFlowRuleEntity entity) {
		if (entity != null && entity.isClusterMode()) {
			ParamFlowClusterConfig config = entity.getClusterConfig();
			if (config == null) {
				config = new ParamFlowClusterConfig();
			}
			// Set cluster rule id.
			config.setFlowId(entity.getId());
		}
		return entity;
	}

}
