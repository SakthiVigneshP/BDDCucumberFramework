package csf.common;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.Assert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import fast.common.glue.CustomizedCommonStepDefs;

public class Reports{
	
	WebDriver driver = null;	
	
	public static ExtentTest test;
	

	public void reports(WebDriver driver,String description, Status testStatus, String stepDetails,HashMap<String, String> map,ExtentTest test ) {
		
		
		String test_Start_time = map.get("TESTCASE_START_TIME");
		String testCaseName = map.get("TESTCASE_NAME");
		String screenshotFileName = null, methodStatus = null;
		
		try {
			System.out.println("Log Status : "+testStatus);
			
			if(testStatus.equals(Status.PASS)) {
				methodStatus = "PASS";
			}else if(testStatus.equals(Status.FAIL)) {
				methodStatus = "FAIL";
			}else if(testStatus.equals(Status.INFO)) {
				methodStatus = "INFO";
			}else if(testStatus.equals(Status.WARNING)) {
				methodStatus = "WARNING";
			}else {
				methodStatus = "FATAL";
			}
			
			stepDetails = stepDetails.replace(";", "");
			
			switch(methodStatus) {
			
			case "PASS":
			{
				screenshotFileName = capture(driver,test_Start_time, testCaseName,stepDetails).trim();
				System.out.println(screenshotFileName);
				test.pass("PASS",MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				break;
			}
			case "FAIL":
			{
				screenshotFileName = capture(driver,test_Start_time, testCaseName,stepDetails).trim();
				test.fail("FAIL",MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				org.testng.Assert.fail(description);
				break;
			}
			case "ERROR":
			{
				screenshotFileName = capture(driver,test_Start_time, testCaseName,stepDetails).trim();
				test.fail("ERROR",MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				org.testng.Assert.fail(description);
				break;
			}
			case "WARNING":
			{
				screenshotFileName = capture(driver,test_Start_time, testCaseName,stepDetails).trim();
				test.warning("WARNING",MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				org.testng.Assert.fail(description);
				break;
			}
			case "FATAL":
			{
				screenshotFileName = capture(driver,test_Start_time, testCaseName,stepDetails).trim();
				test.fail("FATAL",MediaEntityBuilder.createScreenCaptureFromPath(screenshotFileName).build());
				org.testng.Assert.fail(description);
				break;
			}
			default:{
				System.out.println("Invalid log status");
				break;
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String capture(WebDriver driver, String Test_Start_Time, String testCaseName, String screenShotName) {
		String fileName = null, path=null,filePath = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
			Date date = new Date();
			TakesScreenshot ts = (TakesScreenshot)driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			fileName = screenShotName + "_" + dateFormat.format(date) + ".png";
			
			filePath = "\\TestResults\\" + Test_Start_Time + "\\" + testCaseName + "_Images" + "\\" + fileName;
			path = System.getProperty("user.dir")+filePath;
			
			String pathAlone = System.getProperty("user.dir") + "\\TestResults\\" + Test_Start_Time + "\\" + testCaseName + "_Images";
			File folder = new File(pathAlone);
			
			File destination = new File(path);
			
			if(!folder.exists()) {
				folder.mkdir();
			}
			FileUtils.copyFile(source, destination);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//return testCaseName + "_Images" + "\\" + fileName;
		return path;
	}
}
