public class Task {
    protected String description;
    protected String type;
    protected boolean isDone;

    public Task() {
        description = "";
    }

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        type = " ";
    }

    public Task(String description, String type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "[" + type + "][" + getStatusIcon() + "] " + description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with ss
    }

}
