/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import Model.AccountModel;
import guiStudent.Sdashboard;
import guiStudent.changePass;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author 2ndyrGroupB
 */
public class changePassController {
    Sdashboard sd;
    changePass cp;
    AccountModel am;
    String currentPass;
    changePassController(Sdashboard sd, changePass cp) throws ClassNotFoundException {
        this.sd = sd;
        this.cp = cp;
        
        cp.setVisible(true);
        myAccount();
        initListener();
        
    }

    private void initListener() {
        System.out.println(currentPass);
        cp.getBtncancel().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                cp.dispose();
                sd.setVisible(true);
            }
        });
        
        cp.getBtnsave().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                if(!cp.getPfcurpass().getText().equals(currentPass)){
                    JOptionPane.showMessageDialog(null, "Current password does not match");
                }else if(cp.getPfnewpass().getText().equals("") && cp.getPfrepass().getText().equals("")){
                   JOptionPane.showMessageDialog(null, "Input all fields");
                }else if(!cp.getPfnewpass().getText().equals(cp.getPfrepass().getText())){
                    JOptionPane.showMessageDialog(null, "New Password does not match");
                }else{
                    try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
                        try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                            writer.println("updatePass");
                            writer.println("Update tblaccount set password = '" + cp.getPfnewpass().getText() + "' where personID = " + am.getPersonID());
                            
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            
                            String serverResponse = reader.readLine();
                            
                            if(serverResponse.contains("OK")){
                                JOptionPane.showMessageDialog(null, "Password successfully updated");
                                cp.dispose();
                                sd.setVisible(true);
                            }else{
                                JOptionPane.showMessageDialog(null, "Sorry ! Dili pwede");
                            }
                        }
                    }catch(IOException ex){

                    }
                }
                
                
            }
        });
        
    }

    private void myAccount() throws ClassNotFoundException {
        try(Socket socket = new Socket(InetAddress.getByName("localhost"), 4000)){
            try(PrintWriter writer = new PrintWriter(socket.getOutputStream(),true)){
                writer.println("myAccount");
                ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                am = (AccountModel) reader.readObject();
                currentPass = am.getPassword();
            }
        }catch(IOException ex){
            
        }
    }
    
}
