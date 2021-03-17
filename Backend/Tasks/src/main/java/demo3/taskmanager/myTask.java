package demo3.taskmanager;

import javax.persistence.*;

@Entity
public class myTask {
    /**
     * Task ID, primary field. Integer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    /**
     * User name, String.
     */
    @Column
    String user;

    /**
     * Task name, String.
     */
    @Column
    String task;

    /**
     * Assign date, String.
     */
    @Column
    String assignedDate;

    /**
     * Due date, String.
     */
    @Column
    String dueDate;

    /**
     * Completion status. Boolean.
     */
    @Column
    Boolean completed;

    /**
     * Constructs a new task object
     */
    public myTask() {
        id = 0;
        user = null;
        task = null;
        assignedDate = null;
        dueDate = null;
        completed = false;
    }

    /**
     * Different constructor, with non-empty fields
     *
     * @param id           - task id
     * @param user         - user name
     * @param task         - task object
     * @param assignedDate - assign date
     * @param dueDate      - due date
     */
    public myTask(int id, String user, String task, String assignedDate, String dueDate) {
        this.id = id;
        this.task = task;
        this.assignedDate = assignedDate;
        this.dueDate = dueDate;
        completed = false;
    }

    /**
     * Returns task ID
     *
     * @return - task ID, Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns user name
     *
     * @return - user name, String
     */
    public String getUser() {
        return user;
    }

    /**
     * Returns task name
     *
     * @return - task name, String
     */
    public String getTask() {
        return task;
    }

    /**
     * Sets task with the given task object
     *
     * @param task - task object to set
     */
    public void setTask(String task) {
        this.task = task;
    }

    /**
     * Get assigned date of the task
     *
     * @return - Assigned date, String
     */
    public String getAssignedDate() {
        return assignedDate;
    }

    /**
     * Get due date of the task
     *
     * @return - Due date, String
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Set due date of the task
     *
     * @param date - due date, String
     */
    public void setDueDate(String date) {
        dueDate = date;
    }

    /**
     * Set Assigned date of the task
     *
     * @param date - assign date
     */
    public void setAssignedDate(String date) {
        assignedDate = date;
    }

    /**
     * Set the completion status of the task
     *
     * @param task - task to set completion status
     */
    public void completedTask(myTask task) {
        this.completed = true;
    }

}
