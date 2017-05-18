package com.zgsy.bs.common.utils;

import com.zgsy.bs.common.model.TableInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Java on 2017/5/13
 */
public class MysqlUtils {

    public static Connection connection;
    public static Statement statement;

    public static void initMysqlUtils(String url) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取某个表结构
    public static TableInfo getTableInfo(String tableName) throws Exception {
        String sql = "SELECT * FROM " + tableName.toUpperCase() + " WHERE 1 != 1";
        ResultSet rs = statement.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        TableInfo tableInfo = new TableInfo();
        Integer count = resultSetMetaData.getColumnCount();
        tableInfo.setColumnSize(count);
        List<String> columnNames = new ArrayList<String>();
        List<String> columnTypes = new ArrayList<String>();
        List<String> columnDescs = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            columnNames.add(resultSetMetaData.getColumnName(i));
            columnTypes.add(resultSetMetaData.getColumnTypeName(i));

        }
        rs = statement.executeQuery("show full columns from " + tableName.toUpperCase());
        while (rs.next()) {
            String desc = rs.getString("Comment");
            if (desc.equals("") || desc == null) {
                desc = "无注释";
            }
            columnDescs.add(desc);
        }

        tableInfo.setColumnDescs(columnDescs);
        tableInfo.setColumnNames(columnNames);
        tableInfo.setColumnTypes(columnTypes);
        tableInfo.setTableName(tableName);
        String tableDesc = getCommentByTableName(tableName);
        if (tableDesc.equals("")||tableDesc==null){
            tableDesc="此表无注释";
        }
        tableInfo.setTableDesc(tableDesc);
        return tableInfo;
    }


    public static List<String> getAllTableName() throws Exception {
        List<String> tables = new ArrayList<String>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tables.add(tableName);
        }
        return tables;
    }


    public static String getCommentByTableName(String tableName) throws Exception {

        Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                return comment;
            }
        return "无注解";

    }

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }


//    public static void main(String[] args) {
//        initMysqlUtils("jdbc:mysql://localhost/project_hosting?user=root&password=root");
//        System.out.println("aa");
//    }


}
