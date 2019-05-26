/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import gui.AddPerson;
import gui.dashboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author tagactaciv_sd205a2
 */
public class addPersonController {
    AddPerson ap;
    dashboard db;
    
    
    public addPersonController(JFrame addPerson, JFrame dashboard) {
        this.db = (dashboard) dashboard;
        this.ap = (AddPerson) addPerson;
        initListener();
    }

    private void initListener() {
        ap.setVisible(true);
        
        ap.getBtnback().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ap.dispose();
                db.setVisible(true);
            }
        });
        
        ap.getBtnadd().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(ap.getTfemail().getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Please populate all fields");
                }else if(ap.getTfname().getText().equals("") ){
                    JOptionPane.showMessageDialog(null,"Please populate all fields");
                }else{
                    try (Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)) {
                        try(PrintWriter writer = new PrintWriter(socket.getOutputStream() , true)){
                            writer.println("addPerson");
                            writer.println("Insert into tbladdpeson (Name, Gender, Email, Assigned) VALUES ( '" + ap.getTfname().getText() + "','"
                            + ap.getCmbgender().getSelectedItem().toString() + "','" + ap.getTfemail().getText() + "',false)");
    //                        System.out.println("Insert into tbladdpeson (Name, Gender, Email, Assigned) VALUES ( '" + ap.getTfname().getText() + "','"
    //                        + ap.getCmbgender().getSelectedItem().toString() + "','" + ap.getTfemail().getText() + "','False');");

                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            String responseFromServer = reader.readLine();

                            System.out.println("Server Response : " + responseFromServer);

                            if(responseFromServer.contains("OK")){
                                responseFromServer = "OK";
                            }

                            switch(responseFromServer){
                                case "OK" : JOptionPane.showMessageDialog(null, "Successfully added");clearText(); break;
                                default: JOptionPane.showMessageDialog(null, responseFromServer);                    
                            }

                        }                   
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
                
                
            }
        });
    }
    
    
    
    private void clearText(){
        ap.getTfemail().setText("");
        
        ap.getTfname().setText("");
    }
    
}
