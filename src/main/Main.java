package main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.ParseException;

public class Main {

	public static void main(String[] args) throws ParseException, IOException {

		String documentPath = args[0];
		
		String[] extArray = documentPath.split("\\.");
		
		String ext = extArray[extArray.length - 1];
		ext = "." + ext.toLowerCase();
		String pdfPath = documentPath.replace(ext, ".pdf");

		String thumbDirPath =  args[1];

		int port = Integer.parseInt(args[2]);
		
		System.out.println("convert pdf start");
		DocumentToPDF docToPdf = new DocumentToPDF();
		docToPdf.startConvert(documentPath, pdfPath,port);
		System.out.println("covert pdf finished");

		System.out.println("covert thumb start");
		File thumbDir = new File(thumbDirPath);
		thumbDir.mkdirs();
		PDFToThumb p = new PDFToThumb();
		p.pdfToImage(pdfPath, thumbDirPath);
		System.out.println("covert thumb finished");
		
		System.out.println("delete pdf file");
		File pdf = new File(pdfPath);
		pdf.delete();
	}
}
