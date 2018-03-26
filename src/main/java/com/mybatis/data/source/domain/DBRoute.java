package com.mybatis.data.source.domain;

import java.io.Serializable;

/**
 * @author yin.huang
 * @date 2018年1月23日 下午2:48:54
 */
public class DBRoute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6594251491917354115L;

	private String dbName = null;

	public DBRoute() {
	}

	public DBRoute(String dbName) {
		setDbName(dbName);
	}

	public static DBRoute getRoute(String dbName) {
		DBRoute back = new DBRoute();
		back.setDbName(dbName);
		return back;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName
	 *            the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String toString() {
		String str = "";

		if (dbName != null) {
			str += String.format("[{}]", dbName);
			return str;
		}

		return null;
	}
}
