package ee.tbogdel.testexercise.utils.utils;

import com.google.gson.JsonObject;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;

public class ParseJson {

    public static PricingParameters getPricingConditions() throws IOException, InterruptedException {
        // Create JSON request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("product", "Small loan");

        String jsonRequest = requestBody.toString();
        System.out.println("Request JSON: " + jsonRequest);

        // Send POST request with JSON
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://taotlus.bigbank.ee/api/v2/pricing-conditions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.body());

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.body());

        for (JsonNode loan : jsonResponse) {
            String product = loan.get("maintenanceFee").asText();
            String rate = loan.get("interestRate").asText();
            System.out.println( "maintenanceFee: " + product);
            System.out.println( "InterestRate: " + rate);
            return new PricingParameters(rate, product);
        }
        return null;
    }

    public static String getMonthlyPaymentCalculationFromServer() throws IOException, InterruptedException {
        PricingParameters pricingParameters = getPricingConditions();
        assert pricingParameters != null;
        System.out.println("pricingParameters = " + pricingParameters);

        // Create JSON request body
        String jsonRequest = createJsonRequest(pricingParameters);
        System.out.println("Request JSON: " + jsonRequest);

        // Send POST request with JSON
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://taotlus.bigbank.ee/api/v1/loan/calculate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Response status: " + response.statusCode());
        System.out.println("Response body: " + response.body());

        // Parse JSON response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(response.body());

            String monthlyPayment = jsonResponse.get("monthlyPayment").asText();
            System.out.println( "monthlyPayment: " + monthlyPayment);
            return monthlyPayment;

    }

    private static String createJsonRequest(PricingParameters pricingParameters) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("currency", "EUR"); // Constant value
        requestBody.addProperty("productType", "SMALL_LOAN_EE01"); // Constant value
        requestBody.addProperty("maturity", 60); //From test data
        requestBody.addProperty("administrationFee", Double.parseDouble(pricingParameters.maintenanceFee)); // From pricing conditions
        requestBody.addProperty("conclusionFee", 365); // Constant value
        requestBody.addProperty("amount", 3870); // From test data
        requestBody.addProperty("monthlyPaymentDay", 15); //Constant value
        requestBody.addProperty("interestRate", Double.parseDouble(pricingParameters.interestRate)); // From pricing conditions

        return requestBody.toString();
    }

}