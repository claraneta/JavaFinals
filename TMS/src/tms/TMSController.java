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
    
    //tablemodels for view
    DefaultTableModel tableModel;
    DefaultTableModel tblvPeople;
    DefaultTableModel tblTask;
    
    //staff view
    Sdashboard sd;
    changePass cp;
    viewMytask vmt;
    
    //controllers
    addPersonController apc;
    viewPeopleController vpc;
    createTaskController ctc;
    viewTaskController vtc;
    
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
                        
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String type = reader.readLine().toString();
                        if (type.contains("1")){
                            type = "1";
                        }
                        
                        String name = reader.readLine();
                        String responseFromServer = reader.readLine();
                        if(responseFromServer.equals("OK")){
                            su.dispose();
                            
                            if(type.equals("1")){
                                db = new dashboard();
                                db.setVisible(true);
                                dbinitListener();
                            }else{
                                sd = new Sdashboard();
                                sd.getLblname().setText(name);
                                sd.setVisible(true);
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Account not found");
                        }
                    }
                    
                    
                } catch (IOException ex) {
                    System.out.println(ex);
                }  

            }
            
        });
        
    }
    
    private void dbinitListener() {
        
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
                
                ct = new CreateTask();
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
                
            }
        });
    }
    
}
