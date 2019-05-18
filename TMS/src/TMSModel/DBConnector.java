/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMSModel;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author student
 */
public class DBConnector {

    private String dbms = "mysql";
    private String serverName = "localhost";
    private int portNumber = 3080;
//    private String dbName = "project_database?useTimezone=true&serverTimezone=UTC";
    private String dbName = "dbmanager";
    private Connection conn;
    Statement retrieveStmt = null;
    private String dbAdmin = "root";
    private String dbPass = "";

    public DBConnector() {
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

        try {
            this.conn = ds.getConnection(); //establish connection
            System.out.println("Connection Established.");
        } catch (SQLException E) {
            System.out.println(E.getMessage()); //display error message
        }
        //End constructor
    }

    public DBConnector(String srvr, String dbms, int portNum, String user, String pass, String db) {
        /*PARAMERTERIZED CONSTRUCTOR:
         Accepts the basic data needed to establish a connection with database as a set of parameters, and assigns these values to the attributes (global variables)*/

        this.serverName = serverName;
        this.dbms = dbms;
        this.portNumber = portNumber;
        this.dbAdmin = user;
        this.dbPass = dbPass;
        this.dbName = dbName;

        MysqlDataSource ds = new MysqlDataSource();

        ds.setUser(user);
        ds.setPassword(dbPass);
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(dbName);

//continued in next slide...
/*PARAMERTERIZED CONSTRUCTOR continued...*/
        try {
//Create a new connection through the DataSource object “ds”
            this.conn = ds.getConnection();
            System.out.println("Connection Established!");
        } catch (SQLException E) {
            System.out.println(E.getMessage()); //display error message
        }
    }

    public void insert(String query) throws SQLException {
        Statement stmt = null;

        try {
            stmt = (Statement) this.conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet select(String query) throws SQLException {
        ResultSet rs = null;

        try {
            retrieveStmt = (Statement) this.conn.createStatement();
            rs = retrieveStmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public void closeRetrieveStatement() {

        try {
            retrieveStmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(String query) throws SQLException {
        Statement stmt = null;

        try {
            stmt = (Statement) this.conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void delete(String query) {
        Statement stmt = null;

        try {
            stmt = (Statement) this.conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}