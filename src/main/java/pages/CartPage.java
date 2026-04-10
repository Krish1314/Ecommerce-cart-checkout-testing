package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for the Shopping Cart page on SauceDemo.
 *
 * This page appears after clicking the cart icon and shows all added products.
 * Methods here check cart contents and start the checkout process.
 */
public class CartPage {
    private final WebDriver driver;

    // CSS selector targets all elements with class "inventory_item_name"
    private final By cartItemNames = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Checks if a specific product is in the cart.
     * Uses XPath to find a div with both the right class AND matching text.
     * findElements() returns a list — if the list has items, the product is in the cart.
     */
    public boolean isProductInCart(String productName) {
        return driver.findElements(By.xpath(
                "//div[@class='inventory_item_name' and text()='" + productName + "']"
        )).size() > 0;
    }

    /** Returns true if no product names are found on the page (cart is empty). */
    public boolean isCartEmpty() {
        return driver.findElements(cartItemNames).isEmpty();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}
