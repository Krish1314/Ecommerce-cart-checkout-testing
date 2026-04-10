package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the SauceDemo Login page.
 *
 * PAGE OBJECT MODEL (POM) — a design pattern where each web page gets its own
 * Java class. The class holds:
 *   1. Locators (how to find elements on the page)
 *   2. Actions  (methods that interact with those elements)
 *
 * Benefits:
 *   - If the page UI changes, you only update THIS class (not every test)
 *   - Test code reads like plain English: loginPage.enterUsername("standard_user")
 *   - Locators are defined once and reused across methods
 */
public class LoginPage {
    private final WebDriver driver;

    // --- Locators ---
    // By.id() finds elements by their HTML id attribute (fastest and most reliable)
    // By.cssSelector() uses CSS selectors for more complex lookups
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /** Navigate the browser to the SauceDemo login page. */
    public void open() {
        driver.get("https://www.saucedemo.com");
    }

    /** Clear the field first (in case it has old text), then type the username. */
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    /** Returns the text of the error message shown after a failed login. */
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
