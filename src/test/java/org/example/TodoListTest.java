package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TodoListTest {
    @Test
    void addTrimsTaskText() {
        TodoList todoList = new TodoList();
        todoList.add("  read a book  ");
        assertEquals(1, todoList.size());
        assertEquals("read a book", todoList.getAll().get(0));
    }

    @Test
    void ignoresNullAndBlankTasks() {
        TodoList todoList = new TodoList();
        todoList.add(null);
        todoList.add("   ");
        assertEquals(0, todoList.size());
    }

    @Test
    void removesValidIndex() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");
        assertTrue(todoList.remove(0));
        assertEquals(List.of("second"), todoList.getAll());
    }

    @Test
    void rejectsInvalidRemovalIndexes() {
        TodoList todoList = new TodoList();
        todoList.add("only task");
        assertFalse(todoList.remove(-1));
        assertFalse(todoList.remove(1));
        assertEquals(1, todoList.size());
    }

    @Test
    void getAllReturnsDefensiveCopy() {
        TodoList todoList = new TodoList();
        todoList.add("original");
        List<String> copy = todoList.getAll();
        copy.add("outside change");
        assertEquals(List.of("original"), todoList.getAll());
    }
}
