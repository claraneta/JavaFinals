/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.TaskModel;
import gui.ViewTask;
import gui.dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 2ndyrGroupB
 */
public class viewTaskController {
    dashboard db;
    ViewTask vt;
    DefaultTableModel model;
    ArrayList <TaskModel> taskList = new ArrayList();
    ObjectInputStream read;
    viewTaskController(dashboard db, ViewTask vt, DefaultTableModel tblTask) {
        this.db = db;
        this.vt = vt;
        this.model = tblTask;
        
        this.vt.setVisible(true);
        loadTasks();
        initListener();
    }

    private void initListener() {
        this.vt.getBtnback().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                vt.dispose();
                db.setVisible(true);
            }
        });
        
        
    }

    private void loadTasks() {
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadTasks");
                writer.println("Select * from tbltasks");
                
                try {
                    taskList = (ArrayList<TaskModel>) read.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(viewTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String response = reader.readLine();
                
                if(response.equals("OK")){
                    Object[] arr = new Object[2];
                    for(int x = 0; x < taskList.size(); x++){
                        //arr[0] = personList.get(x).getID();
                        arr[0] = taskList.get(x).getTaskName();
                        arr[1] = taskList.get(x).getTasksize();
                        
                        model.addRow(arr);
                    }
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(viewTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
