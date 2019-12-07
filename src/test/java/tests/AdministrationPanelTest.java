package tests;

import model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class AdministrationPanelTest extends TestBase {
    private static final String LOGIN_NAME = "admin";
    private static final String PASSWORD = "admin";

    private static final String COUNTRY_PAGE_URL = TestBase.BASE_URL + "/admin/?app=countries&doc=countries";
    private static final String GEOZONES_URL = TestBase.BASE_URL + "/admin/?app=geo_zones&doc=geo_zones";

    /* Countries Page */
    private static final String LOGOUT_LOCATOR = "//i[@class='fa fa-sign-out fa-lg']";
    private static final String COUNTRY_NAME_LOCATOR = "/html/body/div/div/div/table/tbody/tr/td/form/table/tbody/tr/td[5]/a[1]";
    private static final String COUNTRY_ZONE_LOCATOR = "/html/body/div/div/div/table/tbody/tr/td/form/table/tbody/tr/td[6]";

    /* Edit Country Page */
    private static final String EXTERNAL_LINK_LOCATOR = "//i[@class='fa fa-external-link']/..";

    /* Edit model.Product Page */
    private static final String CATALOG_LOCATOR = "//a[contains(@href, 'app=catalog&doc=catalog')]";
    private static final String ADD_PRODUCT_LOCATOR = "//a[contains(@href, 'doc=edit_product')]";
    private static final String SET_ACTIVE_LOCATOR = "//label[1]/input[1]";
    private static final String NAME_INPUT_LOCATOR = "//input[@type='text'][contains(@name, 'name')]";
    private static final String CATALOG_ROOT_LOCATOR = "//input[contains(@data-name, 'Root')]";
    private static final String CATALOG_CUSTOM_LOCATOR = "//input[contains(@data-name, 'Rubber Duck')]";
    private static final String CATALOG_CUSTOM_LINK_LOCATOR = "//a[text()[contains(.,'Rubber Duck')]]";
    private static final String PRODUCT_LINK_LOCATOR = "//html//tr/td[3]/a[contains(@href, 'product_id=')]";
    private static final String IMAGE_FILE_LOCATOR = "//input[@type='file']";
    private static final String PURCHASE_PRICE_LOCATOR = "//input[@name='purchase_price']";
    private static final String PRICE_USD_LOCATOR = "//input[@name='prices[USD]']";
    private static final String SAVE_BUTTON_LOCATOR = "//html//p//button[@name='save']";
    private static final String INFORMATION_TAB_LOCATOR = "//a[@href='#tab-information']";
    private static final String PRICES_TAB_LOCATOR = "//a[@href='#tab-prices']";

    private static final String MANUFACTURER_SELECTOR_LOCATOR = "//select[@name='manufacturer_id']";
    private static final String CURRENCY_SELECTOR_LOCATOR = "//select[@name='purchase_price_currency_code']";

    @Test
    private void testAdminPageLogin() {
        System.out.println("--- testAdminPageLogin ---");
        driver.navigate().to(TestBase.BASE_URL + "/admin/");
        wait.until(titleIs("My Store"));
        driver.findElement(By.name("username")).sendKeys(LOGIN_NAME);
        driver.findElement(By.name("password")).sendKeys(PASSWORD);
        driver.findElement(By.name("login")).click();

        Assert.assertTrue(driver.findElement(By.xpath(LOGOUT_LOCATOR)).isDisplayed());
    }

    @Test
    private void testAdminPageLogout() {
        System.out.println("--- testAdminPageLogout ---");
        driver.findElement(By.xpath(LOGOUT_LOCATOR)).click();
        Assert.assertTrue(driver.findElement(By.name("login")).isDisplayed());
    }

    @Test
    private void testAdminPageBoxAppMenu() {
        testAdminPageLogin();
        System.out.println("--- testAdminPageBoxAppMenu ---");
        List<WebElement> liAppMenus = (driver.findElements(By.id("app-")));
        int liCount = liAppMenus.size();

        for (int i = 0; i < liCount; i++) {
            liAppMenus = (driver.findElements(By.id("app-")));
            liAppMenus.get(i).click();

            List<WebElement> liAppMenus2lvl = driver.findElements(By.xpath("//li[contains(@id, 'doc-')]"));
            int liCount2lvl = liAppMenus2lvl.size();

            for (int j = 0; j < liCount2lvl; j++) {
                liAppMenus2lvl = driver.findElements(By.xpath("//li[contains(@id, 'doc-')]"));
                liAppMenus2lvl.get(j).click();
                System.out.println(driver.findElement(By.xpath("//h1")).getText());
                Assert.assertTrue(driver.findElement(By.xpath("//h1")).isDisplayed());
            }
        }

        testAdminPageLogout();
    }

    @Test
    private void testCountriesSorting() {

        testAdminPageLogin();
        System.out.println("--- testCountriesSorting ---");
        driver.navigate().to(COUNTRY_PAGE_URL);

        verifyCountryNamesSorting(COUNTRY_NAME_LOCATOR);

        testAdminPageLogout();
    }

    @Test
    private void testCountryZonesSorting() {
        testAdminPageLogin();
        System.out.println("--- testCountriesSorting ---");
        driver.navigate().to(COUNTRY_PAGE_URL);
        String zonesCountryNameLocator = "//html//table[@id='table-zones']//tr/td[3]";

        List<WebElement> countries = driver.findElements(By.xpath(COUNTRY_NAME_LOCATOR));
        List<WebElement> zones = driver.findElements(By.xpath(COUNTRY_ZONE_LOCATOR));

        int countryCounter = zones.size();
        for (int i = 0; i < countryCounter; i++) {
            if (!zones.get(i).getText().equals("0")) {
                System.out.println("Verify zones for: " + countries.get(i).getText());
                countries.get(i).click();

                verifyCountryNamesSorting(zonesCountryNameLocator);

                driver.navigate().to(COUNTRY_PAGE_URL);
                countries = driver.findElements(By.xpath(COUNTRY_NAME_LOCATOR));
                zones = driver.findElements(By.xpath(COUNTRY_ZONE_LOCATOR));
            }
        }
        testAdminPageLogout();
    }

    @Test
    private void testGeoZonesSorting() {
        testAdminPageLogin();
        System.out.println("--- testGeoZonesSorting ---");
        driver.navigate().to(GEOZONES_URL);

        String countryLocator = "//tr[@class='row']//td[3]//a";
        String countryZoneLocator = "//select[contains(@name, '[zone_code]')]";

        List<WebElement> countries = driver.findElements(By.xpath(countryLocator));

        int countryCounter = countries.size();
        for (int i = 0; i < countryCounter; i++) {
            System.out.println("Verify zones for: " + countries.get(i).getText());
            countries.get(i).click();

            verifyCountryNamesSorting(countryZoneLocator);

            driver.navigate().to(GEOZONES_URL);
            countries = driver.findElements(By.xpath(countryLocator));
        }
        testAdminPageLogout();
    }

    @Test
    private void testEditCountryPageExternalLinks() {
        testAdminPageLogin();
        System.out.println("--- testEditCountryPageExternalLinks ---");
        driver.navigate().to(COUNTRY_PAGE_URL);
        driver.findElement(By.xpath(COUNTRY_NAME_LOCATOR)).click();
        List<WebElement> externalLinks = driver.findElements(By.xpath(EXTERNAL_LINK_LOCATOR));
        int linksCounter = externalLinks.size();

        String mainWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        for (int i = 0; i < linksCounter; i++) {
            String externalLinkValue = externalLinks.get(i).getAttribute("href");
            System.out.println("Link to open: " + externalLinkValue);
            externalLinks.get(i).click();

            String newWindow = wait.until(thereIsWindowOtherThan(oldWindows));
            driver.switchTo().window(newWindow);

            System.out.println("Link opened: " + driver.getCurrentUrl() + "\n");

            driver.close();
            driver.switchTo().window(mainWindow);

            externalLinks = driver.findElements(By.xpath(EXTERNAL_LINK_LOCATOR));
        }
        testAdminPageLogout();
    }

    @Test
    private void testAddNewProduct() {
        testAdminPageLogin();
        System.out.println("--- testAddNewProduct ---");

        Product duck = new Product(
                "Custom Duck " + System.currentTimeMillis(),
                "10",
                "10");
        duck.setImagePath(System.getProperty("user.dir") + "/pic/rubber-ducks-country.jpg");
        duck.setManufacturer("ACME Corp.");

        System.out.println(duck);

        driver.findElement(By.xpath(CATALOG_LOCATOR)).click();
        driver.findElement(By.xpath(ADD_PRODUCT_LOCATOR)).click();

        driver.findElement(By.xpath(SET_ACTIVE_LOCATOR)).click();
        driver.findElement(By.xpath(NAME_INPUT_LOCATOR)).sendKeys(duck.getName());
        driver.findElement(By.xpath(CATALOG_ROOT_LOCATOR)).click();
        driver.findElement(By.xpath(CATALOG_CUSTOM_LOCATOR)).click();
        driver.findElement(By.xpath(IMAGE_FILE_LOCATOR)).sendKeys(duck.getImagePath());

        driver.findElement(By.xpath(INFORMATION_TAB_LOCATOR)).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Select manufacturerSelector = new Select(driver.findElement(By.xpath(MANUFACTURER_SELECTOR_LOCATOR)));
        manufacturerSelector.selectByVisibleText(duck.getManufacturer());


        driver.findElement(By.xpath(PRICES_TAB_LOCATOR)).click();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Select currencySelector = new Select(driver.findElement(By.xpath(CURRENCY_SELECTOR_LOCATOR)));
        currencySelector.selectByVisibleText("US Dollars");
        driver.findElement(By.xpath(PURCHASE_PRICE_LOCATOR)).clear();
        driver.findElement(By.xpath(PURCHASE_PRICE_LOCATOR)).sendKeys(duck.getPriceWithDiscount());
        driver.findElement(By.xpath(PRICE_USD_LOCATOR)).sendKeys(duck.getPrice());

        driver.findElement(By.xpath(SAVE_BUTTON_LOCATOR)).click();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath(CATALOG_LOCATOR)).click();
        driver.findElement(By.xpath(CATALOG_CUSTOM_LINK_LOCATOR)).click();

        Assert.assertTrue(driver.findElement(By.xpath("//a[text()[contains(.,'" + duck.getName() + "')]]")).isDisplayed());

        testAdminPageLogout();
    }

    @Test
    private void testAdminPanelConsoleLog() {
        SoftAssert softAssert = new SoftAssert();
        testAdminPageLogin();
        System.out.println("--- testAdminPanelConsoleLog ---");

        driver.findElement(By.xpath(CATALOG_LOCATOR)).click();
        driver.findElement(By.xpath(CATALOG_CUSTOM_LINK_LOCATOR)).click();

        List<WebElement> productLinks = driver.findElements(By.xpath(PRODUCT_LINK_LOCATOR));
        int linksCounter = productLinks.size();
        for (int i = 0; i < linksCounter; i++) {

            productLinks.get(i).click();

            for (LogEntry l : driver.manage().logs().get("browser").getAll()) {
                System.out.println(l);
                softAssert.assertNull(l);
            }

            driver.navigate().back();
            productLinks = driver.findElements(By.xpath(PRODUCT_LINK_LOCATOR));
        }
        softAssert.assertAll();
        testAdminPageLogout();
    }

    private void verifyCountryNamesSorting(String locator) {
        List<String> countryNames = new ArrayList<>();
        driver.findElements(By.xpath(locator)).forEach(e -> countryNames.add(e.getText()));

        List<String> toBeSortedCountryNames = new ArrayList<>( countryNames );
        java.util.Collections.sort(toBeSortedCountryNames);

        System.out.println("Actual: " + countryNames);
        System.out.println("Expected: " + toBeSortedCountryNames);

        Assert.assertEquals(countryNames, toBeSortedCountryNames);
    }

    private ExpectedCondition<String> thereIsWindowOtherThan(Set<String> oldWindows) {
        return input -> {
            Set<String> handles = input.getWindowHandles();
            handles.removeAll(oldWindows);
            return handles.size() > 0 ? handles.iterator().next() : null;
        };
    }
}
