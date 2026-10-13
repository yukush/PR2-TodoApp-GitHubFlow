package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Stores tasks in memory and provides task-management operations. */
public class TodoList {
    private final List<TodoItem> items = new ArrayList<>();

    /** Adds a normalized non-empty task. Null and blank values are ignored. */
    public void add(String item) {
        if (item == null) {
            return;
        }
        String normalizedItem = item.trim();
        if (!normalizedItem.isEmpty()) {
            items.add(new TodoItem(normalizedItem));
        }
    }

    /** Removes the task at a zero-based index. Returns false for an invalid index. */
    public boolean remove(int index) {
        if (!isValidIndex(index)) {
            return false;
        }
        items.remove(index);
        return true;
    }

    /** Returns a defensive copy of task descriptions, preserving their order. */
    public List<String> getAll() {
        List<String> descriptions = new ArrayList<>(items.size());
        for (TodoItem item : items) {
            descriptions.add(item.description);
        }
        return descriptions;
    }

    public int size() {
        return items.size();
    }

    /** Removes every task and its completion state. */
    public void clear() {
        items.clear();
    }

    /** Marks a task completed. Returns false when the index is invalid. */
    public boolean markDone(int index) {
        if (!isValidIndex(index)) {
            return false;
        }
        items.get(index).completed = true;
        return true;
    }

    public boolean isDone(int index) {
        return isValidIndex(index) && items.get(index).completed;
    }

    /** Finds descriptions containing the query, without case sensitivity. */
    public List<String> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String normalizedQuery = query.trim().toLowerCase(Locale.ROOT);
        List<String> matches = new ArrayList<>();
        for (TodoItem item : items) {
            if (item.description.toLowerCase(Locale.ROOT).contains(normalizedQuery)) {
                matches.add(item.description);
            }
        }
        return matches;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < items.size();
    }

    private static final class TodoItem {
        private final String description;
        private boolean completed;

        private TodoItem(String description) {
            this.description = description;
        }
    }
}
