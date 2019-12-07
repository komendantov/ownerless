package pages;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.function.Function;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CreateAccountPage extends PageBase {
    private static final String INPUT_FIRSTNAME_LOCATOR = "//input[@name='firstname']";
    private static final String INPUT_LASTNAME_LOCATOR = "//input[@name='lastname']";
    private static final String INPUT_ADDRESS1_LOCATOR = "//input[@name='address1']";
    private static final String INPUT_POSTCODE_LOCATOR = "//input[@name='postcode']";
    private static final String INPUT_CITY_LOCATOR = "//input[@name='city']";
    private static final String INPUT_EMAIL_LOCATOR = "//input[@name='email']";
    private static final String INPUT_PHONE_LOCATOR = "//input[@name='phone']";
    private static final String INPUT_PASSWORD_LOCATOR = "//input[@name='password']";
    private static final String INPUT_CONFIRMED_PASSWORD_LOCATOR = "//input[@name='confirmed_password']";
    private static final String SELECTOR_COUNTRY_LOCATOR = "//select[@name='country_code']";
    private static final String SELECTOR_ZONE_LOCATOR = "//select[@name='zone_code']";
    private static final String SUBMIT_BUTTON_LOCATOR = "//button[@type='submit']";

    public void fillCreateAccountFields(User user) {
        driver.findElement(By.xpath(INPUT_FIRSTNAME_LOCATOR)).sendKeys(user.getFirsname());
        driver.findElement(By.xpath(INPUT_LASTNAME_LOCATOR)).sendKeys(user.getLastname());
        driver.findElement(By.xpath(INPUT_ADDRESS1_LOCATOR)).sendKeys(user.getAddress1());
        driver.findElement(By.xpath(INPUT_POSTCODE_LOCATOR)).sendKeys(user.getPostcode());
        driver.findElement(By.xpath(INPUT_CITY_LOCATOR)).sendKeys(user.getCity());
        driver.findElement(By.xpath(INPUT_EMAIL_LOCATOR)).sendKeys(user.getLogin());
        driver.findElement(By.xpath(INPUT_PHONE_LOCATOR)).clear();
        driver.findElement(By.xpath(INPUT_PHONE_LOCATOR)).sendKeys(user.getPhone());
        driver.findElement(By.xpath(INPUT_PASSWORD_LOCATOR)).sendKeys(user.getPassword());
        driver.findElement(By.xpath(INPUT_CONFIRMED_PASSWORD_LOCATOR)).sendKeys(user.getPassword());

        Select countrySelector = new Select(driver.findElement(By.xpath(SELECTOR_COUNTRY_LOCATOR)));
        countrySelector.selectByVisibleText(user.getCountry());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Select zoneSelector = new Select(driver.findElement(By.xpath(SELECTOR_ZONE_LOCATOR)));
        zoneSelector.selectByVisibleText(user.getZone());
        wait.until((Function<WebDriver, Object>) driver ->
                driver.findElement(By.xpath(SUBMIT_BUTTON_LOCATOR)).isDisplayed());
        driver.findElement(By.xpath(SUBMIT_BUTTON_LOCATOR)).click();
    }

    @Override
    public void open(String string) {
        driver.navigate().to(string + "/create_account");
        wait.until(titleIs("Create Account | My Store"));
    }

    public CreateAccountPage(EventFiringWebDriver driver) {
        super(driver);
    }
}
