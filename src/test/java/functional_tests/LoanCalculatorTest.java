package functional_tests;

import ee.tbogdel.testexercise.utils.base.Base;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

@Feature("Main menu functionality")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoanCalculatorTest extends Base {

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Calculator modal view opening")
    @Test
    @Order(1)
    void openLoanCalculator() {
        loanApplicationPage.navigateToLoanApplicationPage();
        loanApplicationPage.verifyLoanCalcView();
    }

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Saving selected values")
    @Test
    @Order(2)
    void savingLoanCalcSelectedValues() throws Exception {
        loanApplicationPage.navigateToLoanApplicationPage();
        modifyMonthlyPaymentAndCheckSavings("3870", "60", "3,870");
    }

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Saving selected values")
    @Test
    @Order(2)
    void savingLoanCalcSelectedValues_testB() throws Exception {
        loanApplicationPage.navigateToLoanApplicationPage();
        modifyMonthlyPaymentAndCheckSavings("25000", "55", "25,000");
    }

    private void modifyMonthlyPaymentAndCheckSavings(String amount, String period, String amountConverted) throws Exception {
        loanApplicationPage.modifyLoanCalcValues(amount, period);
        loanApplicationPage.closeLoanCalcModal(true);
        loanApplicationPage.verifyLoanAmountInApplication(amount);
        loanApplicationPage.openLoanCalculator();
        loanApplicationPage.verifyLoanCalcValues(amountConverted, period);
        loanApplicationPage.verifyMonthlyPayment(Integer.parseInt(amount), Integer.parseInt(period));
    }

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Closing the window discards loan calculator changes")
    @Test
    @Order(3)
    void discardLoanCalcChanges() {
        loanApplicationPage.navigateToLoanApplicationPage();
        String amount = "3870";
        String amountDefault = "5000";
        String period = "60";
        loanApplicationPage.modifyLoanCalcValues(amount, period);
        loanApplicationPage.closeLoanCalcModal(false);
        loanApplicationPage.verifyLoanAmountInApplication(amountDefault);
        loanApplicationPage.openLoanCalculator();
        loanApplicationPage.verifyLoanCalcValues(loanApplicationPage.loanCalcModalAmountValue, loanApplicationPage.loanCalcModalPeriodValue);
    }



}
