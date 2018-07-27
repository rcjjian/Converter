package task.base;

import java.util.UUID;
import java.util.concurrent.Callable;

public class BaseTask implements Callable<Integer> {

	protected String taskId; 
	
	protected int status = 0;
	
	public BaseTask() {
		this.taskId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public Integer call(){
		this.status = 0;
		return 1;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void cancelTask()  throws Exception{
		this.status = -1;
	}
	 
	
}
