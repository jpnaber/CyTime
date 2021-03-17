package demo2l.userlogons;

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

    public friendList() {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }

    public friendList(Integer num, String u1, String u2) {
        this.id = num;
        this.user1 = u1;
        this.user2 = u2;
    }

    public Integer getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }
}
