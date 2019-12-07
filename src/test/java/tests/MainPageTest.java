package tests;

import model.Product;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.MainPage;
import pages.PageBase;
import pages.ProductDetailsPage;

public class MainPageTest extends TestBase {

    @Test
    private void testMainPageProductSticker() {
        System.out.println("--- testMainPageProductSticker ---");

        PageBase mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        SoftAssert softAssert = ((MainPage) mainPage).verifyStickers();
        softAssert.assertAll();
    }

    @Test
    private void testProductDetails() {
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

    @Test
    private void testProductDetailsElements() {
        System.out.println("--- testProductDetailsElements ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        mainPage.verifyProductDetailsElements();

        ProductDetailsPage productDetailsPage = mainPage.openCampaignProductDetailsPage();

        productDetailsPage.verifyProductDetailsElements();
    }

    @Test
    private void testCartAddProduct() {
        System.out.println("--- testCartAddProduct ---");
        MainPage mainPage = new MainPage(driver);
        mainPage.open(BASE_URL);

        for (int i = 0; i < 3; i++) {
            ProductDetailsPage productDetailsPage = mainPage.openFirstProductDetailsPage();
            productDetailsPage.addProductToCart();
            mainPage.open(BASE_URL);
        }

        CartPage cartPage = mainPage.checkout();
        cartPage.cartRemoveAll();
        Assert.assertTrue(cartPage.cartIsEmpty());
    }
}
