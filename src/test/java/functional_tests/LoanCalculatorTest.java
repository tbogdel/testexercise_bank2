package functional_tests;

import ee.tbogdel.testexercise.utils.base.Base;
import ee.tbogdel.testexercise.utils.utils.ParseJson;
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
        String amount = "3870";
        String amountConverted = "3,870";
        String period = "60";
        loanApplicationPage.modifyLoanCalcValues(amount, period);
        loanApplicationPage.closeLoanCalcModal(true);
        loanApplicationPage.verifyLoanAmountInApplication(amount);
        loanApplicationPage.openLoanCalculator();
        loanApplicationPage.verifyLoanCalcValues(amountConverted, period);
        loanApplicationPage.verifyMonthlyPayment();
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
