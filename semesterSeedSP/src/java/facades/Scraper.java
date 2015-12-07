package facades;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Scraper {

  private static final String[] airlines = {
    "http://angularairline-plaul.rhcloud.com/api/flightinfo/",
    "http://angularairline-plaul.rhcloud.com/api/flightinfo/",
    "http://SorryIDontExistK.com"};

  public static void main(String[] args) throws IOException {
    Scraper tester = new Scraper();
    String response = tester.getOffers(airlines, "CDG", "2016-01-06T00:00:00.000Z", 2);
    System.out.println(response);
  }

  public String getOffers(String[] urls, String origin, String date, int seats) throws MalformedURLException, IOException {
    String response = "[";
    boolean isFirst = true;
    for (String url : urls) {
      url += origin + "/" + date + "/" + seats;
      HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
      con.setRequestProperty("Content-Type", "application/json;");
      con.setRequestProperty("Accept", "application/json");
      con.setRequestProperty("Method", "GET");
      try {
        int HttpResult = con.getResponseCode();
        if (HttpResult == 200) {
          Scanner responseReader = new Scanner(new InputStreamReader(con.getInputStream(), "utf-8"));
          String res = "";
          while (responseReader.hasNext()) {
            res += responseReader.nextLine() + System.getProperty("line.separator");
          }
          if (isFirst) {
            response += res;
            isFirst = false;
          } else {
            response += "," + res;
          }
        } //If you wan't to do something with the error response
        else if (HttpResult >= 400) {
          // Scanner errorResponseReader = new Scanner(new InputStreamReader(con.getErrorStream(), "utf-8"));
          //...
        }

      } catch (UnknownHostException e) {
        //Figure our how to report this
      }
    }
    response = response + "]";
    return response;
  }
}