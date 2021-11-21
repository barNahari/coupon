package project.SQL;

import java.sql.*;
import java.util.Map;

public class DButils {
    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            //take a connection for connection pool.
            connection = ConnectionPool.getInstance().getConnection();
            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
           statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
    /*
    public static boolean runBetterQuery(String query, Map<Integer, Object> params) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            //our simple way
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String){
                        statement.setString(key,String.valueOf(value));
                    } else if (value instanceof Date){
                        statement.setDate(key, (Date)value);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(Boolean)value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(Double)value);
                    } else if (value instanceof Float){
                        statement.setFloat(key,(Float)value);
                    } else if (value instanceof Timestamp){
                        statement.setTimestamp(key,(Timestamp) value);
                    }
                } catch (SQLException err) {
                    System.out.println("Error in sql :" + err.getMessage());
                }
            });
            statement.execute();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error in executing sql");
            return false;
        } finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return true;
    }

     */

}

