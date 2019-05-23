/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tms;

import Model.TaskMemberModel;
import Model.TaskModel;
import gui.dashboard;
import gui.viewSummary;
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
 * @author student
 */
public class viewTaskSummaryController {
    
    viewSummary vs;
    DefaultTableModel tblViewTasks;
    DefaultTableModel tblViewMembers;
    dashboard db;
    ArrayList <TaskModel> taskList = new ArrayList();
     ArrayList<TaskMemberModel> memberList = new ArrayList();
    ObjectInputStream read;
    viewTaskSummaryController(viewSummary vs, DefaultTableModel tblViewTasks, DefaultTableModel tblViewMembers,dashboard db) {
       this.vs = vs;
       this.tblViewMembers = tblViewMembers;
       this.tblViewTasks = tblViewTasks;
       this.db = db;
       tblViewMembers.getDataVector().clear();
       vs.setVisible(true);
       loadTasks();
       initListener();
    }

    private void initListener() {
        
        vs.getBtnBack().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                vs.dispose();
                db.setVisible(true);
            }
        });
        
        vs.getBtnOkay().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                loadMembers();
            }
        });
        
    }
    
    private void loadMembers(){
        
        String taskname = tblViewTasks.getValueAt(vs.getTblTasks().getSelectedRow(), 0).toString();
        
        String taskID = "";
        for(int x = 0; x< taskList.size(); x++){
            if(taskList.get(x).getTaskName().equals(taskname)){
                taskID = Integer.toString(taskList.get(x).getTaskID());
            }
        }
        System.out.println("TaskID : " + taskID);
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadMembers");
                writer.println("Select * from tbltaskmember where TaskID = " + taskID);
                
                try {
                    memberList = (ArrayList<TaskMemberModel>) read.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(viewTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String serverResponse = reader.readLine();
                
                if(serverResponse.contains("OK")){
                    System.out.println("REsponse from Server" + serverResponse);
                    System.out.println(memberList.size() + "\n");
                    if(memberList.size() == 0){
                        if(tblViewMembers.getRowCount()> 0){                            
                            tblViewMembers.setRowCount(0);
                        }
                    }else{
                        Object[] arr = new Object[1];
                        for(int x = 0; x < memberList.size(); x++){
                            arr[0] = memberList.get(x).getPerson();
                            tblViewMembers.addRow(arr);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(viewTaskSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void loadTasks() {
        tblViewTasks.getDataVector().clear();
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadTasks");
                writer.println("Select * from tbltasks");
                
                try {
                    taskList = (ArrayList<TaskModel>) read.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(viewTaskController.class.getName()).log(Level.SEVERE, null, ex);
                }
                String serverResponse = reader.readLine();
                
                if(serverResponse.contains("OK")){
                    Object[] arr = new Object[1];
                    for(int x = 0; x < taskList.size(); x++){
                        //arr[0] = personList.get(x).getID();
                        arr[0] = taskList.get(x).getTaskName();
                        tblViewTasks.addRow(arr);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(viewTaskSummaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
