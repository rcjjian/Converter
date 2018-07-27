package converter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.fonts.FontMappings;

import converter.base.BaseConveter;

public class PDFToPNGConverter extends BaseConveter {

	private PdfDecoder decode_pdf ;
	
	private float PDF_SCALE = 1.3f;
	
	public PDFToPNGConverter(String inputPath, String outputPath) {
		super(inputPath, outputPath);
	}

	/***
	 * @param pdfPath
	 * @param dirPath
	 * @param msgNum
	 *            截图数量达到3张时通知
	 * @throws Exception 
	 */
	public void startConvert() throws Exception {
		super.startConvert();
		
		/** instance of PdfDecoder to convert PDF into image */
		decode_pdf = new PdfDecoder(true);

		/** set mappings for non-embedded fonts to use */
		FontMappings.setFontReplacements();
		/** open the PDF file - can also be a URL or a byte array */
		try {
			decode_pdf.openPdfFile(this.inputPath); // file
			// decode_pdf.openPdfFile("C:/myPDF.pdf", "password"); //encrypted file
			// decode_pdf.openPdfArray(bytes); //bytes is byte[] array with PDF
			// decode_pdf.openPdfFileFromURL("http://www.mysite.com/myPDF.pdf",false);
			
			/** get page 1 as an image */
			// page range if you want to extract all pages with a loop
			int start = 1, end = decode_pdf.getPageCount();
			for (int i = start; i < end + 1; i++) {
				if(!this.isConverting)break; //被改变状态
				decode_pdf.setPageParameters(PDF_SCALE, i);
				BufferedImage img = decode_pdf.getPageAsImage(i);
				ImageIO.write(img, "png", new File(this.outputPath + "/slide" + i + ".png"));
				img.flush();
			} 	
		} finally {
			decode_pdf.closePdfFile();
			decode_pdf.dispose();
			decode_pdf = null;
		}
	}

	public void cancelConvert() throws Exception {
		super.cancelConvert();
		if(this.decode_pdf != null) {
			this.decode_pdf.closePdfFile();
			File file = new File(this.inputPath);
			file.deleteOnExit();
			
			File dirFile = new File(this.outputPath);
			if(dirFile.exists()) {
				for (File childFile : dirFile.listFiles()) {
					childFile.delete();
				}
				dirFile.delete();
			}
			dirFile = null;
			this.decode_pdf = null;
		}
		
	}
}
