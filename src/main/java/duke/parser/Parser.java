package duke.parser;

import duke.command.Command;
import duke.command.AddCommand;
import duke.command.ListCommand;
import duke.command.DeleteCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;
import duke.command.HelpCommand;
import duke.command.InvalidCommand;
import duke.command.TodoCommand;
import duke.command.DeadlineCommand;
import duke.command.EventCommand;
import duke.error.DukeException;
import duke.error.ErrorTypes;
import duke.ui.Ui;

public class Parser { // deals with making sense of the user command
    public Command parseCommand(String input, Ui ui) {
        String[] arrayOfInput = input.split(" ");
        arrayOfInput[0] = arrayOfInput[0].trim();
        switch (arrayOfInput[0]) {
        case (ListCommand.COMMAND_WORD):
            return new ListCommand();
        case (HelpCommand.COMMAND_WORD):
            return new HelpCommand();
        case (MarkCommand.COMMAND_WORD):
            return prepareMarkUnmarkDelete(arrayOfInput, MarkCommand.COMMAND_WORD, ui);
        case (UnmarkCommand.COMMAND_WORD):
            return prepareMarkUnmarkDelete(arrayOfInput, UnmarkCommand.COMMAND_WORD, ui);
        case (DeleteCommand.COMMAND_WORD):
            return prepareMarkUnmarkDelete(arrayOfInput, DeleteCommand.COMMAND_WORD, ui);
        case (AddCommand.COMMAND_TODO):
            return prepareTodoCommand(input);
        case (AddCommand.COMMAND_DEADLINE):
            return prepareDeadlineCommand(input);
        case (AddCommand.COMMAND_EVENT):
            return prepareEventCommand(input);
        default:
            return new InvalidCommand(ErrorTypes.INVALID_INPUT);
        }
    }

    private Command prepareMarkUnmarkDelete(String[] input, String command, Ui ui) throws IndexOutOfBoundsException {
        try {
            int taskNumber;
            switch (command) {
            case MarkCommand.COMMAND_WORD:
                taskNumber = Integer.parseInt(Parser.parseMarkUnmarkDelete(input, MarkCommand.COMMAND_WORD)) - 1;
                return new MarkCommand(taskNumber);
            case UnmarkCommand.COMMAND_WORD:
                taskNumber = Integer.parseInt(Parser.parseMarkUnmarkDelete(input, UnmarkCommand.COMMAND_WORD)) - 1;
                return new UnmarkCommand(taskNumber);
            case DeleteCommand.COMMAND_WORD:
                taskNumber = Integer.parseInt(Parser.parseMarkUnmarkDelete(input, DeleteCommand.COMMAND_WORD)) - 1;
                return new DeleteCommand(taskNumber);
            default:
                return new InvalidCommand(ErrorTypes.INVALID_COMMAND);
            }
        } catch (DukeException e) {
            return new InvalidCommand(ErrorTypes.INVALID_COMMAND);
        } catch (IndexOutOfBoundsException e) {
            ui.showExceedTask();
            return new InvalidCommand(ErrorTypes.INVALID_COMMAND);
        }
    }

    private Command prepareTodoCommand(String input) {
        try {
            InputValidity.checkTodo(input);
            String taskName = input.replace(AddCommand.COMMAND_TODO, "").trim();
            return new TodoCommand(taskName);
        } catch (DukeException e) {
            return new InvalidCommand(ErrorTypes.INVALID_TODO);
        }
    }

    private Command prepareDeadlineCommand(String input) {
        try {
            InputValidity.checkDeadline(input);
            input = input.replace(AddCommand.COMMAND_DEADLINE, "").trim();
            String taskName = input.split(InputValidity.DEADLINE_DELIMITER, 2)[0].trim();
            String deadline = input.split(InputValidity.DEADLINE_DELIMITER, 2)[1].trim();
            return new DeadlineCommand(taskName, deadline);
        } catch (DukeException e) {
            return new InvalidCommand(ErrorTypes.INVALID_DEADLINE_COMMAND);
        }
    }

    private Command prepareEventCommand(String input) {
        try {
            InputValidity.checkValidEvent(input);
            input = input.replace(AddCommand.COMMAND_EVENT, "").trim();
            String taskName = input.split(InputValidity.EVENT_FROM_DELIMITER, 2)[0].trim();
            input = input.replace(taskName, "").trim();
            input = input.replace(InputValidity.EVENT_FROM_DELIMITER, "").trim();
            String startDate = input.split(InputValidity.EVENT_TO_DELIMITER, 2)[0].trim();
            String endDate = input.split(InputValidity.EVENT_TO_DELIMITER, 2)[1].trim();
            return new EventCommand(taskName, startDate, endDate);
        } catch (DukeException e) {
            return new InvalidCommand(ErrorTypes.INVALID_EVENT_COMMAND);
        }
    }

    public static String parseMarkUnmarkDelete(String[] userInput, String command) throws DukeException {
        InputValidity.isValid(userInput, command);
        return userInput[1].trim();
    }
}
