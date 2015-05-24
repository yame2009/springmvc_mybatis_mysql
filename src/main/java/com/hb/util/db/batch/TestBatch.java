package com.hb.util.db.batch;

import java.io.IOException; 
import java.sql.*; 
import java.util.Random;

import com.hb.util.db.JdbcUtils;

/** 
* JDBC批量Insert优化（下） 
* 
*  <a>http://lavasoft.blog.51cto.com/62575/185010</a>
* 
* @author leizhimin 2009-7-29 10:03:10 
*/ 
public class TestBatch { 
	
		//DbConnectionBroker是一个简单，轻量级的数据库连接池。
//		public static DbConnectionBroker myBroker = null; 
        public static JdbcUtils myBroker = null; 

        static { 
                try { 
                	myBroker = new JdbcUtils(); 
//                        myBroker = new DbConnectionBroker("com.mysql.jdbc.Driver", 
//                                        "jdbc:mysql://192.168.104.163:3306/testdb", 
//                                        "vcom", "vcom", 2, 4, 
//                                        "c:\\testdb.log", 0.01); 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
        } 

        /** 
         * 初始化测试环境 
         * 
         * @throws SQLException 异常时抛出 
         */ 
        public static void init() throws SQLException { 
                Connection conn = myBroker.getConnection(); 
                conn.setAutoCommit(false); 
                Statement stmt = conn.createStatement(); 
                stmt.addBatch("DROP TABLE IF EXISTS tuser"); 
                stmt.addBatch("CREATE TABLE tuser (\n" + 
                                "    id bigint(20) NOT NULL AUTO_INCREMENT,\n" + 
                                "    name varchar(12) DEFAULT NULL,\n" + 
                                "    remark varchar(24) DEFAULT NULL,\n" + 
                                "    createtime datetime DEFAULT NULL,\n" + 
                                "    updatetime datetime DEFAULT NULL,\n" + 
                                "    PRIMARY KEY (id)\n" + 
                                ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8"); 
                stmt.executeBatch(); 
                conn.commit(); 
//                myBroker.freeConnection(conn); 
                myBroker.releaseConn();
        } 

        /** 
         * 100000条静态SQL插入 
         * 
         * @throws Exception 异常时抛出 
         */ 
        public static void testInsert() throws Exception { 
                init();         //初始化环境 
                Long start = System.currentTimeMillis(); 
                Random ran = new Random();
                for (int i = 0; i < 100000; i++) { 
                        String sql = "\n" + 
                                        "insert into testdb.tuser \n" + 
                                        "\t(name, \n" + 
                                        "\tremark, \n" + 
                                        "\tcreatetime, \n" + 
                                        "\tupdatetime\n" + 
                                        "\t)\n" + 
                                        "\tvalues\n" + 
                                        "\t('" +String.valueOf(ran.nextInt(12)) + "', \n" + 
                                        "\t'" + String.valueOf(ran.nextInt(24))  + "', \n" + 
                                        "\tnow(), \n" + 
                                        "\tnow()\n" + 
                                        ")"; 
                        Connection conn = myBroker.getConnection(); 
                        conn.setAutoCommit(false); 
                        Statement stmt = conn.createStatement(); 
                        stmt.execute(sql); 
                        conn.commit(); 
//                        myBroker.freeConnection(conn); 
                        myBroker.releaseConn();
                } 
                Long end = System.currentTimeMillis(); 
                System.out.println("单条执行100000条Insert操作，共耗时：" + (end - start) / 1000f + "秒！"); 
        } 

        /** 
         * 批处理执行静态SQL测试 
         * 
         * @param m 批次 
         * @param n 每批数量 
         * @throws Exception 异常时抛出 
         */ 
        public static void testInsertBatch(int m, int n) throws Exception { 
                init();             //初始化环境 
                Long start = System.currentTimeMillis(); 
                Random ran = new Random();
                for (int i = 0; i < m; i++) { 
                        //从池中获取连接 
                        Connection conn = myBroker.getConnection(); 
                        conn.setAutoCommit(false); 
                        Statement stmt = conn.createStatement(); 
                        
                        for (int k = 0; k < n; k++) { 
                                String sql = "\n" + 
                                                "insert into testdb.tuser \n" + 
                                                "\t(name, \n" + 
                                                "\tremark, \n" + 
                                                "\tcreatetime, \n" + 
                                                "\tupdatetime\n" + 
                                                "\t)\n" + 
                                                "\tvalues\n" + 
                                                "\t('" + String.valueOf(ran.nextInt(12)) + "', \n" + 
                                                "\t'" + String.valueOf(ran.nextInt(24)) + "', \n" + 
                                                "\tnow(), \n" + 
                                                "\tnow()\n" + 
                                                ")"; 
                                //加入批处理 
                                stmt.addBatch(sql); 
                        } 
                        stmt.executeBatch();    //执行批处理 
                        conn.commit(); 
//                        stmt.clearBatch();        //清理批处理 
                        stmt.close(); 
//                        myBroker.freeConnection(conn); //连接归池 
                        myBroker.releaseConn();
                } 
                Long end = System.currentTimeMillis(); 
                System.out.println("批量执行" + m + "*" + n + "=" + m * n + "条Insert操作，共耗时：" + (end - start) / 1000f + "秒！"); 
        } 

        /** 
         * 100000条预定义SQL插入 
         * 
         * @throws Exception 异常时抛出 
         */ 
        public static void testInsert2() throws Exception {     //单条执行100000条Insert操作，共耗时：40.422秒！ 
                init();         //初始化环境 
                Long start = System.currentTimeMillis(); 
                String sql = "" + 
                                "insert into testdb.tuser\n" + 
                                "    (name, remark, createtime, updatetime)\n" + 
                                "values\n" + 
                                "    (?, ?, ?, ?)"; 
                
                Random ran = new Random();
                for (int i = 0; i < 100000; i++) { 
                        Connection conn = myBroker.getConnection(); 
                        conn.setAutoCommit(false); 
                        PreparedStatement pstmt = conn.prepareStatement(sql); 
                        pstmt.setString(1, String.valueOf(ran.nextInt(12))); 
                        pstmt.setString(2, String.valueOf(ran.nextInt(24))); 
                        pstmt.setDate(3, new Date(System.currentTimeMillis())); 
                        pstmt.setDate(4, new Date(System.currentTimeMillis())); 
                        pstmt.executeUpdate(); 
                        conn.commit(); 
                        pstmt.close(); 
//                        myBroker.freeConnection(conn); 
                        myBroker.releaseConn();
                } 
                Long end = System.currentTimeMillis(); 
                System.out.println("单条执行100000条Insert操作，共耗时：" + (end - start) / 1000f + "秒！"); 
        } 

        /** 
         * 批处理执行预处理SQL测试 
         * 
         * @param m 批次 
         * @param n 每批数量 
         * @throws Exception 异常时抛出 
         */ 
        public static void testInsertBatch2(int m, int n) throws Exception { 
                init();             //初始化环境 
                Long start = System.currentTimeMillis(); 
                String sql = "" + 
                                "insert into testdb.tuser\n" + 
                                "    (name, remark, createtime, updatetime)\n" + 
                                "values\n" + 
                                "    (?, ?, ?, ?)"; 
                Random ran = new Random();
                for (int i = 0; i < m; i++) { 
                        //从池中获取连接 
                        Connection conn = myBroker.getConnection(); 
                        conn.setAutoCommit(false); 
                        PreparedStatement pstmt = conn.prepareStatement(sql); 
                        for (int k = 0; k < n; k++) { 
                                pstmt.setString(1, String.valueOf(ran.nextInt(12))); 
                                pstmt.setString(2, String.valueOf(ran.nextInt(24))); 
                                pstmt.setDate(3, new Date(System.currentTimeMillis())); 
                                pstmt.setDate(4, new Date(System.currentTimeMillis())); 
                                //加入批处理 
                                pstmt.addBatch(); 
                        } 
                        pstmt.executeBatch();    //执行批处理 
                        conn.commit(); 
//                        pstmt.clearBatch();        //清理批处理 
                        pstmt.close(); 
//                        myBroker.freeConnection(conn); //连接归池 
                        myBroker.releaseConn();
                } 
                Long end = System.currentTimeMillis(); 
                System.out.println("批量执行" + m + "*" + n + "=" + m * n + "条Insert操作，共耗时：" + (end - start) / 1000f + "秒！"); 
        } 

        public static void main(String[] args) throws Exception { 
                init(); 
                Long start = System.currentTimeMillis(); 
                System.out.println("--------C组测试----------"); 
                testInsert(); 
                testInsertBatch(100, 1000); 
                testInsertBatch(250, 400); 
                testInsertBatch(400, 250); 
                testInsertBatch(500, 200); 
                testInsertBatch(1000, 100); 
                testInsertBatch(2000, 50); 
                testInsertBatch(2500, 40); 
                testInsertBatch(5000, 20); 
                Long end1 = System.currentTimeMillis(); 
                System.out.println("C组测试过程结束，全部测试耗时：" + (end1 - start) / 1000f + "秒！"); 

                System.out.println("--------D组测试----------"); 
                testInsert2(); 
                testInsertBatch2(100, 1000); 
                testInsertBatch2(250, 400); 
                testInsertBatch2(400, 250); 
                testInsertBatch2(500, 200); 
                testInsertBatch2(1000, 100); 
                testInsertBatch2(2000, 50); 
                testInsertBatch2(2500, 40); 
                testInsertBatch2(5000, 20); 

                Long end2 = System.currentTimeMillis(); 
                System.out.println("D组测试过程结束，全部测试耗时：" + (end2 - end1) / 1000f + "秒！"); 
        } 
}