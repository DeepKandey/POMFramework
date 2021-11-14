package com.qa.util;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlDBUtil {

    protected static DriverManagerDataSource dataSource;
    public static Connection connection;

    public SqlDBUtil(String DBName) throws Exception {
        initDBConnection(DBName);
    }

    public static void initDBConnection(String DBName) throws Exception {
        switch (DBName.toUpperCase()) {
            case "ORACLE" -> {
                dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
                dataSource.setUrl("jdbc:oracle:thin:@" + System.getenv("ORACLE_CONNECTION_STRING"));
                dataSource.setUsername(System.getenv("ORACLE_UNAME"));
                dataSource.setPassword(System.getenv("ORACLE_PASSWORD"));
                connection = dataSource.getConnection();
            }
            case "MYSQL" -> {
                dataSource = new DriverManagerDataSource();
                dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_CONNECTION_STRING"));
                dataSource.setUsername(System.getenv("MYSQL_UNAME"));
                dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
                connection = dataSource.getConnection();
            }
            default -> throw new Exception("Please specify correct DB Name");
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public boolean verifyFirstQueryResult(String DBQuery, String expVal) {
        return getFirstQueryResult(DBQuery).equals(expVal);
    }

    public String getFirstQueryResult(String DBQuery) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DBQuery);
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }

    public Map<String, Object> readRow(String DBQuery) {
        Map<String, Object> row = new HashMap<>();
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DBQuery);
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            int columns = resultSetMetaData.getColumnCount();
            for (int i = 1; i < columns; i++) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }

    public List<Map<String, Object>> readRows(String DBQuery) {
        List<Map<String, Object>> rows = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData resultSetMetaData;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(DBQuery);
            resultSetMetaData = resultSet.getMetaData();
            int columns = resultSetMetaData.getColumnCount();
            Map<String, Object> row;
            while (resultSet.next()) {
                row = new HashMap<>();
                for (int i = 1; i < columns; i++) {
                    row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return rows;
    }

    public List<Object> retrieveSpecificColumnValueFromDB(String columnName, List<Map<String, Object>> mapList) {
        List<Object> objectList;
        objectList = mapList.stream().flatMap(map -> map.entrySet().stream())
                .filter(entry -> entry.getKey().equals(columnName)).map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return objectList;
    }

    public int updateTable(String updateQuery) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(updateQuery);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return -1;
    }
}
