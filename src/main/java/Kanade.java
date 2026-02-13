import java.util.ArrayList;
import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
    private ArrayList<Task> Tasks = new ArrayList<Task>();
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
                Tasks.get(target).setStatus(false);
            } else if (words[0].equals("mark")) {
                target = Integer.parseInt(ln.replace("mark ", ""));
                Tasks.get(target).setStatus(true);
            } else if (words[0].equals("delete")){
                target = Integer.parseInt(ln.replace("delete ", ""));
                if(target >= Tasks.size()){
                    PrintMsg("Index out of bounds, please reenter.");
                    continue;
                }
                PrintMsg("Sure, I've removed item " + target.toString());
                Tasks.remove(target.intValue());

            } else if (words[0].equals("todo")) {
                try{
                    Tasks.add(new Todo(ln));
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" (•̀⤙•́ ) The description of a Todo cannot be empty, try again");
                    continue;
                }

                numTask += 1;
            } else if (words[0].equals("deadline")) {
                try{
                    Tasks.add(new Deadline(ln));
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" ( ._. )\"\"You are missing the /by argument");
                    continue;
                }
                catch (IllegalArgumentException e){
                    PrintMsg("Description is empty.");
                    continue;
                }
                numTask += 1;
            } else if (words[0].equals("event")) {
                try{
                    Tasks.add(new Event(ln));
                }
                catch (IllegalArgumentException e){
                    PrintMsg("Description is empty.");
                    continue;
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" ( ._. )\"\"Make sure you have /from and /to arguments");
                    continue;
                }

                numTask += 1;
            } else {
                PrintMsg("Sry I didn't understand (\"-ࡇ-)");
            }

        }
    }

    public void PrintTasks() {
        System.out.println("_________________________");
        Integer i = 0;
        for (i = 0; i < Tasks.size(); i += 1) {
            System.out.println(i.toString() + "." + Tasks.get(i).toString());
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


