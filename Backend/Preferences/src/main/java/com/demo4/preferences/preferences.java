package com.demo4.preferences;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class preferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String user;

    @Column
    Boolean notifications;

    public preferences() {
        id = 0;
        user = null;
        notifications = true;
    }

    public preferences(Integer id, String user, Boolean notif) {
        this.id = id;
        this.user = user;
        this.notifications = notif;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Boolean toggleNotifications() {
        return notifications;
    }

}
