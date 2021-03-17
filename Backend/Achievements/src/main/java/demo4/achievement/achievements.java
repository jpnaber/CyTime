package demo4.achievement;

import javax.persistence.*;

public class achievements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String user;   //first user (Main account)

    @Column
    int numAchievements;

    public achievements(int id, ) {
        this.id = id;
        this.user = user;
        this. = user2;
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
