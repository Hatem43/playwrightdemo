package pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    protected Page page;
    private final String usernameTextbox="//input[@placeholder='Username']";
    private final String passwordTextbox="//input[@placeholder='Password']";
    private String loginButton="//button[@type='submit']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username,String password) {
        page.navigate("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        page.fill(usernameTextbox, username);
        page.fill(passwordTextbox, password);
        page.click(loginButton);

    }

}
