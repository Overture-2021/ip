public class Task {
    protected String description;
    protected String type;
    protected boolean isDone;

    /**
     * Creates an empty task with no description.
     */
    public Task() {
        description = "";
    }

    /**
     * Creates a task with the provided description and a blank type marker.
     *
     * @param description task description text
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        type = " ";
    }

    /**
     * Creates a task with the provided description and type marker.
     *
     * @param description task description text
     * @param type task type marker
     */
    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Updates completion status and prints a confirmation message.
     *
     * @param newStatus {@code true} to mark the task as done, {@code false} otherwise
     */
    public void setStatus(boolean newStatus) {

        System.out.println("_________________________");
        if (newStatus) {
            this.isDone = true;
            System.out.println("Nice! I've marked this task as done");
            System.out.println("[" + type + "]" + "[X] " + description);
        } else {
            this.isDone = false;
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("[" + type + "]" + "[ ] " + description);
        }
        System.out.println("_________________________");
    }

    /**
     * Returns the printable representation of this task.
     *
     * @return task display string with type and status
     */
    @Override
    public String toString() {
        return "[" + type + "][" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the status icon used in task display.
     *
     * @return {@code "X"} when done, otherwise a blank space
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with ss
    }

}
