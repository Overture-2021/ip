import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
    public Kanade(){
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|\n";

        System.out.println("Initiating...\n" + logo);
    }

    public void Echo(){
        String ln = "";
        while(!ln.equals("bye")){
            ln = sc.nextLine();
            if(ln.equals("bye")){
                PrintMsg("Bye. Hope to see you again soon!");
                break;
            }
            PrintMsg(ln);
        }
    }

    public void PrintMsg(String input){
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }
    public static void main(String[] args) {
        Kanade k423 = new Kanade();
        k423.PrintMsg("Hello! I'm Kanade\nWhat can I do for you?");
        k423.Echo();

    }
}


