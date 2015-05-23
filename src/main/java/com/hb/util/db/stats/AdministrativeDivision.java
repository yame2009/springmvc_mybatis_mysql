/**  
 * @Title: AdministrativeDivision.java 
 * @Package stats 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月23日 下午4:09:49 
 * @version V1.0  
 */ 
package com.hb.util.db.stats;

import java.util.List;

import com.hb.models.StudentInfo;
import com.hb.util.db.JdbcUtils;

/** 
 * @ClassName: AdministrativeDivision 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author huangbing 
 * @date 2015年5月23日 下午4:09:49  
 */

public class AdministrativeDivision
{
    public static void main(String[] s)
    {
        JdbcUtils jdbcUtils = new JdbcUtils();
        jdbcUtils.getConnection();
        // String sql = "insert into userinfo(username,pswd) values(?,?)";
        // List<Object> params = new ArrayList<Object>();
        // params.add("rose");
        // params.add("123");
        // try {
        // boolean flag = jdbcUtils.updateByPreparedStatement(sql, params);
        // System.out.println(flag);
        // } catch (SQLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        String sql = "select * from xyx_xzqh ";
//      List<Object> params = new ArrayList<Object>();
//      params.add(1);
        try {
            List<Stats> list = jdbcUtils.findMoreRefResult(sql,
                    null, Stats.class);
            System.out.println(list);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            jdbcUtils.releaseConn();
        }
    }
}
