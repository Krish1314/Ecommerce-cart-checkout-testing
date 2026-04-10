package stepDefinitions;

import hooks.Hooks;
import hooks.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.CartPage;
import pages.CheckoutPage;

/**
 * Step Definitions for Checkout scenarios.
 *
 * These steps cover the multi-step checkout process:
 *   1. Proceed to checkout from cart
 *   2. Enter personal details
 *   3. Continue to order summary
 *   4. Finish and verify confirmation
 *
 * Also handles negative scenarios (missing required fields).
 */
public class CheckoutSteps {

    private CartPage cartPage() {
        return new CartPage(Hooks.getDriver());
    }

    private CheckoutPage checkoutPage() {
        return new CheckoutPage(Hooks.getDriver());
    }

    @When("the user proceeds to checkout")
    public void userProceedsToCheckout() {
        cartPage().clickCheckout();
    }

    @When("the user enters first name {string}, last name {string}, zip {string}")
    public void userEntersCheckoutDetails(String firstName, String lastName, String zip) {
        checkoutPage().fillCheckoutInfo(firstName, lastName, zip);
    }

    /** Happy path — fills info, clicks continue, waits for step two, then finishes. */
    @When("the user clicks Continue and then Finish")
    public void userClicksContinueAndThenFinish() {
        checkoutPage().clickContinue();
        checkoutPage().waitForCheckoutStepTwo();
        checkoutPage().clickFinish();
    }

    @Then("the order confirmation message {string} should be displayed")
    public void orderConfirmationMessageShouldBeDisplayed(String expectedMessage) {
        String actual = checkoutPage().getConfirmationMessage();
        // assertTrue with contains() — checks that the actual message includes the expected text
        Assertions.assertTrue(actual.contains(expectedMessage), "Expected confirmation message not shown.");
    }

    /** Negative test — intentionally leave first name empty. */
    @When("the user leaves first name empty, enters last name {string}, zip {string}")
    public void userLeavesFirstNameEmpty(String lastName, String zip) {
        checkoutPage().fillCheckoutInfo("", lastName, zip);
    }

    @When("the user clicks Continue")
    public void userClicksContinue() {
        checkoutPage().clickContinue();
    }

    /** Fills valid details and advances to the overview page (used for total verification). */
    @When("the user proceeds to checkout and fills in valid details")
    public void userProceedsToCheckoutAndFillsValidDetails() {
        cartPage().clickCheckout();
        checkoutPage().fillCheckoutInfo("Krish", "Patel", "411001");
        checkoutPage().clickContinue();
        checkoutPage().waitForCheckoutStepTwo();
    }

    /**
     * Verifies the order total matches the product price saved earlier.
     * Uses assertEquals with a delta of 0.01 for safe double comparison
     * (floating-point numbers can have tiny rounding differences).
     */
    @Then("the checkout overview should display the correct item total")
    public void checkoutOverviewShouldDisplayCorrectItemTotal() {
        double expectedItemTotal = TestContext.getExpectedItemTotal();
        double actualItemTotal = checkoutPage().getItemTotal();
        Assertions.assertEquals(expectedItemTotal, actualItemTotal, 0.01, "Item total mismatch.");
    }
}
