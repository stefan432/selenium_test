import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    public static final String TRANSFER_FUNDS_TAB_NAME = "Transfer Funds";
    private WebDriver driver;

    @BeforeTest
    public void beforeTest() {

        ChromeDriverManager.getInstance().version("2.40").setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://zero.webappsecurity.com/");
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement btnSignIn = driver.findElement(By.id("signin_button"));
        actions.moveToElement(btnSignIn).perform();
        btnSignIn.click();
        WebElement inputUsername = driver.findElement(By.id("user_login"));
        inputUsername.sendKeys("username");
        WebElement inputPassword = driver.findElement(By.id("user_password"));
        inputPassword.sendKeys("password");
        WebElement btnSubmit = driver.findElement(By.className("btn-primary"));
        btnSubmit.click();
        WebElement linkTransferFunds = driver.findElement(By.linkText(TRANSFER_FUNDS_TAB_NAME));
        linkTransferFunds.click();
        WebElement ddlFromAccount = driver.findElement(By.id("tf_fromAccountId"));
        ddlFromAccount.click();
        List<WebElement> ddlFromAccountsOptions = ddlFromAccount.findElements(By.tagName("option"));

        for (WebElement ddlFromAccountsOption : ddlFromAccountsOptions) {
            String optionText = ddlFromAccountsOption.getText();
            if (optionText.contains("Checking")) {
                ddlFromAccountsOption.click();
                break;
            }
        }
        ddlFromAccount.click();


        WebElement ddlToAccount = driver.findElement(By.id("tf_toAccountId"));
        ddlToAccount.click();
        List<WebElement> ddlToAccountsOptions = ddlToAccount.findElements(By.tagName("option"));
        for (WebElement ddlToAccountsOption : ddlToAccountsOptions) {
            String optionText = ddlToAccountsOption.getText();
            if (optionText.contains("Credit")) {
                ddlToAccountsOption.click();
                break;
            }
        }
        ddlToAccount.click();


        WebElement inputAmount = driver.findElement(By.cssSelector("input[name='amount']"));
        inputAmount.sendKeys("300");
        WebElement inputDescription = driver.findElement(By.id("tf_description"));
        inputDescription.sendKeys("Pay check for the UI Automation Course");
        driver.findElement(By.id("btn_submit")).click();
    }

    @Test(priority = 1)
    public void checkStateOneFromAccount() {
        WebElement inputFromAccount = driver.findElement(By.id("tf_fromAccountId"));
        String inputText = inputFromAccount.getAttribute("value");
        Assert.assertEquals(inputText, "Checking", "The input is not correct");
    }

    @Test(priority = 1)
    public void checkStateOneToAccount() {
        WebElement inputToAccount = driver.findElement(By.id("tf_toAccountId"));
        String inputText = inputToAccount.getAttribute("value");
        Assert.assertEquals(inputText, "Credit Card", "The input is not correct");
    }

    @Test(priority = 1)
    public void checkStateOneAmount() {
        WebElement inputAmount = driver.findElement(By.id("tf_amount"));
        String inputText = inputAmount.getAttribute("value");
        Assert.assertEquals(inputText, "300", "The input is not correct");
    }

    @Test(priority = 1)
    public void checkStateOneDescription() {
        WebElement inputDescription = driver.findElement(By.id("tf_description"));
        String inputText = inputDescription.getAttribute("value");
        Assert.assertEquals(inputText, "Pay check for the UI Automation Course", "The input is not correct");
    }

    @Test(priority = 2)
    public void checkStateTwoSuccessMessage() {
        driver.findElement(By.id("btn_submit")).click();
        WebElement successMessageDiv = driver.findElement(By.cssSelector(".alert.alert-success"));
        String actualText = successMessageDiv.getText();
        Assert.assertTrue(actualText.contains("successfully"), "There transaction wasn't successful");
    }


    @AfterTest
    public void afterTest() {
        driver.close();
        driver.quit();
    }
}
