package com.alibaba.csp.sentinel.dashboard.repository.rule;

import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;

import java.util.List;

/**
 * Interface to store and find rules.
 *
 * @author leyou
 */
public interface RuleRepository<T, ID> {

	/**
	 * Save one.
	 * @param entity
	 * @return
	 */
	T save(T entity);

	/**
	 * Save all.
	 * @param rules
	 * @return rules saved.
	 */
	List<T> saveAll(List<T> rules);

	/**
	 * Delete by id
	 * @param id
	 * @return entity deleted
	 */
	T delete(ID id);

	/**
	 * Find by id.
	 * @param id
	 * @return
	 */
	T findById(ID id);

	/**
	 * Find all by machine.
	 * @param machineInfo
	 * @return
	 */
	List<T> findAllByMachine(MachineInfo machineInfo);

	/**
	 * Find all by application.
	 * @param appName valid app name
	 * @return all rules of the application
	 * @since 1.4.0
	 */
	List<T> findAllByApp(String appName);

	/// **
	// * Find all by app and enable switch.
	// * @param app
	// * @param enable
	// * @return
	// */
	// List<T> findAllByAppAndEnable(String app, boolean enable);

}
