public class Duke {
    public static void main(String[] args) {
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|\n";
        System.out.println("_________________________");
        System.out.println("Initiating...\n" + logo);
        Message greet = new Message("Hello! I'm Kanade\nWhat can I do for you?");
        Message farewell = new Message("Bye. Hope to see you again soon!");
    }
}


