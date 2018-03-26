package com.mybatis.data.source.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:47:08
 */
public class DBRouteConfig {

	// 配置所有数据库节点
	private List<String> allNodeNameList = new ArrayList<String>();

	// 路由策略默认返回的数据库节点列表
	private List<String> defaultNodeNameList = new ArrayList<String>();

	private Map<String, SqlSessionFactory> sqlMapList;

	private Map<String, SqlSession> sqlMapTemplateList = new HashMap<String, SqlSession>();

	/**
	 * 根据给定的路由策略返回对应的数据库节点列表
	 * 
	 * @param dbRoute
	 * @return
	 */
	public List<String> routingDB(DBRoute dbRoute) {
		if (null == dbRoute) {
			return new ArrayList<String>();
		}

		List<String> nodeNameList = new ArrayList<String>();

		String dbName = dbRoute.getDbName();

		if (dbName != null) {
			if (dbName.indexOf(",") != -1) {
				StringTokenizer st = new StringTokenizer(dbName, ",");

				while (st.hasMoreTokens()) {
					String dbxid = st.nextToken();
					dbxid = dbxid.trim();

					if (allNodeNameList.contains(dbxid)) {
						nodeNameList.add(dbxid);
					}
				}

				return nodeNameList.isEmpty() ? defaultNodeNameList : nodeNameList;
			} else if (allNodeNameList.contains(dbName)) {
				nodeNameList.add(dbName);
				return nodeNameList.isEmpty() ? defaultNodeNameList : nodeNameList;
			}
		}

		return nodeNameList.isEmpty() ? defaultNodeNameList : nodeNameList;
	}

	/**
	 * 根据给定的路由策略及查询SQL编号返回对应的数据库列表
	 * 
	 * @param dr
	 * @param sqlId
	 * @return
	 */
	public Map<String, SqlSession> getSqlMapTemplates(DBRoute dr) {
		List<String> dbNameList = this.routingDB(dr);
		if (null == dbNameList || dbNameList.isEmpty()) {
			throw new RuntimeException("No database found, please confirm the parameters. DBRoute=[" + dr + "]");
		}

		Map<String, SqlSession> retDbList = new HashMap<String, SqlSession>();

		for (int i = 0; i < dbNameList.size(); i++) {
			String dbName = dbNameList.get(i);
			SqlSession o = sqlMapTemplateList.get(dbName);
			if (o != null) {
				retDbList.put(dbName, o);
			}
		}

		return retDbList;
	}

	public Map<String, SqlSessionFactory> getSqlMapList() {
		return sqlMapList;
	}

	public void setSqlMapList(Map<String, SqlSessionFactory> sqlMapList) {
		this.sqlMapList = sqlMapList;

		// 创建SqlMapTemplate列表
		for (Iterator<String> it = sqlMapList.keySet().iterator(); it.hasNext();) {
			String dbKey = it.next();
			SqlSessionFactory sqlMapClient = (SqlSessionFactory) sqlMapList.get(dbKey);
			SqlSession sqlMT = sqlMapClient.openSession();
			sqlMapTemplateList.put(dbKey, sqlMT);
		}

		this.allNodeNameList.addAll(sqlMapList.keySet());
	}

	public Map<String, SqlSession> getSqlMapTemplateList() {
		return sqlMapTemplateList;
	}

	public void setSqlMapTemplateList(Map<String, SqlSession> sqlMapTemplateList) {
		this.sqlMapTemplateList = sqlMapTemplateList;
	}

	public void setDefaultNodeNameList(List<String> defaultNodeNameList) {
		this.defaultNodeNameList = defaultNodeNameList;
	}
}
