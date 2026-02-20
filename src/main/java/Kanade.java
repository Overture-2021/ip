import java.util.ArrayList;

public class Kanade {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    protected static Integer numTask;

    /**
     * Constructs a Kanade chatbot instance, prints the startup banner, and loads saved tasks.
     */
    public Kanade() {
        numTask = 0;
        tasks = Storage.loadTasks();
        numTask = tasks.size();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Prints all tasks currently in memory with their list indexes.
     */
    public void printTasks() {
        System.out.println("_________________________");
        Integer i = 0;
        for (i = 0; i < tasks.size(); i += 1) {
            System.out.println(i.toString() + "." + tasks.get(i).toString());
        }
        System.out.println("_________________________");
    }




    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Kanade kiana = new Kanade();
        Ui ui = new Ui(kiana);
        ui.Chat();
    }
}
