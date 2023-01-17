package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory storage for authority rules.
 *
 * @author Eric Zhao
 * @since 0.2.1
 */
@Component
public class InMemAuthorityRuleStore extends InMemoryRuleRepositoryAdapter<AuthorityRuleEntity> {

	private static AtomicLong ids = new AtomicLong(0);

	@Override
	protected long nextId() {
		return ids.incrementAndGet();
	}

}
