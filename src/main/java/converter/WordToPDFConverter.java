package converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import converter.base.BaseConveter;

public class WordToPDFConverter extends BaseConveter{

	public WordToPDFConverter(String inputPath, String outputPath) {
		super(inputPath,outputPath);
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

	public void startConvert() throws Exception {
		this.licence();
	
		File file = new File(this.outputPath);
		FileOutputStream os = new FileOutputStream(file);

		Document doc = new Document(this.inputPath);
		doc.save(os, SaveFormat.PDF);
	}

}
