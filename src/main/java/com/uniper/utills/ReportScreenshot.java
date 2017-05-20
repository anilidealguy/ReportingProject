package com.uniper.utills;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.uniper.model.Report;


public class ReportScreenshot {
	private static Logger s_logger = Logger.getLogger(ReportScreenshot.class);
	
	public static String getCroppedImage(String screenShot, String dimensions){
		
		
		String[] dims = dimensions.split(";");
		File outputFile = new File(screenShot);//.getAbsoluteFile();
		System.out.println(System.getProperty("user.dir"));
		String filePath = "../webapps/UniperReporting/images/screenshot_cropped.jpg";
		try {
			Image orig = ImageIO.read(outputFile);
			int x = Integer.parseInt(dims[0]), y = Integer.parseInt(dims[1]), w = Integer.parseInt(dims[2]), h = Integer.parseInt(dims[3]);  // define the sections to cut out
			BufferedImage outputImage = new BufferedImage(850, 450, BufferedImage.OPAQUE);
			Graphics2D g = outputImage.createGraphics();
			g.drawImage(orig, 0, 0, 850, 450, x, y, x+w, y+h, null);
			g.dispose();
			//ImageIO.write(outputImage, "jpg", new File("c:\\Anil\\mkyong_jpg.jpg"));
			
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.OPAQUE);
			bi.getGraphics().drawImage(orig, 0, 0, w, h, x, y, x + w, y + h, null);
			File croppedFile = new File(filePath);
			ImageIO.write(bi, "jpg", croppedFile);
			//filePath = croppedFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;
	}
	
	public static String getScreenshot(String url) {
		DesiredCapabilities caps = DesiredCapabilities.phantomjs();
		//String phantomJsBinaryPath = "C:/Users/achebr/Desktop/Learn/phantomjs-2.1.1-windows/bin/phantomjs.exe";
		
		String phantomJsBinaryPath = "C:/Anil/ReqJars/phantomjs-2.1.1-windows/bin/phantomjs.exe";
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsBinaryPath);
		caps.setCapability("phantomjs.page.zoomFactor", 0.25);
		
		WebDriver driver = new PhantomJSDriver(caps);
		
		/*String script = "var page = this;page.zoomFactor = 0.5;";
		Object[] args = new Object[4];
		((PhantomJSDriver) driver).executeScript(script,args );*/
		driver.manage().window().setSize(new Dimension(2000, 900));
		
		((JavascriptExecutor)driver).executeScript("document.body.style.transform='scale(0.9)';");
		s_logger.info(url);
		s_logger.info("ReportScreenshot : " + System.getProperty("user.dir"));
		driver.get(url);
		((JavascriptExecutor)driver).executeScript("document.body.style.transform='scale(0.9)';");
		/*WebElement html = driver.findElement(By.tagName("html"));
		html.sendKeys(Keys.chord(Keys.CONTROL, "o"));*/
		//((JavascriptExecutor) driver).executeScript("", arg1)
		
		System.out.println(System.getProperty("user.dir"));
		String screenShot = "../webapps/UniperReporting/images/screenshot.png";
		try {
			TimeUnit.SECONDS.sleep(20);
			
			//driver.manage().window().
			File rawImage = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File outputFile = new File(screenShot).getAbsoluteFile();
			System.out.println(outputFile.getAbsolutePath());
			FileUtils.copyFile(rawImage, outputFile,true);
			
			//ReportScreenshot.getCroppedImage("anil");
		
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			driver.quit();
		}
		return screenShot;
	}
}
