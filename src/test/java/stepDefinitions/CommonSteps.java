package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Common Step Definitions — shared steps used across multiple feature files.
 *
 * KEY CONCEPT — Step Reuse:
 *   When multiple feature files share the same step text (e.g. error message
 *   verification), you define it ONCE in a shared class. Cucumber matches
 *   by step text, not by class, so this step works in login.feature,
 *   cart.feature, and checkout.feature.
 */
public class CommonSteps {

    /**
     * Verifies that an error message is shown on the page.
     * Uses case-insensitive matching with contains() so tests aren't
     * brittle about exact capitalization.
     */
    @Then("an error message {string} should be displayed")
    public void verifyErrorMessage(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(20));
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")))
                .getText();
        Assertions.assertTrue(
                actual.toLowerCase().contains(expectedMessage.toLowerCase()),
                "Expected error to contain: " + expectedMessage + " but was: " + actual
        );
    }
}
