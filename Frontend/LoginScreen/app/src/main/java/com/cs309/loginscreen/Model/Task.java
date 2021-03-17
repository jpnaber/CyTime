package com.cs309.loginscreen.Model;

/**
 * Task Object for the All Task presenter. Upon receiving of the
 * JSON package from ServerRequest, All Task parser parses the
 * JSON array, and construct a Task object.
 *
 * @author Bofu
 */
public class Task {

    String id;          // Task ID
    String userName;    // Task User Name
    String taskName;    // Task Name
    String dueDate;     // Task due Date
    String dueTime;     // Task due Time

    /**
     * Constructs a Task object
     * @param id - Task ID
     * @param userName - Task User Name
     * @param taskName - Task Name
     * @param dueDate - Task due date
     * @param dueTime - Task due time
     */
    public Task(String id, String userName, String taskName, String dueDate, String dueTime) {
        this.id = id;
        this.userName = userName;
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    /**
     * Returns task ID
     * @return - Task id
     */
    public String getId() { return id; }

    /**
     * Returns User Name
     * @return - User Name
     */
    public String getUserName() { return userName; }

    /**
     * Returns Task name
     * @return - Task Name
     */
    public String getTaskName() { return taskName; }

    /**
     * Returns due date
     * @return - due date
     */
    public String getDueDate() { return dueDate; }

    /**
     * Returns due time
     * @return - due time
     */
    public String getDueTime() { return dueTime; }
}
