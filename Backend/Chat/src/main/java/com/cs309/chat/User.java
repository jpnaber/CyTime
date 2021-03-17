package com.cs309.chat;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String userName;

    @Column
    private String passW;

    @Column
    private String uStatus;

    public User() {
        this.id = id;
        this.userName = userName;
        this.passW = passW;
        this.uStatus = uStatus;
    }

    public User(Integer num, String user,String pass,String stat)
    {
        this.id = num;
        this.userName = user;
        this.passW = pass;
        this.uStatus = stat;
    }


    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return passW;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passW = password;
    }

    public boolean verifyPassword(String p) {
        return passW.equals(p);
    }

    public String getuStatus() {
        return this.uStatus;
    }

    public void setStatus(String status) {
        this.uStatus = status;
    }

}
