package tests;

import io.cucumber.java.Before;
import io.cucumber.java.en.When;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class OpenPageTest extends TestBase {

    @Before
    public void init() {
        initWebDriver();
    }

    @When("I create my first test")
    public void i_create_my_first_test() {
        driver.navigate().to("http://www.google.com");
        wait.until(titleIs("Google"));
    }
}