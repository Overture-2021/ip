public class Event extends Task {
    String from;
    String to;

    public Event(String ln) {
        super("", "E");
        int indexOfFrom = ln.indexOf("/from");
        int indexOfTo = ln.indexOf("/to");
        description = ln.substring(6, indexOfFrom).trim();

        if(description.equals("")){
            throw new IllegalArgumentException();
        }

        from = ln.substring(indexOfFrom + 5, indexOfTo).trim();
        to = ln.substring(indexOfTo + 3).trim();

        System.out.println("_________________________");
        System.out.println("Got it. I've added this task:" + this);
        Integer numT = Kanade.numTask + 1;
        System.out.println("Now you have " + numT.toString() + " task(s) in the list");
        System.out.println("_________________________");
    }

    @Override
    public String toString() {
        return super.toString() + "(from: " + from + ", to: " + to + ")";
    }


}
