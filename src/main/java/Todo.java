public class Todo extends Task {
    public Todo(String ln) {
        super("", "T");

        this.description = ln.substring(5);

        System.out.println(" (•̀⤙•́ ) The description of a Todo cannot be empty.");

        System.out.println("_________________________");
        System.out.println("Got it. I've added this task:" + this);
        Integer numT = Kanade.numTask + 1;
        System.out.println("Now you have " + numT.toString() + " task(s) in the list");
        System.out.println("_________________________");
    }
}
