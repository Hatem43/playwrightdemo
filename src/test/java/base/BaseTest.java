package base;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;

public class BaseTest{

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    protected BrowserContext context;

    @BeforeSuite
    public void setup() {
        playwright = Playwright.create();
        browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000).setArgs(Arrays.asList("--start-maximized")));
        context=browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page=context.newPage();
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
