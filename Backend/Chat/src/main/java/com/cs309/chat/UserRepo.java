package com.cs309.chat;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User findUserById(Integer id);

    User findUserByUserName(String name);
}
