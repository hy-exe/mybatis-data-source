package com.mybatis.data.source.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午3:06:34
 */
public class BaseDO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7725115871533796806L;

  private DBRoute           dbRoute;

  public DBRoute getDbRoute() {
    return dbRoute;
  }

  public void setDbRoute(DBRoute dbRoute) {
    this.dbRoute = dbRoute;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
