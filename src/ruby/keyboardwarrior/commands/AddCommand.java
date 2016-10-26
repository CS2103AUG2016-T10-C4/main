package ruby.keyboardwarrior.commands;

import ruby.keyboardwarrior.data.TasksList;
import ruby.keyboardwarrior.data.exception.IllegalValueException;
import ruby.keyboardwarrior.data.task.*;

/**
 * Adds a task to Keyboard Warrior.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String DEADLINE_WORD = " by ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Adds a task to the Keyboard Warrior. "
            + "Only supports task details which can be enter after the command word seperated by a space. \n\t"
            + "To add a Todo: \n\t"
            + "Format: add TASK \n\t"
            + "Example: " + COMMAND_WORD + " do something"
            + "To add a Deadline: \n\t"
            + "Format: add TASK by DATE [TIME] \n\t"
            + "Example: " + COMMAND_WORD + " do something" + DEADLINE_WORD + "120416 1800"
            + "To add an Event: \n\t"
            + "Format: add DATE TIME to TIME TASK\n\t"
            + "Example: " + COMMAND_WORD + "120416 1800 to 2100 do something";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Keyboard Warrior";

    private Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */

    public AddCommand(String details) throws IllegalValueException {
		try{
	    	String lowerCaseDetails = details.toLowerCase();
			int byExist = lowerCaseDetails.lastIndexOf(DEADLINE_WORD);
			if(byExist != -1 && Integer.parseInt(lowerCaseDetails.substring(byExist+4,byExist+10)) > 0){
				if(details.length() > byExist+10)
					this.toAdd = new Task(new TaskDetails(details.substring(0,byExist)), new DateTime(details.substring(byExist+4)));
				else
					this.toAdd = new Task(new TaskDetails(details.substring(0,byExist)), new Date(details.substring(byExist+4)));
			} else
				this.toAdd = new Task(new TaskDetails(details));
		} catch (NumberFormatException | StringIndexOutOfBoundsException siobe){
			this.toAdd = new Task(new TaskDetails(details));
		}
    }

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute() throws Exception{
        try {
            tasksList.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (TasksList.DuplicateTaskException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }
    
    public Task getTask(){
        return toAdd;
    }
    
    @Override
    public boolean isMutating() {
    	return true;
    }
}
