import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDate by;

    public Deadline(String ln) {
        super("", "D");

        int indexOfBy = ln.indexOf("/by");

        String desc = ln.substring(9, indexOfBy);

        if(desc.equals("")){
            throw new IllegalArgumentException();
        }

        description = desc;

        by = LocalDate.parse(ln.substring(indexOfBy + 4));

        System.out.println("_________________________");
        System.out.println("Got it. I've added this task: " + this);
        Integer numT = Kanade.numTask + 1;
        System.out.println("Now you have " + numT.toString() + " task(s) in the list");
        System.out.println("_________________________");
    }

    public String dateToString(LocalDate by){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return by.format(formatter);
    }

    @Override
    public String toString() {
        return super.toString() + "(by: " + dateToString(by) + ")";
    }
}
