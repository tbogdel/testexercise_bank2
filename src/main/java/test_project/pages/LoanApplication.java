package test_project.pages;

import test_project.base.Base;
import test_project.utils.ParseJson;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.util.Locale;

import static com.sun.activation.registries.LogSupport.log;


public class LoanApplication {

    public final WebDriver driver;
    private final WebDriverWait wait10Sec;
    private final Base base;

    public LoanApplication(WebDriver webDriver, WebDriverWait wait){
        driver = webDriver;
        wait10Sec = wait;
        base = new Base(webDriver, wait10Sec);
    }

    // Labels and values
    String loanApplicationPageURL = "https://taotlus.bigbank.ee/";
    String loanCalcModalTitle = "Vali sobiv summa ja periood";
    String loanCalcModalAmountTitle = "Laenusumma";
    String loanCalcModalMinAmountTitle = "500 €";
    String loanCalcModalMaxAmountTitle = "30,000 €";
    String loanCalcModalPeriodTitle = "Periood";
    String loanCalcModalMinPeriodTitle = "6 KUUD";
    String loanCalcModalMaxPeriodTitle = "120 KUUD";
    public String loanCalcModalAmountValue = "5,000";// Default value
    public final String amountDefault = "5000";
    public final String periodDefault = "60";
    public String loanCalcModalPeriodValue = "60"; // Default value
    String loanCalcModalAmountUnit = "€";
    String loanCalcModalPeriodUnit = "kuud";
    String loanCalcModalMonthlyPaymentTitle = "KUUMAKSE";
    String loanCalcModalMonthlyPaymentValue = "€123.13";
    String loanCalcModalSubmit = "JÄTKA";

    //Locators
    public By loanCalcModalTitleLabel = By.xpath("//dialog[@role='dialog' and @aria-modal='true'] //span[@class='bb-calculator-modal__heading']");
    public By loanCalcModalAmountTitleLabel = By.xpath("//div[@data-testid='bb-input']/label[@for='header-calculator-amount']");
    public By loanCalcModalMinAmountTitleLabel = By.xpath("//div[@id='header-calculator-amount']/div[@class='bb-slider__ranges']/span[1]");
    public By loanCalcModalMaxAmountTitleLabel = By.xpath("//div[@id='header-calculator-amount']/div[@class='bb-slider__ranges']/span[2]");
    public By loanCalcModalPeriodTitleLabel = By.xpath("//div[@data-testid='bb-input']/label[@for='header-calculator-period']");
    public By loanCalcModalMinPeriodTitleLabel = By.xpath("//div[@id='header-calculator-period']/div[@class='bb-slider__ranges']/span[1]");
    public By loanCalcModalMaxPeriodTitleLabel = By.xpath("//div[@id='header-calculator-period']/div[@class='bb-slider__ranges']/span[2]");

    public By loanCalcModalAmountValueInput = By.xpath("//input[@id='header-calculator-amount']");
    public By loanCalcModalPeriodValueInput = By.xpath("//input[@id='header-calculator-period']");
    public By loanCalcModalAmountUnitLabel = By.cssSelector("#header-calculator-amount + .input-addon-wrapper .input-addon");
    public By loanCalcModalPeriodUnitLabel = By.cssSelector("#header-calculator-period + .input-addon-wrapper .input-addon");
    public By loanCalcModalMonthlyPaymentTitleLabel = By.xpath("//div[@class='bb-labeled-value__label']");
    public By loanCalcModalMonthlyPaymentValueLabel = By.xpath("//div[@class='bb-labeled-value__value']");
    public By loanCalcModalSubmitBtn = By.cssSelector(".bb-calculator-modal__submit-button");
    public By loanCalcModalCloseBtn = By.cssSelector(".bb-modal__close");

    public By loanApplicationLoanAmount = By.xpath("//div[@class='bb-edit-amount__amount']");

    @Step("Navigate to Loan Application Page")
    public void navigateToLoanApplicationPage() {
        // Open loan application page
        driver.get(loanApplicationPageURL);
        base.labelVerification(loanCalcModalTitleLabel, loanCalcModalTitle);
    }

    @Step ("Verify loan calculator labels and values")
    public void verifyLoanCalcView() {
        // Verify loan modal window labels and values
        base.labelVerification(loanCalcModalAmountTitleLabel, loanCalcModalAmountTitle);
        base.labelVerification(loanCalcModalMinAmountTitleLabel, loanCalcModalMinAmountTitle);
        base.labelVerification(loanCalcModalMaxAmountTitleLabel, loanCalcModalMaxAmountTitle);

        base.labelVerification(loanCalcModalPeriodTitleLabel, loanCalcModalPeriodTitle);
        base.labelVerification(loanCalcModalMinPeriodTitleLabel, loanCalcModalMinPeriodTitle);
        base.labelVerification(loanCalcModalMaxPeriodTitleLabel, loanCalcModalMaxPeriodTitle);

        // Add verification for the amount and period input fields
        base.labelVerification(loanCalcModalAmountUnitLabel, loanCalcModalAmountUnit);
        base.waitForInputFieldAndReadText(loanCalcModalAmountValueInput, loanCalcModalAmountValue);
        base.labelVerification(loanCalcModalPeriodUnitLabel, loanCalcModalPeriodUnit);
        base.waitForInputFieldAndReadText(loanCalcModalPeriodValueInput, loanCalcModalPeriodValue);

        // Add verification for the monthly payment title and submit button
        base.labelVerification(loanCalcModalMonthlyPaymentTitleLabel, loanCalcModalMonthlyPaymentTitle);
        base.labelVerification(loanCalcModalSubmitBtn, loanCalcModalSubmit);

    }

    @Step ("Modify loan calculator values")
    public void modifyLoanCalcValues(
            @Nullable String amount,
            @Nullable String period)
    {
        // Modify loan calculator values
        if (amount != null) {
            base.waitForInputFieldAndFillText(loanCalcModalAmountValueInput, amount);
        }
        if (period != null) {
            base.waitForInputFieldAndFillText(loanCalcModalPeriodValueInput, period);
        }
    }

    @Step ("Close loan calculator modal")
    public void closeLoanCalcModal(boolean isSave) {
        // Close loan calculator modal
        if (isSave) {
            base.waitForButtonClickableAndClick(loanCalcModalSubmitBtn);
        } else {
            base.waitForButtonClickableAndClick(loanCalcModalCloseBtn);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        wait10Sec.until(ExpectedConditions.invisibilityOfElementLocated(loanCalcModalTitleLabel));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


    }

    @Step ("Verify loan amount in the application")
    public void verifyLoanAmountInApplication(String expectedAmount) {
        wait10Sec.until(driver ->
                !driver.findElement(loanApplicationLoanAmount).getText().trim().isEmpty()
        );
        // Verify loan amount in the application
        base.labelVerification(loanApplicationLoanAmount, expectedAmount + " €");
    }

    @Step ("Open loan calculator modal")
    public void openLoanCalculator() {
        // Open loan calculator modal
        base.waitForButtonClickableAndClick(loanApplicationLoanAmount);
        base.labelVerification(loanCalcModalTitleLabel, loanCalcModalTitle);
    }

    @Step ("Verify loan calculator values")
    public void verifyLoanCalcValues(String amount, String period) {
        // Verify loan calculator values
        base.waitForInputFieldAndReadText(loanCalcModalAmountValueInput, amount);
        base.waitForInputFieldAndReadText(loanCalcModalPeriodValueInput, period);
    }

    @Step ("Verify loan calculator values")
    public void verifyMonthlyPayment( int amount, int period) throws Exception {
        String monthlyPayment = ParseJson.getMonthlyPaymentCalculationFromServer(amount, period);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("€#,##0.00", symbols);
        String formatted = df.format(Double.parseDouble(monthlyPayment));
        // Verify monthly payment value
        base.labelVerification(loanCalcModalMonthlyPaymentValueLabel, formatted);
    }

    @Step ("Modify monthly payment")
    public void modifyMonthlyPayment(
            @Nullable String amount,
            @Nullable String period
    ) throws Exception {
        navigateToLoanApplicationPage();

        // Always use the given values for modifying loan calculator
        modifyLoanCalcValues(amount, period);

        // Use fallback values only for verification
        String verifyAmount = (amount == null || amount.isEmpty()) ? amountDefault : amount;
        String verifyPeriod = (period == null || period.isEmpty()) ? periodDefault : period;

        verifyMonthlyPayment(
                Integer.parseInt(verifyAmount),
                Integer.parseInt(verifyPeriod)
        );
    }
}
