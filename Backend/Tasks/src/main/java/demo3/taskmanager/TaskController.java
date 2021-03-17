package demo3.taskmanager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TaskDatabase db;

    /**
     * Method: GET
     * Usage: "/mytask/{id}"
     * Returns a task object with the task ID
     *
     * @param id - task ID
     * @return
     */
    @GetMapping("/mytask/{id}")
    public myTask getTask(@PathVariable Integer id) {
        return db.getOne(id);
    }

    /**
     * Method: Any Request
     * Usage: "/alltasks"
     * Returns all the tasks in the database
     *
     * @return - All tasks in the data base
     */
    @RequestMapping("/alltasks")
    public List<myTask> alltasks() {
        return db.findAll();
    }

    /**
     * Method: POST
     * Usage: "/mytask/{id}{user}{task}{assigned}{due}"
     * Add a task to the server, of the user, with assigned date and due date
     *
     * @param task - task object to add to the database
     * @return task - return the task object that just got added to database
     */
    @PostMapping("/mytask/{id}{user}{task}{assigned}{due}")
    public myTask createTask(@RequestBody myTask task) {
        db.save(task);
        return task;
    }

    /**
     * Method: POST
     * Usage: "/updateTask/{id}{newTask}{assigned}{due}"
     * Replace the task of the given task ID with a new task
     *
     * @param t  - new task to add to
     * @param id - old task to be replaced
     * @return - old task that got deleted
     */
    @PutMapping("/updateTask/{id}{newTask}{assigned}{due}")
    public myTask updateTask(@RequestBody myTask t, @PathVariable Integer id) {
        myTask old_task = db.getOne(id);
        old_task.setTask(t.task);
        db.save(old_task);
        return old_task;
    }

    /**
     * Method: DELETE
     * Usage: "/deleteTask/{id}"
     * Deletes the task of the given ID
     *
     * @param id - Task ID
     * @return info of deletion, on success
     */
    @DeleteMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Integer id) {
        db.delete(db.getOne(id));
        return "Task: " + getTask(id) + "has been deleted.";
    }

    /**
     * Method: GET
     * Usage: "/oldtask/{id}"
     * Reopens the task of the given ID
     *
     * @param id - task ID
     * @return - task that just reopened
     */
    @GetMapping("/oldtask/{id}")
    public myTask reopenTask(@PathVariable Integer id) {

        return db.getOne(id);
    }
}
