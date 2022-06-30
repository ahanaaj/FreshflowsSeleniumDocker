package com.freshflows.tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseTest {

    protected WebDriver driver;
    public ExtentReports extent;
    public ExtentTest extentTest;


    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source =ts.getScreenshotAs(OutputType.FILE);
        String destination =System.getProperty("user.dir") + "/FailedTestsScreenshot" + dateName + ".png";
        File finalDestination =new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @BeforeTest
    public void setupDriver(ITestContext ctx) throws MalformedURLException {
        // BROWSER => chrome / firefox
        // HUB_HOST => localhost / 10.0.1.3 / hostname

        String host = "localhost";
        MutableCapabilities dc;

        if(System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            dc = new FirefoxOptions();
        }else{
            dc = new ChromeOptions();
        }

        if(System.getProperty("HUB_HOST") != null){
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";
        dc.setCapability("name", testName);
        this.driver = new RemoteWebDriver(new URL(completeUrl), dc);

        extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html", true);
        extent.addSystemInfo("Environment", "QA");
    }

    @AfterTest
    public void quitDriver(ITestResult result) throws IOException{
        extent.flush();
        extent.close();
        if(result.getStatus() == ITestResult.FAILURE)
        {
            extentTest.log(LogStatus.FAIL, "Test case failed" +result.getName());
            String screenshotPath = BaseTest.getScreenshot(driver, result.getName());
            extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));

        }
        else if(result.getStatus() == ITestResult.SKIP){
            extentTest.log(LogStatus.SKIP, "Test case skipped" +result.getName());
        }
        extent.endTest(extentTest);
        this.driver.quit();
    }

}
