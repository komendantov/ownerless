package tests;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import model.User;
import org.junit.Assert;
import pages.BoxAccountPageBlock;
import pages.CreateAccountPage;
import pages.MainPage;

public class AccountServiceTest extends TestBase {

    @Before
    public void init() {
        initWebDriver();
    }

    @When("I sign in$")
    public void i_sign_in() {
        System.out.println("--- testUserLogin ---");
        User user = new User("user123@example.com", "qwe123");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        boxAccountPageBlock.open(BASE_URL);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogin(user.getLogin(), user.getPassword()));
    }

    @When("I sign out$")
    public void i_sign_out() {
        System.out.println("--- testUserLogout ---");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }

    @Given("I create account$")
    public void i_create_account() {
        System.out.println("--- testCreateAccount ---");
        User user = new User();
        System.out.println(user.getLogin() + " " + user.getPassword());
        CreateAccountPage createAccountPage = new CreateAccountPage(driver);
        createAccountPage.open(BASE_URL);

        createAccountPage.fillCreateAccountFields(user);

        Assert.assertTrue(new MainPage(driver).verifySuccessMessage());

        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogin(user.getLogin(), user.getPassword()));
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }
}
