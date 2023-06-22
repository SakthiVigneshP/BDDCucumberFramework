package fast.common.glue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import csf.common.DataExtraction;

public class csfBaseStepDefinitions {
	protected Map<String,String> map;
	protected ExtentTest test;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	String Test_Start_Time = null;
	DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmmss");
	Date date;
	HashMap<String, String> testDataCollection = null;
	String testCaseName = null;
	DataExtraction testDataObj = new DataExtraction();

	public csfBaseStepDefinitions(Map<String, String> map, ExtentTest test, String test_Start_Time,
			DateFormat dateFormat, Date date, HashMap<String, String> testDataCollection, DataExtraction testDataObj, String tcName) {
		super();
		this.map = map;
		this.test = test;
		Test_Start_Time = test_Start_Time;
		this.dateFormat = dateFormat;
		this.date = date;
		this.testDataCollection = testDataCollection;
		this.testDataObj = testDataObj;
		this.testCaseName = tcName;
	}
}
