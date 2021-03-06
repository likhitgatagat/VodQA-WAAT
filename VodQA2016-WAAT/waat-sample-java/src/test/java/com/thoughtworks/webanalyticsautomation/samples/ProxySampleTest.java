package com.thoughtworks.webanalyticsautomation.samples;

import com.thoughtworks.webanalyticsautomation.Controller;
import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.Status;
import com.thoughtworks.webanalyticsautomation.common.BROWSER;
import com.thoughtworks.webanalyticsautomation.common.TestBase;
import com.thoughtworks.webanalyticsautomation.scriptrunner.helper.WebDriverScriptRunnerHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.webanalyticsautomation.common.Utils;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

import static com.thoughtworks.webanalyticsautomation.Controller.getInstance;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Feb 2, 2011
 * Time: 5:17:08 PM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

public class ProxySampleTest extends TestBase {
    private Logger logger = Logger.getLogger(getClass());
    private Engine engine;
    private WebAnalyticTool webAnalyticTool = WebAnalyticTool.PROXY;
    private InputFileType inputFileType = InputFileType.XML;
    private boolean keepLoadedFileInMemory = true;
    private String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"src", "main", "resources", "log4j.properties"});
    private String inputDataFileName = Utils.getAbsolutePath(new String[] {"src", "test", "sampledata", "TestData.xml"});
    private String actionName = "OpenWAATArticleOnBlog_Proxy";
    private WebDriverScriptRunnerHelper webDriverScriptRunnerHelper;
    private WebDriver driverInstance;

    @Test
    public void captureAndVerifyDataReportedToWebAnalytics_Proxy_GoogleAnalytics_WebDriver_Firefox() throws
            Exception {
        String baseURL = "http://essenceoftesting.blogspot.com";
        String navigateToURL = baseURL + "/search/label/waat";
        ArrayList<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("https://ssl.google-analytics.com/__");
        int minimumNumberOfPackets = 1;

        engine = getInstance(webAnalyticTool, inputFileType, keepLoadedFileInMemory, log4jPropertiesAbsoluteFilePath);
        Proxy seProxy = (Proxy) engine.getSeleniumBasedProxyPlugin();

        startWebDriver(BROWSER.firefox, baseURL, seProxy);
        logger.info("Start capture");
        engine.enableWebAnalyticsTesting(actionName);
        logger.info("Do action");
        driverInstance.get(navigateToURL);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) { }

        logger.info("Verify result");
        Result verificationResult = engine.verifyWebAnalyticsData(inputDataFileName, actionName, urlPatterns, minimumNumberOfPackets);

        Assert.assertNotNull("Verification status should NOT be NULL", verificationResult.getVerificationStatus());
        Assert.assertNotNull("Failure details should NOT be NULL", verificationResult.getListOfErrors());
        logVerificationErrors(verificationResult);
        Assert.assertEquals("Verification status should be PASS", Status.PASS, verificationResult.getVerificationStatus());
        Assert.assertEquals("Failure details should be empty", 0, verificationResult.getListOfErrors().size());
    }

    private void startWebDriver(BROWSER browser, String baseURL, Proxy seProxy) {
        webDriverScriptRunnerHelper = new WebDriverScriptRunnerHelper(logger, browser, baseURL);
        webDriverScriptRunnerHelper.startDriverUsingProxy(seProxy);
        driverInstance = (WebDriver) webDriverScriptRunnerHelper.getDriverInstance();
    }

    @Before
    public void setup () {
        Controller.reset();
    }

    @After
    public void tearDown() throws Exception {
        if (engine!=null) {
            engine.disableWebAnalyticsTesting();
        }
        if (webDriverScriptRunnerHelper!=null) {
            webDriverScriptRunnerHelper.stopDriver();
        }
    }
}
