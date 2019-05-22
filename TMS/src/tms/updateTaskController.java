/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import gui.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 2ndyrGroupB
 */
public class updateTaskController {
    UpdateTask2 ut;
    ViewTask vt;
    private String rowId;
    updateTaskController(UpdateTask2 ut, ViewTask vt, String taskname, String size, String rowId) {
        this.rowId = rowId;
        this.vt = vt;
        this.ut= ut;
        ut.setVisible(true);
        ut.getTftaskname().setText(taskname);
        ut.getTfteamsize().setText(size);
        initListener();
    }

    private void initListener() {
       this.ut.getBtncancel().addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               ut.dispose();
           }
       });
       
       this.ut.getBtnupdate().addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               
               
               String taskname = ut.getTftaskname().getText();
               String size = ut.getTfteamsize().getText();
               
               if(taskname.equals("") && taskname.equals("")){
                   JOptionPane.showMessageDialog(null,"Please input all fields!");
               }else{
                   try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                       try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                           writer.println("updateTask");
                           writer.println("Update tbltasks set TaskName = '" + taskname + "', TaskSize = '" + size + "' where TaskID = '" + rowId + "'");
                           BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                           String serverResponse = reader.readLine();
                           if(serverResponse.contains("OK")){
                               JOptionPane.showMessageDialog(null, "Task is succesfully updated");
                               
                           }else{
                               JOptionPane.showMessageDialog(null, serverResponse);
                           }
                           
                       }
                   }catch(IOException ex){
                       
                   }
               }
           }
       });
    }
    
}
