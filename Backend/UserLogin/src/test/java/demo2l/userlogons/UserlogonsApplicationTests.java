package demo2l.userlogons;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.awt.image.PackedColorModel;
import java.sql.Connection;

import java.sql.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserlogonsApplicationTests {
    @InjectMocks
    private TaskController tc;

    @Mock
    private TaskRepo tr;
    private Task task = new Task(1,"adnan","clean","tomorrow","8");
    private List<Task> list = new ArrayList<Task>();

    @BeforeEach
    public void setup()
    {
        tc.deleteUserTask("Jordan");
    }

    @Test
    public void addTaskTest()
    {
        int n = tc.getTaskCount();
        tc.addTask(task);
        tc.getTasks();
        assertEquals(tc.getTaskCount(),n+1);
    }

    @Test
    public void checkUserTasksTest()
    {
        tc.addTask(new Task(1,"Jordan","Clean","Monday","5pm"));
        tc.addTask(new Task(1,"Jordan","Run","Tueday","6pm"));
        tc.addTask(new Task(1,"Jordan","Homework","Thursday","5pm"));
        list.addAll((Collection<? extends Task>) tc.findTasksByTaskName("Jordan"));
        assertEquals(list.size(),3);
    }

    @Test
    public void deleteUserTasks()
    {
        int n = tc.getTaskCount();
        tc.deleteUserTask("adnan");
        assertEquals(tc.getTaskCount(),n-1);
    }

//    @Test
//    public void getUsersTest() {
//        userLogin user = new userLogin();
//        user.id = 1;
//        user.email = "adnan";
//        when(login.requestUsers()).thenReturn((List<userLogin>) Stream.of(user));
//        assertEquals(); //NEED TO CREATE A METHOD  THAT KEEPS TRACK OF USERS ADD
//    }

//    @Test
//    public void addUserTest()
//    {
//        userLogin user = new userLogin();
//        user.email = "adnanx15@gmail.com";
//        user.user1 = "adnan";
//        when(login.createUser(user)).thenReturn(user);
//        assertEquals(user,login.createUser(user));
//    }
//
//    @Test
//    public void deleteUserTest()
//    {
//        userLogin user = new userLogin();
//        user.email = "adnanx15@gmail.com";
//        user.user1 = "adnanx15";
//        login.createUser(user);
//
//
//    }

}
