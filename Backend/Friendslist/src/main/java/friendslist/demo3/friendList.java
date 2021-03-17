package friendslist.demo3;

import javax.persistence.*;

@Entity
public class friendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String user1;   //first user (Main account)

    @Column
    String user2;    //second user (Main accounts friends)


    public Integer getId() {
        return id;
    }

    public String getUser() {
        return user1;
    }

    public String getFriends() {
        return user2;
    }
}
