package org.example;

import java.util.List;
import java.util.Scanner;

/** Console interface for the in-memory TodoList. */
public class TodoApp {
    private TodoApp() {
    }

    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        try (Scanner scanner = new Scanner(System.in)) {
            printHelp();
            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    break;
                }
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\s+", 2);
                String command = parts[0].toLowerCase();
                String argument = parts.length > 1 ? parts[1].trim() : "";
                switch (command) {
                    case "add" -> addTask(todoList, argument);
                    case "remove" -> removeTask(todoList, argument);
                    case "list" -> printTasks(todoList, todoList.getAll(), true);
                    case "done" -> markDone(todoList, argument);
                    case "search" -> searchTasks(todoList, argument);
                    case "clear" -> clearTasks(todoList);
                    case "help" -> printHelp();
                    case "exit" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Unknown command. Type 'help' to see commands.");
                }
            }
        }
    }

    private static void addTask(TodoList todoList, String argument) {
        if (argument.isBlank()) {
            System.out.println("Usage: add <task>");
            return;
        }
        int previousSize = todoList.size();
        todoList.add(argument);
        System.out.println(todoList.size() > previousSize ? "Added." : "Task was empty and ignored.");
    }

    private static void removeTask(TodoList todoList, String argument) {
        Integer index = parseIndex(argument);
        if (index == null) {
            System.out.println("Usage: remove <index> (index starts at 0)");
            return;
        }
        System.out.println(todoList.remove(index) ? "Removed." : "Index out of range.");
    }

    private static void markDone(TodoList todoList, String argument) {
        Integer index = parseIndex(argument);
        if (index == null) {
            System.out.println("Usage: done <index> (index starts at 0)");
            return;
        }
        System.out.println(todoList.markDone(index) ? "Task marked as done." : "Index out of range.");
    }

    private static Integer parseIndex(String argument) {
        try {
            return Integer.valueOf(argument);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static void searchTasks(TodoList todoList, String query) {
        if (query.isBlank()) {
            System.out.println("Usage: search <text>");
            return;
        }
        List<String> matches = todoList.search(query);
        printTasks(todoList, matches, false);
    }

    private static void clearTasks(TodoList todoList) {
        int removedCount = todoList.size();
        todoList.clear();
        System.out.printf("Cleared %d task(s).%n", removedCount);
    }

    private static void printTasks(TodoList todoList, List<String> tasks, boolean showStatus) {
        if (tasks.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int index = 0; index < tasks.size(); index++) {
            String status = "";
            if (showStatus) {
                status = todoList.isDone(index) ? "[x] " : "[ ] ";
            }
            System.out.printf("%d: %s%s%n", index, status, tasks.get(index));
        }
    }

    private static void printHelp() {
        System.out.println("Todo CLI commands:");
        System.out.println("  add <task>      - add a task");
        System.out.println("  list            - list all tasks");
        System.out.println("  remove <index>  - remove a task by zero-based index");
        System.out.println("  done <index>    - mark a task completed");
        System.out.println("  search <text>   - find tasks by substring (case-insensitive)");
        System.out.println("  clear           - remove all tasks");
        System.out.println("  help            - show this help");
        System.out.println("  exit            - quit the application");
    }
}
