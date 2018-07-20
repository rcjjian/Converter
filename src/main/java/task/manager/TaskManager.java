package task.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import task.ConverterTask;

/***
 * @author JadonYuen
 * 每一个socket client 对应一个任务管理
 * 而线程池则作为全局使用 
 * */
public class TaskManager {
	
	//全局线程池
	private static ExecutorService executorService  = Executors.newCachedThreadPool();
	
	private Map<String,Future<Integer>> taskMap = new HashMap<String, Future<Integer>>();
	
	public TaskManager() { 
		//创建线程池
	}

	/***
	 * 添加任务
	 * @param task
	 * @param taskName
	 */
	public void addTask(ConverterTask task,String taskName) {
		Future<Integer> future = executorService.submit(task);
		this.taskMap.put(taskName, future);
	}
	
	public int isDone(String taskName) {
		Future<Integer> future = this.taskMap.get(taskName);
		if(future != null) {
			return future.isDone() ? 1 : 0;
		}else {
			return -1;
		}
	}
	
	public Set<String> getWorkingTasks(){
		return this.taskMap.keySet();
	}
	 
	/***
	 * 移除任务
	 * @param taskName
	 */
	public void removeTask(String taskName) {
		Future<Integer> future = this.taskMap.get(taskName);
		if(future != null) {
			future.cancel(true);
			this.taskMap.remove(taskName);
		}
	}
	
	public void shutdown() {
		this.executorService.shutdown();
		Set<String> set = this.taskMap.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			Future<Integer> future = this.taskMap.get(key);
			future.cancel(true);
			this.taskMap.remove(key);
			iterator.remove();
		}
	}
	
}
