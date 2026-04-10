package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Cucumber Hooks — setup and teardown that run BEFORE and AFTER every scenario.
 *
 * KEY CONCEPTS:
 *   @Before → runs before each Cucumber scenario (opens a fresh browser)
 *   @After  → runs after each Cucumber scenario (closes the browser)
 *
 * This ensures every test starts with a clean browser and doesn't leak
 * browser windows. WebDriverManager automatically downloads the correct
 * ChromeDriver version for your installed Chrome.
 */
public class Hooks {
    private static WebDriver driver;

    @Before
    public void setUp() {
        // WebDriverManager finds and downloads the right ChromeDriver automatically
        // (no need to manually download chromedriver.exe!)
        WebDriverManager.chromedriver().setup();

        // ChromeOptions configures how Chrome behaves during tests
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");        // open browser full-screen
        options.addArguments("--window-size=1920,1080");  // fallback window size
        options.addArguments("--disable-notifications");  // block popup notifications

        // Run headless (no visible browser) if -Dheadless=true is passed to Maven
        // Useful for CI/CD pipelines where there's no display
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);

        // Implicit wait — Selenium will wait up to 5 seconds for elements
        // before throwing NoSuchElementException
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // closes the browser and ends the WebDriver session
        }
        TestContext.clear(); // clean up any shared test data
    }

    /** Provides the WebDriver instance to Page Objects and Step Definitions. */
    public static WebDriver getDriver() {
        return driver;
    }

    /** Provides a reusable explicit wait with 10-second timeout. */
    public static WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
