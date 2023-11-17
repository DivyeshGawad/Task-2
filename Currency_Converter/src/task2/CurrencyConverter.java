package task2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter {

	// API key from Open Exchange Rates 
	private static final String API_KEY = "47c13ee8634240e2b58c016f2d545a16"; 

	public static void main(String[] args) {

		// Replace with the currency you want to convert from
		String fromCurrency = "USD";

		// Replace with the currency you want to convert to
		String toCurrency = "INR";
		
		// Replace with the amount you want to convert
		double amount = 12; 

		double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
		System.out.println(amount + " " + fromCurrency + " = " + convertedAmount + " " + toCurrency);
	}

	public static double convertCurrency(String fromCurrency, String toCurrency, double amount) {
		try {
			URL url = new URL("https://open.er-api.com/v6/latest?base=" + fromCurrency + "&symbols=" + toCurrency);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONObject rates = jsonResponse.getJSONObject("rates");
			double exchangeRate = rates.getDouble(toCurrency);

			return amount * exchangeRate;

		} catch (Exception e) {
			
			// For Indicating errors occurred during conersion
			e.printStackTrace();
			return -1;
		}
	}
}
