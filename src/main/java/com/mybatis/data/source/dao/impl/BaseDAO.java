package com.mybatis.data.source.dao.impl;

import java.util.List;

import com.mybatis.data.source.domain.DBRoute;
import com.mybatis.data.source.domain.DBRouteConfig;
import com.mybatis.data.source.domain.HandleDelegate;
import com.mybatis.data.source.domain.HandleDelegateImpl;

/**
 * 
 * @author yin.huang
 * @date 2018年1月23日 下午2:44:53
 *
 */
public class BaseDAO {

	private HandleDelegate handleDelegate = new HandleDelegateImpl();

	private DBRoute defaultDB;

	public void setDbRouteConfig(DBRouteConfig dbRouteConfig) {
		this.handleDelegate.setDbRouteConfig(dbRouteConfig);
	}

	public Object insert(String statementName, Object parameterObject) {
		return getHandleDelegate().insert(statementName, parameterObject, getDefaultDB());
	}

	public int update(String statementName, Object parameterObject) {
		return getHandleDelegate().update(statementName, parameterObject, getDefaultDB());
	}

	public int delete(String statementName, Object parameterObject) {
		return getHandleDelegate().delete(statementName, parameterObject, getDefaultDB());
	}

	@SuppressWarnings("rawtypes")
	public void batchInsert(String statementName, List memberList) {
		getHandleDelegate().batchInsert(statementName, memberList, getDefaultDB());
	}

	@SuppressWarnings("rawtypes")
	public void batchUpdate(String statementName, List memberList) {
		getHandleDelegate().batchUpdate(statementName, memberList, getDefaultDB());
	}

	public Object queryForObject(String statementName, Object parameterObject) {
		return getHandleDelegate().queryForObject(statementName, parameterObject, getDefaultDB());
	}

	public Object queryForObject(String statementName) {
		return getHandleDelegate().queryForObject(statementName, getDefaultDB());
	}

	@SuppressWarnings("rawtypes")
	public List queryForList(String statementName) {
		return getHandleDelegate().queryForList(statementName, getDefaultDB());
	}

	@SuppressWarnings("rawtypes")
	public List queryForList(String statementName, Object parameterObject) {
		return getHandleDelegate().queryForList(statementName, parameterObject, getDefaultDB());
	}

	public Integer queryForCount(String countStatement, Object param) {
		return getHandleDelegate().queryForCount(countStatement, param, getDefaultDB());
	}

	public Integer queryForCount(String countStatement) {
		return getHandleDelegate().queryForCount(countStatement, getDefaultDB());
	}

	public HandleDelegate getHandleDelegate() {
		return handleDelegate;
	}

	public void setHandleDelegate(HandleDelegate handleDelegate) {
		this.handleDelegate = handleDelegate;
	}

	public DBRoute getDefaultDB() {
		return defaultDB;
	}

	public void setDefaultDB(DBRoute defaultDB) {
		this.defaultDB = defaultDB;
	}
}
