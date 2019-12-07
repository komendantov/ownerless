package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.function.Function;

public class BoxAccountPageBlock extends PageBase {
    private static final String EMAIL_INPUT_LOCATOR = "//input[@name='email']";
    private static final String PASSWORD_INPUT_LOCATOR = "//input[@type='password']";
    private static final String LOGIN_BUTTON_LOCATOR = "//button[@name='login']";
    private static final String LOGOUT_USER_LOCATOR = "//a[contains(@href, '/logout')]";

    public boolean verifyUserLogin(String login, String password){
        driver.findElement(By.xpath(EMAIL_INPUT_LOCATOR)).clear();
        driver.findElement(By.xpath(EMAIL_INPUT_LOCATOR)).sendKeys(login);
        driver.findElement(By.xpath(PASSWORD_INPUT_LOCATOR)).clear();
        driver.findElement(By.xpath(PASSWORD_INPUT_LOCATOR)).sendKeys(password);
        driver.findElement(By.xpath(LOGIN_BUTTON_LOCATOR)).click();
        return driver.findElement(By.xpath(LOGOUT_USER_LOCATOR)).isDisplayed();
    }

    public boolean verifyUserLogout(){
        wait.until((Function<WebDriver, Object>) driver ->
                driver.findElement(By.xpath(LOGOUT_USER_LOCATOR)).isDisplayed());
        driver.findElement(By.xpath(LOGOUT_USER_LOCATOR)).click();
        wait.until((Function<WebDriver, Object>) driver ->
                driver.findElement(By.xpath(LOGIN_BUTTON_LOCATOR)).isDisplayed());
        return driver.findElement(By.xpath(LOGIN_BUTTON_LOCATOR)).isDisplayed();
    }

    public BoxAccountPageBlock(EventFiringWebDriver driver) {
        super(driver);
    }
}
