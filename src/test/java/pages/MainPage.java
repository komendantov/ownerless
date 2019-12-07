package pages;

import model.Product;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;

public class MainPage extends PageBase {

    private static final String CAMPAIGNS_PRODUCT_LOCATOR = "//div[@id='box-campaigns']//div[@class='content']//ul//li//a[@class='link']";
    private static final String MAIN_PAGE_PRODUCT_NAME_LOCATOR = "//div[@id='box-campaigns']//div[@class='content']//ul//li//a[@class='link']//div[@class='name']";
    private static final String MAIN_PAGE_PRICE_LOCATOR = "//div[@id='box-campaigns']//div[@class='content']//ul//li//a[@class='link']//div[@class='price-wrapper']//s[@class='regular-price']";
    private static final String MAIN_PAGE_DISCOUNT_PRICE_LOCATOR = "//div[@id='box-campaigns']//div[@class='content']//ul//li//a[@class='link']//div[@class='price-wrapper']//strong[@class='campaign-price']";
    private static final String ALL_PRODUCTS_LOCATOR = "product"; // for XPath: "//li[contains(@class, 'product')]"
    private static final String STICKER_LOCATOR = "sticker";
    private static final String CHECKOUT_LINK_LOCATOR = "//div[@id='cart']//a[3]";


    public SoftAssertions verifyStickers() {
        SoftAssertions softAssert = new SoftAssertions();
        List<WebElement> productBoxes = driver.findElements(By.className(ALL_PRODUCTS_LOCATOR));
        productBoxes.forEach(e -> {
            List<WebElement> stickers = (e.findElements(By.className(STICKER_LOCATOR)));
            System.out.println("For '" + e.getText() + "' stickers count = " + stickers.size() + "\n");
            softAssert.assertThat(stickers.size()).isEqualTo(1);

        });
        return softAssert;
    }

    public Product getCampaignProductDetails() {
        return new Product(
                driver.findElement(By.xpath(MAIN_PAGE_PRODUCT_NAME_LOCATOR)).getText(),
                driver.findElement(By.xpath(MAIN_PAGE_PRICE_LOCATOR)).getText(),
                driver.findElement(By.xpath(MAIN_PAGE_DISCOUNT_PRICE_LOCATOR)).getText()
        );
    }

    public ProductDetailsPage openCampaignProductDetailsPage(){
        driver.findElement(By.xpath(CAMPAIGNS_PRODUCT_LOCATOR)).click();
        return new ProductDetailsPage(driver);
    }

    public ProductDetailsPage openFirstProductDetailsPage(){
        driver.findElement(By.className(ALL_PRODUCTS_LOCATOR)).click();
        return new ProductDetailsPage(driver);
    }

    public CartPage checkout(){
        driver.findElement(By.xpath(CHECKOUT_LINK_LOCATOR)).click();
        return new CartPage(driver);
    }

    public boolean verifySuccessMessage(){
        return driver.findElement(By.xpath("//div[@class='notice success']")).isDisplayed();
    }

    public void verifyProductDetailsElements() {
        super.verifyProductDetailsElements(MAIN_PAGE_PRICE_LOCATOR, MAIN_PAGE_DISCOUNT_PRICE_LOCATOR);
    }

    public MainPage(EventFiringWebDriver driver) {
        super(driver);
    }
}
