/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmsserverModel;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author josh
 */
public class DBConnector {
    /*A generic class for acquiring connections, statements,
    and sending queries and receiving result sets*/ 

    /*make attributes for required parameters for connection, and store default values 			(optional)*/
    private String dbms = "mysql"; 			//DBMS to connect to.
    private String serverName = "localhost"; 	//server where DBMS is located.
    private int portNumber = 3306; 			//port number to access.
    private String dbName = "dbmanager"; 		//name of specific database.
    private Connection conn; 				//A global connection object, free for use.
    Statement retrieveStmt = null; 			//A global statement object, free for use.
    private String dbAdmin = "root"; 			//The database username to use.
    private String dbPass = ""; 				//The database password to use.

    public DBConnector(){
        /*PARAMETER-LESS CONSTRUCTOR:
        establishes connection using default values stored in attributes.*/

        //Create a new data source object
        MysqlDataSource ds = new MysqlDataSource();
        //Set required properties (data about the database connection to be made)
        ds.setUser(dbAdmin);
        ds.setPassword(dbPass);
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(dbName);

         try{
                this.conn = ds.getConnection(); //establish connection
                System.out.println("Database connection Established.");
         }
        catch(SQLException E){
                 System.out.println(E.getMessage()); //display error message
        }
        //End constructor
    }

    public DBConnector(String srvr, String dbms, int portNum, String dbUser, String pass, String db){
        /*PARAMERTERIZED CONSTRUCTOR:
        Accepts the basic data needed to establish a connection with database as a set of parameters, and assigns these values to the attributes (global variables)*/

        this.serverName = serverName;
        this.dbms = dbms;
        this.portNumber = portNumber;
        this.dbAdmin = dbUser;
        this.dbPass = dbPass;
        this.dbName = dbName;

        MysqlDataSource ds = new MysqlDataSource();

        ds.setUser(dbUser);
        ds.setPassword(dbPass);
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(dbName);

        //continued in next slide...
        /*PARAMERTERIZED CONSTRUCTOR continued...*/
        try{
                //Create a new connection through the DataSource object “ds”
                this.conn = ds.getConnection();
        }
        catch(SQLException E){
                System.out.println(E.getMessage()); //display error message
        }
    }
        
    public void insert(String query) throws SQLException{
        //Receives a (insert) query String and executes using the global statement.

        Statement stmt = null;

        //Create a new statement
        stmt = (Statement) this.conn.createStatement();
        //Executes the given insert query, “query”. 
        stmt.executeUpdate(query);
        //Closes the global statement.
        stmt.close();
    }
        

    public void update(String query) throws SQLException{
        //Receives a (insert) query String and executes using the global statement.

        Statement stmt = null;

        //Create a new statement
        stmt = (Statement) this.conn.createStatement();
        //Executes the given update query, “query”. 
        stmt.executeUpdate(query);
        //Closes the global statement.
        stmt.close();
    }

    public ResultSet select(String query) throws SQLException {
        /*Accepts an SQL query, in the form of a String.
	Returns the ResultSet extracted by the executed query.*/
        
        ArrayList dataExtract = null;
        
        ResultSet rs = null;
        try {
            retrieveStmt = (Statement) this.conn.createStatement();
            rs = retrieveStmt.executeQuery(query);
        }
        catch (SQLException e ) {
            System.out.println(e.getMessage()); //Print error message
        }
        return rs;
    }
    
    public void closeRetrieveStatement(){
        /*Closes the retrieveStmt field. The retrieveStmt field is used to execute a query*/

        try{
                retrieveStmt.close(); //Closes the statement, “retrieveStmt”.
        }
        catch(Exception E){
                System.out.println(E.getMessage()); //Print error message
        }
    }
}
