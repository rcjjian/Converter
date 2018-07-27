package task;

import java.util.Map;

import converter.base.IConverter;
import task.base.BaseTask;
import task.base.ITaskCallback;

/***
 * 转换格式任务 
 */
public class ConverterTask extends BaseTask {
	
	private IConverter converter;
	private ITaskCallback taskCallback;
	
	private Map<String, String> externalMap; //外部可选项数据
	
	public ConverterTask(IConverter converter,ITaskCallback taskCallback) {
		super();
		this.converter = converter;
		this.taskCallback = taskCallback;
	}
	
	public ConverterTask(IConverter converter,ITaskCallback taskCallback,Map<String,String> externalMap) {
		this(converter, taskCallback);
		this.externalMap = externalMap;
	}
	
	public Map<String, String> getExternalMap(){
		return this.externalMap;
	}
	
	public Integer call(){
		super.call();
		System.out.println("convert task start");
		try {
			converter.startConvert();
			System.out.println("convert task finish");
			this.status = 1;
		}catch(Exception e) {
			e.printStackTrace();
			this.status = -1;
			System.out.println("covert task error or interupt!!");
		}finally {
			taskCallback.callback(this);
		}
		return 1;
	}

	
	/***
	 * 取消任务所创建的文件
	 * @throws Exception 
	 */
	public void cancelTask() throws Exception {
		super.cancelTask();
		converter.cancelConvert();
	}
	
}
