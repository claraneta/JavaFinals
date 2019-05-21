/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import TMSModel.AccountModel;
import TMSModel.TaskModel;
import gui.signUp;
import gui.*;
import guiStudent.Sdashboard;
import guiStudent.changePass;
import guiStudent.viewMytask;
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

/**
 *
 * @author 2ndyrGroupB
 */
public class TMSController {

    signUp su;
    TaskModel tasks;
    dashboard db;
    AddPerson ap;
    CreateTask ct;
    ViewTask vt;
    DefaultTableModel tableModel;
    Sdashboard sd;
    changePass cp;
    viewMytask vmt;
    addPersonController apc;
    Socket soc;
    AccountModel account;
    ObjectOutputStream writer;
    ObjectInputStream reader;
    public TMSController() {
        su = new signUp();
        su.setVisible(true);
        tableModel = new DefaultTableModel(0, 0);
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Task Size");
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
                        String type = reader.readLine();
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
    }
    

}
//                if("staff".equals(su.getTfusername().getText()) && "staff".equals(su.getPfpass().getText())){
//                    System.out.println("Successfully logged in");
//                    su.dispose();
//                    db = new dashboard();
//                    db.setVisible(true);
//                    
//                    db.getBtnaddpeople().addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            db.setVisible(false);
//                            
//                            ap = new AddPerson();
//                            ap.setVisible(true);
//                            
//                            ap.getBtnadd().addActionListener(new ActionListener(){
//                                @Override
//                                public void actionPerformed(ActionEvent e){
//                                    if(ap.getTfemail().getText().equals("")){
//                                        JOptionPane.showMessageDialog(null, "Please input all fields");
//                                    }else{
//                                        ap.setVisible(false);
//                                        db.setVisible(true);
//                                    }
//                                }
//                            });
//                            
//                            ap.getBtnback().addActionListener(new ActionListener(){
//                                @Override
//                                public void actionPerformed(ActionEvent e){
//                                    ap.dispose();
//                                    db.setVisible(true);
//                                }
//                            });
//                        }
//                    });
//                    
//                    db.getBtncreate().addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            db.setVisible(false);
//                            
//                            ct = new CreateTask();
//                            ct.setVisible(true);
//                            
//                            ct.getBtncreatetask().addActionListener(new ActionListener(){
//                                @Override
//                                public void actionPerformed(ActionEvent e){
//                                    if(ct.getTftaskname().getText().equals("") && ct.getTftasksize().getText().equals("")){
//                                        JOptionPane.showMessageDialog(null, "Please input all fields");
//                                    }else{
//                                        //Give the data to the server
//                                        ct.setVisible(false);
//                                        db.setVisible(true);
//                                    }
//                                }
//                            });
//                            
//                            ct.getBtnback().addActionListener(new ActionListener(){
//                                @Override
//                                public void actionPerformed(ActionEvent e){
//                                    ct.dispose();
//                                    db.setVisible(true);
//                                }
//                            });
//                        }
//                    });
//                    
//                    db.getBtnview().addActionListener(new ActionListener(){
//                        @Override
//                        public void actionPerformed(ActionEvent e){
//                            
//                        }
//                    });
//                    
//                }
//                else if(su.getTfusername().equals("student") && su.getPfpass().getText().equals("student")){
//                    su.dispose();
//                    
//                    sd = new Sdashboard();
//                }else{
//                    JOptionPane.showMessageDialog(null, "Incorrect Credentials");
//                }
//               