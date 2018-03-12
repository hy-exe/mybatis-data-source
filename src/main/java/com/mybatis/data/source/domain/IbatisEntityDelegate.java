package com.mybatis.data.source.domain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:52:27
 */
public class IbatisEntityDelegate implements EntityDelegate {

  private static final Logger logger = LoggerFactory.getLogger(IbatisEntityDelegate.class);

  private DBRouteConfig       dbRouteConfig;

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
  public void batchInsert(final String statementName, final List memberList, DBRoute dr) {
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
  public void batchUpdate(final String statementName, final List memberList, DBRoute dr) {
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

  protected Map.Entry<String, SqlSession> getSqlMapTemplate(DBRoute dr, String statementName) {
    Map<String, SqlSession> dbMap = dbRouteConfig.getSqlMapTemplates(dr, statementName);

    if (dbMap.isEmpty()) {
      throw new RuntimeException("no database found, please confirm the parameters. DBRoute=[" + dr + "], statement=[" + statementName + "]");
    }

    if (dbMap.size() != 1) {
      throw new RuntimeException("more than 1 database found, please confirm the parameters. DBRoute=[" + dr + "], statement=[" + statementName + "]");
    }

    return dbMap.entrySet().iterator().next();
  }

  public DBRouteConfig getDbRouteConfig() {
    return dbRouteConfig;
  }

  public void setDbRouteConfig(DBRouteConfig dbRouteConfig) {
    this.dbRouteConfig = dbRouteConfig;
  }

  private void logRunTime(String statementName, String dbName, long runTime) {
    if (logger.isDebugEnabled()) {
      logger.debug("Sql " + statementName + " executed on " + dbName + " databases. Run time estimated: " + runTime + "ms");
    }
  }

}
