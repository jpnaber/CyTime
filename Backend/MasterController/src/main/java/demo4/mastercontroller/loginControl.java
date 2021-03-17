package demo4.mastercontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.*;

import java.util.List;

@RestController
public class loginControl {
    @Autowired
    userDB database;

    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";

    @RequestMapping("/allusers")
    List<userLogin> requestUsers() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "select * from MyProject.userLogin";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("email"));
            }
            connect.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return database.findAll();
    }

    @PostMapping("/userlogin")
    userLogin createUser(@RequestBody userLogin login) {
        database.save(login);
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "insert into MyProject.userLogin (email,user1) values (?,?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, login.getEmail());
            pstmt.setString(2, login.getUser1());
            pstmt.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login;
    }

}
