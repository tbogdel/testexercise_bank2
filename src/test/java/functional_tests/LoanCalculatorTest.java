package functional_tests;

import test_project.base.Base;
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
    @DisplayName("Calculating monthly payment and saving selected values")
    @Test
    @Order(2)
    void savingLoanCalcSelectedValues() throws Exception {
        String amount = "30001";
        String period = "6";
        String amountConverted = "30,000"; // Converted value for verification
        loanApplicationPage.navigateToLoanApplicationPage();
        loanApplicationPage.modifyMonthlyPayment(amount, period);
        loanApplicationPage.closeLoanCalcModal(true);
        loanApplicationPage.verifyLoanAmountInApplication(amount);
        loanApplicationPage.openLoanCalculator();
        loanApplicationPage.verifyLoanCalcValues(amountConverted, period);
        loanApplicationPage.verifyMonthlyPayment(Integer.parseInt(amount), Integer.parseInt(period));
        //modifyMonthlyPaymentAndCheckSavings("3870", "60", "3,870");
    }

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Calculating monthly payment and APRC")
    @Test
    @Order(2)
    void savingLoanCalcSelectedValues_testB() throws Exception {
        // Calculating monthly payment and APRC for Loan amount change
        loanApplicationPage.modifyMonthlyPayment("29000", null);
        // Calculating monthly payment and APRC for Period change
        loanApplicationPage.modifyMonthlyPayment(null, "100");
    }

    @Story("Loan application – calculator")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Closing the window discards loan calculator changes")
    @Test
    @Order(3)
    void discardLoanCalcChanges() {
        loanApplicationPage.navigateToLoanApplicationPage();
        String amount = "3870";
        String period = "60";
        loanApplicationPage.modifyLoanCalcValues(amount, period);
        loanApplicationPage.closeLoanCalcModal(false);
        loanApplicationPage.verifyLoanAmountInApplication(loanApplicationPage.amountDefault);
        loanApplicationPage.openLoanCalculator();
        loanApplicationPage.verifyLoanCalcValues(loanApplicationPage.loanCalcModalAmountValue, loanApplicationPage.periodDefault);
    }



}
