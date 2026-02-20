import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private final Kanade kanade;
    private Parser parser;
    public Ui(Kanade kanade) {
        this.kanade = kanade;
        parser = new Parser(kanade);
    }
    public static void printMsg(String input) {
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }
    public void Chat() {
        String ln = "";
        boolean shouldExit = false;
        while(!shouldExit){
            ln = sc.nextLine();
            shouldExit = parser.parse(ln);
        }


    }
}
