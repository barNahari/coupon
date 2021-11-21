package project.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * connection pool class
 */
public class ConnectionPool {
    //File->Project Structure->Libraries -> + -> from maven ...
    //mysql:mysql-connector-java:jar:8.0.25
    /**
     * connection pool instance
     */
    private static ConnectionPool instance = null;
    /**
     maximum connection
     */
    public static final int NUM_OF_CONNECTION = 10;
    /**
     * stack of connection
     */
    private Stack<Connection> connections = new Stack<>();

    //fields

    //cto'r

    /**
     * Constructor for creating instance of class (SingleTon)
     * @throws SQLException if we have an exception regarding SQL
     */
    private ConnectionPool() throws SQLException {
        //open all connections
        openAllConnections();
    }

    /**
     * get instance of connection pool (SingleTon)
     * @return a connection to mySQL database
     * @throws SQLException throws SQL exception
     */
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                //double check
                if (instance == null){
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * open all connection to data base
     * @throws SQLException exception handler for SQL exceptions
     */
    public void openAllConnections() throws SQLException {
        for (int index=0;index < NUM_OF_CONNECTION;index+=1){
            //DATABASE credentials
            Connection connection = DriverManager.getConnection(DataBaseManager.URL,DataBaseManager.USER_NAME,DataBaseManager.PASSWORD);
            connections.push(connection);
        }
    }

    /**
     * get a single connection
     * @return a connection to the database
     * @throws InterruptedException throws interrupt exception
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections){
            //check if the stack is empty
            if (connections.isEmpty()){
                //wait until we will get a connection back
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * return a connection from user
     * @param connection get an existing connection
     */
    public void returnConnection(Connection connection){
        synchronized (connections){
            connections.push(connection);
            //notify that we got back a connection from the user...
            //notify all connections that are waiting to connection to be release..
            connections.notify();
        }
    }

    /**
     * close all connections
     * @throws InterruptedException handle stop of thread
     */
    public void closeAllConnection() throws InterruptedException {
        synchronized (connections){
            while (connections.size()<NUM_OF_CONNECTION){
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}

