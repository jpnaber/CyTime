package friendslist.demo3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.Collections;
import java.util.List;

@RestController
public class flControl {
    @Autowired
    flDB database;

    @GetMapping("/friendList/{id}")
    friendList getUser(@PathVariable Integer id) {
        return database.getOne(id);
    }

    @RequestMapping("/friendList/allfriends")
        //Returns all the friends in the db
    List<friendList> requestFriends() {
        return database.findAll();
    }

//    @RequestMapping("/friendlist/myfriends") //THIS MIGHT WORK I HAVE NO CLUE IT WAS LATE AND I GOT TIRED JOSH!!!!!!!!!!
//    List<friendList> requestMyFriends(@RequestBody friendList user)
//    {
//        return database.findAllById(Collections.singleton(user.id));
//    }

    @PostMapping("/friendList/add")
    friendList addFriend(@RequestBody friendList add) {
        friendList second = new friendList();
        second.id = add.id + 1;
        second.user1 = add.user2;
        second.user2 = add.user1;
        database.save(add);
        database.save(second);
        return add; //This might not be needed.
    }

    @DeleteMapping("friendlist/delete")
    friendList deleteFriend(@PathVariable Integer id) {
        database.delete(database.getOne(id));
        database.delete(database.getOne(id + 1));
        return database.getOne(id);
    }

}
