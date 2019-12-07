package tests;

import model.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BoxAccountPageBlock;
import pages.CreateAccountPage;
import pages.MainPage;

public class AccountServiceTest extends TestBase {

    @Test
    private void testUserLogin() {
        System.out.println("--- testUserLogin ---");
        User user = new User("user123@example.com", "qwe123");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        boxAccountPageBlock.open(BASE_URL);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogin(user.getLogin(), user.getPassword()));
    }

    @Test
    private void testUserLogout() {
        System.out.println("--- testUserLogout ---");
        BoxAccountPageBlock boxAccountPageBlock = new BoxAccountPageBlock(driver);
        Assert.assertTrue(boxAccountPageBlock.verifyUserLogout());
    }


    @Test
    private void testCreateAccount() {
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
