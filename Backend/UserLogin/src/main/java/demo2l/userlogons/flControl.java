package demo2l.userlogons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.*;


import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
public class flControl implements Iterable<friendList> {
    @Autowired
    flDB database;
    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";
    List<friendList> list = new ArrayList<friendList>();

//    @GetMapping("/friendList/{id}")
//    friendList getUser(@PathVariable Integer id)
//    {
//        return database.getOne(id);
//    }

//    @RequestMapping("/friendList/allfriends") //Returns all the friends in the db
//    List<friendList> requestFriends()
//    {
//        return database.findAll();
//    }

    @RequestMapping("/friendlist/user1")
        //THIS WORKS I DID IT I AM TIRED :D
    Iterable<friendList> requestMyFriends(@RequestBody friendList u) {
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "select * from MyProject.friendslist where user1 = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, u.getUser1());
            ResultSet rs = pstmt.executeQuery();
            connect.commit();

            while (rs.next()) {
                list.add(new friendList(rs.getInt("id"), rs.getString("user1"), rs.getString("user2")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @PostMapping("/friendList/add")
    friendList addFriend(@RequestBody friendList add) {
        friendList second = new friendList(add.id + 1, add.user2, add.user1);
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "insert into MyProject.friendslist (id,user1,user2) values (?,?,?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, add.getId());
            pstmt.setString(2, add.getUser1());
            pstmt.setString(3, add.getUser2());
            pstmt.executeUpdate();
            sql = "insert into MyProject.friendslist (id,user1,user2) values (?,?,?)";
            pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, second.getId());
            pstmt.setString(2, second.getUser1());
            pstmt.setString(3, second.getUser2());
            pstmt.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.save(add);
        database.save(second);
        return add; //This might not be needed.
    }

    @DeleteMapping("friendList/delete")
    friendList deleteFriend(@RequestBody friendList u) {
        if (u.getId() % 2 != 0) {
            u.id = u.getId() - 1;
        }
        database.delete(database.getOne(u.getId()));
        database.delete(database.getOne(u.getId() + 1));
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "delete from MyProject.friendslist where id = (?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, u.getId());
            pstmt.executeUpdate();
            sql = "delete from MyProject.friendslist where id = (?)";
            pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1, u.getId() + 1);
            pstmt.executeUpdate();
            connect.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return database.getOne(u.getId());
    }

    @Override
    public Iterator<friendList> iterator() {
        return list.iterator();
    }
}
