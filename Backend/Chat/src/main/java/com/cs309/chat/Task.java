package com.cs309.chat;

import javax.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String userName;
    private String taskName;
    private String dueDate;
    private String dueTime;

    public Task() {
        this.id = id;
        this.userName = userName;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public Task(Integer num, String user,String task,String date, String time)
    {
        this.id = num;
        this.userName = user;
        this.taskName = task;
        this.dueDate = date;
        this.dueTime = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getDueTime() {
        return dueTime;
    }
}
