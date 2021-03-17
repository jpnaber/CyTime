package com.cs309.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;

    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";
    List<User> list = new ArrayList<>();

    @PostMapping("/addUser")
    public String addUser(@RequestBody User u) {
        User oldUser = userRepo.findUserByUserName(u.getUserName());
        if (oldUser == null) {
            userRepo.save(u);
            return "New user added!";
        } else {
            return "Username already in use.";
        }
    }

    @PostMapping("/addU")
    public String addUser(@RequestParam String userName, @RequestParam String passW,@RequestParam String uStatus) {
        User oldUser = userRepo.findUserByUserName(userName);
        if (oldUser == null) {
            User newU = new User();
            newU.setUserName(userName);
            newU.setPassword(password);
            try {
                Connection connect = DriverManager.getConnection(url,user,password);
                connect.setAutoCommit(false);
                String sql = "insert into MyProject.chatUser (id,userName,passW,ustatus) values (?,?,?,?)";
                PreparedStatement pstmt = connect.prepareStatement(sql);
                pstmt.setNull(1,1);
                pstmt.setString(2,userName);
                pstmt.setString(3,passW);
                pstmt.setString(4,uStatus);
                pstmt.executeUpdate();
                connect.commit();
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            userRepo.save(newU);
            return "New user added!";
        } else {
            return "Username already in use.";
        }
    }

    @GetMapping("/listAllUser")
    public Iterable<User> getAllUsers() {
        {
            userRepo.deleteAll();
            list.clear();
            try {
                Connection connect = DriverManager.getConnection(url,user,password);
                connect.setAutoCommit(false);
                String sql = "select * from MyProject.chatUser";
                PreparedStatement pstmt = connect.prepareStatement(sql);
                ResultSet rs =pstmt.executeQuery();
                connect.commit();
                while (rs.next())
                {
                    list.add(new User(rs.getInt("id"),rs.getString("userName"),rs.getString("passW"),rs.getString("uStatus")));
                    userRepo.save(new User(rs.getInt("id"),rs.getString("userName"),rs.getString("passW"),rs.getString("uStatus")));
                }
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String userName) {
        getAllUsers();
        User user = userRepo.findUserByUserName(userName);
        if (user != null) return user.getPassword();
        else return "No such user found.";
    }

    @GetMapping("/verify")
    public String verifyIdentity(@RequestParam String userName, @RequestParam String passW) {
        getAllUsers();
        User me = userRepo.findUserByUserName(userName);
        if (me == null) return "No such user.";
        if (me.getPassword().equals(passW)) return "Success!";
        else return "Password Incorrect!";
    }

    @GetMapping("/getStatus")
    public String getStatus(@RequestParam String userName) {
        getAllUsers();
        User me = userRepo.findUserByUserName(userName);
        return me.getuStatus();
    }

    @PostMapping("/setStatus")
    public void setStatus(@RequestParam String userName, @RequestParam String uStatus) {
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "update MyProject.chatUser  set ustatus = ? where userName = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,uStatus);
            pstmt.setString(2,userName);
            pstmt.executeUpdate();
            connect.commit();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/delUser")
    public void deleteUser(@RequestParam String userName) {
        User u = userRepo.findUserByUserName(userName);
        userRepo.delete(u);
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "delete from MyProject.chatUser where userName = (?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,userName);
            pstmt.executeUpdate();
            connect.commit();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
