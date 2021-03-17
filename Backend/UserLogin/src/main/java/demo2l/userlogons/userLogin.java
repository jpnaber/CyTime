package demo2l.userlogons;

import javax.persistence.*;

@Entity
public class userLogin {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Integer id;

    @Id
    String email;    //second user (Main accounts friends)

    @Column
    String user1;


//    public Integer getId()
//    {
//        return id;
//    }

    public String getEmail() {
        return email;
    }

    public String getUser1() {
        return user1;
    }


}
