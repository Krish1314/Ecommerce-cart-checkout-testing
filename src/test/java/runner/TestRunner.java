package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Test Runner — the entry point that tells JUnit to run Cucumber tests.
 *
 * KEY CONCEPTS:
 *   @Suite                  → JUnit 5 annotation to run a test suite
 *   @IncludeEngines         → use the "cucumber" engine (not JUnit's default)
 *   @SelectClasspathResource → look for .feature files in "features" folder
 *                              (under src/test/resources/features/)
 *
 * Configuration parameters:
 *   GLUE = "stepDefinitions,hooks"
 *     → tells Cucumber where to find Step Definitions and Hooks classes
 *
 *   PLUGIN = "pretty, json:..., html:..."
 *     → "pretty"  = colorful console output
 *     → "json:..."  = generates a JSON report (used by the React dashboard)
 *     → "html:..."  = generates an HTML report you can open in a browser
 *
 * To run: mvn test (Maven finds this class and executes it)
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "stepDefinitions,hooks")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, json:target/cucumber-report.json, html:target/cucumber-html-report.html"
)
public class TestRunner {
}
