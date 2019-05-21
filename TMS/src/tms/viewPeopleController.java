/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.PersonModel;
import gui.dashboard;
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
import javax.swing.JFrame;

/**
 *
 * @author tagactaciv_sd205a2
 */
public class viewPeopleController {
    viewPeople vp;
    dashboard db;
    
    ObjectInputStream read;
    public viewPeopleController(JFrame vp, JFrame db) throws ClassNotFoundException {
        this.vp = (viewPeople) vp;
        this.db = (dashboard) db;
        vp.setVisible(true);
        loadPeople();
        initListener();
    }

    private void initListener() {
        vp.getBtnback().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                vp.dispose();
                db.setVisible(true);
            }
        });
    }

    private void loadPeople() throws ClassNotFoundException {
        try(Socket socket = new Socket(InetAddress.getByName("localhost"),4000 ) ){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadPeople");
                writer.println("Select * from tbladdpeson");
                PersonModel person = (PersonModel) (read.readObject());
                System.out.println(person.getName());
                String response = reader.readLine();
                
                switch(response){
                    case "OK": System.out.println("na hala");break;
                    default: System.out.println("nag pataka");break;
                }
            }
        }catch(IOException ex){
            System.out.println("Di mao");
        }
    }
    
}
