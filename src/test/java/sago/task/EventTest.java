package sago.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {

    @Test
    public void toString_notDone_correctFormat() {
        Event e = new Event(
                "camp",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 3)
        );

        String result = e.toString();

        assertTrue(result.contains("[E]"), "Should contain type icon [E]");
        assertTrue(result.contains("[ ]"), "New event should be not done");
        assertTrue(result.contains("camp"), "Should contain description");
        assertTrue(result.contains("(from: Mar 01 2026 to: Mar 03 2026)"),
                "Should contain correctly formatted date range");
    }

    @Test
    public void toString_afterMarkDone_showsDoneStatus() {
        Event e = new Event(
                "camp",
                LocalDate.of(2026, 3, 1),
                LocalDate.of(2026, 3, 3)
        );

        e.markAsDone();
        String result = e.toString();

        assertTrue(result.contains("[X]"), "Done event should show [X] status");
        assertTrue(result.contains("[E]"), "Type icon should remain [E]");
    }

    @Test
    public void getFromAndTo_returnCorrectDates() {
        LocalDate from = LocalDate.of(2026, 3, 1);
        LocalDate to = LocalDate.of(2026, 3, 3);

        Event e = new Event("camp", from, to);

        assertEquals(from, e.getFrom(), "getFrom should return the start date");
        assertEquals(to, e.getTo(), "getTo should return the end date");
    }
}
