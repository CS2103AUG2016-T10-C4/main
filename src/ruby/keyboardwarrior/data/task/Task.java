package ruby.keyboardwarrior.data.task;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: field values are validated.
 */
public class Task {

    private TaskDetails details;
    private Integer taskType; // 0 for To-do, 1 for Deadline, 2 for Event
    private Date date;
    private DateTime startTime;
    private DateTime endTime;
    
    public Task(TaskDetails details) {
    	this.taskType = 0;
        this.details = details;
    }
    
    public Task(TaskDetails details, DateTime dateTime) {
    	this.taskType = 1;
        this.details = details;
        this.endTime = dateTime;
    }
    
    public Task(TaskDetails details, Date date) {
    	this.taskType = 1;
        this.details = details;
        this.date = date;
    }
    
    public Task(TaskDetails details, DateTime startTime, DateTime endTime) {
    	this.taskType = 2;
        this.details = details;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public TaskDetails getDetails() {
        return details;
    }
    
    public Date getDate(){
    	return date;
    }
    
    public DateTime getStartTime(){
    	return startTime;
    }
    
    public DateTime getEndTime(){
    	return endTime;
    }
    
    public int getTaskType(){
    	return taskType;
    }
    
    public boolean equals(Object other) {
        if (other instanceof Task){
            Task task = (Task) other;
            return this.getDetails().equals(task.getDetails());
        }
        else
            return false;
    }
    
    @Override
    public String toString(){
    	if(taskType == 0)
    		return details.toString();
    	else if (taskType == 1) {
    		if(endTime == null)
    			return details.toString() + "\n\t Deadline:\t" + date.toString();
    		else
    			return details.toString() + "\n\t Deadline:\t" + endTime.toString();	
    	} else {
    		return details.toString() + "\n\t Start time:\t" + startTime.toString() + "\n\t End time:\t" + endTime.toString(); 
    	}
    }

    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(details);
    }

}