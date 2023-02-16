package duke.command;

import duke.error.DukeException;
import duke.parser.Parser;
import duke.ui.Ui;
import duke.task.Task;

import java.util.ArrayList;

public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String OUTPUT_MESSAGE = " Noted. Task removed: ";
    public static final String REMAINING_TASK_COUNT = " Now you have %d task[s] in the list." + Ui.NEW_LINE;
    public static final String INVALID_COMMAND_MESSAGE = " Invalid input! Valid input format: \"delete <number>\"!";
    public static final String MESSAGE_USAGE = " " + COMMAND_WORD + ": deletes a task from the task list. "
            + Ui.NEW_LINE + "  Parameters: task number"
            + Ui.NEW_LINE + "  Example: " + COMMAND_WORD + " 1";

    public static void deleteTask(ArrayList<Task> tasks, String[] arrayOfInput) throws DukeException {
        int taskNumber = Integer.parseInt(Parser.parseCommand(arrayOfInput, COMMAND_WORD)) - 1;
        Ui.deleteTask(tasks, taskNumber);
        tasks.remove(tasks.get(taskNumber));
        Task.totalTasks -= 1;
    }
}