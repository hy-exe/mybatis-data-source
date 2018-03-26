package com.mybatis.data.source.domain;

import java.util.List;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:47:42
 */
public interface HandleDelegate {

	/**
	 * 设置路由
	 * 
	 * @param dbRouteConfig
	 */
	void setDbRouteConfig(DBRouteConfig dbRouteConfig);

	/**
	 * 返回查找对象，将在多库之间分别查询
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return 返回第一个找到的对象
	 */
	Object queryForObject(String statementName, Object parameterObject, DBRoute dr);

	/**
	 * 返回查找对象，将在多库之间分别查询
	 * 
	 * @param statementName
	 * @param dr
	 * @return 返回第一个找到的对象
	 */
	Object queryForObject(String statementName, DBRoute dr);

	/**
	 * 返回记录列表，将在多库之间分别查询
	 * 
	 * @param statementName
	 * @param dr
	 * @return
	 */
	List<Object> queryForList(String statementName, DBRoute dr);

	/**
	 * 返回记录列表，将在多库之间分别查询
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return
	 */
	List<Object> queryForList(String statementName, Object parameterObject, DBRoute dr);

	/**
	 * 返回记录个数，将在多库之间分别查询
	 * 
	 * @param countStatement
	 * @param param
	 * @param dr
	 * @return
	 */
	Integer queryForCount(String countStatement, Object param, DBRoute dr);

	/**
	 * 返回记录个数，将在多库之间分别查询
	 * 
	 * @param countStatement
	 * @param dr
	 * @return
	 */
	Integer queryForCount(String countStatement, DBRoute dr);

	/**
	 * 单库分页查询
	 * 
	 * @param countStatement
	 * @param listStatement
	 * @param param
	 * @param paginator
	 * @param dr
	 * @return
	 */
	List<Object> queryForPagedList(String countStatement, String listStatement, Object param, Paginator paginator,
			DBRoute dr);

	/**
	 * 单库分页查询
	 * 
	 * @param countStatement
	 * @param listStatement
	 * @param param
	 * @param paginator
	 * @param dr
	 * @return
	 */
	List<Object> queryForPagedList(String listStatement, Object param, Paginator paginator, DBRoute dr);

	/**
	 * 单库删除
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return
	 */
	int delete(String statementName, Object parameterObject, DBRoute dr);

	/**
	 * 单库更新
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return
	 */
	int update(String statementName, Object parameterObject, DBRoute dr);

	/**
	 * 单库插入
	 * 
	 * @param statementName
	 * @param parameterObject
	 * @param dr
	 * @return
	 */
	Object insert(String statementName, Object parameterObject, DBRoute dr);

	/**
	 * 单库批量插入
	 * 
	 * @param statementName
	 * @param memberList
	 * @param dr
	 */
	@SuppressWarnings("rawtypes")
	void batchInsert(final String statementName, final List memberList, DBRoute dr);

	/**
	 * 单库批量更新
	 * 
	 * @param statementName
	 * @param memberList
	 * @param dr
	 */
	@SuppressWarnings("rawtypes")
	void batchUpdate(final String statementName, final List memberList, DBRoute dr);
}
