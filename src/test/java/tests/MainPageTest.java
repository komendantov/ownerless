package tests;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Product;
import model.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import pages.CartPage;
import pages.MainPage;
import pages.PageBase;
import pages.ProductDetailsPage;

public class MainPageTest extends TestBase {

    @Before
    public void init() {
        initWebDriver();
    }

    @Step(shortName = "verifyProductStickerIsDisplayed")
    @Given("I verify page product sticker$")
    public void i_verify_page_product_sticker() {
        System.out.println("--- testMainPageProductSticker ---");

        PageBase mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        SoftAssertions softAssert = ((MainPage) mainPage).verifyStickers();
        softAssert.assertAll();
    }

    @Step(shortName = "verifyProductDetails", preconditionSteps = {"addProductToCart"})
    @Then("I verify product details$")
    public void i_verify_product_details() {
        System.out.println("--- testProductDetails ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        Product mainPageDuck = mainPage.getCampaignProductDetails();

        System.out.println("mainPage: \n" + mainPageDuck);

        ProductDetailsPage productDetailsPage = mainPage.openCampaignProductDetailsPage();
        Product detailsPageDuck = productDetailsPage.getDetailsPageProduct;
        System.out.println("productDetailsPage: \n" + detailsPageDuck);

        Assert.assertEquals(mainPageDuck, detailsPageDuck);
    }

    @Step(shortName = "verifyProductDetailsPageElements")
    @Then("I verify product details elements")
    public void i_verify_product_details_elements() {
        System.out.println("--- testProductDetailsElements ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        mainPage.verifyProductDetailsElements();

        ProductDetailsPage productDetailsPage = mainPage.openCampaignProductDetailsPage();

        productDetailsPage.verifyProductDetailsElements();
    }

    @Step(shortName = "addProductToCart")
    @When("I add product to cart$")
    public void testCartAddProduct() {
        System.out.println("--- testCartAddProduct ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);
        for (int i = 0; i < 3; i++) {
            ProductDetailsPage productDetailsPage = mainPage.openFirstProductDetailsPage();
            productDetailsPage.addProductToCart();
            mainPage.open(BASE_URL);
        }
    }

    @Step(shortName = "removeAllFromCart", preconditionSteps = {"addProductToCart"})
    @Then("I remove all from cart")
    public void i_remove_all_from_cart() {
        MainPage mainPage = new MainPage(driver);
       // mainPage.open(BASE_URL);
        CartPage cartPage = mainPage.checkout();
        cartPage.cartRemoveAll();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }
}
