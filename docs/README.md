# Kanade User Guide

Kanade is a lightweight command-line task tracker. It supports todos, deadlines, and events, and stores tasks in a local `tasks.json` file.

## Quick start

1. Ensure JDK 17 is installed.
2. Run `Kanade.main()` from `src/main/java/Kanade.java` in your IDE.
3. Type commands in the terminal that appears.

Notes:
- Indexes shown in `list` start at 0, and those indexes are used by `mark`, `unmark`, and `delete`.
- Dates use ISO format: `yyyy-mm-dd`.

## Command summary

```
list
todo DESCRIPTION
deadline DESCRIPTION /by YYYY-MM-DD
event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD
mark INDEX
unmark INDEX
delete INDEX
find KEYWORD
bye
```

## Listing tasks

Shows all tasks with their indexes.

Example: `list`

```
_________________________
0.[T][ ] read book
1.[D][X] submit report(by: March 1, 2026)
_________________________
```

## Adding a todo

Adds a task with a description.

Example: `todo read book`

```
_________________________
Got it. I've added this task:[T][ ] read book
Now you have 1 task(s) in the list
_________________________
```

## Adding a deadline

Adds a task that has a due date.

Example: `deadline submit report /by 2026-03-01`

```
_________________________
Got it. I've added this task: [D][ ] submit report(by: March 1, 2026)
Now you have 2 task(s) in the list
_________________________
```

## Adding an event

Adds a task that has a start and end date.

Example: `event training /from 2026-03-10 /to 2026-03-12`

```
_________________________
Got it. I've added this task:[E][ ] training(from: March 10, 2026, to: March 12, 2026)
Now you have 3 task(s) in the list
_________________________
```

## Marking or unmarking a task

Updates a task's completion status.

Example: `mark 0`

```
_________________________
Nice! I've marked this task as done
[T][X] read book
_________________________
```

Example: `unmark 0`

```
_________________________
OK, I've marked this task as not done yet:
[T][ ] read book
_________________________
```

## Deleting a task

Removes a task by its index.

Example: `delete 1`

```
_________________________
Sure, I've removed item 1
_________________________
```

## Finding tasks

Lists tasks whose descriptions contain a keyword.

Example: `find report`

```
_________________________
1.[D][ ] submit report(by: March 1, 2026)
_________________________
```

## Exiting

Saves tasks and exits the application.

Example: `bye`

```
_________________________
Bye. Hope to see you again soon!
_________________________
```

## Data storage

Kanade saves tasks to `tasks.json` in the current working directory. If the file is missing, it will be created automatically.
