package fast.common.glue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v111.domsnapshot.model.StringIndex;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.io.File;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import csf.common.DataExtraction;
import csf.common.Reports;


public class CustomizedCommonStepDefs {
	
	WebDriver driver = null;
	Reports reportObj = new Reports();
	public static ExtentTest test;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	String Test_Start_Time = null;
	DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
	Date date;
	HashMap<String, String> testDataCollection = null;
	public HashMap<String, String> map = new HashMap<String,String>();
	DataExtraction testDataObj = new DataExtraction();
	String Move_Reports_From_Download_To_Result_Folder = "true";
	@Before
	public void beforeHook(Scenario scenario) {
		date = new Date();
		Test_Start_Time = dateFormat.format(date);
		
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\TestResults\\"+Test_Start_Time+"\\"+scenario.getName()
		+Test_Start_Time+".html");
		htmlReporter.config().setDocumentTitle("Extent Report");
		htmlReporter.config().setReportName(scenario.getName());
		//htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		test = extent.createTest(scenario.getName(), "Sakthi's Framework");
		map.put("TESTCASE_START_TIME", Test_Start_Time);
		
		String tcName = scenario.getName();
		
		map.put("TESTCASE_NAME", tcName);
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\sakth\\BDD_Cucumber_framework\\BDDCucumber\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
	
		
		//testDataCollection = testDataObj.fecthTestData(System.getProperty("user.dir")+"\\testData\\"+"csf_DataSheet.xlsx", tcName);
		
	}
	
	@After
	public void afterHook(Scenario scenario) {
		
		if(scenario.getStatus().toString().equals("PASSED")) {
			test.log(Status.PASS, "Status: "+scenario.getStatus());
		}else if(scenario.getStatus().toString().equals("FAILED")) {
			test.log(Status.FAIL, "Status: "+scenario.getStatus());
			test.log(Status.INFO, "Exception is : "+Thread.currentThread().getStackTrace());
		}else if(scenario.getStatus().toString().equals("SKIPPED")) {
			test.log(Status.SKIP, "Status: "+scenario.getStatus());
		}else {
//			test.log(Status.ERROR, "Test Case not fully run");
		}
	//	htmlReporter.flush();
		extent.flush();
		driver.close();
		driver.quit();
	}
	@Given("launch the application")
	public void launch_the_application() {
	    // Write code here that turns the phrase above into concrete actions
		driver.get("https://www.saucedemo.com/v1/");
		driver.manage().window().maximize();
		reportObj.reports(driver, "URL Lauched successfully", Status.PASS, "URL Lauched successfully",map,test);
		
	}

	@When("application launched")
	public void application_launched() {
	    // Write code here that turns the phrase above into concrete actions
		Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
		reportObj.reports(driver, "Application Lauched Successfully", Status.PASS, "Application Lauched Successfully",map,test);
	   
	}
	@Then("login with {string} and {string}")
	public void login_with_and(String userName, String password) {
	    // Write code here that turns the phrase above into concrete actions
		
		driver.findElement(By.id("user-name")).sendKeys(userName);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("login-button")).click();

		reportObj.reports(driver, "Login Details Entered", Status.PASS, "Login Details Entered",map,test);
	}

	@Then("check login success or not")
	public void check_login_success_or_not() {
	    // Write code here that turns the phrase above into concrete actions
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Products']")));
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Products']")).isDisplayed());

		reportObj.reports(driver, "Login Success", Status.PASS, "Login Success",map,test);
	}

	public String reportFile_Rename_DownloadFolder(String type,String folder, String old_FileName,String new_FileName){
		String newName = null;
		String path = null;
		String fileExtension = null;
		String newCompleteFileName = null;

		try{
			String tempPath = System.getProperty("user.dir")+"\\src\\test\\resources\\csf\\downloads";
			String downloadFilePath = System.getProperty("user.dir") +"\\TestResults\\"+Test_Start_Time+
					"\\download\\"+testDataCollection.get("TESTCASE_NAME")+"";
			File outputDir = new File(tempPath+"\\"+Test_Start_Time+"\\");

			if(!outputDir.exists()){
				outputDir.mkdir();
			}

			path = tempPath +"\\"+Test_Start_Time+"\\";

			testDataCollection.put("REPORT_DOWNLOAD_FOLDER",path);
			testDataCollection.put("DOWNLOAD_FOLDER",path);

			DataExtraction exObj = new DataExtraction();

			boolean fileFound = false;

			File file = new File(folder);
			System.out.println("Reading this "+file.toString());

			if(file.isDirectory()){
				File[] files = file.listFiles();
				List<File> fileList = Arrays.asList(files);

				switch (type) {
				case "PDF":
					fileExtension = ".pdf";
					break;
				case "XLS":
					fileExtension=".xls";
					break;
				case "CSV":
					fileExtension = ".csv";
					break;
				case "HTML":
					fileExtension = ".html";
					break;
				}

				if(files.length>0)
				{
					Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
					File latestFile = files[0];

						for(int i=0;i<50;i++){
							if(latestFile.getName().contains(".crdownload")){
								Thread.sleep(1000);
								files = file.listFiles();
								Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
								latestFile = files[0];
							}else{
								break;
							}
						}

						if(latestFile.getName().startsWith(old_FileName) && latestFile.getName().contains(fileExtension)){
							fileFound=true;
							String tempString = latestFile.getName().toString();
							System.out.println("Temp String : "+tempString);
							System.out.println(latestFile.getAbsolutePath());

							newCompleteFileName = new_FileName + fileExtension;

							newName = latestFile.getAbsolutePath().replace(tempString,newCompleteFileName);

							boolean isRenamed = latestFile.renameTo(new File(newName));

							if(isRenamed){
								System.out.println(String.format("Renamed this file %s to %s",latestFile.getName(), newName));
							}else{
								System.out.println(String.format("%s file is not renamed to %s",latestFile.getName(), newName));
							}

						}
				}
				if(!fileFound){
					for(File f : fileList){
						System.out.println("Name : "+f.getName());
						if(!f.isDirectory() && f.getName().startsWith(old_FileName) && f.getName().endsWith(fileExtension)){
							String tempString = f.getName().toString();
							System.out.println("Temp String : "+tempString);
							System.out.println(f.getAbsolutePath());
							newCompleteFileName = new_FileName+fileExtension;
							newName = f.getAbsolutePath().replace(tempString,newCompleteFileName);
							boolean isRenamed = f.renameTo(new File(newName));
							if (isRenamed){
								System.out.println(String.format("Renamed this file %s to %s", f.getName(),newName));
							}else{
								System.out.println(String.format("%s file is not renamed to %s", f.getName(),newName));
							}
							break;
						}
					}
				}
			}

			outputDir = new File(path);
			if(!outputDir.exists())
			{
				outputDir.mkdir();
			}

			File from = new File(folder+newCompleteFileName);
			File to = new File(downloadFilePath+newCompleteFileName);
			FileUtils.copyFile(from,to);

			to = new File(path+newCompleteFileName);
			FileUtils.moveFile(from,to);
		}
		catch (Exception e){
			System.out.println("Exception in rename download report");
			e.printStackTrace();
		}

	return newName;
	}
	public void reportFile_Move(String type,String folder, String newPath,String old_FileName) {
		String fileExtension = null;


		try {
			if (Move_Reports_From_Download_To_Result_Folder.equalsIgnoreCase("true")) {

				File file = new File(folder);
				System.out.println("Reading this " + file.toString());

				if (file.isDirectory()) {
					File[] files = file.listFiles();
					List<File> fileList = Arrays.asList(files);
					String finalFileName = "";
					String addVal = "";

					switch (type) {
						case "PDF":
							fileExtension = ".pdf";
							break;
						case "XLS":
							fileExtension = ".xls";
							break;
						case "CSV":
							fileExtension = ".csv";
							break;
						case "HTML":
							fileExtension = ".html";
							break;
					}
					File outputDir = new File(newPath);

					if (!outputDir.exists()) {
						outputDir.mkdir();
					}
					DateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmmss");

					if (files.length > 0) {
						Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
						File latestFile = files[0];

						for (int i = 0; i < 50; i++) {
							if (latestFile.getName().contains(".crdownload")) {
								Thread.sleep(1000);
								files = file.listFiles();
								Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
								latestFile = files[0];
							} else {
								break;
							}
						}

						if (latestFile.getName().startsWith(old_FileName) && latestFile.getName().contains(fileExtension)) {
							finalFileName = latestFile.getName().replace(".crdownload", "");

							dateFormat = new SimpleDateFormat("ddMMyy");
							addVal = dateFormat.format(date);

							String newFileName = folder + dateFormat + "_" + finalFileName;
							latestFile.renameTo(new File(newFileName));
							String remitFileName = addVal + "_" + finalFileName;
							testDataCollection.put("REMIT_FILE_NAME", remitFileName);
						}
						files = file.listFiles();
						for (File newUpdatedFile : files) {

							if (newUpdatedFile.getName().equals(addVal + "_" + finalFileName)) {
								File to = new File(newPath + addVal + "_" + finalFileName);
								FileUtils.moveFile(newUpdatedFile, to);
							}
						}
					} else {
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception in rename download report");
			e.printStackTrace();
		}
	}

	public ArrayList<String> csv_report_dataFetch(String filePath){

		ArrayList<String> masterlistheader = new ArrayList<>();
		ArrayList<ArrayList<String>> mastermultiplecontent = new ArrayList<ArrayList<String>>();
		ArrayList<String> masterlistcontent = new ArrayList<String>();
		ArrayList<String> fileContent = new ArrayList<String>();

		try{
			FileReader fileReader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fileReader);

			String line = "";

			while(((line = br.readLine())!= null)){
				System.out.println("Reading the CSV........!");
				line.replace("\"","");
				fileContent.add(line);
				System.out.println("The line is: "+line);
			}
			System.out.println("Done..........................!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileContent;
	}

	public ArrayList<String> pdf_report_dataFetch(String filePath){

		ArrayList<String> masterlistheader = new ArrayList<>();
		ArrayList<ArrayList<String>> mastermultiplecontent = new ArrayList<ArrayList<String>>();
		ArrayList<String> masterlistcontent = new ArrayList<String>();
		ArrayList<String> fileContent = new ArrayList<String>();

		try{
			String reportDownloadPath = System.getProperty("user.dir")+"downloads";

			PDDocument document = PDDocument.load(new File(reportDownloadPath));

			if(!document.isEncrypted()){
				PDFTextStripper stripper = new PDFTextStripper();
				String text = stripper.getText(document);
				System.out.println("Text : "+text);
			}
			document.close();
			System.out.println("Done..........................!");
		}catch(Exception e){
			e.printStackTrace();
		}
		return fileContent;
	}
}
