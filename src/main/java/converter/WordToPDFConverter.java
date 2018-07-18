package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class WordToPDFConverter {

	public WordToPDFConverter() {
	}

	/***
	 * 调用声明，不然会有水印
	 */
	private void licence() {
		try {
			File file = new File("./licence.xml");
			FileInputStream fis = new FileInputStream(file);
			License aposeLic = new License();
			aposeLic.setLicense(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startConvert(String docPath,String pdfPath) throws Exception {
		this.licence();
	
		File file = new File(pdfPath);
		FileOutputStream os = new FileOutputStream(file);

		Document doc = new Document(docPath);
		doc.save(os, SaveFormat.PDF);
	}

}
