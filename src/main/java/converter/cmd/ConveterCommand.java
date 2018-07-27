package converter.cmd;

import org.json.JSONObject;

/***
 * 转换命令属性
 * 
 * @author JadonYuen
 *
 */
public class ConveterCommand {

	private JSONObject jsonObj;

	public ConveterCommand(JSONObject jsonObj) throws Exception {
		this.jsonObj = jsonObj;

		if (jsonObj.isNull("action")) { // 必须参数
			throw (new Exception("param action is required!"));
		} else {
			this.action = jsonObj.getString("action");
		}
		
		if(!jsonObj.isNull("externalId")) {
			this.externalId = jsonObj.getString("externalId");
		}

		if (this.action.equals("play")) {
			if (jsonObj.isNull("inputPath"))
				throw (new Exception("param inputPath is required!"));
			else
				this.inputPath = jsonObj.getString("inputPath");

			if (jsonObj.isNull("outputPath"))
				throw (new Exception("param outputPath is required!"));
			else
				this.outputPath = jsonObj.getString("outputPath");
		} else if (this.action.equals("stop")) {
			if (jsonObj.isNull("taskId"))
				throw (new Exception("param taskId is required!"));
			else
				this.taskId = jsonObj.getString("taskId");
		} else {
			throw (new Exception("param action is error!"));
		}
	}

	public String getTaskId() {
		return this.taskId;
	}

	public String getInputPath() {
		return this.inputPath;
	}

	public String getOutputPath() {
		return this.outputPath;
	}

	public String getAction() {
		return this.action;
	}
	
	public String getExternalId() {
		return this.externalId;
	}

	private String inputPath;

	private String outputPath;

	private String action;

	private String taskId;// 不是必须属性 在使用停止执行时会使用

	private String externalId; //外部id，作为客户端请求命令返回标识
	
	public String toJSONString() {
		return this.jsonObj != null ? jsonObj.toString() : "";
	}
}
