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
import java.util.Hashtable;
import javax.swing.JOptionPane;

/**
 *
 * @author 2ndyrGroupB
 */
public class TMSServerController {
    
    ServerSocket ss;
    Socket sock;
    
    BufferedReader bin;
    BufferedWriter bon;
    
    ObjectOutputStream writer;
    
    DBConnector dbc;
    
    
    AccountModel am;
    
    private ArrayList<AccountModel> accountsList;
    ArrayList<PersonModel> personList = new ArrayList();
    ArrayList<TaskModel> taskList = new ArrayList();
    ArrayList<TaskMemberModel> memberList = new ArrayList();
    
    public TMSServerController() {
        this.dbc = new DBConnector();   //instantiate DBConnector
        try {
            this.acceptConnections();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void acceptConnections() throws ClassNotFoundException{
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
                //read 1st line from socket (contains method name to invoke)
                switch(bin.readLine()){
                    //read 2nd line from socket (contains query to execute) and load it as argument to the method to be called.
                    case "myAccount": response = myAccount(writer);
                    break;
                    case "addPerson": response =  addPerson(bin.readLine());
                    break;
                    case "checkPerson": response = checkPerson(bin.readLine());
                    break;
                    case "getPerson": response = getPerson(bin.readLine(),writer);
                    break;
                    case "createAccount" : response = createAccount(bin.readLine());
                    break;
                    case "InsertTask" : response = insertTask(bin.readLine());
                    break;
                    case "readAccounts": response = readAccount(bin.readLine(),writer);
                    break;
                    case "viewMyTask" : response = viewMyTask(writer);
                    break;
                    case "loadPeople": response = loadPeople(bin.readLine(),writer);
                    break;
                    case "loadTasks" : response = loadTasks(bin.readLine(), writer);
                    break;
                    case "deleteTask": response = deleteTask(bin.readLine());
                    break;
                    case "updateTask" : response = updateTask(bin.readLine());
                    break;
                    case "insertMembers" : response = insertMembers(bin.readLine());
                    break;
                    case "loadMembers" : response = loadMembers(bin.readLine(),writer);
                    break;
                    case "logout": logout(sock);
                    break;
                    case "updateTaskStatus" : response = updateTaskStatus(bin.readLine());
                    break;
                    case "updatePass": response = updatePass(bin.readLine());
                    break;
                    default: System.out.println("Invalid instruction.");
                    break;
                    
                }
                
                bon.write(response);
                bon.close();
                
            }
        } catch (IOException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String updatePass(String query){
        String response = "OK";
        
        try {
            this.dbc.update(query);
        } catch (SQLException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    
    private String myAccount(ObjectOutputStream writer){
        String response = "OK";
        
        try {
            writer.writeObject(am);
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    
    private String updateTaskStatus(String query){
        String response = "OK";
        try {
            this.dbc.update(query);
        } catch (SQLException ex) {
            response = "SQL Exception";
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    private String checkPerson(String query){
        String response = "OK";
        
        ResultSet rs = null;
        
        try {
            rs = this.dbc.select(query);
            if(rs.next()){
                response = "Account is already existing"; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    
    private String getPerson(String query, ObjectOutputStream writer) throws IOException{
        String response = "OK";
        //int ID,String name, String gender, String email,boolean assigned, boolean accountStatus
        ResultSet rs = null;
        PersonModel personModel;
        try {
            rs = this.dbc.select(query);
            if(rs.next()){
                personModel = new PersonModel(rs.getInt("IDPerson"),rs.getString("Name"), rs.getString("Gender"), rs.getString("Email"),rs.getBoolean("Assigned"), rs.getBoolean("AccountStatus"));
                writer.writeObject(personModel);
                writer.flush();
            }else{
                response = "Person not found";
            }
        } catch (SQLException ex) {
            response = "SQL syntax error";
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    
    private String createAccount(String query){
        String response = "OK";
        PersonModel personModel;
        ResultSet rs = null;
        System.out.println(query);
        try {
            rs = this.dbc.select(query);
            if(rs.next()){
                personModel = new PersonModel(rs.getInt("IDPerson"), rs.getString("Name"), rs.getString("Gender"), rs.getString("Email"), rs.getBoolean("Assigned"),rs.getBoolean("AccountStatus"));
                String newAccount = "Insert INTO tblaccount (PersonID, username, password, usertype) VALUES( " 
                                    + personModel.getID() + ",'user" + personModel.getName() + "','1234','2')" ;
                System.out.println(newAccount);
                this.dbc.insert(newAccount);
            }
        } catch (SQLException ex) {
            response = "SQLException";
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    
    private String viewMyTask(ObjectOutputStream writer) throws IOException{
        ResultSet rs = null;
        String response = "OK";
        String Task = ""; //naa na sulod
        TaskMemberModel memberModel;
        TaskModel taskModel;
        
        Hashtable<String, String> ht = new Hashtable<String,String>();
        
        String query = "Select * from tbltaskmember where PersonID = " + am.getPersonID();
        String myTask;
        
        try {
            rs = this.dbc.select(query);
            
            if(rs.next()){
                memberModel = new TaskMemberModel(rs.getInt("PersonID"), rs.getString("Name"), rs.getInt("TaskID"));
                myTask = "Select * from tbltasks where TaskID = " + memberModel.getTaskID();
                ResultSet set = this.dbc.select(myTask);
                
                if(set.next()){
                    taskModel = new TaskModel(set.getInt("TaskID"), set.getString("TaskName"), set.getInt("TaskSize"));
                    Task = taskModel.getTaskName();
                    String member = "Select * from tbltaskmember where TaskID = " + Integer.toString(taskModel.getTaskID());
                    ResultSet rset = this.dbc.select(member);
                    
                    while(rset.next()){
                        memberModel = new TaskMemberModel(rset.getInt("PersonID"), rset.getString("Name"), rset.getInt("TaskID"));
                        ht.put(memberModel.getPerson(), Task);
                    }
                    writer.writeObject(ht);
                    writer.flush();
                }
            }else{
                response = "You have no Task";
            }
        } catch (SQLException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
        
    }
    
    private String readAccount(String query, ObjectOutputStream writer) throws IOException {
        String response = "OK";
        ResultSet rs = null;
        try {
            rs = dbc.select(query);
            //this.accountsList = am.populateAccounts(rs);
            if(!rs.next()){
                response = "Account Not found";
            }else{
                am = new AccountModel(rs.getInt("IDAccount"), rs.getInt("PersonID"), rs.getString("username"), rs.getString("password"), rs.getInt("usertype"));
                writer.writeObject(am);
                writer.flush();
//                bon.write(Integer.toString(rs.getInt("usertype")) + "\n" + rs.getString("username") + "\n");
//                bon.flush();
            }
        } catch (SQLException ex) {
            response = "Account not found";
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }
    
    private void logout(Socket sock){
        
        try {
            sock.close();
        } catch (IOException ex) {
            Logger.getLogger(TMSServerController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }

    private String loadMembers(String query,ObjectOutputStream writer) throws IOException{
        ResultSet rs = null;
        memberList.clear();
        String response = "OK";
        
        TaskMemberModel memberModel;
        
        try {
            rs = this.dbc.select(query);
            
            while(rs.next()){
                memberModel = new TaskMemberModel(rs.getInt("PersonID"),rs.getString("Name"), rs.getInt("TaskID"));
                memberList.add(memberModel);
            }
            
            writer.writeObject(memberList);
            writer.flush();
        } catch (SQLException ex) {
            System.out.println(ex);
            response = "SQLException";
        }
        
        
        return response;
    }
    
    private String insertMembers(String query){
        System.out.println(query);
        String response = "OK";
        try{
            this.dbc.insert(query);
        }catch(SQLException ex){
            response = "SQLException";
        }
        return response;
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
                person = new PersonModel(rs.getInt("IDPerson"),rs.getString("Name"), rs.getString("Gender"), rs.getString("Email"),rs.getBoolean("Assigned"), rs.getBoolean("AccountStatus"));
                personList.add(person);
            }
            writer.writeObject(personList);
            writer.flush();
            
        }catch(SQLException ex){
            response = "SQL Exception";
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
        System.out.println(query);
        try{
            this.dbc.insert(query);
        }catch (SQLException ex){
            response = "Adding Person Denied";
            System.out.println(ex);
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
