/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmsserverModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2ndyrGroupA
 */
public class AccountModel{

    private static final long serialVersionUID = 1L;
    private int IDAccount;
    private String username;
    private String password;
    private int usertype;

    /**
     * @return the itemID
     */
    public AccountModel(int id, String uname, String pass, int utype) {
        this.IDAccount = id;
        this.username = uname;
        this.password =pass ;
        this.usertype=utype;
    }
    public AccountModel(String uname, String pass){
        this.username = uname;
        this.password = pass;
    }
    public ArrayList populateAccounts(ResultSet rs){
        ArrayList accountList = null;
        try {
            if(rs.next()){
                accountList = new ArrayList<>();
                do{
                    accountList.add(new AccountModel(rs.getInt("IDAccount"),rs.getString("username"),rs.getString("password"),rs.getInt("usertype")));
                }while(rs.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountList;
    }
    public int getIDAccount() {
        return IDAccount;
    }

    public void setIDAccount(int IDAccount) {
        this.IDAccount = IDAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    

    public Object[] toArray() {
        Object[] itemArr = new Object[4];
        itemArr[0] = this.getIDAccount();
        itemArr[1] = this.getUsername();
        itemArr[2] = this.getPassword();
        itemArr[2] = this.getUsertype();

        return itemArr;
    }
}
