/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.TaskModel;
import gui.CreateTask;
import gui.UpdateTask2;
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
import javax.swing.JOptionPane;
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
    UpdateTask2 ut;
    updateTaskController utc;
    private String  selectedrowid;
    viewTaskController(dashboard db, ViewTask vt, DefaultTableModel tblTask,UpdateTask2 ut) {
        this.ut = ut;
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
        
        this.vt.getBtnDelete().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //System.out.println(taskList.get(vt.getjTable1().getSelectedRow() ).getTaskID());
                try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                    try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                        writer.println("deleteTask");
                        
                        int id = taskList.get(vt.getjTable1().getSelectedRow() ).getTaskID();
                        String ID = Integer.toString(id);
                        
                        writer.println("Delete from tbltasks where TaskID = " + ID);
                        
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        
                        String serverResponse = reader.readLine();
                        
                        if(serverResponse.contains("OK")){
                            serverResponse = "OK";
                        }
                        
                        switch(serverResponse){
                            case "OK": model.removeRow(vt.getjTable1().getSelectedRow());JOptionPane.showMessageDialog(null, "Successfully Deleted !");
                            break;
                            default : JOptionPane.showMessageDialog(null, serverResponse);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(viewTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        this.vt.getBtnUpdate().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(vt.getjTable1().getSelectedRow() < 0){
                    JOptionPane.showMessageDialog(null, "Select a row to update");
                }else{
                    vt.dispose();
                    selectedrowid = Integer.toString(taskList.get(vt.getjTable1().getSelectedRow()).getTaskID());
                    
                    String taskname = model.getValueAt(vt.getjTable1().getSelectedRow(), 0).toString();
                    String size = model.getValueAt(vt.getjTable1().getSelectedRow(), 1).toString();

                    utc = new updateTaskController(ut,vt,taskname,size,selectedrowid);
                }
                
                
            }
        });
        
        
    }

    private void loadTasks() {
        model.getDataVector().clear();
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
