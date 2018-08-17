package converter;

import java.io.File;

import converter.base.BaseConveter;
import utils.PathUtils;

/***
 * word文档导出预览图png
 * @author JadonYuen
 */
public class WordToPNGConveter extends BaseConveter {

	public WordToPNGConveter(String inputPath,String outputPath) {
		super(inputPath,outputPath);
	}
	
	private WordToPDFConverter wordToPdf = null;
	
	/***
	 * 先转pdf格式再转png（20180719目前版本只支持这种笨拙方式，发现有一步到位方法再迭代）
	 */
	public void startConvert() throws Exception {
		super.startConvert();
		
		String ext = PathUtils.getExt(this.inputPath);
		String pdfPath =  this.inputPath.replace(ext, ".pdf");
		
		File file = new File(this.outputPath);
		if(!file.exists())file.mkdirs();
		
		wordToPdf = new WordToPDFConverter(this.inputPath, pdfPath);
		wordToPdf.startConvert();
		
		PDFToPNGConverter pdfConverter = new PDFToPNGConverter(pdfPath, this.outputPath);
		pdfConverter.startConvert();
		
		//移除pdf文件
		wordToPdf.deletePDF();
		wordToPdf = null;
		pdfConverter = null;
		file = null;
		System.gc();
	}

	public void cancelConvert() throws Exception {
		super.cancelConvert();
		File dirFile = new File(this.outputPath);
		if(dirFile.exists()) {
			for (File childFile : dirFile.listFiles()) {
				childFile.delete();
			}
			dirFile.delete();
		}
		dirFile = null;
		wordToPdf.deletePDF();
		
		System.gc();
	}
	
}
