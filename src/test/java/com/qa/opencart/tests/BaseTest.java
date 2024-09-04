package com.qa.opencart.tests;


import com.microsoft.playwright.Page;

import com.qa.factory.*;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {
    PlaywrightFactory playwrightFactory;
    protected HomePage homePage;
    protected LoginPage loginPage;
    Page page;
    Properties prop;


    @Parameters({"browser"})
    @BeforeTest
    public void setup(String browserName)
    {
        playwrightFactory=new PlaywrightFactory();
        prop=playwrightFactory.init_prop();
        if(!browserName.contains(""))
        {
            prop.setProperty("browser",browserName);
        }else {
        page=playwrightFactory.initBrowser(prop);
        }homePage=new HomePage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();

    }
}
