package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class PageBase {
    public EventFiringWebDriver driver;
    public WebDriverWait wait;

    public PageBase(EventFiringWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public void verifyProductDetailsElements(String regularPriceLocator, String discountPriceLocator) {

        String priceAttributes = driver.findElement(By.xpath(regularPriceLocator))
                .getCssValue("text-decoration");
        System.out.println(priceAttributes);

        Assert.assertTrue(priceAttributes.contains("line-through"));

        String colorRP = driver.findElement(By.xpath(regularPriceLocator)).getCssValue("color");
        String[] value = colorRP.replace("rgba(", "").replace(")", "").split(",");

        Assert.assertEquals(
                Integer.parseInt(value[0].trim()),
                Integer.parseInt(value[1].trim()),
                Integer.parseInt(value[2].trim()));

        String discountPriceAttributes = driver.findElement(By.xpath(discountPriceLocator))
                .getCssValue("font-weight");
        System.out.println(discountPriceAttributes);
        Assert.assertTrue(discountPriceAttributes.contains("700"));

        String colorDP = driver.findElement(By.xpath(discountPriceLocator))
                .getCssValue("color");
        value = colorDP.replace("rgba(", "").replace(")", "").split(",");

        Assert.assertEquals(
                0,
                Integer.parseInt(value[1].trim()),
                Integer.parseInt(value[2].trim()));

        double fontSizeRP = Double.parseDouble(driver.findElement(By.xpath(regularPriceLocator))
                .getCssValue("font-size").replace("px", ""));
        System.out.println(fontSizeRP);

        double fontSizeDP = Double.parseDouble(driver.findElement(By.xpath(discountPriceLocator))
                .getCssValue("font-size").replace("px", ""));
        System.out.println(fontSizeDP);

        Assert.assertTrue(fontSizeRP < fontSizeDP);
    }

    public void open(String string) {
        driver.navigate().to(string);
        wait.until(titleIs("Online Store | My Store"));
    }
}
