import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final Kanade kanade;
    private Parser parser;

    /**
     * Creates the console UI, prints the startup banner, and initializes command parsing.
     *
     * @param kanade chatbot instance used by this UI
     */
    public Ui(Kanade kanade) {
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|\n";

        System.out.println("Initiating...\n" + logo);
        Ui.printMsg("Ciallo～! I'm Kanade!");
        this.kanade = kanade;
        parser = new Parser(kanade);
    }
    /**
     * Prints a framed message line to the output with concise interfaces.
     *
     * @param input message text to print
     */
    public static void printMsg(String input) {
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }

    /**
     * Starts the main input loop and continues until an exit command is received.
     */
    public void Chat() {
        String ln = "";
        boolean shouldExit = false;
        while(!shouldExit){
            ln = sc.nextLine();
            shouldExit = parser.parse(ln);
        }


    }
}
