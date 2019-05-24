/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.AccountModel;
import Model.PersonModel;
import gui.CreateAccount;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tagactaciv_sd205a2
 */
public class viewPeopleController {
    viewPeople vp;
    dashboard db;
    DefaultTableModel model;
    ArrayList<PersonModel> personList = new ArrayList();
    
    private String name;
    
    ObjectInputStream read;
    
    CreateAccount ca;
    
    public viewPeopleController(JFrame vp, JFrame db,ArrayList personList,DefaultTableModel tbl ) throws ClassNotFoundException {
        this.model = tbl;
        this.personList = personList;
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
        
        vp.getBtncreateAccount().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                name = model.getValueAt(vp.getjTable1().getSelectedRow(), 0).toString();
                ca = new CreateAccount();
                vp.setVisible(false);
                
                try {
                    createAccountController cac = new createAccountController(vp,name,ca,getPerson());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(viewPeopleController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private PersonModel getPerson() throws ClassNotFoundException{
        PersonModel personModel = null;
        try(Socket socket = new Socket(InetAddress.getByName("localhost"),4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ObjectInputStream read = new ObjectInputStream(socket.getInputStream());
                writer.println("getPerson");
                writer.println("Select * from tbladdpeson where Name = '"+ name + "'");
                
                personModel = (PersonModel) read.readObject();
                reader.readLine();
                
            }
        }catch(IOException ex){
            
        }
        return personModel;
    }

    private void loadPeople() throws ClassNotFoundException {
        model.getDataVector().clear();
        try(Socket socket = new Socket(InetAddress.getByName("localhost"),4000 ) ){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)){
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                read = new ObjectInputStream(socket.getInputStream());
                writer.println("loadPeople");
                writer.println("Select * from tbladdpeson");
                personList = (ArrayList<PersonModel>) read.readObject();
                
                String response = reader.readLine();
                
                if(response.equals("OK")){
                    Object[] arr = new Object[3];
                    for(int x = 0; x < personList.size(); x++){
                        //arr[0] = personList.get(x).getID();
                        arr[0] = personList.get(x).getName();
                        arr[1] = personList.get(x).getGender();
                        arr[2] = personList.get(x).getEmail();
                        model.addRow(arr);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, response);
                }
            }
        }catch(IOException ex){
            System.out.println("Di mao");
        }
    }
    
}
