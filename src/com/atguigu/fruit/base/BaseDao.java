package com.atguigu.fruit.base;

import com.fasterxml.jackson.databind.introspect.AnnotationCollector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * @author sunshine
 * @version 1.0
 * @date 2022年07月27日 22:27
 * @description
 */
public abstract class BaseDao<T> {
    private final String URL = "jdbc:mysql://localhost:3306/fruitdb?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private final String USERNAME = "root";
    private final String PASSWORD = "123456";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    protected Connection connection;
    protected PreparedStatement ps;
    protected ResultSet resultSet;
    //T的class对象
    private Class entityClass;

    public BaseDao() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        Type[] arguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        //获取T的真实类型
        Type actualType = arguments[0];
        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void close(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //给预处理命令设置参数
    protected void setParams(PreparedStatement ps, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }
    }

    //执行更新，返回影响行数
    protected int executeUpdate(String sql, Object... params) {
        boolean flag = false;
        flag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            connection = getConnection();
            if (flag) {
                ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = connection.prepareStatement(sql);
            }
            setParams(ps, params);
            int count = ps.executeUpdate();
            if (flag) {
                resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    return ((Long) resultSet.getLong(1)).intValue();
                }
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, resultSet);
        }
        return 0;
    }

    //通过反射给object对象的property属性赋予propertyValue
    private void setValue(Object object, String property, Object propertyValue) {
        Class aClass = object.getClass();
        try {
            //获取property对应的属性名
            Field field = aClass.getDeclaredField(property);
            if (field != null) {
                field.setAccessible(true);
                field.set(object, propertyValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //执行复杂查询，返回例如统计结果
    protected Object[] executeComplexQuery(String sql, Object... params) {
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            setParams(ps, params);
            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取结果集列数
            int columnCount = metaData.getColumnCount();
            Object[] objectList = new Object[columnCount];
            if (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object object = resultSet.getObject(i + 1);
                    objectList[i] = object;
                }
                return objectList;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(connection, ps, resultSet);
        }
        return null;
    }

    //执行查询，返回单个实体对象
    protected T load(String sql, Object... params) {
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            setParams(ps, params);
            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取结果集列数
            int columnCount = metaData.getColumnCount();

            if (resultSet.next()) {
                T entity = (T) entityClass.getConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object objectValue = resultSet.getObject(i + 1);
                    setValue(entity, columnName, objectValue);
                }
                return entity;
            }

        } catch (SQLException | NoSuchMethodException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, resultSet);
        }
        return null;
    }

    //执行查询，返回list
    protected List<T> executeQuery(String sql, Object... params) {
        List<T> lists = new ArrayList<>();
        try {
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            setParams(ps, params);
            resultSet = ps.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取结果集列数
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                T entity = (T) entityClass.getConstructor().newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Object objectValue = resultSet.getObject(i + 1);
                    setValue(entity, columnName, objectValue);
                }
                lists.add(entity);
            }

        } catch (SQLException | NoSuchMethodException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, resultSet);
        }
        return lists;

    }
}
