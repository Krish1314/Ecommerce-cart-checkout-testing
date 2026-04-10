package hooks;

/**
 * TestContext — shares data between different Step Definition classes.
 *
 * PROBLEM: Cucumber creates separate instances of each Step Definition class,
 *          so you can't share data between CartSteps and CheckoutSteps using
 *          regular instance variables.
 *
 * SOLUTION: ThreadLocal static variables act as "global" storage that is
 *           safe for parallel test execution. Each thread (test) gets its
 *           own copy of the data.
 *
 * Example flow:
 *   1. CartSteps saves the expected item total: TestContext.setExpectedItemTotal(29.99)
 *   2. CheckoutSteps reads it to verify:       TestContext.getExpectedItemTotal() → 29.99
 */
public class TestContext {
    private static final ThreadLocal<Double> expectedItemTotal = new ThreadLocal<>();

    public static void setExpectedItemTotal(double value) {
        expectedItemTotal.set(value);
    }

    public static double getExpectedItemTotal() {
        Double v = expectedItemTotal.get();
        return v == null ? 0.0 : v; // return 0.0 if nothing was set
    }

    /** Remove stored values — called in Hooks.tearDown() after each scenario. */
    public static void clear() {
        expectedItemTotal.remove();
    }
}
