/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmsserver;

import Model.DBConnector;
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
import Model.AccountModel;
import Model.PersonModel;
import Model.TaskModel;
import java.io.*;
import Model.TaskMemberModel;

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
    ArrayList<PersonModel> personList = new ArrayList();
    ArrayList<TaskModel> taskList = new ArrayList();
    ArrayList<TaskMemberModel> memberList = new ArrayList();
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
                writer = new ObjectOutputStream(sock.getOutputStream());
                reader = new ObjectInputStream(sock.getInputStream());
                //read 1st line from socket (contains method name to invoke)
                switch(bin.readLine()){
                    //read 2nd line from socket (contains query to execute) and load it as argument to the method to be called.
                    case "addPerson": response =  addPerson(bin.readLine());
                    break;
                    case "InsertTask" : response = insertTask(bin.readLine());
                    break;
                    case "readAccounts": response = readAccount(bin.readLine(),bon);
                    break;
                    case "loadPeople": response = loadPeople(bin.readLine(),writer);
                    break;
                    case "loadTasks" : response = loadTasks(bin.readLine(), writer);
                    break;
                    case "deleteTask": response = deleteTask(bin.readLine());
                    break;
                    case "updateTask" : response = updateTask(bin.readLine());
                    break;
                   
                    default: System.out.println("Invalid instruction.");
                }
                
//                switch(reader.readUTF()){
//                    case "insertMembers" : response = insertMembers( (memberList)reader.readObject() );break;
//                }
       
                
                bon.write(response);
                bon.close();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    
    private String loadTasks(String query, ObjectOutputStream writer) throws IOException{
        taskList.clear();
        String response = "OK";
        TaskModel task;
        ResultSet rs = null;
        try{
            rs = this.dbc.select(query);
            
            while(rs.next()){
                task = new TaskModel(rs.getInt("TaskID"), rs.getString("TaskName"), rs.getInt("TaskSize"));
                taskList.add(task);
            }
            writer.writeObject(taskList);
            writer.flush();
        }catch (SQLException ex){
            response = "SQL Exception";
        }
        
        return response;
    }
    
    private String loadPeople(String query,ObjectOutputStream writer) throws IOException {
        personList.clear();
        String response = "OK";
        ResultSet rs = null;
        PersonModel person;
        try{
            rs = this.dbc.select(query);

            while(rs.next()){
                person = new PersonModel(rs.getInt("IDPerson"),rs.getString("Name"), rs.getString("Gender"), rs.getString("Email"));
                personList.add(person);
            }
            writer.writeObject(personList);
            writer.flush();
            
        }catch(SQLException ex){
            response = "SQL Exception";
        }
        
        return response;
    }
    
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
    
    private String deleteTask(String query){
        System.out.println(query);
        String response = "OK";
        try{
            this.dbc.update(query);
        }catch (SQLException ex){
            response = "Deleting Task Denied";
            
        }
        return response;
    }
    
    private String updateTask(String query){
        String response = "OK";
        try{
            this.dbc.update(query);
        }catch(SQLException ex){
            response = "Updating Task Denied";
        }
        return response;
    }
    
    private String addPerson(String query) {
        String response = "OK";
        try{
            this.dbc.insert(query);
        }catch (SQLException ex){
            response = "Adding Person Denied";
        }
        return response;
    }
    
    private String insertTask(String query){
        String response = "OK";
        try{
            this.dbc.insert(query);
        }catch(SQLException ex){
            response = "Adding Task Denied";
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
