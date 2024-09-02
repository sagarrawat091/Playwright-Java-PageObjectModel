package com.qa.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    Page page;
    //constructor
    public LoginPage(Page page)
    {
        this.page=page;
    }

    //String
    public String username="//input[@name='email']";
    public String password="//input[@name='password']";
    public String loginBtn="//input[@value='Login']";
    public String forgotpassword="(//a[text()='Logout'])[2]";



    public String getPageTitle()
    {
        return page.title();
    }
    public String getUrl()
    {
        return page.url();
    }

    public boolean doLogin(String appusername,String apppassword)
    {
        System.out.println("App creds"+appusername+" "+apppassword);
        page.fill(username,appusername);
        page.fill(password,apppassword);
        page.click(loginBtn);
        if(page.isVisible(forgotpassword))
        {
            System.out.println("user is successfully logged in ");
            return true;
        }
        return false;
    }


}
