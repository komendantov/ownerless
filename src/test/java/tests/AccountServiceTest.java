package tests;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Step;
import model.User;
import org.junit.After;
import org.junit.Assert;
import pages.BoxAccountPageBlock;
import pages.CreateAccountPage;
import pages.MainPage;

public class AccountServiceTest extends TestBase {

    BoxAccountPageBlock boxAccountPageBlock;
    private CreateAccountPage createAccountPage;
    private User user;

    @Before
    public void init() {
        initWebDriver();
    }

    @After
    public void beforeTest() {
        createAccountPage = null;
        user = null;
        boxAccountPageBlock = null;
    }

    @Step(shortName = "login", preconditionSteps = {"logout", "verifyProductStickerIsDisplayed"})
    @When("I sign in$")
    public void i_sign_in() {
        System.out.println("--- testUserLogin ---");
        User user = new User("user123@example.com", "qwe123");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        boxAccountPageBlock.open(BASE_URL);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogin(user.getLogin(), user.getPassword()));
    }

    @Step(shortName = "logout")
    @When("I sign out$")
    public void i_sign_out() {
        System.out.println("--- testUserLogout ---");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }

    @Step(shortName = "openCreateAccountPage", preconditionSteps = {"fillCreateAccountFields"})
    @Given("I open create account page$")
    public void i_create_account() {
        System.out.println("--- testCreateAccount ---");
        createAccountPage = new CreateAccountPage(driver);
        createAccountPage.open(BASE_URL);
    }

    @Step(shortName = "fillCreateAccountFields", preconditionSteps = {"loginWithCreatedAccount"})
    @Then("I fill account fields$")
    public void i_fill_account_fields() {
        user = new User();
        System.out.println(user.getLogin() + " " + user.getPassword());
        createAccountPage = new CreateAccountPage(driver);
        createAccountPage.fillCreateAccountFields(user);
        Assert.assertTrue(new MainPage(driver).verifySuccessMessage());
        boxAccountPageBlock = new BoxAccountPageBlock(driver);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }

    @Step(shortName = "verifyCreateAccountSuccessMessage", preconditionSteps = {"openMainPage"}, testData={"country,accountType"})
    @Then("I verify create account success message")
    public static void i_verify_create_account_success_message(){

    }

    @Step(shortName = "loginWithCreatedAccount", preconditionSteps = {"verifyProductStickerIsDisplayed", "verifyProductDetailsPageElements"})
    @Then("I login with created account$")
    public void i_login_with_created_account() {
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogin(user.getLogin(), user.getPassword()));
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }
}