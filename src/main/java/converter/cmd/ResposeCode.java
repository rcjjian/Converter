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

	public static final int ERROR_REQUEST = 400;

	public static final int ERROR_SERVER = 500;

	public JSONObject jsonObj = new JSONObject();

	public ResposeCode() {

	}

	public void setBody(int code) {
		this.setBody(code, null);
	}

	public void setBody(int code, String msg) {
		this.setBody(code, msg, null);
	}

	public void setBody(int code, String msg, Map<String, String> map) {

		jsonObj.put("code", code);

		if (msg == null)
			msg = "request msg";
		
		jsonObj.put("msg", msg);
		
		if(map != null) {
			Set<String> set = map.keySet();
			Iterator<String> iterator = set.iterator();
			while(iterator.hasNext()) {
				String key = iterator.next();
				jsonObj.put(key,map.get(key));
			}
		}
	}

	public String toJSONString() {
		return this.jsonObj.toString();
	}

}
