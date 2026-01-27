public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setStatus(boolean newStatus) {
        System.out.println("_________________________");
        if (newStatus) {
            this.isDone = true;
            System.out.println("Nice! I've marked this task as done");
            System.out.println("[X] " + description);
        } else {
            this.isDone = false;
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("[ ] " + description);
        }
        System.out.println("_________________________");
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with ss
    }

}
