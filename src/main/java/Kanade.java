import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
    private Task[] Tasks = new Task[100];
    private Integer numTask;
    public Kanade(){
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|\n";

        System.out.println("Initiating...\n" + logo);
        numTask=0;
    }

    public void AddEvent(){
        String ln = "";
        while(true){
            ln = sc.nextLine();
            if(ln.equals("bye")){
                PrintMsg("Bye. Hope to see you again soon!");
                break;
            }

            if(ln.equals("list")){
                PrintTasks();
            }
            else{
                Tasks[numTask] = new Task(ln);
                numTask+=1;
                PrintMsg("added: " + ln);
            }

        }
    }

    public void PrintTasks(){
        System.out.println("_________________________");
        Integer i=0;
        for(i=0;i<numTask;i+=1){
            System.out.println(i.toString() + ". " + Tasks[i].description);
        }
        System.out.println("_________________________");
    }

    public void PrintMsg(String input){
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }
    public static void main(String[] args) {
        Kanade k423 = new Kanade();
        k423.PrintMsg("Hello! I'm Kanade\nWhat can I do for you?");
        k423.AddEvent();

    }
}


