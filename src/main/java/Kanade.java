import java.util.ArrayList;
import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
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
        this.printMsg("Ciallo～(∠・ω< )⌒★)! I'm Kanade!");
        numTask = 0;
        tasks = Storage.loadTasks();
        numTask = tasks.size();
    }

    /**
     * Starts the interactive chat loop and handles supported user commands until exit.
     */
    public void Chat() {
        String ln = "";
        int target;
        while (true) {
            ln = sc.nextLine();
            String[] words = ln.split(" ");
            if (ln.equals("bye")) {
                printMsg("Bye. Hope to see you again soon!");
                break;
            } else if (ln.equals("list")) {
                printTasks();
            } else if (words[0].equals("unmark")) {
                target = Integer.parseInt(ln.replace("unmark ", ""));
                tasks.get(target).setStatus(false);
                Storage.saveTasks(tasks);
            } else if (words[0].equals("mark")) {
                target = Integer.parseInt(ln.replace("mark ", ""));
                tasks.get(target).setStatus(true);
                Storage.saveTasks(tasks);
            } else if (words[0].equals("delete")) {
                target = Integer.parseInt(ln.replace("delete ", ""));
                if (target >= tasks.size()) {
                    printMsg("Index out of bounds, please reenter.");
                    continue;
                }
                printMsg("Sure, I've removed item " + Integer.toString(target));
                tasks.remove(target);
                numTask = tasks.size();
                Storage.saveTasks(tasks);

            } else if (words[0].equals("todo")) {
                try {
                    tasks.add(new Todo(ln));
                } catch (StringIndexOutOfBoundsException e) {
                    printMsg(" (•̀⤙•́ ) The description of a Todo cannot be empty, try again");
                    continue;
                }

                numTask += 1;
                Storage.saveTasks(tasks);
            } else if (words[0].equals("deadline")) {
                try {
                    tasks.add(new Deadline(ln));
                } catch (StringIndexOutOfBoundsException e) {
                    printMsg(" ( ._. )\"\"You are missing the /by argument");
                    continue;
                } catch (IllegalArgumentException e) {
                    printMsg("Description is empty.");
                    continue;
                }
                numTask += 1;
                Storage.saveTasks(tasks);
            } else if (words[0].equals("event")) {
                try {
                    tasks.add(new Event(ln));
                } catch (IllegalArgumentException e) {
                    printMsg("Description is empty.");
                    continue;
                } catch (StringIndexOutOfBoundsException e) {
                    printMsg(" ( ._. )\"\"Make sure you have /from and /to arguments");
                    continue;
                }

                numTask += 1;
                Storage.saveTasks(tasks);
            } else if (words[0].equals("find")){
                System.out.println("_________________________");
                for(int i=0;i<numTask;i+=1){
                    if(tasks.get(i).description.contains(words[1])){
                        System.out.println(i + "." + tasks.get(i).toString());
                    }
                }
                System.out.println("_________________________");
            } else {
                printMsg("Sry I didn't understand (\"-ࡇ-)");
            }

        }
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
    public static void printMsg(String input) {
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Kanade kiana = new Kanade();

        kiana.Chat();

    }
}


