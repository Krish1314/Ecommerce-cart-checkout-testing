package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Products listing page on SauceDemo.
 *
 * This is the main page after login, showing all available products.
 * Provides methods to add/remove products and interact with the cart.
 *
 * LOCATOR STRATEGIES used here:
 *   By.cssSelector() → targets elements by CSS class or attribute
 *   By.xpath()       → targets elements using XML path expressions
 *                       (more powerful but harder to read than CSS selectors)
 */
public class ProductPage {
    private final WebDriver driver;

    // Badge showing number of items in cart (e.g. the red "2" bubble)
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    /** Checks if we're on the products page by looking at the URL. */
    public boolean isOnProductsPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    /**
     * Adds a specific product to the cart.
     * The XPath finds the product container that contains the matching product name,
     * then clicks the "Add to cart" button within that specific container.
     */
    public void addProductToCart(String productName) {
        String productContainerXpath = "//div[@class='inventory_item' and .//div[text()='" + productName + "']]";
        By addButton = By.xpath(productContainerXpath + "//button[contains(@id,'add-to-cart')]");
        driver.findElement(addButton).click();
    }

    /** Removes a product from the cart using the same container-targeting XPath approach. */
    public void removeProductFromCart(String productName) {
        String productContainerXpath = "//div[@class='inventory_item' and .//div[text()='" + productName + "']]";
        By removeButton = By.xpath(productContainerXpath + "//button[contains(@id,'remove')]");
        driver.findElement(removeButton).click();
    }

    /** Returns the text shown on the cart badge (e.g. "1", "2"). */
    public String getCartBadgeCount() {
        return driver.findElement(cartBadge).getText();
    }

    /** Clicks the cart icon to navigate to the cart page. */
    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    /**
     * Gets the displayed price of a product (e.g. 29.99 from "$29.99").
     * Strips the "$" sign and converts the text to a double.
     */
    public double getProductPrice(String productName) {
        String productContainerXpath = "//div[@class='inventory_item' and .//div[text()='" + productName + "']]";
        By priceLocator = By.xpath(productContainerXpath + "//div[@class='inventory_item_price']");
        String priceText = driver.findElement(priceLocator).getText().replace("$", "");
        return Double.parseDouble(priceText);
    }
}
