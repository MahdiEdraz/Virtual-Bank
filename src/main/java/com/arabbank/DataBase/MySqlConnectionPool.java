package com.arabbank.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class MySqlConnectionPool{
	
	
    private static ArrayBlockingQueue<Connection> connectionPool ;
    
    static {
    	/*should be read from configrations*/
    	int numberOfConnections = 10; 
    	try {
    	MySqlConnectionPool.createConnectionPool(numberOfConnections);
    	}
    	catch(SQLException e) {
    		//unable to create connections
    		e.printStackTrace();
    	}
    	  
    }

   
   private static void createConnectionPool(int numberOfConnections) throws SQLException {
	   connectionPool = new ArrayBlockingQueue<Connection>(numberOfConnections);
       for(int i= 0 ;i < numberOfConnections ;i++)
           connectionPool.add(MySqlConnection.getConnection());
   }

    
    public static Connection getConnectoin(){

        return connectionPool.poll() ;
    }

   
    public static void releaseConnection(Connection connection) throws SQLException{

        connectionPool.add(connection);
        connection.close();
    }

    /**
     * This method is used to clear the connection pool
     */
    public void shutdown(){

          connectionPool.clear();

    }

	

}
