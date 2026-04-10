package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Checkout pages on SauceDemo.
 *
 * This is the most complex page object because checkout has multiple steps:
 *   Step 1: Enter personal info (first name, last name, zip code)
 *   Step 2: Review order summary (item totals)
 *   Complete: Confirmation message
 *
 * KEY CONCEPT — Explicit Waits:
 *   WebDriverWait + ExpectedConditions = "wait up to X seconds for this
 *   condition to be true, then proceed". This is more reliable than
 *   Thread.sleep() because it returns as soon as the element is ready.
 */
public class CheckoutPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // --- Locators for checkout form fields and buttons ---
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By zipCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By confirmationMessage = By.cssSelector(".complete-header");
    private final By itemTotal = By.cssSelector(".summary_subtotal_label");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        // Wait up to 20 seconds for elements — safe timeout for slow networks
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Fills in the checkout form. Handles null/blank values gracefully
     * because some test scenarios intentionally leave fields empty.
     */
    public void fillCheckoutInfo(String firstName, String lastName, String zipCode) {
        // Wait until the field is visible, click it, clear it, then type
        WebElement firstNameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        firstNameEl.click();
        firstNameEl.clear();
        if (firstName != null && !firstName.isBlank()) {
            firstNameEl.sendKeys(firstName);
            // Verify the value was actually typed (guards against flaky input)
            wait.until(ExpectedConditions.attributeToBe(firstNameEl, "value", firstName));
        }

        WebElement lastNameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        lastNameEl.click();
        lastNameEl.clear();
        if (lastName != null && !lastName.isBlank()) {
            lastNameEl.sendKeys(lastName);
            wait.until(ExpectedConditions.attributeToBe(lastNameEl, "value", lastName));
        }

        WebElement zipEl = wait.until(ExpectedConditions.visibilityOfElementLocated(zipCodeField));
        zipEl.click();
        zipEl.clear();
        if (zipCode != null && !zipCode.isBlank()) {
            zipEl.sendKeys(zipCode);
            wait.until(ExpectedConditions.attributeToBe(zipEl, "value", zipCode));
        }
    }

    /**
     * Clicks the Continue button using JavaScript.
     * JavascriptExecutor.click() is used instead of regular .click() because
     * sometimes elements can be covered by floating headers/footers, and
     * a JS click bypasses that issue.
     */
    public void clickContinue() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickFinish() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        // Wait for the confirmation message to appear before returning
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));
    }

    /** Waits until the URL changes to indicate we're on step two. */
    public void waitForCheckoutStepTwo() {
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void waitForCheckoutError() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
    }

    public void waitForOverviewTotals() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal));
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage)).getText();
    }

    /**
     * Extracts the numeric item total from text like "Item total: $29.99".
     * Strips the label prefix, trims whitespace, and parses to a double.
     */
    public double getItemTotal() {
        String value = wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal))
                .getText()
                .replace("Item total: $", "")
                .trim();
        return Double.parseDouble(value);
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}
