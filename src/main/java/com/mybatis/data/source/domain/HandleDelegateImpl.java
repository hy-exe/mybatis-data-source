package com.mybatis.data.source.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mybatis.data.source.utils.MapUtil;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:51:03
 */
public class HandleDelegateImpl implements HandleDelegate {

	private static final Logger logger = LoggerFactory.getLogger(HandleDelegateImpl.class);

	private DBRouteConfig dbRouteConfig;

	@Override
	public Integer queryForCount(String countStatement, Object param, DBRoute dr) {

		Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, countStatement);

		int totalCount = 0;

		for (Map.Entry<String, SqlSession> e : dbMap.entrySet()) {

			String dbName = e.getKey();

			SqlSession st = dbMap.get(dbName);

			long startTime = System.currentTimeMillis();

			Object returnObject = st.selectOne(countStatement, param);

			long endTime = System.currentTimeMillis();

			logRunTime(countStatement, dbName, endTime - startTime);

			totalCount += ((Integer) returnObject).intValue();
		}

		return totalCount;
	}

	@Override
	public Integer queryForCount(String countStatement, DBRoute dr) {
		return queryForCount(countStatement, null, dr);
	}

	@Override
	public List<Object> queryForList(String statementName, DBRoute dr) {
		return queryForList(statementName, null, dr);
	}

	@Override
	public List<Object> queryForList(String statementName, Object parameterObject, DBRoute dr) {

		Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, statementName);

		List<Object> resultList = new ArrayList<Object>();

		for (Map.Entry<String, SqlSession> e : dbMap.entrySet()) {

			String dbName = e.getKey();

			SqlSession st = dbMap.get(dbName);

			long startTime = System.currentTimeMillis();

			List<Object> list = null;
			if (parameterObject != null) {
				list = st.selectList(statementName, parameterObject);
			} else {
				list = st.selectList(statementName);
			}

			long endTime = System.currentTimeMillis();

			logRunTime(statementName, dbName, endTime - startTime);

			resultList.addAll(list);
		}

		return resultList;
	}

	@Override
	public Object queryForObject(String statementName, Object parameterObject, DBRoute dr) {

		Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, statementName);

		for (Map.Entry<String, SqlSession> e : dbMap.entrySet()) {

			String dbName = e.getKey();

			SqlSession st = dbMap.get(dbName);

			long startTime = System.currentTimeMillis();

			Object returnObject;
			if (parameterObject != null) {
				returnObject = st.selectOne(statementName, parameterObject);
			} else {
				returnObject = st.selectOne(statementName);
			}

			long endTime = System.currentTimeMillis();

			logRunTime(statementName, dbName, endTime - startTime);

			if (returnObject != null) {
				return returnObject;
			}
		}

		return null;
	}

	@Override
	public Object queryForObject(String statementName, DBRoute dr) {
		return queryForObject(statementName, null, dr);
	}

	@Override
	public List<Object> queryForPagedList(String countStatement, String listStatement, Object param,
			Paginator paginator, DBRoute dr) {
		Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, countStatement);

		if (dbMap.size() != 1) {
			throw new RuntimeException("more than 1 database found, please confirm the parameters. DBRoute=[" + dr
					+ "], countStatement=[" + countStatement + "], listStatement=[" + listStatement + "]");
		}

		Integer totalItem = queryForCount(countStatement, param, dr);
		int total = totalItem.intValue();

		paginator.setItems(total);

		List<Object> resultList = new ArrayList<Object>();

		if (total > 0) {
			resultList = queryForPagedList(listStatement, param, paginator, dr);
		}

		return resultList;
	}

	/**
	 * 查询记录集
	 * 
	 * @param listStatement
	 *            查询SQL ID
	 * @param param
	 *            查询参数
	 * @param paginator
	 *            分页器
	 * @param dr
	 *            数据库路由
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> queryForPagedList(String listStatement, Object param, Paginator paginator, DBRoute dr) {
		Map paramsMap = MapUtil.objectToMap(param);

		paramsMap.put("_paging_", "y");
		paramsMap.put("_paging_index_ge_", paginator.getBeginIndex());
		paramsMap.put("_paging_index_lt_", paginator.getEndIndex());
		paramsMap.put("_paging_size_", paginator.getItemsPerPage());

		List<Object> result = queryForList(listStatement, paramsMap, dr);

		return result;
	}

	@Override
	public int delete(String statementName, Object parameterObject, DBRoute dr) {
		Map.Entry<String, SqlSession> e = getSqlMapTemplate(dr, statementName);

		String dbName = e.getKey();
		SqlSession st = e.getValue();

		long startTime = System.currentTimeMillis();
		int affectSize = st.delete(statementName, parameterObject);
		long endTime = System.currentTimeMillis();

		logRunTime(statementName, dbName, endTime - startTime);

		return affectSize;
	}

	@Override
	public int update(String statementName, Object parameterObject, DBRoute dr) {
		Map.Entry<String, SqlSession> e = getSqlMapTemplate(dr, statementName);

		String dbName = e.getKey();
		SqlSession st = e.getValue();
		long startTime = System.currentTimeMillis();
		int affectSize = st.update(statementName, parameterObject);
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, dbName, endTime - startTime);

		return affectSize;
	}

	@Override
	public Object insert(String statementName, Object parameterObject, DBRoute dr) {
		Map.Entry<String, SqlSession> e = getSqlMapTemplate(dr, statementName);

		String dbName = e.getKey();
		SqlSession st = e.getValue();

		long startTime = System.currentTimeMillis();
		Object returnObject = st.insert(statementName, parameterObject);
		long endTime = System.currentTimeMillis();
		logRunTime(statementName, dbName, endTime - startTime);

		return returnObject;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void batchInsert(String statementName, List memberList, DBRoute dr) {
		Map.Entry<String, SqlSession> e = getSqlMapTemplate(dr, statementName);

		String dbName = e.getKey();
		SqlSession st = e.getValue();

		long startTime = System.currentTimeMillis();

		for (Object tObject : memberList) {
			st.insert(statementName, tObject);
		}

		long endTime = System.currentTimeMillis();
		logRunTime(statementName, dbName, endTime - startTime);

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void batchUpdate(String statementName, List memberList, DBRoute dr) {
		Map.Entry<String, SqlSession> e = getSqlMapTemplate(dr, statementName);

		String dbName = e.getKey();
		SqlSession st = e.getValue();

		long startTime = System.currentTimeMillis();

		for (Object tObject : memberList) {
			st.update(statementName, tObject);
		}

		long endTime = System.currentTimeMillis();
		logRunTime(statementName, dbName, endTime - startTime);

	}

	@Override
	public void setDbRouteConfig(DBRouteConfig dbRouteConfig) {
		this.dbRouteConfig = dbRouteConfig;
	}

	private void logRunTime(String statementName, String dbName, long runTime) {
		if (logger.isDebugEnabled()) {
			logger.debug("Sql " + statementName + " executed on " + dbName + " databases. Run time estimated: "
					+ runTime + "ms");
		}
	}

	protected Map.Entry<String, SqlSession> getSqlMapTemplate(DBRoute dr, String statementName) {
		Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, statementName);

		if (dbMap.isEmpty()) {
			throw new RuntimeException("no database found, please confirm the parameters. DBRoute=[" + dr
					+ "], statement=[" + statementName + "]");
		}

		if (dbMap.size() != 1) {
			throw new RuntimeException("more than 1 database found, please confirm the parameters. DBRoute=[" + dr
					+ "], statement=[" + statementName + "]");
		}

		return dbMap.entrySet().iterator().next();
	}

}
