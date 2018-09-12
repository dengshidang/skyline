package com.skyline.common.util.entityutil;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DatabaseUtils {
        private static String driverClass = null;

        private static String url = null;

        private static String user = null;

        private static String password = null;

        // 通过静态块获取jdbc.properties中的数据库驱动信息并初始化静态成员变量
        static {
                /*Properties props = new Properties();
                try {
                        props.load(new FileReader("jdbc.properties"));
                } catch (IOException e) {
                        e.printStackTrace();
                }*/
                driverClass = "com.mysql.jdbc.Driver";
                url = "jdbc:mysql://172.16.2.169:3306/skyline?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
                //url="jdbc:mysql://127.0.0.1:3306/mybatis";
                user = "root";
                password = "123456";

        }

        /**
         * 根据获取的数据库驱动信息来创建数据库连接对象并返回
         * 
         * @return 连接对象
         * @throws Exception
         */
        public static Connection getConnection() throws Exception {
                Connection conn = null;

                Class.forName(driverClass);

                conn = (Connection) DriverManager.getConnection(url, user, password);

                return conn;

        }

        public static void closeDatabase(java.sql.Connection conn, PreparedStatement pstmt, Object object)
                        throws SQLException {
                if (pstmt != null) {
                        pstmt.close();
                        pstmt = null;
                }
                if (conn != null) {
                        conn.close();
                        conn = null;
                }

        }

}
