package com.asapp.common.extentreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import static com.asapp.Constants.EXTENT_REPORTS_DIR;
import static com.asapp.Constants.REPORT_NAME;
import static com.asapp.Constants.TASK_NAME;
import static com.asapp.common.Constants.BROWSER_NAME;
import static com.asapp.common.Constants.ENV_PROPERTY;

public class ExtentReportsManager {

    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private static final ExtentReports extentReports = new ExtentReports();

    private static final Logger LOGGER = LogManager.getLogger(ExtentReportsManager.class);

    /**
     * Initialize Extent Report
     */
    public static void initializeExtentReports() {

        String reportDir = EXTENT_REPORTS_DIR + System.getProperty(TASK_NAME) + "/";
        try {
            FileUtils.deleteDirectory(new File(reportDir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(
                reportDir + System.getProperty(TASK_NAME));

        String reportName = String.format(REPORT_NAME, System.getProperty(TASK_NAME),
                System.getProperty(BROWSER_NAME), System.getProperty(ENV_PROPERTY));

        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().thumbnailForBase64(true);
        extentSparkReporter.config().setTimeStampFormat("dd MMM YYYY hh:mm:ss");
        extentSparkReporter.config().setDocumentTitle(reportName);
        extentReports.attachReporter(extentSparkReporter);
        LOGGER.info("Initialize Extent Report - {}", reportName);

    }

    /**
     * Start Extent Test with the input Name
     *
     * @param testName - Test Name
     */
    public static void startExtentTest(String testName) {

        ExtentTest extentTest = extentReports.createTest(testName)
                .assignDevice(System.getProperty(BROWSER_NAME))
                .assignCategory(System.getProperty(ENV_PROPERTY));
        LOGGER.info("Create Extent Test - {} for Test - {}", extentTest, testName);
        extentTestThreadLocal.set(extentTest);

    }

    /**
     * Start Extent API Test with the input Name
     *
     * @param testName - Test Name
     */
    public static void startExtentApiTest(String testName) {

        ExtentTest extentTest = extentReports.createTest(testName)
                .assignCategory(System.getProperty(ENV_PROPERTY));
        LOGGER.info("Create Extent Test - {} for Test - {}", extentTest, testName);
        extentTestThreadLocal.set(extentTest);

    }

    /**
     * Get Extent Test
     *
     * @return - ExtentTest
     */
    public static ExtentTest getExtent() {
        ExtentTest extentTest;
        try {
            extentTest = extentTestThreadLocal.get();
            LOGGER.info("Get Extent Test - {}", extentTest.toString());
        } catch (Exception e) {
            startExtentTest("Junit 5");
            extentTest = extentTestThreadLocal.get();
        }
        return extentTest;
    }

    /**
     * Clear Extent Test from Thread
     */
    public static void clearExtentTest() {

        LOGGER.info("Remove Extent Test - {} ", extentTestThreadLocal.get());
        extentTestThreadLocal.remove();

    }

    /**
     * Flush Extent Report
     */
    public static void finishExtentTest() {

        LOGGER.info("Extent Report Flush");
        extentReports.flush();

    }

}