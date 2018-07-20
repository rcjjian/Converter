package converter.cmd;

import org.json.JSONObject;

/***
 * ◊™ªª√¸¡Ó Ù–‘
 * @author JadonYuen
 *
 */
public class ConveterCommand {

	private JSONObject jsonObj;
	
	public ConveterCommand(JSONObject jsonObj) throws Exception {
		this.jsonObj = jsonObj;
		try {
			this.inputPath = jsonObj.getString("inputPath");
			this.outputPath = jsonObj.getString("outputPath");
			this.id = jsonObj.getString("id");  //Œ®“ªid
			this.action = jsonObj.getString("action");
		}catch(Exception e) {
			throw(e);
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	private String id;
	
	private String inputPath;
	
	private String outputPath;
	
	private String action;
	
	public String toJSONString() {
		return this.jsonObj != null ? jsonObj.toString() : "";
	}
	
	
}
