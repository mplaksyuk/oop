package abstr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import connection.ConnectionPool;

public abstract class DaoAbstract {
    private static final Logger log = Logger.getLogger(DaoAbstract.class);

    protected static void connect(String query, Next next) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

        try (Connection connection = connectionPool.getConnection()) {
            
            PreparedStatement statement = connection.prepareStatement(query);
            connection.setAutoCommit(false);
            // 
            next.onResult(statement);
            // 
            connection.commit();
            
            connectionPool.releaseConnection(connection);


        } catch (Exception e) {
            log.info(e);
        }
    }

    protected static void connect(String query, NextConnection next) {
        ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

        try (Connection connection = connectionPool.getConnection()) {
            
            PreparedStatement statement = connection.prepareStatement(query);
            connection.setAutoCommit(false);
            // 
            next.onResult(connection, statement);
            // 
            connection.commit();
            
            connectionPool.releaseConnection(connection);


        } catch (Exception e) {
            log.info(e);
        }
    }

    protected interface Next {
        void onResult(PreparedStatement statement) throws SQLException;
    }
    
    protected interface NextConnection {
        void onResult(Connection connection, PreparedStatement statement) throws SQLException;
    }
    
}
