import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*This program API will do a currency exchange based on 10 euro's to pound 
sterling currently worth today.
 */
/**
 * @author Thomas Truong
 */
public class CurrencyAPI {
    public static void convertCurrency (){
        // Create a HTTP Connection.
        String baseUrl = "https://api.getgeoapi.com/api/v2/currency/convert";
        String apiKey = "?api_key=0ac51a327319423244af10fb10b58c00b636be83";
        String callAction = "&from=EUR&to=GBP&amount=10&format=json";
        String urlString = baseUrl + apiKey + callAction;
        URL url;
        try {
            // Make the connection.
            url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Examine the response code.
            int status = con.getResponseCode();
            if (status != 200) {
                System.out.printf("Error: Could not convert: " + status);
            } 
            else {
                // Parsing input stream into a text string.
                BufferedReader in = new BufferedReader(new InputStreamReader
        (con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close the connections.
                in.close();
                con.disconnect();
                // Print out our complete JSON string.
                System.out.println("Output: " + content.toString());
                // Parse that object into a usable Java JSON object.
                JSONObject obj = new JSONObject(content.toString());
                // Print out the conversion rate for today.
                String baseCurrencyAmount = obj.getString("amount");
                String baseCurrency = obj.getString("base_currency_name");
                String currencyAmount = obj.getJSONObject("rates")
                        .getJSONObject("GBP").getString("rate_for_amount");
                String currencyName = obj.getJSONObject("rates")
                        .getJSONObject("GBP").getString("currency_name");
                String exchangeDate = obj.getString("updated_date");
                System.out.println("Conversion: " + baseCurrencyAmount + " " + 
                        baseCurrency + "'s can be exchanged for " + 
                        currencyAmount + " " + currencyName + " on " + 
                        exchangeDate);
            }
        } 
        catch (Exception ex) {
            System.out.println("Error: " + ex);
            return;
        }
    }
}