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
    public TMSController() {
        
    }



    

    

}
