package com.cs309.loginscreen.Model;

/**
 * This is the User Info Object for the project
 * It stores the User Info:
 * Stores: {User Name, User Active Status}
 *
 * @author Bofu
 */
public class UserInfo {

    String userName;    // User Name
    boolean active;     // User Active Status

    /**
     * Constructs an empty new user Info Object
     */
    public UserInfo() {
    }

    /**
     * Set the User name
     * @param name - User Name
     */
    public void setUserName(String name) {
        this.userName = name;
    }

    /**
     * Returns the User Name
     * @return - User Name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the User Active Status
     * @return - True if Active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Set the user Active Status
     * @param status - True if Active
     */
    public void setActive(boolean status) {
        this.active = status;
    }
}
