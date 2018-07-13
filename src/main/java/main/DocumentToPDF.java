package main;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.ExternalOfficeManagerBuilder;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.LocalOfficeManager.Builder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

public class DocumentToPDF {

	private int port = 8100;
	
	private int processTimeout = 1000 * 60 * 2;
	
	private String officeHome = "/opt/libreoffice5.4";
//	private String officeHome = "E:\\libreoffice";
	
	private int maxTasksPerProcess = 10;

	public DocumentToPDF() {
		
	}
	
	private boolean isConnecting() {
		Socket socket = null;
		try {
			 socket = new Socket("127.0.0.1",this.port);
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
		return socket.isConnected();
	}
 
	
	public void startConverter(String documentPath, String pdfPath, int port,String officeHome) throws OfficeException, UnknownHostException, IOException {
	
		if(port != 0)this.port = port; 
		
		if(officeHome != null)this.officeHome = officeHome;
		
		OfficeManager office = null;
		
		if(this.isConnecting()) {
			ExternalOfficeManagerBuilder  extenalBuilder = new ExternalOfficeManagerBuilder();
			extenalBuilder.setConnectOnStart(true);
			extenalBuilder.setPortNumber(this.port);
			office = extenalBuilder.build();
		}else {
			Builder builder  = LocalOfficeManager.builder().install();
			builder.officeHome(officeHome);
			builder.portNumbers(this.port);
			builder.processTimeout(this.processTimeout);
			builder.maxTasksPerProcess(this.maxTasksPerProcess);
			builder.taskExecutionTimeout(this.processTimeout);
			builder.taskQueueTimeout(this.processTimeout);
			office = builder.build();
		}
		office.start();
		File source = new File(documentPath);
		File target = new File(pdfPath);
		OfficeDocumentConverter dc = new OfficeDocumentConverter(office);
		dc.convert(source, target);
//		office.stop();
	}
	
	
}
