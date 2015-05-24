/**  
 * @Title: AdministrativeDivision.java 
 * @Package stats 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月23日 下午4:09:49 
 * @version V1.0  
 */
package com.hb.util.db.stats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hb.models.StudentInfo;
import com.hb.util.commonUtil.StringUtil;
import com.hb.util.db.JdbcUtils;
import com.hb.util.db.dao.Command;
import com.hb.util.db.dao.SQL;
import com.hb.util.db.dao.SQL.Insert;
import com.hb.util.regex.ZZUtil;

/**
 * @ClassName: AdministrativeDivision
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author huangbing
 * @date 2015年5月23日 下午4:09:49
 */

public class AdministrativeDivision {
	private static final String separator = ",";

	public AdministrativeDivision() {
	}

	public List<Xyx_xzqh> getXyx_xzqhList(JdbcUtils jdbcUtils) {
		String sql = "select * from xyx_xzqh ";
		try {
			List<Xyx_xzqh> list = jdbcUtils.findMoreRefResult(sql, null,
					Xyx_xzqh.class);
			System.out.println(list);
			
			return list;
		} catch (Exception e) {
			jdbcUtils.releaseConn();
		}

		return null;
	}

	public List<Stats> doStats(List<Xyx_xzqh> list) {
		Map<String, Stats> provinceMap = new HashMap<String, Stats>();

		Map<String, Stats> cityMap = new HashMap<String, Stats>();
		
		Map<String, Stats> countyMap = new HashMap<String, Stats>();
		
		List<String> deleteList = new ArrayList<String>();
//		deleteList.add("市辖区");
//		deleteList.add("县");
//		deleteList.add("城区");
//		deleteList.add("郊区");

		if (list == null) {
			return null;
		}

		for (Xyx_xzqh xyx : list)
		{
			Stats st = new Stats();
			st.setCountryCode("CN");
			st.setCountryName("中国");
			String code = xyx.getCode().replaceAll(" ", "");
			
			String name = xyx.getName();
			if("市辖区".equals(name) || "县".equals(name) || "城区".equals(name) || "郊区".equals(name))
			{
				deleteList.add(code);
			}
			
			if (code.endsWith("0000")) //province
			{
				st.setProvinceCode(code);
				st.setProvinceName(name);
				provinceMap.put(code, st);
			} 
			else  if (code.endsWith("00")) //city
			{
				String headPro = code.substring(0, 2) + "0000";
				Stats proSt = provinceMap.get(headPro);
				st.setProvinceCode(proSt.getProvinceCode());
				st.setProvinceName(proSt.getProvinceName());
				
				if("市辖区".equals(name) || "县".equals(name) || "城区".equals(name) || "郊区".equals(name))
				{
					name = proSt.getProvinceName();
				}

				st.setCityCode(code);
				st.setCityName(name);
				cityMap.put(code, st);
			}
		   else 
		   {
			   String headCity = code.substring(0, 4) + "00";
				Stats citySt = cityMap.get(headCity);
				st.setProvinceCode(citySt.getProvinceCode());
				st.setProvinceName(citySt.getProvinceName());

				st.setCityCode(citySt.getCityCode());
				st.setCityName(citySt.getCityName());
				
				if("市辖区".equals(name) || "县".equals(name) || "城区".equals(name) || "郊区".equals(name))
				{
					name = citySt.getCityName();
				}
				st.setCountyCode(code);
				st.setCountyName(name);
				countyMap.put(code, st);
			}
		}

		List<Stats> statsList = new ArrayList<Stats>();
		for (Entry<String, Stats> entry : provinceMap.entrySet()) {
			// System.out.println("key= " + entry.getKey() + " and value= " +
			// entry.getValue());
			statsList.add(entry.getValue());
		}

		for (Entry<String, Stats> entry : cityMap.entrySet()) {
			// System.out.println("key= " + entry.getKey() + " and value= " +
			// entry.getValue());
			statsList.add(entry.getValue());
		}
		
		for (Entry<String, Stats> entry : countyMap.entrySet()) {
			// System.out.println("key= " + entry.getKey() + " and value= " +
			// entry.getValue());
			statsList.add(entry.getValue());
		}

		return statsList;

	}

	public void insertDB(List<Stats> statsList, JdbcUtils jdbcUtils) {
		if (statsList == null) {
			return;
		}
		for (Stats st : statsList) {
			Insert sqltemp = SQL.Insert("stats")
					.Values("id", StringUtil.getUUID())
					.Values("countryCode", st.getCountryCode())
					.Values("countryName", st.getCountryName())
					.Values("provinceCode", st.getProvinceCode())
					.Values("provinceName", st.getProvinceName())
					.Values("cityCode", st.getCityCode())
					.Values("cityName", st.getCityName())
					.Values("countyCode", st.getCountyCode())
					.Values("countyName", st.getCountyName())
					.Values("streetCode", st.getStreetCode())
					.Values("streetName", st.getStreetName());

			Command command = sqltemp.toCommand();
			System.out.println(command.getStatement());
			System.out.println(command.getParams());

			// String sql = "insert into userinfo(username,pswd) values(?,?)";
			// List<Object> params = new ArrayList<Object>();
			// params.add("rose");
			// params.add("123");
			try {
				// boolean flag = jdbcUtils.updateByPreparedStatement(sql,
				// params);
				boolean flag = jdbcUtils.insertByPreparedStatement(
						command.getStatement(), command.getParams());
				System.out.println(flag);
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}

	}

	public static void main(String[] s) throws SQLException {
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		
		AdministrativeDivision ad = new AdministrativeDivision();
		jdbcUtils.deleteTable("delete from stats");
		ad.insertDB(ad.doStats(ad.getXyx_xzqhList(jdbcUtils)),jdbcUtils);
		
		jdbcUtils.releaseConn();
	}
}
