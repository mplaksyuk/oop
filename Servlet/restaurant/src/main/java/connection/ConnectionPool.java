package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;

public class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool = new ConnectionPool();

    private final String URL = "jdbc:postgresql://localhost:5432/restaurant";
    private final String USER = "mplaksyuk";
    private final String PASSWORD = "";
    private final int MAX_CONNECTION = 32;

    private BlockingQueue<Connection> connections;

    private ConnectionPool(){
        try 
        {
            Class.forName("org.postgresql.Driver");
        } 
        catch (ClassNotFoundException e) 
        {  
            log.info("JDBC Driver not found");
        }

        connections = new LinkedBlockingQueue<>(MAX_CONNECTION);

        try 
        {
            for (int i = 0; i < MAX_CONNECTION; i++) 
            {
                connections.put(DriverManager.getConnection(URL, USER, PASSWORD));
            }
        }
        catch (SQLException e)
        {
            log.info("Trouble " + e);
        }
        catch (InterruptedException e)
        {
            log.info("Connection was interrupted");
        }
    }

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public Connection getConnection() throws InterruptedException, SQLException {
        Connection c = connections.take();

        return c.isClosed() ? DriverManager.getConnection(URL, USER, PASSWORD) : c;
    }
    public void releaseConnection(Connection c) throws InterruptedException {
        connections.put(c);
    }
}