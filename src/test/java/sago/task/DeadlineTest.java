package sago.task;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeadlineTest {

    @Test
    public void toString_notDone_correctFormat() {
        Deadline d = new Deadline(
                "submit report",
                LocalDate.of(2026, 2, 6)
        );

        String result = d.toString();

        assertTrue(result.contains("[D]"), "Should contain type icon [D]");
        assertTrue(result.contains("submit report"), "Should contain description");
        assertTrue(result.contains("(by: Feb 06 2026)"),
                "Should contain formatted date 'Feb 06 2026'");
        assertTrue(result.contains("[ ]"),
                "New deadline should be not done");
    }

    @Test
    public void toString_afterMarkDone_showsDoneStatus() {
        Deadline d = new Deadline("submit report", LocalDate.of(2026, 2, 6));
        d.markAsDone();

        String result = d.toString();

        assertTrue(result.contains("[X]"), "Done deadline should show [X] status");
        assertTrue(result.contains("[D]"), "Should still contain type icon [D]");
        assertTrue(result.contains("(by: Feb 06 2026)"), "Date formatting should remain correct");
    }

    @Test
    public void getBy_returnsSameDate() {
        LocalDate date = LocalDate.of(2026, 2, 6);
        Deadline d = new Deadline("submit report", date);

        assertTrue(d.getBy().equals(date), "getBy should return the same LocalDate given");
    }
}
