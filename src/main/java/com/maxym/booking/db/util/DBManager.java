package com.maxym.booking.db.util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager dbManager;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (dbManager == null) dbManager = new DBManager();
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context) initContext.lookup("java:/comp/env");

            DataSource dataSource = (DataSource) envContext.lookup("jdbc/booking2");

            connection = dataSource.getConnection();
        } catch (NamingException ex) {
            LOG.error("Failed while getting a JDBC connection", ex);
        }
        return connection;
    }

    public void commitAndClose(Connection connection) {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException ex) {
            LOG.error("Failed while committing a connection", ex);
        }
    }

    public void rollbackAndClose(Connection connection) {
        try {
            connection.rollback();
            connection.close();
        } catch (SQLException ex) {
            LOG.error("Failed while rollbacking user", ex);
        }
    }
}
