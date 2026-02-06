package sago.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    @Test
    public void status_newTodo_notDone() {
        Todo t = new Todo("read book");
        assertFalse(t.isDone());
        assertEquals(" ", t.getStatusIcon());
    }

    @Test
    public void status_afterMarkDone_done() {
        Todo t = new Todo("read book");
        t.markAsDone();
        assertTrue(t.isDone());
        assertEquals("X", t.getStatusIcon());
    }

    @Test
    public void status_markDoneThenUnmark_notDoneAgain() {
        Todo t = new Todo("read book");
        t.markAsDone();
        t.unmark();
        assertFalse(t.isDone());
        assertEquals(" ", t.getStatusIcon());
    }
}
