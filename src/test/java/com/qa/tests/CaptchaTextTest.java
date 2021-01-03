package com.qa.tests;

import com.qa.base.DriverFactory;
import com.qa.pageActions.SignInPageActions;
import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Properties;

public class CaptchaTextTest {

    Properties properties;
    DriverFactory driverFactory;

    @BeforeMethod
    public void setup(Method methodName) throws MalformedURLException {
        driverFactory = new DriverFactory();
        properties = driverFactory.initializeProp();
        driverFactory.initializeDriver(properties, methodName);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.removeDriver();
    }

    @Test
    public void readTextFromCaptchaTest() {
        var signInPageActions = new SignInPageActions();

        signInPageActions.enterEmail();
        signInPageActions.clickNextBtn();
    }
}
