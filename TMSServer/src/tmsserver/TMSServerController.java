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

    public void acceptConnections(){
        String response = null;
        try {
            this.ss = new ServerSocket(4000);
            System.out.println("Port opened. Listening on port 4000.");
            while(true) {
                sock = ss.accept();
                System.out.println("Connected with:"+sock.toString());
                bin  = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                bon = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                writer = new ObjectOutputStream(sock.getOutputStream());
                reader = new ObjectInputStream(sock.getInputStream());
                //read 1st line from socket (contains method name to invoke)
                switch(reader.readUTF()){
                    //read 2nd line from socket (contains query to execute) and load it as argument to the method to be called.
//                    case "createAccount": response =  createAccount(bin.readLine());
//                    break;
//                    case "createMessage": response = createMessage(bin.readLine());
//                    break;
                    case "readAccounts": response = readAccount(reader.readUTF(),reader.readUTF());
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
       
                writer.writeUTF(response);
                writer.close();
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
    private String readAccount(String username, String password) {
        String response = "OK";
        System.out.println(response);
//        am = new AccountModel(username,password);
//        ResultSet rs;
//        String query = "Select * from tblaccount";
//        System.out.println(query);
//        try {
//            rs = dbc.select(query);
//            if(!rs.next()){
//                response = "Account not found !";
//                System.out.println(response);
//            }
//            //this.accountsList = am.populateAccounts(rs);
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        //System.out.println("Initiate response: ");
//        try {
//            for(int i = 0; i < this.accountsList.size();i++){
//                writer.writeObject(accountsList.get(i).getIDAccount() + "," + accountsList.get(i).getUsername()
//                                    + "," + accountsList.get(i).getPassword() + "," + accountsList.get(i).getUsertype()
//                                    + "\n");
//                writer.flush();
////                bon.write(accountsList.get(i).getIDAccount() + " | " + accountsList.get(i).getUsername()
////                        + accountsList.get(i).getPassword() +" | "
////                        + accountsList.get(i).getUsertype()+"\n");
////                bon.flush();
//            }
//        } catch (IOException ex) {
//                Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
//                response = "IOException";
//            }
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
