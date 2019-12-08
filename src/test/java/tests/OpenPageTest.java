package tests;

import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import model.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class OpenPageTest extends TestBase {

    @Before
    public void init() {
        initWebDriver();
    }

    @Step(shortName = "openMainPage", preconditionSteps = {"login", "openCreateAccountPage", "verifyProductStickerIsDisplayed", "openProductDetailsPage", "openCampaignProductDetailsPage",
    "addProductToCart", "openCartPageByCheckout"
    },isStartNode = true)
    @When("I create my first test")
    public void i_create_my_first_test() {
        driver.navigate().to("http://192.168.36.254/litecart");
        wait.until(titleIs("Online Store | My Store"));
    }
}