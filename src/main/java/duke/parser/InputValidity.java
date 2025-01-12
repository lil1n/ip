package duke.parser;

import duke.command.DeleteCommand;
import duke.command.MarkCommand;
import duke.command.UnmarkCommand;
import duke.command.AddCommand;
import duke.error.DukeException;
import duke.error.ErrorTypes;
import duke.error.Error;

/**
 * Checks input validity
 */
public class InputValidity {

    private static final int MINIMUM_TODO_LENGTH = 2;

    private static final int MINIMUM_DEADLINE_LENGTH = 4;

    private static final int MINIMUM_EVENT_LENGTH = 6;

    private static final int VALID_LENGTH_TWO = 2;

    protected static final String DEADLINE_DELIMITER = " /by ";

    protected static final String EVENT_FROM_DELIMITER = " /from ";

    protected static final String EVENT_TO_DELIMITER = " /to ";

    /**
     * Checks whether the input given by user is a valid todo command
     *
     * @param input input given by the user
     * @throws DukeException when the input provided by user does not have sufficient parameters
     */
    protected static void checkTodo(String input) throws DukeException {
        String[] arrayOfInput = input.split(" ");
        if (arrayOfInput.length < MINIMUM_TODO_LENGTH) {
            Error.throwError(ErrorTypes.INVALID_TODO);
        }
    }

    /**
     * Checks whether the input given by user is a valid deadline command
     *
     * @param input input given by the user
     * @throws DukeException when the input provided by user is of incorrect format or does not have
     *                       sufficient parameters
     */
    protected static void checkValidDeadline(String input) throws DukeException {
        String[] arrayInput = input.trim().split(" ");
        if (arrayInput.length < MINIMUM_DEADLINE_LENGTH) {
            Error.throwError(ErrorTypes.INSUFFICIENT_DEADLINE_ARGUMENT);
        }
        boolean isTaskNameAbsent = arrayInput[1].equals(DEADLINE_DELIMITER.trim());
        boolean isDateAbsent = arrayInput[arrayInput.length - 1].equals(DEADLINE_DELIMITER.trim());
        if (!input.contains(DEADLINE_DELIMITER) || isTaskNameAbsent || isDateAbsent) {
            Error.throwError(ErrorTypes.INVALID_DEADLINE_COMMAND);
        }
    }

    /**
     * Checks whether the input given by user is a valid event command
     *
     * @param input input given by the user
     * @throws DukeException when the input provided by user is of incorrect format or does not have
     *                       sufficient parameters
     */
    protected static void checkValidEvent(String input) throws DukeException {
        String[] arrayInput = input.split(" ");
        if (arrayInput.length < MINIMUM_EVENT_LENGTH) {
            Error.throwError(ErrorTypes.INSUFFICIENT_EVENT_ARGUMENT);
        }
        boolean isTaskNameAbsent = arrayInput[1].equals(EVENT_FROM_DELIMITER.trim());
        boolean isToDateAbsent = arrayInput[arrayInput.length - 1].equals(EVENT_TO_DELIMITER.trim());
        if ((!input.contains(EVENT_FROM_DELIMITER) || !input.contains(EVENT_TO_DELIMITER))
                || (input.indexOf(EVENT_FROM_DELIMITER) > input.indexOf(EVENT_TO_DELIMITER)) || isTaskNameAbsent
                || isToDateAbsent) {
            Error.throwError(ErrorTypes.INVALID_EVENT_COMMAND);
        }
        String fromDate = input.split(EVENT_FROM_DELIMITER)[1].split(EVENT_TO_DELIMITER)[0].trim();
        if (fromDate.isEmpty()) {
            Error.throwError(ErrorTypes.INVALID_EVENT_COMMAND);
        }
    }

    /**
     * Checks whether a given string only contains digit characters
     *
     * @param input input given by the user
     * @return true if input only contains digit characters, false otherwise
     */
    private static boolean isStringOfInteger(String input) {
        input = input.trim();
        char[] inputInArray = input.toCharArray();
        for (char c : inputInArray) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether the input provided by user for mark/unmark/delete is valid
     *
     * @param input   an array of the user input, separated by " "
     * @param command command word provided by user: mark/unmark/delete
     * @throws DukeException when insufficient parameters are provided or when wrong parameter format is given
     */
    protected static void checkValid(String[] input, String command) throws DukeException {
        boolean isTwoWordInput = (input.length == VALID_LENGTH_TWO);
        if (!isTwoWordInput || !isStringOfInteger(input[1])) {
            switch (command) {
            case MarkCommand.COMMAND_WORD:
                Error.throwError(ErrorTypes.INVALID_MARK_COMMAND);
                break;
            case UnmarkCommand.COMMAND_WORD:
                Error.throwError(ErrorTypes.INVALID_UNMARK_COMMAND);
                break;
            case DeleteCommand.COMMAND_WORD:
                Error.throwError(ErrorTypes.INVALID_DELETE_COMMAND);
                break;
            }
        }
    }

    /**
     * Checks whether the input provided by user for find command is valid
     *
     * @param input input given by the user
     * @throws DukeException when the find command is invalid
     */
    protected static void checkValidFind(String input) throws DukeException {
        String[] arrayOfInput = input.split(" ");
        boolean isAtLeastTwoWord = (arrayOfInput.length >= VALID_LENGTH_TWO);
        if (!isAtLeastTwoWord) {
            Error.throwError(ErrorTypes.INVALID_FIND_COMMAND);
        }
    }
}
