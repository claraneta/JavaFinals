/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

/**
 *
 * @author 2ndyrGroupA
 */
public class AccountModel implements Serializable{

    private int IDAccount;
    private int PersonID;
    private String username;
    private String password;
    private int usertype;

    /**
     * @return the itemID
     */
    public AccountModel(int accountID, int personID, String uname, String pass, int utype) {
        this.IDAccount = accountID;
        this.PersonID = personID;
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
                    accountList.add(new AccountModel(rs.getInt("IDAccount"),rs.getInt("PersonID"),rs.getString("username"),rs.getString("password"),rs.getInt("usertype")));
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

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int PersonID) {
        this.PersonID = PersonID;
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
