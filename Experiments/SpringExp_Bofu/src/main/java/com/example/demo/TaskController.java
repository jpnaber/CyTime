package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    @Autowired
    private TaskRepo taskRepo;

    private int taskCount = 0;

    @PostMapping("/addTask")
    public String addTask(@RequestParam String name, @RequestParam String due) {
        Task task = new Task();
        task.setName(name);
        task.setDue(due);
        taskRepo.save(task);
        taskCount++;
        return String.format("New task ADDED!! Task %s is due %s!!!", name, due);
    }

    @PostMapping("/deleteTaskId")
    public String deleteTask(@RequestParam int id) {
        Task task = taskRepo.findTaskById(id);
        taskRepo.delete(task);
        taskCount--;
        return String.format("Task %s, due %s, (ID=%d), is now DELETED!",
                task.getName(), task.getDue(), task.getId());
    }

    @GetMapping("/countTask")
    public int getTaskCount() {
        return taskCount;
    }

    @GetMapping("/listTask")
    public Iterable<Task> getTasks() {
        return taskRepo.findAll();
    }

    @GetMapping("/findTask/{id}")
    public Task findTaskById(@PathVariable Integer id) {
        return taskRepo.findTaskById(id);
    }
}
