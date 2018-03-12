package com.mybatis.data.source.domain;

import java.util.List;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:47:42
 */
public interface QueryDelegate {

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
  List<Object> queryForPagedList(String countStatement, String listStatement, Object param, Paginator paginator, DBRoute dr);

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
   * 多库分页查询
   * 
   * @param statementName
   * @param parameterObject
   * @param startResult
   * @param maxResults
   * @param orderByString
   *          排序字段，以逗号相隔，“_”前缀表示倒序，示例：filed1,_field2
   * @param dr
   * @return
   */
  List<Object> queryForMergedList(String statementName, Object parameterObject, Paginator paginator, String orderByString, DBRoute dr);
}
