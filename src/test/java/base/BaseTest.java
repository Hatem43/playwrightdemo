package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest{

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeSuite
    public void setup() {
        playwright = Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
        page=browser.newPage();

    }

    @AfterSuite
    public void teardown() {

        if (browser!=null) {
           browser.close();
        }
        if (playwright!=null) {
            playwright.close();
        }
    }

}
