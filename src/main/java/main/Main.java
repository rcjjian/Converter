package main;

import java.io.File;
import java.util.Date;

import converter.PDFToPNGConverter;
import converter.PPTToPNGConverter;
import converter.WordToPDFConverter;

public class Main {

	public static void main(String[] args) {
		try {
			//String documentPath = "H:\\DTPlatform\\git_project\\temp_test\\platform\\preparelesson\\592246f8778739294f000114\\document\\5b4d4e631340a5dc23000002\\src\\5b4d4e631340a5dc23000002.pptx";
			String documentPath = args[0];

			String[] extArray = documentPath.split("\\.");
			String ext = extArray[extArray.length - 1];
			ext = "." + ext.toLowerCase();
			String pdfPath = documentPath.replace(ext, ".pdf");

			String dirPath = args[1];
			//String dirPath = "H:\\DTPlatform\\git_project\\temp_test\\platform\\preparelesson\\592246f8778739294f000114\\document\\5b4d4e631340a5dc23000002\\thumb";

			int msgNum = 3;// 截图数量达到3张通知

			// 为解决 libreoffice 对 ppt格式文件导出预览图的不足  使用poi

			File thumbDir = new File(dirPath);
			thumbDir.mkdirs();
			System.out.println("start convert");
			Date begin = new Date();
			if (documentPath.endsWith("pptx") || documentPath.endsWith("ppt")) {
				new PPTToPNGConverter().startConvert(documentPath, dirPath);
			} else if (documentPath.endsWith("docx") || documentPath.endsWith("doc")) {
				System.out.println("start convert pdf");
				new WordToPDFConverter().startConvert(documentPath, pdfPath);
				new PDFToPNGConverter().startConvert(pdfPath, dirPath, msgNum);
			}
			Date end = new Date();
			System.out.println(end.getTime() - begin.getTime());
			System.out.println("finish");
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}

	}

}
