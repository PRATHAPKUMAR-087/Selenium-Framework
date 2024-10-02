package seleniumframework.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

  // Method to create and configure the ExtentReports object
  public static ExtentReports getReportObject() {
    String path = System.getProperty("user.dir") + "//reports//index.html";
    ExtentSparkReporter reporter = new ExtentSparkReporter(path);
    
    // Configure the report settings
    reporter.config().setReportName("Web Automation Results");
    reporter.config().setDocumentTitle("Test Results");
    
    ExtentReports extent = new ExtentReports();
    extent.attachReporter(reporter);
    extent.setSystemInfo("Tester", "Prathap Kumar");
    
    return extent;	
  }
}
