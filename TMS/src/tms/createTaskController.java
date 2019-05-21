/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import gui.CreateTask;
import gui.dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 2ndyrGroupB
 */
public class createTaskController {
    dashboard db;
    CreateTask ct;
    DefaultTableModel model;
    public createTaskController(JFrame db, JFrame ct) {
        this.db = (dashboard) db;
        this.ct = (CreateTask) ct;
        
        ct.setVisible(true);
        initListener();
    }

    private void initListener() {
        this.ct.getBtnback().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ct.dispose();
                db.setVisible(true);
            }
        });
        
        this.ct.getBtncreatetask().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String taskname = ct.getTftaskname().getText();
                String size = ct.getTftasksize().getText();
                
                try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                    try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                        writer.println("InsertTask");
                        writer.println("Insert INTO tbltasks (TaskName, TaskSize) VALUES ('" + taskname + "','" + size + "')");
                        
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String serverResponse = reader.readLine();
                        
                        if(serverResponse.contains("OK")){
                            serverResponse = "OK";
                        }
                        
                        switch (serverResponse){
                            case "OK" : JOptionPane.showMessageDialog(null, "Successfully Created !");ct.getTftaskname().setText("");ct.getTftasksize().setText("");break;
                            default : JOptionPane.showMessageDialog(null, serverResponse);break;       
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(createTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
