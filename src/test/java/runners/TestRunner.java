package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(junit = "--step-notifications", features = "src/test/resources/features/", glue = "", tags = {"@loginAndLogout"})
public class TestRunner {
}