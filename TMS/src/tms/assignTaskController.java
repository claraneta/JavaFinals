/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.PersonModel;
import Model.TaskModel;
import gui.AssignTask;
import gui.dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Model.*;

/**
 *
 * @author student
 */
public class assignTaskController {
    AssignTask at;
    dashboard db;
    ArrayList <PersonModel> personList;
    ArrayList <TaskModel> taskList;
    ArrayList <TaskMemberModel> memberList;
    
    ObjectInputStream read;
    PersonModel person;
    DefaultTableModel model;
    private String taskSize;
    
    ObjectOutputStream writer;
    ObjectInputStream reader;
    
    TaskMemberModel membermodel;
    
    
    assignTaskController(dashboard db, AssignTask at,DefaultTableModel model) throws ClassNotFoundException {
        this.model = model;
        this.db = db;
        this.at = at;
        at.setVisible(true);
        initListener();
        loadTasks();
        loadPeople();
    }

    private void initListener() {
        model.getDataVector().clear();
        at.getBtnAdd().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Object[] arr = new Object[2];
                arr[0] = at.getCmbMember().getItemAt(at.getCmbMember().getSelectedIndex());
                String name = arr[0].toString();
                arr[1] = getSex(name);
                
                if(model.getRowCount() + 1 > Integer.parseInt(taskSize)){
                    JOptionPane.showMessageDialog(null, "Number of members in this task is only " + taskSize);
                }else{
                    model.addRow(arr);
                    at.getCmbMember().removeItem(at.getCmbMember().getSelectedItem());
                }
                
            }
        });
        
        at.getBtnOk().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String taskname = at.getCmbtaskname().getItemAt(at.getCmbtaskname().getSelectedIndex()).toString();
                System.out.println(taskname);
                if(taskname.equalsIgnoreCase("Select TaskName")){
                    JOptionPane.showMessageDialog(null, "Invalid Task Name");
                }else{
                    at.getLbTaskname().setText(taskname);
                    taskSize = getSize(taskname);
                    at.getLbTaskSize().setText(taskSize);
                    at.getCmbtaskname().removeItem(at.getCmbtaskname().getSelectedItem());
                    at.getCmbtaskname().setEnabled(false);
                    at.getBtnOk().setEnabled(false);
//                    model.setRowCount(Integer.parseInt(at.getLbTaskSize().getText()));
                }
                
            }
        });
        
        at.getBtnsubmit().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String personId;
                String personName;
                String taskID;
                String taskname = at.getCmbtaskname().getSelectedItem().toString();
                memberList = new ArrayList();
                System.out.println(model.getRowCount());
                for(int x = 0; x < model.getRowCount(); x++ ){
                    //membermodel = new TaskMemberModel()
                    String personname = model.getValueAt(x, 0).toString();
                    
                    personId = Integer.toString(personID(personname));
                    personName = personname(personname);
                    taskID = Integer.toString(taskID(taskname));
                    
                    membermodel = new TaskMemberModel(Integer.parseInt(personId), personName, Integer.parseInt(taskID));
                    memberList.add(membermodel);
                   
                    
                }
                
                try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                    try(PrintWriter out = new PrintWriter(socket.getOutputStream() , true)){
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        writer = new ObjectOutputStream(socket.getOutputStream());
                        out.println("insertMembers");
                        writer.writeObject(memberList);
                        writer.flush();
                        
                        String serverResponse = reader.readLine();
                        
                        if(serverResponse.contains("OK")){
                            JOptionPane.showMessageDialog(null, "Successfully Assigned");
                        }else{
                            JOptionPane.showMessageDialog(null, serverResponse);
                        }
                       
                    }
//                    writer.writeUTF("insertMembers");
                    
   
                    
                }catch(IOException ex){
                    
                }
            }
        });
        
        at.getBtnCancel().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                at.dispose();
                db.setVisible(true);
            }
        });
    }
    
    private int taskID(String name){
        int ID = 0;
        
        for(int x = 0; x < taskList.size(); x++){
            if(taskList.get(x).getTaskName().equals(name) ){
               ID = taskList.get(x).getTaskID();
            }
        }
   
        return ID;
    }
    
    private String personname(String name){
        String pname ="";
        for(int x = 0; x < personList.size(); x++){
            if(personList.get(x).getName().equals(name) ){
               pname = personList.get(x).getName();
            }
        }
        return pname;
    }
    
    private int personID(String name){
        int ID = 0;
        for(int x = 0; x < personList.size(); x++){
            if(personList.get(x).getName().equals(name) ){
                ID = personList.get(x).getID();
            }
        }
        return ID;
    }
    
    private String getSex(String name){
        String gender = "";
        for(int x = 0; x < personList.size(); x++){
            if(personList.get(x).getName().equals(name)){
                gender = personList.get(x).getGender();
            }
        }
        
        return gender;
    }
    
    private String getSize(String taskname){
        String size = "";
        for(int x = 0; x< taskList.size(); x++){
            if(taskList.get(x).getTaskName().equals(taskname)){
                size = Integer.toString(taskList.get(x).getTasksize());
            }
        }
        return size;
    }

    private void loadPeople() throws ClassNotFoundException {
        personList = new ArrayList();
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadPeople");
                writer.println("Select * from tbladdpeson");
                
                personList = (ArrayList<PersonModel>) read.readObject();//accept the model from the server
                String serverResponse = reader.readLine();//read the response from the server
                
                if(serverResponse.contains("OK")){
                    for(int x =0; x < personList.size();x++){
                        at.getCmbMember().addItem(personList.get(x).getName());
                    }
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(assignTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTasks() throws ClassNotFoundException {
        taskList = new ArrayList();
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadTasks");
                writer.println("Select * from tbltasks");
                
                taskList = (ArrayList<TaskModel>) read.readObject();//accept the model from the server
                String serverResponse = reader.readLine();//read the response from the server
                
                if(serverResponse.contains("OK")){
                    for(int x = 0; x < taskList.size(); x++){
                        at.getCmbtaskname().addItem(taskList.get(x).getTaskName());
                    }
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(assignTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}