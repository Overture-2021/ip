public class Deadline extends Task {
    String by;
    public Deadline(String ln){
        super("", "D");

        int indexOfBy = ln.indexOf("/by");
        String desc = ln.substring(9,indexOfBy);

        description = desc;
        by = ln.substring(indexOfBy+4);

        System.out.println("_________________________");
        System.out.println("Got it. I've added this task: " + this);
        Integer numT = Kanade.numTask + 1;
        System.out.println("Now you have " + numT.toString() + " task(s) in the list");
        System.out.println("_________________________");
    }
    @Override
    public String toString(){
        return super.toString() + "(by: " + by + ")";
    }
}
