package org.example;

import org.junit.jupiter.api.Test;

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

    @Test
    void clearRemovesAllTasksAndCompletionState() {
        TodoList todoList = new TodoList();
        todoList.add("first");
        todoList.add("second");
        todoList.markDone(0);
        todoList.clear();
        assertEquals(0, todoList.size());
        assertTrue(todoList.getAll().isEmpty());
        assertFalse(todoList.isDone(0));
    }

    @Test
    void marksOnlyValidIndexAsDone() {
        TodoList todoList = new TodoList();
        todoList.add("finish report");
        assertTrue(todoList.markDone(0));
        assertTrue(todoList.isDone(0));
        assertFalse(todoList.markDone(-1));
        assertFalse(todoList.markDone(1));
    }

    @Test
    void searchIsCaseInsensitiveAndMatchesSubstring() {
        TodoList todoList = new TodoList();
        todoList.add("Read a Book");
        todoList.add("Clean room");
        todoList.add("Book review");
        assertEquals(List.of("Read a Book", "Book review"), todoList.search("BOOK"));
    }

    @Test
    void searchReturnsEmptyForBlankQueryOrNoMatches() {
        TodoList todoList = new TodoList();
        todoList.add("do homework");
        assertTrue(todoList.search("  ").isEmpty());
        assertTrue(todoList.search("travel").isEmpty());
        assertTrue(todoList.search(null).isEmpty());
    }
}
