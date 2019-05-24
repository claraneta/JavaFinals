/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.PersonModel;
import gui.CreateAccount;
import gui.viewPeople;
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
import javax.swing.JOptionPane;

/**
 *
 * @author 2ndyrGroupB
 */
public class createAccountController {
    
    viewPeople vp;
    String personName;
    CreateAccount ca;
    
    PersonModel personModel;
    
    private int ID;
    
    createAccountController(viewPeople vp, String name, CreateAccount ca, PersonModel person) {
        this.vp = vp;
        this.personName = name;
        this.ca = ca;
        this.personModel = person;
        ca.setVisible(true);
        ca.getLblname().setText(personName);
        System.out.println("ID of " + personName + " : " + personModel.getID());
        getPersonID();
        initListener();
        
        //loadPeople();
    }

    private void initListener() {
        ca.getBtncreate().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String person = checkPerson();
                if(person.contains("OK")){
                    try(Socket socket = new Socket(InetAddress.getByName("localhost"),4000 ) ){
                        try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            writer.println("createAccount");
                            writer.println("Select * from tbladdpeson where Name = '" + personName + "'");

                            String response = reader.readLine();

                            if(response.contains("OK")){
                                JOptionPane.showMessageDialog(null, "Successfully created account");
                            }else{
                                JOptionPane.showMessageDialog(null, response);
                            }
                        }
                    }catch(IOException ex){
                        System.out.println("Di mao");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, person);
                } 
            }
        });
    }

    //method that will check if the person has already account
    private String checkPerson() {
        String response ="";
        
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                BufferedReader reader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer.println("checkPerson");
                writer.println("Select * from tblaccount where PersonID = " + personModel.getID());
                System.out.println("Select * from tblaccount where PersonID = " + personModel.getID());
                response = reader.readLine();
                
            }
        }catch (IOException ex){
            
        }
        return response;
    }

    //method that will get the ID of the displayed name in the CreateAccountView
    private void getPersonID() {
        if(personModel.getName().equals(this.personName)){
            ID = personModel.getID();
        }
    }
    
}
