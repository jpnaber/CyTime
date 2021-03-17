package com.demo4.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class preferenceCtrl {
    @Autowired
    preferenceDB database;
    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";
    List<preferences> list = new ArrayList<preferences>();

    @RequestMapping("/preferences/id")
    Iterable<preferences> requestPreferences(@RequestBody preferences p) {
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "SELECT * FROM MyProject.preferences WHERE id = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, p.getId());
            ResultSet rs = pstmt.executeQuery();
            connect.commit();

            while (rs.next()) {
                list.add(new preferences(rs.getInt("id"), rs.getString("user"), rs.getBoolean("notifications")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping("/preferences/addpref")
    preferences addPreferences(@RequestBody preferences p) {
        preferences second = new preferences(p.id + 1, p.user, p.notifications);
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "INSERT INTO MyProject.preferences (id,user,notifications) values (?,?,?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, p.getId());
            pstmt.setString(2, p.getUser());
            pstmt.setBoolean(3, p.toggleNotifications());
            pstmt.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.save(p);
        return p; //This might not be needed.
    }

    @DeleteMapping("preferences/togglenotifications")
    preferences toggleNotif(@RequestBody preferences p) {
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "UPDATE preferences SET notifications = (?) WHERE id = (?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setBoolean(1, p.toggleNotifications());
            pstmt.setInt(2, p.getId());
            pstmt.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return database.getOne(p.getId());
    }

    public Iterator<preferences> iterator() {
        return list.iterator();
    }
}
