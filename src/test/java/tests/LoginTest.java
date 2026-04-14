package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import java.lang.reflect.Method;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {

    LoginPage loginPage;
    public Locator dashboard;
    public Locator errormessage1;
    public Locator errormessage2;
    protected ExtentReports extent;
    protected ExtentTest test;
    public Locator signout;


    @BeforeMethod
    public void setuptest(Method method){
        extent= ExtentManager.getExtent();
        test=extent.createTest(method.getName());
        loginPage=new LoginPage(page);
    }


    @Test
    public void logintestwithvalidcredentials(){

        loginPage.login("Admin","admin123");

        // checking page URL
        Assert.assertEquals(page.url(),"https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        //checking page Title
        //Assert.assertEquals(page.title(),"OrangeHRM");
        dashboard=page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Dashboard"));

        //checking visiability of element
        assertThat(dashboard).isVisible();

        //checking element contains the expected text
        assertThat(dashboard).hasText("Dashboard");

        //checking that element is enabled for interactions
        assertThat(page.locator("//i[@class='oxd-icon bi-chevron-left']")).isEnabled();

        //checking element contains the expected attribute
        assertThat(dashboard).hasAttribute("class", "oxd-text oxd-text--h6 oxd-topbar-header-breadcrumb-module");
        page.locator("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/span/p").click();
        signout=page.getByRole(AriaRole.MENUITEM,new Page.GetByRoleOptions().setName("Logout"));
        signout.click();
    }

    @Test
    public void logintestwithinvalidcredentials(){


        loginPage.login("hatem","123");
        errormessage1=page.getByText("Invalid credentials");
        assertThat(errormessage1).hasText("Invalid credentials");
    }

    @Test
    public void logintestwithemptydata(){
        loginPage.login("","");
        errormessage1=page.locator("(//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'][normalize-space()='Required'])[1]");
        errormessage2=page.locator("(//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'][normalize-space()='Required'])[2]");
        assertThat(errormessage1).hasText("Required");
        assertThat(errormessage1).isVisible();
        assertThat(errormessage2).hasText("Required");
        assertThat(errormessage2).isVisible();
    }

    @Test
    public void logintestwithvalidusernameandemptypassword(){

        loginPage.login("Admin","");
        errormessage2=page.getByText("Required", new Page.GetByTextOptions().setExact(true));
        assertThat(errormessage2).hasText("Required");
        assertThat(errormessage2).isVisible();
        page.pause();
    }





    @Test
    public void logintestwithemptyuserandvalidpassword(){

        loginPage.login("","admin123");
        errormessage1=page.locator("//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'][1]");
        assertThat(errormessage1).hasText("Required");
        assertThat(errormessage1).isVisible();
    }




    @AfterMethod
    public void screen(ITestResult result){
        if(result.getStatus()== ITestResult.SUCCESS){
            String screenshotPath= ScreenshotUtil.getScreenshot(page,result.getName());
            test.addScreenCaptureFromPath(screenshotPath);
        }
        else if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        }
        extent.flush();
    }

}
