package com.thoughtworks.webanalyticsautomation.common;

/**
 * Created by: Anand Bagmar
 * Email: abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 *
 * Copyright 2010 Anand Bagmar (abagmar@gmail.com).  Distributed under the Apache 2.0 License
 */

import com.thoughtworks.webanalyticsautomation.Engine;
import com.thoughtworks.webanalyticsautomation.Result;
import com.thoughtworks.webanalyticsautomation.inputdata.InputFileType;
import com.thoughtworks.webanalyticsautomation.plugins.WebAnalyticTool;
import org.apache.log4j.Logger;

public class TestBase {
    protected final Logger logger = Logger.getLogger(getClass());
    protected Engine engine;
    protected WebAnalyticTool webAnalyticTool;
    protected final InputFileType inputFileType = InputFileType.XML;
    protected boolean keepLoadedFileInMemory = true;
    protected String log4jPropertiesAbsoluteFilePath = Utils.getAbsolutePath(new String[] {"src", "main", "resources", "log4j.properties"});
    protected String inputDataFileName = Utils.getAbsolutePath(new String[] {"src", "test", "sampledata", "TestData.xml"});

    protected void logVerificationErrors(Result verificationResult) {
        logger.info("Verification ERRORS:");
        logger.info (verificationResult.getListOfErrors());
    }
}