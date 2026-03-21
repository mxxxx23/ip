package sago;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sago.storage.Storage;
import sago.task.TaskList;

public class SagoTest {

    @TempDir
    Path tempDir;

    @Test
    public void getResponse_commandFlow_matchesExpectedBehavior() throws Exception {
        Sago sago = newTestInstance();

        assertTrue(sago.getResponse("todo read book").contains("[T][ ] read book"));
        assertTrue(sago.getResponse("deadline return book /by 2026-03-31")
                .contains("[D][ ] return book (by: Mar 31 2026)"));
        assertTrue(sago.getResponse("event hackathon /from 2026-04-01 /to 2026-04-02")
                .contains("[E][ ] hackathon (from: Apr 01 2026 to: Apr 02 2026)"));

        String marked = sago.getResponse("mark 2");
        assertTrue(marked.contains("[D][X] return book (by: Mar 31 2026)"));

        String unmarked = sago.getResponse("unmark 2");
        assertTrue(unmarked.contains("[D][ ] return book (by: Mar 31 2026)"));

        String listed = sago.getResponse("list");
        assertTrue(listed.contains("1. [T][ ] read book"));
        assertTrue(listed.contains("2. [D][ ] return book (by: Mar 31 2026)"));
        assertTrue(listed.contains("3. [E][ ] hackathon (from: Apr 01 2026 to: Apr 02 2026)"));

        String found = sago.getResponse("find book");
        assertTrue(found.contains("1. [T][ ] read book"));
        assertTrue(found.contains("2. [D][ ] return book (by: Mar 31 2026)"));

        String deleted = sago.getResponse("delete 1");
        assertTrue(deleted.contains("Now you have 2 tasks in the list."));
        assertFalse(sago.getResponse("list").contains("read book"));
    }

    @Test
    public void getResponse_helpAndBye_coverRemainingCommands() throws Exception {
        Sago sago = newTestInstance();

        String help = sago.getResponse("help");
        assertTrue(help.contains("list"));
        assertTrue(help.contains("deadline <description> /by <yyyy-MM-dd>"));
        assertTrue(help.contains("bye"));

        assertFalse(sago.isExit());
        String bye = sago.getResponse("bye");
        assertTrue(bye.contains("Bye. Hope to see you again soon!"));
        assertTrue(sago.isExit());
    }

    @Test
    public void getResponse_invalidEventRange_returnsUserFriendlyError() throws Exception {
        Sago sago = newTestInstance();

        String response = sago.getResponse("event bad dates /from 2026-04-03 /to 2026-04-01");

        assertTrue(response.contains("Error: Event start date cannot be after end date."));
    }

    private Sago newTestInstance() throws Exception {
        Storage storage = new Storage(tempDir.resolve("tasks.txt").toString());
        Constructor<Sago> constructor = Sago.class.getDeclaredConstructor(Storage.class, TaskList.class);
        constructor.setAccessible(true);
        return constructor.newInstance(storage, new TaskList());
    }
}
