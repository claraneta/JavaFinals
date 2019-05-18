/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMSModel;

/**
 *
 * @author 2ndyrGroupA
 */
public class AccountModel {

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
