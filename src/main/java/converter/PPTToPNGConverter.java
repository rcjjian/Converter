package converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import converter.base.BaseConveter;
import converter.exception.ConvertException;

public class PPTToPNGConverter extends BaseConveter {

	private double IMAGE_RATE = 1; // 预览图比例

	public PPTToPNGConverter(String inputPath, String outputPath) {
		super(inputPath, outputPath);
	}

	private void toPNG(int pgWidth, int pgHeight, Slide slide, String pngPath) throws IOException{

		int imageWidth = (int) Math.floor(this.IMAGE_RATE * pgWidth);
		int imageHeigth = (int) Math.floor(this.IMAGE_RATE * pgHeight);
		
		BufferedImage img = new BufferedImage(imageWidth, imageHeigth, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		// clear the drawing area
		graphics.setPaint(Color.white);
		graphics.fill(new Rectangle2D.Float(0, 0, imageWidth, imageHeigth));
		graphics.scale(this.IMAGE_RATE, this.IMAGE_RATE);
		slide.draw(graphics);
		// save the output
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(pngPath);
			javax.imageio.ImageIO.write(img, "png", out);
		}finally {
			if(out != null)out.close();
			if(graphics != null)graphics.dispose();
			if(img != null)img.flush();
			img = null;
			graphics = null;
			out = null;
		}
	}

	private void covertPPT(String pptPath, String thumbDirPath) throws Exception {
		FileInputStream is = new FileInputStream(pptPath);
		HSLFSlideShow ppt = new HSLFSlideShow(is);
		Dimension pgsize = ppt.getPageSize();
		int idx = 1;
		try {
			for (HSLFSlide slide : ppt.getSlides()) {
				if (!this.isConverting) {
					System.out.println("covert ppt convert stop!");
					throw new ConvertException();
				}
				this.toPNG(pgsize.width, pgsize.height, slide, thumbDirPath + "/slide" + idx + ".png");
				idx++;
			}
		}catch(Exception e) {
			throw e;
		}finally {
			if(is != null)is.close();
			is = null;
			if(ppt != null)ppt.close();
			ppt = null;
		}
	}

	private void covertPPTX(String pptPath, String thumbDirPath) throws Exception {

		FileInputStream is = new FileInputStream(pptPath);
		XMLSlideShow ppt = new XMLSlideShow(is);
		Dimension pgsize = ppt.getPageSize();
		int idx = 1;
		try {
			for (XSLFSlide slide : ppt.getSlides()) {
				if (!this.isConverting) {
					System.out.println("covert pptx convert stop!");
					throw new ConvertException();
				}
				this.toPNG(pgsize.width, pgsize.height, slide, thumbDirPath + "/slide" + idx + ".png");
				idx++;
			}
		}catch(Exception e) {
			throw e;
		}finally {
			is.close();
			ppt.close();
			is = null;
			ppt = null;
		}
	}

	/***
	 * 开始装换
	 * 
	 * @throws Exception
	 */
	public void startConvert() throws Exception {
		super.startConvert();
		File dirFile = new File(this.outputPath);
		if (!dirFile.exists())
			dirFile.mkdirs();

		if (this.inputPath.endsWith("pptx")) {
			this.covertPPTX(this.inputPath, this.outputPath);
		} else if (this.inputPath.endsWith("ppt")) {
			this.covertPPT(this.inputPath, this.outputPath);
		}
		dirFile = null;
		System.gc();
	}

	public void cancelConvert() throws Exception {
		super.cancelConvert();
		File dirFile = new File(this.outputPath);
		if (dirFile.exists()) {
			for (File file : dirFile.listFiles()) {
				file.delete();
			}
			dirFile.delete();
		}
	}
}
