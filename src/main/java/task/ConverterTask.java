package task;

import java.util.concurrent.Callable;

import converter.base.IConverter;

/***
 * 转换格式任务 
 */
public class ConverterTask implements Callable<Integer> {
	
	private IConverter converter;
	
	public ConverterTask(IConverter converter) {
		this.converter = converter;
	}
	
	
	public Integer call() throws Exception {
		System.out.println("convert task start");
		converter.startConvert();
		System.out.println("convert task finish");
		return 1;
	}
	
}
