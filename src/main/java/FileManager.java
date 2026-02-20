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

public class FileManager {
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

    public static ArrayList<Task> loadTasks() {
        Path path = Paths.get(TASKS_FILE);
        if (!Files.exists(path)) {
            saveTasks(new ArrayList<Task>());
            return new ArrayList<Task>();
        }
        try {
            String json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            ArrayList<TaskRecord> records = parseTaskRecords(json);
            ArrayList<Task> tasks = new ArrayList<Task>();
            suppressOutput(new Runnable() {
                @Override
                public void run() {
                    for (TaskRecord record : records) {
                        Task task = taskFromRecord(record);
                        if (task != null) {
                            tasks.add(task);
                        }
                    }
                }
            });
            return tasks;
        } catch (IOException e) {
            Kanade.printMsg("Could not read " + TASKS_FILE + ". Starting with an empty list.");
        } catch (RuntimeException e) {
            Kanade.printMsg("Invalid " + TASKS_FILE + ". Starting with an empty list.");
        }
        return new ArrayList<Task>();
    }

    public static void saveTasks(List<Task> tasks) {
        Path path = Paths.get(TASKS_FILE);
        String json = serializeTasks(tasks);
        try {
            Files.write(path, json.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            Kanade.printMsg("Could not save tasks to " + TASKS_FILE + ".");
        }
    }

    private static String serializeTasks(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"tasks\": [");
        for (int i = 0; i < tasks.size(); i += 1) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("\n    ").append(serializeTask(tasks.get(i)));
        }
        if (!tasks.isEmpty()) {
            sb.append("\n  ");
        }
        sb.append("]\n}\n");
        return sb.toString();
    }

    private static String serializeTask(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"type\":\"").append(jsonEscape(task.type)).append("\",");
        sb.append("\"description\":\"").append(jsonEscape(task.description)).append("\",");
        sb.append("\"isDone\":").append(task.isDone);
        if (task instanceof Deadline) {
            sb.append(",\"by\":\"").append(jsonEscape(((Deadline) task).by.toString())).append("\"");
        } else if (task instanceof Event) {
            sb.append(",\"from\":\"").append(jsonEscape(((Event) task).from.toString())).append("\",");
            sb.append("\"to\":\"").append(jsonEscape(((Event) task).to.toString())).append("\"");
        }
        sb.append("}");
        return sb.toString();
    }

    private static String jsonEscape(String input) {
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

    private static ArrayList<TaskRecord> parseTaskRecords(String json) {
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

    private static Task taskFromRecord(TaskRecord record) {
        if (record == null || record.type == null) {
            return null;
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
        return task;
    }

    private static void suppressOutput(Runnable action) {
        PrintStream originalOut = System.out;
        try {
            System.setOut(NULL_PRINT_STREAM);
            action.run();
        } finally {
            System.setOut(originalOut);
        }
    }

    private static String asString(Object value) {
        return (value instanceof String) ? (String) value : null;
    }

    private static boolean asBoolean(Object value) {
        return (value instanceof Boolean) ? (Boolean) value : false;
    }
}
