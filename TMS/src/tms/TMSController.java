/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms;

import gui.signUp;
import gui.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 2ndyrGroupB
 */
public class TMSController {

    signUp su;
    Tasks tasks;
    dashboard db;
    AddPerson ap;
    CreateTask ct;
    ViewTask vt;
    DefaultTableModel tableModel;
    Hashtable HT = new Hashtable<Integer, Tasks>();
    String URL = "jdbc:mysql://localhost/dbmanager";
    String pass = "";
    String user = "root";

    public TMSController() {
        su = new signUp();
        su.setVisible(true);
        tableModel = new DefaultTableModel(0, 0);
        tableModel.addColumn("Task ID");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Task Size");
        initAction();
    }

    public void initAction() {
        this.su.getBtnlogin().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                String p = su.getPfpass().getText();
                String u = su.getTfusername().getText();

                try {

                    Connection con = DriverManager.getConnection(URL, user, pass);
                    Statement stat = con.createStatement();
                    ResultSet rs = stat.executeQuery("SELECT * FROM tblaccount;");

                    String usern;
                    String passw;
                    int id;
                    int cnt = 0;
                    int scnt = 0;
                    while (rs.next()) {
                        scnt++;
                        usern = rs.getString("username");
                        passw = rs.getString("password");
                        if (u.equals(usern)) {
                            if (p.equals(passw)) {
                                JOptionPane.showMessageDialog(null, "You're log in.");
                                su.dispose();
                                db = new dashboard();
                                db.setVisible(true);
                                ActionArea();

                                cnt = -1;
                                break;
                            }

                        }
                        cnt++;

                    }

                    if (scnt == cnt) {

                        JOptionPane.showMessageDialog(null, "Wrong Credentials.");
                        su.getTfusername().setText("");
                        su.getPfpass().setText("");

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(TMS.class.getName()).log(Level.SEVERE, null, ex);
                }        // TODO add y//To change body of generated methods, choose Tools | Templates.
            }

            public void ActionArea() {
                db.getBtnaddpeople().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        ap = new AddPerson();
                        ap.setVisible(true);
                        ap.setLocation(400, 400);

                        Addpeople();
                        ap.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }

                    public void Addpeople() {
                        ap.getBtnadd().addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                try {

                                    Connection con = DriverManager.getConnection(URL, user, pass);
//                                    Statement stat = con.createStatement();
//                                    ResultSet rs = stat.executeQuery("SELECT * FROM tblaccount;");

                                    String query = " insert into tbladdpeson (Name, Email)"
                                            + " values (?, ?)";

                                    // create the mysql insert preparedstatement
                                    PreparedStatement preparedStmt = con.prepareStatement(query);

                                    preparedStmt.setString(1, ap.getTfname().getText().toString());
                                    preparedStmt.setString(2, ap.getTfemail().getText().toString());
                                    // execute the preparedstatement
                                    preparedStmt.execute();
                                    System.out.println("Data Added");
                                    JOptionPane.showMessageDialog(null, "New Person Added.");
                                    ap.getTfname().setText("");
                                    ap.getTfemail().setText("");

                                    con.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TMS.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    }
                });
                db.getBtncreate().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {

                        ct = new CreateTask();
                        ct.setVisible(true);
                        ct.setLocation(400, 400);
                        CreatingTask();
                        ct.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    }

                    public void CreatingTask() {
                        ct.getBtncreatetask().addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                try {

                                    Connection con = DriverManager.getConnection(URL, user, pass);
//                                    Statement stat = con.createStatement();
//                                    ResultSet rs = stat.executeQuery("SELECT * FROM tblaccount;");

                                    String query = " insert into tbltasks (TaskName, TaskSize)"
                                            + " values (?, ?)";

                                    // create the mysql insert preparedstatement
                                    PreparedStatement preparedStmt = con.prepareStatement(query);

                                    preparedStmt.setString(1, ct.getTftaskname().getText());
                                    preparedStmt.setInt(2, Integer.parseInt(ct.getTftasksize().getText()));
                                    // execute the preparedstatement
                                    preparedStmt.execute();
                                    System.out.println("New Task Added");
                                    JOptionPane.showMessageDialog(null, "New Task Added.");
                                    ct.getTftaskname().setText("");
                                    ct.getTftasksize().setText("");

                                    con.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(TMS.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });

                    }
                });
                db.getBtnview().addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        vt = new ViewTask(tableModel);
                        vt.setVisible(true);
                        vt.setLocation(40, 40);
                        ViewTasking();
                        vt.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        vt.getBtnback().addActionListener(new ActionListener(){

                            @Override
                            public void actionPerformed(ActionEvent ae) {
                             vt.dispose();
                             tableModel.setRowCount(0);
                            }
                        });

                    }

                    public void ViewTasking() {
                        try {

                            Connection con = DriverManager.getConnection(URL, user, pass);
                            Statement stat = con.createStatement();
                            ResultSet rs = stat.executeQuery("SELECT * FROM tbltasks;");

                            while(rs.next()) {
                                tasks = new Tasks(rs.getInt("TaskID"), rs.getString("TaskName"), rs.getInt("TaskSize"));
                                HT.put(tasks.getTaskID(), tasks);
                                tableModel.addRow(tasks.toArray());
                            }

                            con.close();

                        } catch (SQLException ex) {
                            Logger.getLogger(TMS.class.getName()).log(Level.SEVERE, null, ex);
                        }
//                        tableModel.getDataVector().removeAllElements();
                    }
                });
                db.getBtnviewpeople().addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent ae) {
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });

            }
        });

    }

}
