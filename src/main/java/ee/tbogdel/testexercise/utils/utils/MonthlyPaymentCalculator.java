package ee.tbogdel.testexercise.utils.utils;

import java.util.ArrayList;
import java.util.List;

public class MonthlyPaymentCalculator<pmt> {


        public static void main(String[] args) {
            double amount = 30000;
            double interestRate = 10; // annual %
            int months = 120;
            double adminFee = 3.99;
            double conclusionFee = 365;

            double monthlyPayment = pmt(interestRate / 100 / 12, months, -amount) + adminFee;
            double aprc = calculateAPRC(amount, conclusionFee, monthlyPayment, months);

            System.out.printf("Monthly Payment: %.2f%n", monthlyPayment); // 403.14
            System.out.printf("APRC: %.2f%%%n", aprc); // 11.21%
        }

        // Annuity formula
        public static double pmt(double rate, int nper, double pv) {
            return (rate * pv) / (1 - Math.pow(1 + rate, -nper));
        }

        // IRR-based APRC
        public static double calculateAPRC(double amount, double conclusionFee, double monthlyPayment, int months) {
            List<Double> cashFlows = new ArrayList<>();
            cashFlows.add(amount - conclusionFee);
            for (int i = 0; i < months; i++) {
                cashFlows.add(-monthlyPayment);
            }
            double irrMonthly = irr(cashFlows);
            return (Math.pow(1 + irrMonthly, 12) - 1) * 100;
        }

        // IRR calculation via Newton-Raphson
        public static double irr(List<Double> cashFlows) {
            double guess = 0.01;
            double x0 = guess;
            for (int iter = 0; iter < 1000; iter++) {
                double fValue = 0.0;
                double fDerivative = 0.0;
                for (int t = 0; t < cashFlows.size(); t++) {
                    fValue += cashFlows.get(t) / Math.pow(1 + x0, t);
                    fDerivative += -t * cashFlows.get(t) / Math.pow(1 + x0, t + 1);
                }
                double x1 = x0 - fValue / fDerivative;
                if (Math.abs(x1 - x0) < 1e-10) return x1;
                x0 = x1;
            }
            return x0;
        }


}
