package converter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class PPTToPNGConverter {

	private double IMAGE_RATE = 1.5; // 预览图比例

	public PPTToPNGConverter() {

	}
	
	private void toPNG(int pgWidth,int pgHeight,Slide slide,String pngPath) throws IOException {
		
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
		FileOutputStream out = new FileOutputStream(pngPath);
		javax.imageio.ImageIO.write(img, "png", out);
		out.close();
	}

	private void covertPPT(String pptPath, String thumbDirPath) throws IOException {
		FileInputStream is = new FileInputStream(pptPath);
		HSLFSlideShow ppt = new HSLFSlideShow(is);
		is.close();
		Dimension pgsize = ppt.getPageSize();
		int idx = 1;
		for (HSLFSlide slide : ppt.getSlides()) {
			this.toPNG(pgsize.width,pgsize.height,slide,thumbDirPath + "/slide" + idx + ".png");
			idx++;
		}
		ppt.close();
	}

	private void covertPPTX(String pptPath, String thumbDirPath) throws IOException {

		FileInputStream is = new FileInputStream(pptPath);
		XMLSlideShow ppt  = new XMLSlideShow(is);
		is.close();
		Dimension pgsize = ppt.getPageSize();
		int idx = 1;
		for (XSLFSlide slide : ppt.getSlides()) {
			this.toPNG(pgsize.width,pgsize.height,slide,thumbDirPath + "/slide" + idx + ".png");
			idx++;
		}
		ppt.close();
	}

	/***
	 * 开始装换
	 * 
	 * @throws IOException
	 */
	public void startConvert(String pptPath, String thumbDirPath) throws IOException {

		if (pptPath.endsWith("pptx")) {
			this.covertPPTX(pptPath, thumbDirPath);
		} else if (pptPath.endsWith("ppt")) {
			this.covertPPT(pptPath, thumbDirPath);
		}
	}

}
