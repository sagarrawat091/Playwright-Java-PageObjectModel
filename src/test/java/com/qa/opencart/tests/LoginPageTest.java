package com.qa.opencart.tests;

import com.qa.appConstants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test(priority = 0)
    public void loginPageNavigationtest()
    {
        loginPage=homePage.navigateToLoginPage();
        String actualTitle=loginPage.getPageTitle();
        System.out.println("page act title "+actualTitle);
        Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE);
    }

    @Test(priority = 1)
    public void checkUrl(){
        String actualurl=loginPage.getUrl();
        System.out.println(actualurl);
        System.out.println(prop.getProperty("loginurl"));
        Assert.assertEquals(actualurl,prop.getProperty("loginurl"));
    }

    @Test(priority = 2)
    public void appLoginTest()
    {
        System.out.println("user name "+prop.getProperty("username"));
        System.out.println("password "+prop.getProperty("password"));
        Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(),prop.getProperty("password").trim()));

    }


}
