package com.mybatis.data.source.domain;

import java.util.List;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:51:56
 */
public interface EntityDelegate {

  /**
   * 设置路由
   * 
   * @param dbRouteDAOSupport
   */
  void setDbRouteConfig(DBRouteConfig dbRouteConfig);

  int delete(String statementName, Object parameterObject, DBRoute dr);

  int update(String statementName, Object parameterObject, DBRoute dr);

  Object insert(String statementName, Object parameterObject, DBRoute dr);

  @SuppressWarnings("rawtypes")
  void batchInsert(final String statementName, final List memberList, DBRoute dr);

  @SuppressWarnings("rawtypes")
  void batchUpdate(final String statementName, final List memberList, DBRoute dr);
}
