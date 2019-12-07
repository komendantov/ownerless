package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.function.Function;

public class CartPage extends PageBase {

    private static final String EMPTY_CART_MESSAGE_LOCATOR = "//html//p[1]//em";
    private static final String CART_REMOVE_PRODUCT_LOCATOR = "[name=remove_cart_item]";
    private static final String CART_FORM_LOCATOR = "[name =cart_form]";

    public void cartRemoveAll(){
        while (driver.findElements(By.xpath(EMPTY_CART_MESSAGE_LOCATOR)).size() != 1){
            driver.findElement(By.cssSelector(CART_REMOVE_PRODUCT_LOCATOR)).click();
            Integer skuLines = driver.findElements(By.cssSelector(CART_FORM_LOCATOR)).size();
            wait.until((Function<WebDriver, Object>) driver -> driver
                    .findElements(By.cssSelector(CART_FORM_LOCATOR)).size() != skuLines ||
                    !driver.findElement(By.cssSelector(CART_FORM_LOCATOR)).isDisplayed());
        }
    }

    public boolean cartIsEmpty (){
        return driver.findElement(By.xpath(EMPTY_CART_MESSAGE_LOCATOR)).isDisplayed();
    }

    public CartPage(EventFiringWebDriver driver) {
        super(driver);
    }
}
