package converter;

import java.io.InputStream;

import converter.base.BaseConveter;
import converter.exception.ConvertException;
import utils.PathUtils;

/***
 * 多媒体转码 MP3 MP4 转m3u8格式
 * 
 * @author JadonYuen
 *
 */
public class MediaToM3u8Converter extends BaseConveter {

	/***
	 * 子进程
	 */
	private Process process;

	
	public MediaToM3u8Converter(String inputPath, String outputPath) {
		super(inputPath, outputPath);
	}
	
	@Override
	public void startConvert() throws Exception {
		super.startConvert();
		
		String ext = PathUtils.getExt(this.inputPath);
		String fileName = PathUtils.getFileName(this.inputPath);
		String m3u8Name = fileName.replace(ext, ".m3u8");
		
		String m3u8_path = PathUtils.concat(this.outputPath, m3u8Name);

		String command = "./ffmpeg.exe -i " + this.inputPath
				+ " -c:v libx264 -acodec copy -scodec copy -f segment -segment_list_type m3u8 -segment_list " + m3u8_path
				+ " -reset_timestamps 0 -segment_time 2 " + this.outputPath + "/out%03d.ts";
		
		InputStream is = null;
//		InputStream errorIs = null;
		try {
			this.process = Runtime.getRuntime().exec(command);
//			is = this.process.getInputStream();
			is = this.process.getErrorStream();
			byte[] b = new byte[1024];
			//开始阻塞，等待执行完毕
			while(is.read(b) > 0);
			int result = process.waitFor();  
			if(result > 0)throw new ConvertException();
		}finally {
			if(is != null) {
				is.close();
				is = null;
			}
		}
	}
	
	@Override
	public void cancelConvert() throws Exception {
		super.cancelConvert();
		if(this.process != null) {
			this.process.destroy();
		}
	}

}
