/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMSModel;

import java.awt.Image;

/**
 *
 * @author 2ndyrGroupB
 */
public class TaskModel {
    
    private int TaskID;
    private String TaskName;
    private int Tasksize;
  

    /**
     * @return the itemID
     */
    
    public int getTaskID() {
        return TaskID;
    }

    public TaskModel(int id, String name, int size) {
        this.TaskID = id;
        this.TaskName = name;
        this.Tasksize = size;
    }

    public void setTaskID(int TaskID) {
        this.TaskID = TaskID;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String TaskName) {
        this.TaskName = TaskName;
    }

    public int getTasksize() {
        return Tasksize;
    }

    public void setTasksize(int Tasksize) {
        this.Tasksize = Tasksize;
    }

   
   
    public Object[] toArray(){
        Object[] itemArr = new Object[3];
        itemArr[0] = this.getTaskID();
        itemArr[1] = this.getTaskName();
        itemArr[2] = this.getTasksize();
        
        return itemArr;
    }
}