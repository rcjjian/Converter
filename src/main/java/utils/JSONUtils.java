package utils;

import org.json.JSONObject;

public class JSONUtils {

	public static JSONObject parse(String jsonStr) {
		
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonStr);
		}catch(Exception e) {
			System.out.println(e);
		}
		return jsonObj;
	}
	
}
