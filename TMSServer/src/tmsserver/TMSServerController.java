/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmsserver;

import chateeserver.DBConnector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tmsserverModel.AccountModel;
import java.io.*;

/**
 *
 * @author 2ndyrGroupB
 */
public class TMSServerController {
    ServerSocket ss;
    Socket sock;
    BufferedReader bin;
    BufferedWriter bon;
    DBConnector dbc;
    AccountModel am;
    private  ArrayList<AccountModel> accountsList;
    ObjectOutputStream writer;
    ObjectInputStream reader;
    public TMSServerController() {
        this.dbc = new DBConnector();   //instantiate DBConnector
        this.acceptConnections();
    }

    private void acceptConnections(){
        String response = null;
        try {
            this.ss = new ServerSocket(4000);
            System.out.println("Port opened. Listening on port 4000.");
            while(true) {
                sock = ss.accept();
                System.out.println("Connected with:"+sock.toString());
                bin  = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                bon = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                
                //read 1st line from socket (contains method name to invoke)
                switch(bin.readLine()){
                    //read 2nd line from socket (contains query to execute) and load it as argument to the method to be called.
//                    case "createAccount": response =  createAccount(bin.readLine());
//                    break;
//                    case "createMessage": response = createMessage(bin.readLine());
//                    break;
                    case "readAccounts": response = readAccount(bin.readLine(),bon);
                    break;
//                    case "readMessages": readMessages(bin.readLine());
//                    break;
//                    case "updateAccount": updateAccount(bin.readLine());
//                    break;
//                    case "deleteAccount": deleteAccount(bin.readLine());
//                    break;
                    case "Hi": System.out.println("I got you client");break;
                    default: System.out.println("Invalid instruction.");
                }
       
                
                bon.write(response);
                bon.close();
                //bon.close();            //close buffered writer object
            }
        } catch (IOException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private String createAccount(String query) {
//        String response = "OK";
//        try {
//            this.dbc.insert(query);
//        }catch(SQLException e){
//            response = "SQL Exception";
//        }
//        return response;
//    }
//
//    private String createMessage(String query) {
//        String response = "OK";
//        try{
//            this.dbc.insert(query);
//        }catch(SQLException e){
//            response = "SQL Exception";
//        }
//        return response;
//    }
//
    private String readAccount(String query, BufferedWriter bon) throws IOException {
        String response = "OK";
        ResultSet rs =null;
        try {
            rs = dbc.select(query);
            //this.accountsList = am.populateAccounts(rs);
            if(!rs.next()){
                response = "Account Not found";
            }else{
                bon.write(Integer.toString(rs.getInt("usertype")) + "\n" + rs.getString("username") + "\n");
                bon.flush();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
//
//    private String readMessages(String query) {
//        String response = null;
//        //TO DO Code here...
//        return response;
//    }
//
//    private String updateAccount(String query) {
//        String response = null;
//        //TO DO Code here...
//        return response;
//    }
//
//    private String deleteAccount(String query) {
//        String response = null;
//        //TO DO Code here...
//        return response;
//    }
//    
}
