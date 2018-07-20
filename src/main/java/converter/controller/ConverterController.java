package converter.controller;

import converter.PPTToPNGConverter;
import converter.WordToPNGConveter;
import converter.base.IConverter;

/***
 * 转换控制器，用于文件格式后缀判断
 * @author JadonYuen
 *
 */
public class ConverterController {

	private ConverterController() {
		
	}
	
	public static IConverter getConveter(String inputPath,String outputPath) {
		
		IConverter conveter = null;
		if(inputPath.endsWith(".ppt") || inputPath.endsWith(".pptx")) {
			
			conveter = new PPTToPNGConverter(inputPath, outputPath);
			
		}else if(inputPath.endsWith(".doc") || inputPath.endsWith(".docx")) {
			
			conveter = new WordToPNGConveter(inputPath, outputPath);
			
		}else {
			
		}
		return conveter;
	}
	
}
