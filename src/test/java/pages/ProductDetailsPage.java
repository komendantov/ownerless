package pages;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductDetailsPage extends PageBase {

    private static final String DETAILS_PAGE_PRODUCT_NAME_LOCATOR = "//h1[@class='title']";
    private static final String DETAILS_PAGE_PRICE_LOCATOR = "//s[@class='regular-price']";
    private static final String DETAILS_PAGE_DISCOUNT_PRICE_LOCATOR = "//strong[@class='campaign-price']";
    private static final String PRODUCT_SIZE_SELECTOR_LOCATOR = "[name='options[Size]']";
    private static final String ADD_TO_CART_BUTTON_LOCATOR = "add_cart_product";
    private static final String CART_ITEMS_QUANTITY_LOCATOR = "span.quantity";

    public Product getDetailsPageProduct = new Product(
            driver.findElement(By.xpath(DETAILS_PAGE_PRODUCT_NAME_LOCATOR)).getText(),
            driver.findElement(By.xpath(DETAILS_PAGE_PRICE_LOCATOR)).getText(),
            driver.findElement(By.xpath(DETAILS_PAGE_DISCOUNT_PRICE_LOCATOR)).getText()
    );

    public void verifyProductDetailsElements() {
        super.verifyProductDetailsElements(DETAILS_PAGE_PRICE_LOCATOR, DETAILS_PAGE_DISCOUNT_PRICE_LOCATOR);
    }

    public void addProductToCart() {
        if (driver.findElements(By.cssSelector(PRODUCT_SIZE_SELECTOR_LOCATOR)).size() > 0) {
            new Select(driver.findElement(By.cssSelector(PRODUCT_SIZE_SELECTOR_LOCATOR))).selectByIndex(1);
        }
        int count = Integer.parseInt(driver.findElement(By.cssSelector(CART_ITEMS_QUANTITY_LOCATOR)).getText());
        driver.findElement(By.name(ADD_TO_CART_BUTTON_LOCATOR)).click();

        wait.until((WebDriver d) -> Integer.parseInt(driver
                .findElement(By.cssSelector(CART_ITEMS_QUANTITY_LOCATOR))
                .getText()) == count + 1);
    }

    public ProductDetailsPage(EventFiringWebDriver driver) {
        super(driver);
    }
}
