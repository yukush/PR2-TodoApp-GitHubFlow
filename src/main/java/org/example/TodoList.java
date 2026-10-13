package org.example;

import java.util.ArrayList;
import java.util.List;

/** Stores task descriptions and provides the core list operations. */
public class TodoList {
    private final List<String> items = new ArrayList<>();

    public void add(String item) {
        if (item == null) {
            return;
        }
        String normalizedItem = item.trim();
        if (!normalizedItem.isEmpty()) {
            items.add(normalizedItem);
        }
    }

    public boolean remove(int index) {
        if (index < 0 || index >= items.size()) {
            return false;
        }
        items.remove(index);
        return true;
    }

    public List<String> getAll() {
        return new ArrayList<>(items);
    }

    public int size() {
        return items.size();
    }
}
