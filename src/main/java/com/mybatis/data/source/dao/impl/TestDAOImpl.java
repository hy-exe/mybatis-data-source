/**
 * 
 */
package com.mybatis.data.source.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mybatis.data.source.dao.ITestDAO;

/**
 * 
 * @author Administrator
 *
 */
public class TestDAOImpl extends BaseDAO implements ITestDAO {

	@Override
	public Integer getInfoEntityList(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", param);
		return super.queryForCount("TEST_INFO.SELECT_TEST_INFO", map);
	}

}
