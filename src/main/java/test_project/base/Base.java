package test_project.base;

import test_project.pages.LoanApplication;
import org.testng.Assert;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class Base {

    public WebDriver driver;
    public WebDriverWait wait10Sec;
    public LoanApplication loanApplicationPage;

    public Base(WebDriver driver, WebDriverWait wait10Sec) {
        this.driver = driver;
        this.wait10Sec = wait10Sec;
    }

    public Base() {
    }

    @BeforeEach
    public void setup(){
        this.driver = WebDriverManager.initializeWebDriver();
        this.wait10Sec = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.loanApplicationPage = new LoanApplication(driver, wait10Sec);

    }

    @AfterEach
    public void cleanup(){
        driver.quit();
        driver = null;
    }

    @Step ("Label verification for text {1}")
    public void labelVerification(By locator, String textExpected) {
        try {
            wait10Sec.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        } catch (Exception e) {
            screenshot();
            e.printStackTrace();
            Assert.fail("TEST FAILED: Expected element not found " + locator + " " + textExpected);
        }
        String actual = driver.findElement(locator).getText().trim();
        Assert.assertEquals(actual, textExpected, "TEST FAILED: Expected text does not match actual text for element " + locator);
    }

    @Step("Wait for button {0} to be clickable and click")
    public void waitForButtonClickableAndClick(By button) {
        try {
            wait10Sec.until(ExpectedConditions.elementToBeClickable(button));
            driver.findElement(button).click();
        } catch (Exception e) {
            screenshot();
            e.printStackTrace();
            Assert.fail("TEST FAILED: Expected element not found or not clickable " + button);
        }
    }

    @Step("Wait for input field {1} to be visible and fill in")
    public void waitForInputFieldAndFillText(By locator, String value) {
        try {
            wait10Sec.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            driver.findElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE); // Clear the field before entering text
            driver.findElement(locator).sendKeys(value); // Fill in the input field
            wait10Sec.until(ExpectedConditions.attributeToBe(locator, "value", value));
            driver.findElement(locator).sendKeys(Keys.TAB); // Fill in the input field and move focus away// Wait for 5 seconds to see the input value
        } catch (Exception e) {
            screenshot();
            e.printStackTrace();
            Assert.fail("TEST FAILED: Field not found " + locator);
        }
    }

    @Step("Wait for input field {1} to be visible and read text")
    public void waitForInputFieldAndReadText(By locator, String expectedValue) {
        try {
            wait10Sec.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            wait10Sec.until(ExpectedConditions.attributeToBe(locator, "value", expectedValue));
            String actualValue = driver.findElement(locator).getAttribute("value");
            Assert.assertEquals(expectedValue, actualValue, "Input field value is not correct!");

        } catch (Exception e) {
            screenshot();
            e.printStackTrace();
            Assert.fail("TEST FAILED: Field not found " + locator);
        }
    }

    @Step("User takes a screenshot.")
    @Attachment
    public byte[] screenshot() {
        return ((TakesScreenshot) this.driver)
                .getScreenshotAs(OutputType.BYTES);
    }

}
