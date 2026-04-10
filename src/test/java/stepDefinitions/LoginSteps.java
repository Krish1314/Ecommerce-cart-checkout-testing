package stepDefinitions;

import hooks.Hooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.ProductPage;

/**
 * Step Definitions for Login scenarios.
 *
 * KEY CONCEPT — HOW CUCUMBER WORKS:
 *   1. You write test scenarios in plain English (.feature files using Gherkin)
 *   2. Each line (step) maps to a Java method via annotations:
 *        @Given → precondition setup
 *        @When  → action being tested
 *        @Then  → expected result verification
 *   3. The text in quotes matches the Gherkin step text
 *   4. {string} captures quoted values as method parameters
 *
 * Example mapping:
 *   Feature file: When the user enters username "standard_user" and password "secret_sauce"
 *   Maps to:      enterCredentials("standard_user", "secret_sauce")
 */
public class LoginSteps {

    // Helper methods create Page Objects on demand using the shared WebDriver
    private LoginPage loginPage() {
        return new LoginPage(Hooks.getDriver());
    }

    private ProductPage productPage() {
        return new ProductPage(Hooks.getDriver());
    }

    @Given("the user is on the SauceDemo login page")
    public void userOnLoginPage() {
        loginPage().open();
    }

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage().enterUsername(username);
        loginPage().enterPassword(password);
    }

    @When("the user clicks the login button")
    public void clickLogin() {
        loginPage().clickLogin();
    }

    @When("the user clicks the login button without entering credentials")
    public void clickLoginWithoutCredentials() {
        loginPage().clickLogin();
    }

    @Then("the user should be redirected to the products page")
    public void verifyProductsPage() {
        // Assertions.assertTrue checks a condition — test FAILS if it's false
        Assertions.assertTrue(productPage().isOnProductsPage(), "User was not redirected to products page.");
    }
}
