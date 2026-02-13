import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Kanade {
    Scanner sc = new Scanner(System.in);
    private ArrayList<Task> Tasks = new ArrayList<Task>();
    protected static Integer numTask;
    private static final String TASKS_FILE = "tasks.json";
    private static final PrintStream NULL_PRINT_STREAM = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) {
        }
    });

    private static class TaskRecord {
        private String type;
        private String description;
        private boolean isDone;
        private String by;
        private String from;
        private String to;
    }

    private static class JsonParser {
        private final String input;
        private int index;

        private JsonParser(String input) {
            this.input = (input == null) ? "" : input;
        }

        private Object parseValue() {
            skipWhitespace();
            if (index >= input.length()) {
                return null;
            }
            char c = input.charAt(index);
            if (c == '{') {
                return parseObject();
            }
            if (c == '[') {
                return parseArray();
            }
            if (c == '"') {
                return parseString();
            }
            if (c == 't') {
                return parseTrue();
            }
            if (c == 'f') {
                return parseFalse();
            }
            if (c == 'n') {
                return parseNull();
            }
            throw new IllegalArgumentException("Invalid JSON value");
        }

        private Map<String, Object> parseObject() {
            Map<String, Object> map = new HashMap<String, Object>();
            expect('{');
            skipWhitespace();
            if (consume('}')) {
                return map;
            }
            while (true) {
                skipWhitespace();
                String key = parseString();
                skipWhitespace();
                expect(':');
                Object value = parseValue();
                map.put(key, value);
                skipWhitespace();
                if (consume('}')) {
                    break;
                }
                expect(',');
            }
            return map;
        }

        private List<Object> parseArray() {
            List<Object> list = new ArrayList<Object>();
            expect('[');
            skipWhitespace();
            if (consume(']')) {
                return list;
            }
            while (true) {
                Object value = parseValue();
                list.add(value);
                skipWhitespace();
                if (consume(']')) {
                    break;
                }
                expect(',');
            }
            return list;
        }

        private String parseString() {
            expect('"');
            StringBuilder sb = new StringBuilder();
            while (index < input.length()) {
                char c = input.charAt(index++);
                if (c == '"') {
                    break;
                }
                if (c == '\\') {
                    if (index >= input.length()) {
                        break;
                    }
                    char esc = input.charAt(index++);
                    switch (esc) {
                    case '"':
                        sb.append('"');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case '/':
                        sb.append('/');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    case 'n':
                        sb.append('\n');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'u':
                        if (index + 4 <= input.length()) {
                            String hex = input.substring(index, index + 4);
                            index += 4;
                            try {
                                sb.append((char) Integer.parseInt(hex, 16));
                            } catch (NumberFormatException e) {
                                sb.append('?');
                            }
                        }
                        break;
                    default:
                        sb.append(esc);
                        break;
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        private Boolean parseTrue() {
            if (input.startsWith("true", index)) {
                index += 4;
                return Boolean.TRUE;
            }
            throw new IllegalArgumentException("Invalid JSON literal");
        }

        private Boolean parseFalse() {
            if (input.startsWith("false", index)) {
                index += 5;
                return Boolean.FALSE;
            }
            throw new IllegalArgumentException("Invalid JSON literal");
        }

        private Object parseNull() {
            if (input.startsWith("null", index)) {
                index += 4;
                return null;
            }
            throw new IllegalArgumentException("Invalid JSON literal");
        }

        private void skipWhitespace() {
            while (index < input.length()) {
                char c = input.charAt(index);
                if (c != ' ' && c != '\n' && c != '\r' && c != '\t') {
                    break;
                }
                index++;
            }
        }

        private void expect(char expected) {
            skipWhitespace();
            if (index >= input.length() || input.charAt(index) != expected) {
                throw new IllegalArgumentException("Invalid JSON structure");
            }
            index++;
        }

        private boolean consume(char expected) {
            skipWhitespace();
            if (index < input.length() && input.charAt(index) == expected) {
                index++;
                return true;
            }
            return false;
        }
    }

    public Kanade() {
        String logo = " _  __                     _      \n"
                + "| |/ /                    | |     \n"
                + "| ' /  __ _ _ __   __ _  __| | ___ \n"
                + "|  <  / _` | '_ \\ / _` |/ _` |/ _ \\\n"
                + "| . \\| (_| | | | | (_| | (_| |  __/\n"
                + "|_|\\_\\\\__,_|_| |_|\\__,_|\\__,_|\\___|❤\n";

        System.out.println("Initiating...\n" + logo);
        this.PrintMsg("Ciallo～(∠・ω< )⌒★)! I'm Kanade!\n こんにちは！私の名前は奏（かなで）です！よろしくお願いします！");
        numTask = 0;
        loadTasks();
    }

    public void Chat() {
        String ln = "";
        int target;
        while (true) {
            ln = sc.nextLine();
            String[] words = ln.split(" ");
            if (ln.equals("bye")) {
                PrintMsg("Bye. Hope to see you again soon!\nまたね！");
                break;
            } else if (ln.equals("list")) {
                PrintTasks();
            } else if (words[0].equals("unmark")) {
                target = Integer.parseInt(ln.replace("unmark ", ""));
                Tasks.get(target).setStatus(false);
                saveTasks();
            } else if (words[0].equals("mark")) {
                target = Integer.parseInt(ln.replace("mark ", ""));
                Tasks.get(target).setStatus(true);
                saveTasks();
            } else if (words[0].equals("delete")){
                target = Integer.parseInt(ln.replace("delete ", ""));
                if(target >= Tasks.size()){
                    PrintMsg("Index out of bounds, please reenter.");
                    continue;
                }
                PrintMsg("Sure, I've removed item " + Integer.toString(target));
                Tasks.remove(target);
                numTask = Tasks.size();
                saveTasks();

            } else if (words[0].equals("todo")) {
                try{
                    Tasks.add(new Todo(ln));
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" (•̀⤙•́ ) The description of a Todo cannot be empty, try again");
                    continue;
                }

                numTask += 1;
                saveTasks();
            } else if (words[0].equals("deadline")) {
                try{
                    Tasks.add(new Deadline(ln));
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" ( ._. )\"\"You are missing the /by argument");
                    continue;
                }
                catch (IllegalArgumentException e){
                    PrintMsg("Description is empty.");
                    continue;
                }
                numTask += 1;
                saveTasks();
            } else if (words[0].equals("event")) {
                try{
                    Tasks.add(new Event(ln));
                }
                catch (IllegalArgumentException e){
                    PrintMsg("Description is empty.");
                    continue;
                }
                catch (StringIndexOutOfBoundsException e){
                    PrintMsg(" ( ._. )\"\"Make sure you have /from and /to arguments");
                    continue;
                }

                numTask += 1;
                saveTasks();
            } else {
                PrintMsg("Sry I didn't understand (\"-ࡇ-)");
            }

        }
    }

    public void PrintTasks() {
        System.out.println("_________________________");
        Integer i = 0;
        for (i = 0; i < Tasks.size(); i += 1) {
            System.out.println(i.toString() + "." + Tasks.get(i).toString());
        }
        System.out.println("_________________________");
    }

    public static void PrintMsg(String input) {
        System.out.println("_________________________");
        System.out.println(input);
        System.out.println("_________________________");
    }

    private void loadTasks() {
        Path path = Paths.get(TASKS_FILE);
        if (!Files.exists(path)) {
            saveTasks();
            return;
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            ArrayList<TaskRecord> records = parseTaskRecords(json);
            Tasks.clear();
            suppressOutput(new Runnable() {
                @Override
                public void run() {
                    for (TaskRecord record : records) {
                        addTaskFromRecord(record);
                    }
                }
            });
            numTask = Tasks.size();
        } catch (IOException e) {
            PrintMsg("Could not read " + TASKS_FILE + ". Starting with an empty list.");
        } catch (RuntimeException e) {
            PrintMsg("Invalid " + TASKS_FILE + ". Starting with an empty list.");
        }
    }

    private void saveTasks() {
        Path path = Paths.get(TASKS_FILE);
        String json = serializeTasks();
        try {
            Files.write(path, json.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            PrintMsg("Could not save tasks to " + TASKS_FILE + ".");
        }
    }

    private String serializeTasks() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"tasks\": [");
        for (int i = 0; i < Tasks.size(); i += 1) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\n    ").append(serializeTask(Tasks.get(i)));
        }
        if (!Tasks.isEmpty()) {
            sb.append("\n  ");
        }
        sb.append("]\n}\n");
        return sb.toString();
    }

    private String serializeTask(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"type\":\"").append(jsonEscape(task.type)).append("\",");
        sb.append("\"description\":\"").append(jsonEscape(task.description)).append("\",");
        sb.append("\"isDone\":").append(task.isDone);
        if (task instanceof Deadline) {
            sb.append(",\"by\":\"").append(jsonEscape(((Deadline) task).by)).append("\"");
        } else if (task instanceof Event) {
            sb.append(",\"from\":\"").append(jsonEscape(((Event) task).from)).append("\",");
            sb.append("\"to\":\"").append(jsonEscape(((Event) task).to)).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    private String jsonEscape(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i += 1) {
            char c = input.charAt(i);
            switch (c) {
            case '\\':
                sb.append("\\\\");
                break;
            case '"':
                sb.append("\\\"");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            default:
                if (c < 0x20) {
                    String hex = Integer.toHexString(c);
                    sb.append("\\u");
                    for (int j = hex.length(); j < 4; j += 1) {
                        sb.append('0');
                    }
                    sb.append(hex);
                } else {
                    sb.append(c);
                }
                break;
            }
        }
        return sb.toString();
    }

    private ArrayList<TaskRecord> parseTaskRecords(String json) {
        ArrayList<TaskRecord> records = new ArrayList<TaskRecord>();
        if (json == null || json.trim().isEmpty()) {
            return records;
        }
        Object root = new JsonParser(json).parseValue();
        if (!(root instanceof Map)) {
            return records;
        }
        Object tasksValue = ((Map<?, ?>) root).get("tasks");
        if (!(tasksValue instanceof List)) {
            return records;
        }
        for (Object item : (List<?>) tasksValue) {
            if (!(item instanceof Map)) {
                continue;
            }
            Map<?, ?> map = (Map<?, ?>) item;
            TaskRecord record = new TaskRecord();
            record.type = asString(map.get("type"));
            record.description = asString(map.get("description"));
            record.isDone = asBoolean(map.get("isDone"));
            record.by = asString(map.get("by"));
            record.from = asString(map.get("from"));
            record.to = asString(map.get("to"));
            if (record.type != null) {
                records.add(record);
            }
        }
        return records;
    }

    private void addTaskFromRecord(TaskRecord record) {
        if (record == null || record.type == null) {
            return;
        }
        String description = record.description == null ? "" : record.description;
        Task task;
        switch (record.type) {
        case "T":
            task = new Todo("todo " + description);
            break;
        case "D":
            if (record.by == null) {
                task = new Task(description, "D");
                break;
            }
            task = new Deadline("deadline " + description + "/by " + record.by);
            break;
        case "E":
            if (record.from == null || record.to == null) {
                task = new Task(description, "E");
                break;
            }
            task = new Event("event " + description + "/from " + record.from + "/to " + record.to);
            break;
        default:
            task = new Task(description, record.type);
            break;
        }
        task.isDone = record.isDone;
        Tasks.add(task);
    }

    private void suppressOutput(Runnable action) {
        PrintStream originalOut = System.out;
        try {
            System.setOut(NULL_PRINT_STREAM);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
    }

    private String asString(Object value) {
        return (value instanceof String) ? (String) value : null;
    }

    private boolean asBoolean(Object value) {
        return (value instanceof Boolean) ? (Boolean) value : false;
    }

    public static void main(String[] args) {
        Kanade k423 = new Kanade();

        k423.Chat();

    }
}


