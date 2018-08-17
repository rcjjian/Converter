package utils;

public class PathUtils {

	public static String getExt(String path) {
		 if (path != null && path.length() > 0) { 
	            int dot = path.lastIndexOf('.'); 
	            if (dot >-1 && (dot < path.length() - 1)) { 
	                return  "." + path.substring(dot + 1).toLowerCase(); 
	            } 
	        } 
		 return "";
	}
	
	public static String getFileName(String path) {
		if (path != null && path.length() > 0) {
			String[] pathArray = path.split("\\\\|\\/");
			if(pathArray.length > 0) {
				String filename = pathArray[pathArray.length - 1];
				return filename;
			}
        }
		return "";
	}
	
	public static String concat(String dirPath,String filename) {
		String concatPath = "";
		String dirPathLastStr = dirPath.substring(dirPath.length() -1);
		if(dirPathLastStr.equals("\\") || dirPathLastStr.equals("/")) {
			concatPath = dirPath + filename;
		}else {
			concatPath = dirPath + "/" + filename;
		}
		return concatPath;
	}
	
}
