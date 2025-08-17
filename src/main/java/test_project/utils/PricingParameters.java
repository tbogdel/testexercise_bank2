package test_project.utils;

public class PricingParameters {

    public final String interestRate;
    public final String maintenanceFee;


    public PricingParameters(String interestRate, String maintenanceFee) {
        this.interestRate = interestRate;
        this.maintenanceFee = maintenanceFee;
    }

    @Override
    public String toString() {
        return "PricingParameters{" +
                "interestRate='" + interestRate + '\'' +
                ", maintenanceFee='" + maintenanceFee + '\'' +
                '}';
    }
}
