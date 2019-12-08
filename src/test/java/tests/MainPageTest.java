package tests;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
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

    @Step(shortName = "verifyProductStickerIsDisplayed", testData = {"country", "accountType"})
    @Given("I verify page product sticker$")
    public void i_verify_page_product_sticker() {
        System.out.println("--- testMainPageProductSticker ---");

        PageBase mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        SoftAssertions softAssert = ((MainPage) mainPage).verifyStickers();
        softAssert.assertAll();
    }

    @Step(shortName = "verifyProductDetails", preconditionSteps = {"addProductToCart"}, testData = {"address1", "address2", "address3"})
    @Given("I verify product details$")
    public void i_verify_product_details(DataTable dataTable) {
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

    @Step(shortName = "openProductDetailsPage", preconditionSteps = {"verifyProductDetails", "verifyProductDetailsPageElements", "addProductToCart"}, testData = {"address1", "address2", "address3"})
    @Given("I open product details page$")
    public void i_open_product_details_page(DataTable dataTable) {
    }

    @Step(shortName = "openCampaignProductDetailsPage", preconditionSteps = {"verifyProductDetails", "verifyProductDetailsPageElements", "addProductToCart"}, testData = {"address1", "address2", "address3"})
    @Given("I open campaign product details page$")
    public void i_open_campaign_product_details_page(DataTable dataTable) {

    }

    @Step(shortName = "verifyProductDetailsPageElements")
    @Given("I verify product details elements")
    public void i_verify_product_details_elements() {
        System.out.println("--- testProductDetailsElements ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        mainPage.verifyProductDetailsElements();

        ProductDetailsPage productDetailsPage = mainPage.openCampaignProductDetailsPage();

        productDetailsPage.verifyProductDetailsElements();
    }

    @Step(shortName = "addProductToCart", preconditionSteps = {"openCartPageByCheckout"}, testData = {"address1", "address2", "address3"})
    @Given("I add product to cart$")
    public void testCartAddProduct(DataTable dataTable) {
        System.out.println("--- testCartAddProduct ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);
        for (int i = 0; i < 3; i++) {
            ProductDetailsPage productDetailsPage = mainPage.openFirstProductDetailsPage();
            productDetailsPage.addProductToCart();
            mainPage.open(BASE_URL);
        }
    }

    @Step(shortName = "openCartPageByCheckout", preconditionSteps = {"removeAllFromCart"}, testData = {"country", "accountType"})
    @Given("I open cart page by checkout$")
    public void i_open_cart_page_by_checkout(DataTable dataTable) {
    }


    @Step(shortName = "removeAllFromCart", preconditionSteps = {"addProductToCart"}, testData = {"country", "accountType"})
    @Given("I remove all from cart")
    public void i_remove_all_from_cart(DataTable dataTable) {
        MainPage mainPage = new MainPage(driver);
        CartPage cartPage = mainPage.checkout();
        cartPage.cartRemoveAll();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }

    @Step(shortName = "verifyCartIsEmpty", testData = {"country", "accountType"})
    @Given("I verify cart is empty$")
    public void i_verify_cart_is_empty(DataTable dataTable) {
        MainPage mainPage = new MainPage(driver);
        CartPage cartPage = mainPage.checkout();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }
}
