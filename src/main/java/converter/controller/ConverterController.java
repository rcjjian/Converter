package converter.controller;

import converter.MediaToM3u8Converter;
import converter.PPTToPNGConverter;
import converter.WordToPNGConveter;
import converter.base.IConverter;
import utils.PathUtils;

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
		
		String ext = PathUtils.getExt(inputPath);
		
		if(ext.equals(".ppt") || ext.equals(".pptx")) {
			
			conveter = new PPTToPNGConverter(inputPath, outputPath);
			
		}else if(ext.equals(".doc") ||ext.equals(".docx")) {
			
			conveter = new WordToPNGConveter(inputPath, outputPath);
			
		}else if(ext.equals(".mp4") || ext.equals(".mp3")){
		
			conveter = new MediaToM3u8Converter(inputPath, outputPath);
			
		}
		return conveter;
	}
	
}
