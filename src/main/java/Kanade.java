import java.util.ArrayList;

public class Kanade {
    private ArrayList<Task> tasks = new ArrayList<Task>();
    protected static Integer numTask;

    /**
     * Constructs a Kanade chatbot instance, prints the startup banner, and loads saved tasks.
     */
    public Kanade() {
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|❤\n";

        System.out.println("Initiating...\n" + logo);
        Ui.printMsg("Ciallo～(∠・ω< )⌒★)! I'm Kanade!");
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
     * Prints a framed message line to standard output.
     *
     * @param input message text to print
     */


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
