package converter;

import java.io.File;

import converter.base.BaseConveter;

/***
 * word文档导出预览图png
 * @author JadonYuen
 */
public class WordToPNGConveter extends BaseConveter {

	public WordToPNGConveter(String inputPath,String outputPath) {
		super(inputPath,outputPath);
	}
	
	/***
	 * 先转pdf格式再转png（20180719目前版本只支持这种笨拙方式，发现有一步到位方法再迭代）
	 */
	public void startConvert() throws Exception {
		super.startConvert();
		
		String[] extArray = this.inputPath.split("\\.");
		String ext = extArray[extArray.length - 1];
		ext = "." + ext.toLowerCase();
		String pdfPath =  this.inputPath.replace(ext, ".pdf");
		
		File file = new File(this.outputPath);
		if(!file.exists())file.mkdirs();
		
		new WordToPDFConverter(this.inputPath, pdfPath).startConvert();
		new PDFToPNGConverter(pdfPath, this.outputPath).startConvert();
		//移除pdf文件
//		File pdfFile = new File(pdfPath);
//		pdfFile.delete();
	}
	
}
