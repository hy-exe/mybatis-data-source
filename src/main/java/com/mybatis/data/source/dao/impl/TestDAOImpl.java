/**
 * 
 */
package com.mybatis.data.source.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mybatis.data.source.dao.ITestDAO;

/**
 * @author qingwei.zhou
 * @version 2016-6-29 下午3:04:12
 */
public class TestDAOImpl extends BaseDAO implements ITestDAO {

	@Override
	public Integer getBscInfoEntityListByLac(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param", param);
		return super.queryForCount("BSC_INFO.SELECT_BSC_INFO_BY_LAC", map);
	}

}
