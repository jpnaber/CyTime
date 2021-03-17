package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepo extends CrudRepository<Task, Integer> {

    Task findTaskById(Integer id);
}
