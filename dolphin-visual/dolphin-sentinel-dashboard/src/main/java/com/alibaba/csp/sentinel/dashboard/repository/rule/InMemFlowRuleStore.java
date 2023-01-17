package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.slots.block.flow.ClusterFlowConfig;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Store {@link FlowRuleEntity} in memory.
 *
 * @author leyou
 */
@Component
public class InMemFlowRuleStore extends InMemoryRuleRepositoryAdapter<FlowRuleEntity> {

	private static AtomicLong ids = new AtomicLong(0);

	@Override
	protected long nextId() {
		return ids.incrementAndGet();
	}

	@Override
	protected FlowRuleEntity preProcess(FlowRuleEntity entity) {
		if (entity != null && entity.isClusterMode()) {
			ClusterFlowConfig config = entity.getClusterConfig();
			if (config == null) {
				config = new ClusterFlowConfig();
				entity.setClusterConfig(config);
			}
			// Set cluster rule id.
			config.setFlowId(entity.getId());
		}
		return entity;
	}

}
