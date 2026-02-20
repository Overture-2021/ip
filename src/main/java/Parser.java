import java.util.ArrayList;

public class Parser {
    private Kanade kanade;
    ArrayList<Task> tasks;
    public Parser(Kanade kanade){
        this.kanade = kanade;
        tasks = kanade.getTasks();
    }

    public boolean parse(String ln){
        int target;
        String[] words = ln.split(" ");
        if (ln.equals("bye")) {
            Ui.printMsg("Bye. Hope to see you again soon!");
            return true;
        } else if (ln.equals("list")) {
            kanade.printTasks();
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
                Ui.printMsg("Index out of bounds, please reenter.");
                return false;
            }
            Ui.printMsg("Sure, I've removed item " + Integer.toString(target));
            tasks.remove(target);
            Kanade.numTask = tasks.size();
            Storage.saveTasks(tasks);

        } else if (words[0].equals("todo")) {
            try {
                tasks.add(new Todo(ln));
            } catch (StringIndexOutOfBoundsException e) {
                Ui.printMsg(" (•̀⤙•́ ) The description of a Todo cannot be empty, try again");
                return false;
            }

            Kanade.numTask += 1;
            Storage.saveTasks(tasks);
        } else if (words[0].equals("deadline")) {
            try {
                tasks.add(new Deadline(ln));
            } catch (StringIndexOutOfBoundsException e) {
                Ui.printMsg(" ( ._. )\"\"You are missing the /by argument");
                return false;
            } catch (IllegalArgumentException e) {
                Ui.printMsg("Description is empty.");
                return false;
            }
            Kanade.numTask += 1;
            Storage.saveTasks(tasks);
        } else if (words[0].equals("event")) {
            try {
                tasks.add(new Event(ln));
            } catch (IllegalArgumentException e) {
                Ui.printMsg("Description is empty.");
                return false;
            } catch (StringIndexOutOfBoundsException e) {
                Ui.printMsg(" ( ._. )\"\"Make sure you have /from and /to arguments");
                return false;
            }

            Kanade.numTask += 1;
            Storage.saveTasks(tasks);
        } else if (words[0].equals("find")) {
            System.out.println("_________________________");
            for (int i = 0; i < Kanade.numTask; i += 1) {
                if (tasks.get(i).description.contains(words[1])) {
                    System.out.println(i + "." + tasks.get(i).toString());
                }
            }
            System.out.println("_________________________");
        } else {
            Ui.printMsg("Sry I didn't understand (\"-ࡇ-)");
        }

        return false;
    }
}
