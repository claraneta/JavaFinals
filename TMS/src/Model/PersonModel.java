/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author tagactaciv_sd205a2
 */
public class PersonModel implements Serializable{
    
    private String name;
    private String gender;
    private String email;
    private int ID;
    private boolean assigned;
    private boolean accountStatus;
    public PersonModel(int ID,String name, String gender, String email,boolean assigned, boolean accountStatus) {
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.assigned = assigned;
        this.accountStatus = accountStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    
    public Object[] toArray(){
        Object[] itemArr = new Object[4];
        itemArr[0] = this.getID();
        itemArr[1] = this.getName();
        itemArr[2] = this.getGender();
        itemArr[3] = this.getEmail();
        
        return itemArr;
    }
    
    
}
