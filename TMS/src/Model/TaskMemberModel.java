/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author 2ndyrGroupB
 */
public class TaskMemberModel implements Serializable {

    private int personID;
    private String person;
    private int taskID;
    

    /**
     * @return the itemID
     */
    
    public TaskMemberModel(int personID, String person, int taskID) {
        this.personID = personID;
        this.person = person;
        this.taskID = taskID;
        
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    

  

    public Object[] toArray() {
        Object[] itemArr = new Object[3];
        itemArr[0] = this.getPersonID();
        itemArr[1] = this.getPerson();
        itemArr[2] = this.getTaskID();
        

        return itemArr;
    }

}
