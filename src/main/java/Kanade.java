import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
    private Task[] Tasks = new Task[100];
    protected static Integer numTask;

    public Kanade() {
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|❤\n";

        System.out.println("Initiating...\n" + logo);
        this.PrintMsg("Ciallo～(∠・ω< )⌒★)! I'm Kanade!\n こんにちは！私の名前は奏（かなで）です！よろしくお願いします！");
        numTask = 0;
    }

    public void Chat() {
        String ln = "";
        Integer target;
        while (true) {
            ln = sc.nextLine();
            String[] words = ln.split(" ");
            if (ln.equals("bye")) {
                PrintMsg("Bye. Hope to see you again soon!\nまたね！");
                break;
            } else if (ln.equals("list")) {
                PrintTasks();
            } else if (words[0].equals("unmark")) {
                target = Integer.parseInt(ln.replace("unmark ", ""));
                Tasks[target].setStatus(false);
            } else if (words[0].equals("mark")) {
                target = Integer.parseInt(ln.replace("mark ", ""));
                Tasks[target].setStatus(true);
            } else if (words[0].equals("todo")){
                Tasks[numTask] = new Todo(ln);
                numTask += 1;
            } else if (words[0].equals("deadline")){
                Tasks[numTask] = new Deadline(ln);
                numTask += 1;
            } else if(words[0].equals("event")){
                Tasks[numTask] = new Event(ln);
                numTask += 1;
            }
            else {
                Tasks[numTask] = new Task(ln);
                numTask += 1;
                PrintMsg("added: " + ln);
            }

        }
    }

    public void PrintTasks() {
        System.out.println("_________________________");
        Integer i = 0;
        for (i = 0; i < numTask; i += 1) {
            System.out.println(i.toString() + "." + Tasks[i].toString());
        }
        System.out.println("_________________________");
    }

    public static void PrintMsg(String input) {
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }

    public static void main(String[] args) {
        Kanade k423 = new Kanade();

        k423.Chat();

    }
}


