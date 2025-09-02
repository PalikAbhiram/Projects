import java.util.Scanner;
import java.util.HashMap;

public class CurrencyConverter {
    public static void main(String[] args) {
        // Hardcoded exchange rates for demonstration (Base: INR)
        HashMap<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 0.012); // INR to USD
        exchangeRates.put("EUR", 0.011); // INR to EUR
        exchangeRates.put("GBP", 0.0095); // INR to GBP
        exchangeRates.put("INR", 1.0); // INR to INR
        exchangeRates.put("JPY", 1.80); // INR to JPY

        HashMap<String, String> currencySymbols = new HashMap<>();
        currencySymbols.put("USD", "$");
        currencySymbols.put("EUR", "€");
        currencySymbols.put("GBP", "£");
        currencySymbols.put("INR", "₹");
        currencySymbols.put("JPY", "¥");

        Scanner scanner = new Scanner(System.in);

        // Currency selection
        System.out.println("Available currencies: INR, USD, EUR, GBP, JPY");
        System.out.print("Enter base currency: ");
        String baseCurrency = scanner.next().toUpperCase();
        System.out.print("Enter target currency: ");
        String targetCurrency = scanner.next().toUpperCase();

        // Validate currency input
        if (!exchangeRates.containsKey(baseCurrency) || !exchangeRates.containsKey(targetCurrency)) {
            System.out.println("Invalid currency selection!");
            return;
        }

        // User inputs amount to convert
        System.out.print("Enter amount in " + baseCurrency + ": ");
        double amount = scanner.nextDouble();

        // Currency conversion
        double inINR = amount / exchangeRates.get(baseCurrency); // Convert to INR first
        double convertedAmount = inINR * exchangeRates.get(targetCurrency);

        // Display result
        System.out.println("Converted Amount: " + currencySymbols.get(targetCurrency) + String.format("%.2f", convertedAmount) + " " + targetCurrency);

        scanner.close();
    }
}
