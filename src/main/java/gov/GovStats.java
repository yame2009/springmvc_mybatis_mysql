/**  
 * @Title: GovStats.java 
 * @Package gov 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年5月19日 上午9:44:51 
 * @version V1.0  
 */ 
package gov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
/**
 * 最新县及县以上行政区划代码（截止2014年10月31日）
 * http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html
 * http://www.oschina.net/code/snippet_735545_47887 代码引用页面
 * 
 * 国家统计局行政区划代码：http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/
        MySQL+jsoup+已经快被遗忘的纯JDBC来存数据
 * @author dindinmail
 * 
 */
public class GovStats {
    private static final Logger log = Logger.getLogger("xzqhdm");
    private static final String separator = ",";
 
    public static void main(String[] args) throws Exception {
        List<String> list = getData();
        for (String m : list) {
            System.out.println(m);
        }
        saveData(list);
    }
 
    private static List<String> getData() throws Exception {
        List<String> retList = new ArrayList<String>();
 
        Document doc = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201504/t20150415_712722.html").get();
        Elements ps = doc.select(".TRS_PreAppend p");
        for (Element e : ps) {
            Elements spans = e.select(">span");
            Element firstE = spans.first();
            Element secondE = spans.get(1);
            String key = firstE.text().trim().replace(" ", "").replace("　", "");
            if (key.endsWith("0000")) {
                key = key.substring(0, 2);
            } else if (key.endsWith("00")) {
                key = key.substring(0, 4);
            }
            retList.add(key + separator + secondE.text().trim().replace(" ", "").replace("　", ""));
        }
        return retList;
    }
 
    private static void saveData(List<String> list) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://192.168.1.229/db_test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "root", "123456");
            conn.setAutoCommit(false);// 事务不自动提交
 
            pstmt = conn.prepareStatement("delete from xyx_xzqh");
            int count = pstmt.executeUpdate();
            log.log(Level.INFO, "___删除数据" + count);
 
            int batch = 500;// 一次插入多少笔
            int loop = list.size() / batch;// batch可以循环的次数
            int last = list.size() - batch * loop;// 最后剩余
            int start = 0;// list从哪开始取数据
            int index = 1;// 问号参数
            int allCount = 0;// 统计总共插入多少数据
 
            for (int i = 0; i < loop; i++) {
                index = 1;// 重置到第一个问号参数
                pstmt = conn.prepareStatement(getPreSql(batch));
                for (int j = start; j < start + batch; j++) {
                    String[] s = list.get(j).split(separator);
                    for (int k = 0; k < s.length; k++) {
                        pstmt.setString(index, s[k]);
                        index++;
                    }
                }
                count = pstmt.executeUpdate();
                allCount += count;
                log.log(Level.INFO, "___插入数据" + count);
                start += batch;
            }
 
            if (last > 0) {
                index = 1;// 重置到第一个问号参数
                pstmt = conn.prepareStatement(getPreSql(last));
                for (int j = start; j < list.size(); j++) {
                    String[] s = list.get(j).split(separator);
                    for (int k = 0; k < s.length; k++) {
                        pstmt.setString(index, s[k]);
                        index++;
                    }
                }
                count = pstmt.executeUpdate();
                allCount += count;
                log.log(Level.INFO, "___插入数据" + count);
            }
            log.log(Level.INFO, "___一共插入" + allCount);
 
            conn.commit();// 提交事务
            conn.setAutoCommit(true);// 还原现场
 
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "___找不到MySQL驱动包" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.log(Level.SEVERE, "___执行SQL异常" + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                    conn.setAutoCommit(true);// 还原现场
                    log.log(Level.WARNING, "___回滚数据");
                }
            } catch (SQLException e1) {
                log.log(Level.SEVERE, "___回滚异常" + e.getMessage());
                e1.printStackTrace();
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE, "___关闭数据库连接异常" + e.getMessage());
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 生成sql
     * 
     * @return
     */
    private static String getPreSql(int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `xyx_xzqh` (`code`, `name`) VALUES ");
        for (int i = 0; i < count; i++) {
            sb.append(" (?, ?),");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
