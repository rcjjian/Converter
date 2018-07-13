package main;

import java.io.File;

public class Main {

	public static void main(String[] args){
		try {
//			String documentPath = "H:\\DTPlatform\\测试资源\\5 爷爷和小树.pptx";
			String documentPath = args[0];
			String[] extArray = documentPath.split("\\.");
			String ext = extArray[extArray.length - 1];
			ext = "." + ext.toLowerCase();
			String pdfPath = documentPath.replace(ext, ".pdf");
			
			String dirPath = args[1];
//			String dirPath = "H:\\DTPlatform\\测试资源\\thumb";
			
			int port = Integer.parseInt(args[2]);
//			int port = 8100;
			
			int thumbNum = 3;// 截图数量达到3张通知
			
			System.out.println("start convert pdf");
			
			String officeHome = args[3];
			
			new DocumentToPDF().startConverter(documentPath,pdfPath,port,officeHome);
			
			File thumbDir = new File(dirPath);
			thumbDir.mkdirs();

			System.out.println("start convert png");
			new PDFToThumb().pdfToImage(pdfPath, dirPath,thumbNum);
			
			System.out.println("finish");
			System.exit(0);
			
		}catch(Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	
	}
	
}
