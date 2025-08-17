package ee.tbogdel.testexercise.utils.utils;

import com.google.gson.JsonObject;
import io.qameta.allure.internal.shadowed.jackson.databind.JsonNode;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.*;
import java.net.URI;

public class ParseJson {

    public static String getPricingConditions() throws IOException, InterruptedException {
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

        if (jsonResponse.isArray() && !jsonResponse.isEmpty()) {
            JsonNode firstItem = jsonResponse.get(0);
            if (firstItem.has("interestRate")) {
                String interestRate = firstItem.get("interestRate").asText();
                System.out.println("Parsed interestRate: " + interestRate);
            } else {
                System.out.println("Field 'interestRate' not found!");
            }
        } else {
            System.out.println("Response is not an array or empty!");
        }

        for (JsonNode loan : jsonResponse) {
            String product = loan.get("product").asText();
            String rate = loan.get("interestRate").asText();
            System.out.println( "Product: " + product);
            System.out.println( "InterestRate: " + rate);
            return rate;
        }
        return null;
    }

    public static String getMonthlyPaymentCalculationFromServer() throws IOException, InterruptedException {
        // Create JSON request body
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("currency", "EUR");
        requestBody.addProperty("productType", "SMALL_LOAN_EE01");
        requestBody.addProperty("maturity", 60);
        requestBody.addProperty("administrationFee", 3.99);
        requestBody.addProperty("conclusionFee", 365);
        requestBody.addProperty("amount", 3870);
        requestBody.addProperty("monthlyPaymentDay", 15);
        requestBody.addProperty("interestRate", 14.9);

        String jsonRequest = requestBody.toString();
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

}