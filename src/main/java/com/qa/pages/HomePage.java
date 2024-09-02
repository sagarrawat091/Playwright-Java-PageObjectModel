package com.qa.pages;

import com.microsoft.playwright.Page;

public class HomePage {
    Page page;
    public String searchtextbox="div#search input";
    public String searctBtn="div#search button";
    public String searchedText="div#content h1";
    public String myAccountBtn="//span[text()='My Account']";
    public String loginBtn="//a[text()='Login']";

    //constructor to get the page
    public HomePage(Page page)
    {
        this.page=page;
    }

    //actions
    public String getPageTile()
    {
        String pagetitle=page.title();
        System.out.println("the page title is "+pagetitle);
        return pagetitle;
    }
    public String getHomePageURl()
    {
        String url=page.url();
        System.out.println(url);
        return url;
    }

    public String doSearch(String productName)
    {
        page.fill(searchtextbox,productName);
        page.click(searctBtn);
        return page.textContent(searchedText);

    }
    public LoginPage navigateToLoginPage()
    {
        page.click(myAccountBtn);
        page.click(loginBtn);
        return new LoginPage(page);
    }
}
