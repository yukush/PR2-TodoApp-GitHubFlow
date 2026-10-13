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
            System.out.println("Simple Todo CLI. Commands: add <task>, remove <index>, list, exit");
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
                String argument = parts.length > 1 ? parts[1] : "";
                switch (command) {
                    case "add" -> {
                        if (argument.isBlank()) {
                            System.out.println("Usage: add <task>");
                        } else {
                            todoList.add(argument);
                            System.out.println("Added.");
                        }
                    }
                    case "remove" -> removeTask(todoList, argument);
                    case "list" -> printTasks(todoList.getAll());
                    case "exit" -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Unknown command. Commands: add, remove, list, exit");
                }
            }
        }
    }

    private static void removeTask(TodoList todoList, String argument) {
        try {
            int index = Integer.parseInt(argument);
            System.out.println(todoList.remove(index) ? "Removed." : "Index out of range.");
        } catch (NumberFormatException exception) {
            System.out.println("Usage: remove <index>");
        }
    }

    private static void printTasks(List<String> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int index = 0; index < tasks.size(); index++) {
            System.out.printf("%d: %s%n", index, tasks.get(index));
        }
    }
}
