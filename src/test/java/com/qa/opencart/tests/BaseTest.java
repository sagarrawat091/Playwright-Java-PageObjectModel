package com.qa.opencart.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.qa.factory.PlaywrightFactory;
import com.qa.pages.HomePage;
import com.qa.pages.LoginPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.util.Properties;

public class BaseTest {
    PlaywrightFactory playwrightFactory;
    protected HomePage homePage;
    protected LoginPage loginPage;
    Page page;
    Properties prop;

    @BeforeTest
    public void setup()
    {
        playwrightFactory=new PlaywrightFactory();
        prop=playwrightFactory.init_prop();
        page=playwrightFactory.initBrowser(prop);
        homePage=new HomePage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();

    }
}
