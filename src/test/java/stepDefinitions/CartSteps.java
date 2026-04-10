package stepDefinitions;

import hooks.Hooks;
import hooks.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

/**
 * Step Definitions for Shopping Cart scenarios.
 *
 * These steps handle adding/removing products and verifying cart state.
 * Notice how steps can be reused across multiple scenarios — the @Given
 * "the user is logged in as {string}" step is used by both cart and
 * checkout feature files.
 */
public class CartSteps {

    private LoginPage loginPage() {
        return new LoginPage(Hooks.getDriver());
    }

    private ProductPage productPage() {
        return new ProductPage(Hooks.getDriver());
    }

    private CartPage cartPage() {
        return new CartPage(Hooks.getDriver());
    }

    /**
     * Composite step — performs a full login sequence.
     * This is a common pattern: a @Given step that sets up a precondition
     * by performing multiple actions (open page → enter credentials → click login).
     */
    @Given("the user is logged in as {string}")
    public void userIsLoggedInAs(String username) {
        loginPage().open();
        loginPage().enterUsername(username);
        loginPage().enterPassword("secret_sauce"); // SauceDemo's standard password
        loginPage().clickLogin();
        Assertions.assertTrue(productPage().isOnProductsPage(), "Login failed for user: " + username);
    }

    @When("the user adds {string} to the cart")
    public void userAddsProductToCart(String productName) {
        productPage().addProductToCart(productName);
    }

    @Then("the cart badge should display {string}")
    public void cartBadgeShouldDisplay(String expectedCount) {
        Assertions.assertEquals(expectedCount, productPage().getCartBadgeCount());
    }

    /**
     * Another composite step — logs in, captures the product price for later
     * verification, adds the product to cart, opens the cart, and verifies
     * the product is there. Uses TestContext to share price data with
     * CheckoutSteps for total validation.
     */
    @Given("the user has {string} in the cart")
    public void userHasProductInTheCart(String productName) {
        userIsLoggedInAs("standard_user");
        // Save the product price so CheckoutSteps can verify the order total
        TestContext.setExpectedItemTotal(productPage().getProductPrice(productName));
        productPage().addProductToCart(productName);
        productPage().openCart();
        Assertions.assertTrue(cartPage().isProductInCart(productName), "Expected product was not found in cart.");
    }

    @When("the user removes {string} from the cart")
    public void userRemovesProductFromTheCart(String productName) {
        Hooks.getDriver().navigate().back(); // go back to products page
        productPage().removeProductFromCart(productName);
        productPage().openCart(); // return to cart to verify it's empty
    }

    @Then("the cart should be empty")
    public void cartShouldBeEmpty() {
        Assertions.assertTrue(cartPage().isCartEmpty(), "Cart is not empty.");
    }
}
