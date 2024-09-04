package com.qa.factory;


import com.microsoft.playwright.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class PlaywrightFactory {
    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    //thread local
    private static final ThreadLocal<Playwright> tlplaywright=new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser=new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlBrowserContext=new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage=new ThreadLocal<>();

    //getter
    public static Playwright getPlaywright()
    {
        return tlplaywright.get();
    }
    public static Browser getBrowser()
    {
        return tlBrowser.get();
    }
    public static BrowserContext getBrowserContext()
    {
        return tlBrowserContext.get();
    }
    public static Page getPage()
    {
        return tlPage.get();
    }

    public Page initBrowser(Properties prop)
    {
        String BrowserName= prop.getProperty("browser").trim();
        System.out.println("Browser name is "+BrowserName);
        tlplaywright.set(Playwright.create());
        switch (BrowserName.toLowerCase())
        {
            case "chromium":
//                browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless"))));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless")))));
                break;

            case "firefox":
//                browser=playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless"))));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless")))));
                break;

            case "safari":
//                browser=playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless"))));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless")))));

                break;

            case "chrome":
//                browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(Boolean.parseBoolean(prop.getProperty("headless"))));
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(Boolean.parseBoolean(prop.getProperty("headless")))));
                break;
        }

//        browserContext=browser.newContext();
        tlBrowserContext.set(getBrowser().newContext());
//        page=browserContext.newPage();
        tlPage.set(tlBrowserContext.get().newPage());
        getPage().navigate(prop.getProperty("url"));
//        page.navigate(prop.getProperty("url"));
        return getPage();
    }

    public Properties init_prop()
    {
        try {
            FileInputStream ip=new FileInputStream("./src/test/resources/Config/Config.properties");
            prop=new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return prop;
    }
    public static String takeScreenshot()
    {
        String path=System.getProperty("user.dir")+"/screenshot/"+System.currentTimeMillis()+".png";
        getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        return path;
    }
}
