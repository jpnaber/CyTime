package demo4.achievement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class acheivementsCtrl {
    @Autowired
    achievementsDB database;
    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";
    List<achievements> list = new ArrayList<achievements>();

    @RequestMapping("/friendlist/user1")
    Iterable<achievements> requestMyFriends(@RequestBody achievements a) {
        try {
            Connection connect = DriverManager.getConnection(url, user, password);
            connect.setAutoCommit(false);
            String sql = "select * from MyProject.preferences where id = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, a.getUser());
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
    public Iterator<achievements> iterator() {
        return list.iterator();
    }

}
