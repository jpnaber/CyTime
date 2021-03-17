package com.cs309.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskRepo taskRepo;

    private int taskCount = 0;
    String url = "jdbc:mysql://coms-309-mc-10.cs.iastate.edu:3306/";
    String user = "mc10";
    String password = "MC_10projectuser";

    @PostMapping("/addTask")
    public String addTask(@RequestBody Task task) {
//        taskRepo.save(task);
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "insert into MyProject.usertasks (id,userName,taskName,dueDate,dueTime) values (?,?,?,?,?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setNull(1,1);
            pstmt.setString(2, task.getUserName());
            pstmt.setString(3,task.getTaskName());
            pstmt.setString(4,task.getDueDate());
            pstmt.setString(5,task.getDueTime());
            pstmt.executeUpdate();
            connect.commit();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        taskCount++;
        return String.format("New task ADDED!");
    }

    @PostMapping("/addT")
    public String addTask(@RequestParam String userName,
                          @RequestParam String taskName, @RequestParam String dueDate,
                          @RequestParam String dueTime) {
        Task task = new Task();
        task.setUserName(userName);
        task.setTaskName(taskName);
        task.setDueDate(dueDate);
        task.setDueTime(dueTime);
//        taskRepo.save(task);
        taskCount++;
        return String.format("New task ADDED!");
    }

    @PostMapping("/del")
    public String deleteTask(@RequestParam int id) {
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "delete from MyProject.usertasks where id = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            connect.commit();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Task t = taskRepo.findTaskById(id);
//        taskRepo.delete(t);
        taskCount--;
        return ("Success!");
    }

    @PostMapping("/delTaskFor")
    public String deleteUserTask(@RequestParam String userName) {
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "delete from MyProject.usertasks where userName = (?)";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,userName);
            pstmt.executeUpdate();
            connect.commit();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        Iterable<Task> tList = taskRepo.findAllByUserName(userName);
//        for (Task t : tList) taskRepo.delete(t);
        return String.format("All task deleted for %s.", userName);
    }

    @GetMapping("/getTaskCount")
    public int getTaskCount() {
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "select count(distinct id)  as numTasks from MyProject.usertasks";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            connect.commit();
            while (rs.next())
            {
                taskCount = rs.getInt("numTasks");
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskCount;
    }

    @GetMapping("/listAllTask")
    public Iterable<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "select * from MyProject.usertasks";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            ResultSet rs =pstmt.executeQuery();
            connect.commit();
            while (rs.next())
            {
                list.add(new Task(rs.getInt("id"),
                        rs.getString("userName"),
                        rs.getString("taskName"),
                        rs.getString("dueDate"),
                        rs.getString("dueTime")));
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
//        return taskRepo.findAll();
    }

    @GetMapping("/listTaskFor")
    public Iterable<Task> findTasksByTaskName(@RequestParam String userName){
        List<Task> list = new ArrayList<>();
        try {
            Connection connect = DriverManager.getConnection(url,user,password);
            connect.setAutoCommit(false);
            String sql = "select * from MyProject.usertasks where userName = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1,userName);
            ResultSet rs = pstmt.executeQuery();
            connect.commit();
            while (rs.next())
            {
                list.add(new Task(rs.getInt("id"),rs.getString("userName"),rs.getString("taskName"),rs.getString("dueDate"),rs.getString("dueTime")));
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @GetMapping("/task/{id}")
    public Task findTaskById(@PathVariable Integer id) {
//        return taskRepo.findTaskById(id);
        return null;
    }
}
