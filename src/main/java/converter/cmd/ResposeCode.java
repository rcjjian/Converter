package converter.cmd;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/***
 * 回复请求者信息
 * 
 * @author JadonYuen
 */
public class ResposeCode {

	public static final int OK = 200;

	public static final int EXECUTE_SUCCESS = 201; //命令执行成功
	
	public static final int EXECUTE_STOP = 202; //停止命令执行
	
	public static final int ERROR_REQUEST = 400;

	public static final int ERROR_SERVER = 500;

	private JSONObject jsonObj;

	public ResposeCode() {

	}

	public void setBody(int code) {
		this.setBody(code, null);
	}

	public void setBody(int code, String msg) {
		this.setBody(code, msg, null);
	}

	public void setBody(int code, String msg, Map<String, String> map) {

		this.jsonObj = new JSONObject();
		jsonObj.put("code", code);

		if (msg == null)
			msg = "request msg";
		
		this.jsonObj.put("msg", msg);
		
		if(map != null) {
			Set<String> set = map.keySet();
			Iterator<String> iterator = set.iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				this.jsonObj.put(key,map.get(key));
			}
		}
	}

	public String toJSONString() {
		String str = this.jsonObj.toString();
		System.out.println(str);
		return str;
	}

}
