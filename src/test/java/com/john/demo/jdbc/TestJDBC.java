package com.john.demo.jdbc;

import com.john.demo.utils.DBUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.*;
import java.util.Date;

public class TestJDBC {

    @Test
    public void testOracleDriver() {

        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
//        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String user = "scott";
        String pwd = "scott";

        try {
            Connection connection = DriverManager.getConnection(url, user, pwd);
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void testJdbcDateType() {
        Connection connection = DBUtils.getConnection();
        String sql = "insert into user (username, nickname, password, createtime) values (?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "zhangsan");
            pstmt.setString(2, "张三");
            pstmt.setString(3, "123");
            pstmt.setObject(4, new Date());

            int effectRows = pstmt.executeUpdate();
            System.out.println("影响行数：" + effectRows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDbutilsDateType() {
        Connection connection = DBUtils.getConnection();
        String sql = "insert into user (username, nickname, password, createtime) values (?,?,?,?)";
        QueryRunner qr = new QueryRunner();
        try {
            int effectRows = qr.update(connection, sql, "lisi", "李四", "456", new Timestamp(System.currentTimeMillis()));
            System.out.println("影响行数：" + effectRows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
