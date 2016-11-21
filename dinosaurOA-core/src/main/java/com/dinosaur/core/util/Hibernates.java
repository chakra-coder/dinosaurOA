package com.dinosaur.core.util;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库方言工具类
 * @author Alcott Hawk
 * @date 2016/11/15
 */
public class Hibernates {

    /**
     * 从DataSource中获取jdbcUrl链接的Dialect类型，支持的数据库类型可扩展，目前只支持Oracle, H2, MySql,
     * PostgreSql, SQLServer
     *
     * @param dataSource
     * @return
     */
    public static String getDialect(DataSource dataSource) {
        String jdbcUrl = getJdbcUrlFromDataSource(dataSource);
        if (StringUtils.contains(jdbcUrl, ":h2:")) {
            return H2Dialect.class.getName();
        } else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
            return MySQL57InnoDBDialect.class.getName();
        } else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
            return Oracle10gDialect.class.getName();
        } else if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
            return PostgreSQL82Dialect.class.getName();
        } else if (StringUtils.contains(jdbcUrl, ":sqlserver:")) {
            return SQLServer2008Dialect.class.getName();
        } else {
            throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
        }
    }

    private static String getJdbcUrlFromDataSource(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection == null) {
                throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
            }
            return connection.getMetaData().getURL();
        } catch (SQLException e) {
            throw new RuntimeException("Could not get database url", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
