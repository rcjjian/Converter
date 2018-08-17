package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import converter.base.BaseConveter;

public class WordToPDFConverter extends BaseConveter {

	private File pdfFile;
	
	private Document doc;

	private static boolean hasLoadedLicense = false;

	public WordToPDFConverter(String inputPath, String outputPath) {
		super(inputPath, outputPath);
	}

	/***
	 * 调用声明，不然会有水印
	 */
	private static void licence() {
		if (hasLoadedLicense)
			return;
		FileInputStream fis = null;
		try {
			File file = new File("./licence.xml");
			fis = new FileInputStream(file);
			License aposeLic = new License();
			aposeLic.setLicense(fis);
			hasLoadedLicense = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startConvert() throws Exception{
		this.licence();
		FileOutputStream os = null;
		try {
			pdfFile = new File(this.outputPath);
			os = new FileOutputStream(pdfFile);
			doc = new Document(this.inputPath);
			doc.acceptAllRevisions();
			doc.save(os, SaveFormat.PDF);
			
		} finally {
			try {
				if(doc != null)doc.cleanup();
				if(os != null) {
					os.flush();
					os.close();
				}
				os = null;
				doc = null;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void deletePDF() {
		if (pdfFile != null) {
			pdfFile.delete();
			pdfFile = null;
		}
	}
	
	public void cancelConvert() throws Exception {
		super.cancelConvert();
		this.deletePDF();
	}
}
