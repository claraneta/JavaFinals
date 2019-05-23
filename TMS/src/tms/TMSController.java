/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.AccountModel;
import Model.PersonModel;
import Model.TaskModel;
import gui.*;
import guiStudent.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author 2ndyrGroupB
 */
public class TMSController {
    
    //admin view
    signUp su;
    TaskModel tasks;
    dashboard db;
    AddPerson ap;
    CreateTask ct;
    ViewTask vt;
    viewPeople viewP;
    UpdateTask2 ut;
    AssignTask at;
    viewSummary vs;
    
    //tablemodels for view
    DefaultTableModel tableModel;
    DefaultTableModel tblvPeople;
    DefaultTableModel tblTask;
    DefaultTableModel tblassignTask;
    DefaultTableModel tblViewTasks;
    DefaultTableModel tblViewMembers;
    DefaultTableModel tblViewMyTask;
    
    //staff view
    Sdashboard sd;
    changePass cp;
    viewMytask vmt;
    
    //controllers
    addPersonController apc;
    viewPeopleController vpc;
    createTaskController ctc;
    viewTaskController vtc;
    assignTaskController atc;
    viewTaskSummaryController vtsc;
    
    //socket
    Socket soc;
    
    //models
    AccountModel account;
    PersonModel person;
    
    //DataStreams
    ObjectOutputStream writer;
    ObjectInputStream reader;
    
    //Collections
    public static ArrayList <PersonModel> personList = new ArrayList() ;
    
    public TMSController() {
        su = new signUp();
        su.setVisible(true);
        
        initListener();
        
    }
    
    private void initListener(){
        
        su.getBtnlogin().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //account = new AccountModel(su.getTfusername().getText(), su.getPfpass().getText());
                try (Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)) {
                    try(PrintWriter writer = new PrintWriter(socket.getOutputStream() , true)){
                        writer.println("readAccounts");
                        writer.println("Select * from tblaccount where username = '" + su.getTfusername().getText() + "' AND password = '" 
                        + su.getPfpass().getText() + "'");
                        BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                        
                        try {
                            account = (AccountModel) reader.readObject();
                            String serverResponse = read.readLine();
                            if(serverResponse.contains("OK")){
                                su.dispose();
                                
                                if(account.getUsertype() == 1){
                                    db = new dashboard();
                                    db.setVisible(true);
                                    dbinitListener();
                                }else{
                                    sd = new Sdashboard();
                                    sd.setVisible(true);
                                    sdinitListener(account);
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, serverResponse);
                            }
                            

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(TMSController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    
                } catch (IOException ex) {
                    System.out.println(ex);
                }  

            }
            
        });
        
    }
    
    private void sdinitListener(AccountModel am){
        
        tblViewMyTask = new DefaultTableModel(0,0);
        tblViewMyTask.addColumn("Name");
        
        sd.getLblname().setText(am.getUsername());
        
        sd.getBtnviewmytask().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                sd.setVisible(false);
                vmt = new viewMytask(tblViewMyTask);
                vmt.setVisible(true);
                Hashtable <String,String> ht = new Hashtable<String,String>();
                try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                    try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                        writer.println("viewMyTask");
                        ObjectInputStream objectReader = new ObjectInputStream(socket.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        try {
                            ht = (Hashtable<String, String>) objectReader.readObject();
                            ArrayList<String> names = new ArrayList();
                            names.addAll(ht.keySet());
                            Object[] arr = new Object[names.size()];
                            
                            vmt.getLblTaskName().setText(ht.get(names.get(0)));
                            
                            for(int x = 0; x < arr.length;x++){
                                arr[0] = names.get(x);
                                tblViewMyTask.addRow(arr);
                            }
                            
                            
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(TMSController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        String response = reader.readLine();
                        
                    }
                }catch(IOException ex){
                    
                }
            }
        });
    }
    
    
    private void dbinitListener() {
        
        tableModel = new DefaultTableModel(0, 0);
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Task Size");
        
        tblvPeople = new DefaultTableModel(0,0);
        tblvPeople.addColumn("Name");
        tblvPeople.addColumn("Gender");
        tblvPeople.addColumn("Email");
        
        tblTask = new DefaultTableModel(0,0);
        tblTask.addColumn("TaskName");
        tblTask.addColumn("TaskSize");
        
        tblassignTask = new DefaultTableModel(0,0);
        tblassignTask.addColumn("Name");
        tblassignTask.addColumn("Gender");
        
        tblViewTasks = new DefaultTableModel(0,0);
        tblViewTasks.addColumn("Task Name");
        
        tblViewMembers = new DefaultTableModel(0,0);
        tblViewMembers.addColumn("Name");
        
        
        db.getBtnaddpeople().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                db.setVisible(false);
                ap = new AddPerson();
                apc = new addPersonController(ap,db);
                
            }
        });
        
        db.getBtnviewpeople().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                db.setVisible(false);
                
                viewP = new viewPeople(tblvPeople);
                try {
                    vpc = new viewPeopleController(viewP,db,personList,tblvPeople);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TMSController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        db.getBtncreate().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                db.setVisible(false);
                
                ct = new CreateTask();//view
                ctc = new createTaskController(db,ct);
            }
        });
        
        db.getBtnview().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ut = new UpdateTask2();
                vt = new ViewTask(tblTask);
                vtc = new viewTaskController(db,vt,tblTask,ut);
            }
        });
        
        db.getBtnassign().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                at = new AssignTask(tblassignTask);
                db.setVisible(false);
                try {
                    atc = new assignTaskController(db,at,tblassignTask);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TMSController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        db.getBtnViewTaskSummary().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                db.setVisible(false);
                vs = new viewSummary(tblViewTasks,tblViewMembers);
                vtsc = new viewTaskSummaryController(vs,tblViewTasks,tblViewMembers,db);
            }
        });
        
        db.getBtnlogout().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                    try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                        
                        writer.println("logout");
                        db.dispose();
                    }
                }catch(IOException ex){
                    
                }
            }
        });
    }
    
}
