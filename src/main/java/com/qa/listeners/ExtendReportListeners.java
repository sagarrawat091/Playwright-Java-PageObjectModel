package com.qa.listeners;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import static com.qa.factory.PlaywrightFactory.takeScreenshot;

public class ExtendReportListeners implements ITestListener {
    private static final String OUTPUT_FOLDER="./Build/";
    private static final String FILE_NAME="TestExecutionReport.html";

    private static ExtentReports extent=init();
    public static ThreadLocal<ExtentTest> test=new ThreadLocal<>();
    private static ExtentReports extentReports;
    private static ExtentReports init() {
        Path path= Paths.get(OUTPUT_FOLDER);
        if(!Files.exists(path))
        {
            try{
                Files.createDirectories(path);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        extentReports=new ExtentReports();
        ExtentSparkReporter reporter=new ExtentSparkReporter(OUTPUT_FOLDER+FILE_NAME);
        reporter.config().setReportName("Open Cart Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System","Window");
        extentReports.setSystemInfo("Author","Sagar Testing ");
        extentReports.setSystemInfo("Build#","1.1");
        extentReports.setSystemInfo("Team","OMS");
        extentReports.setSystemInfo("Customer Name","NAL");
        return extentReports;
    }

    @Override
    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite is ending");
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName=result.getMethod().getMethodName();
        String qualifieldName= result.getMethod().getQualifiedName();
        int last=qualifieldName.lastIndexOf(".");
        int mid=qualifieldName.substring(0,last).lastIndexOf(".");
        String className=qualifieldName.substring(mid+1,last);
        System.out.println(methodName+" Started!");
        ExtentTest extentTest=extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }
    public synchronized void onTestSucess(ITestResult result){
        System.out.println(result.getMethod().getMethodName()+" Passed!");
        test.get().pass("test passed");
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }
    public synchronized void onTestFailure(ITestResult result){
        System.out.println(result.getMethod().getMethodName()+" Failed!");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }
    public synchronized void onTestSkipped(ITestResult result){
        System.out.println(result.getMethod().getMethodName()+" Skipped!");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailButWithinSucessPercentage(ITestResult result)
    {
        System.out.println("onTestFailButWithinSucessPercentage for "+result.getMethod().getMethodName());
    }
    private Date getTime(long milis)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milis);
        return calendar.getTime();
    }


}
