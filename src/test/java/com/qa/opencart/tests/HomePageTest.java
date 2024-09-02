package com.qa.opencart.tests;

import com.qa.appConstants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest{

    @Test
    public void homePageTitleTest()
    {
        String actual=homePage.getPageTile();
        Assert.assertEquals(actual, AppConstants.Home_PAGE_TITLE);
    }
    @Test
    public void homePageUrl()
    {
        String actualurl=homePage.getHomePageURl();
        Assert.assertEquals(actualurl,prop.getProperty("url"));
    }
    @DataProvider
    public Object[][] getProductNames(){
        return new Object[][]{
                {"Mackbook"},
                {"Moto"},
                {"Samsung"}
        };
    }

    @Test(dataProvider = "getProductNames")
    public void searchtest(String ProductName)
    {
        String actualtext=homePage.doSearch(ProductName);
        Assert.assertEquals(actualtext,"Search - "+ProductName);
    }


}
