/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
public class ReserveFacade {
 private static String url = "http://angularairline-plaul.rhcloud.com/api/flightreservation";
 private static String json = "{\n"
 + " \"flightID\":\"COL3256x100x2016-01-10T19:00:00.000Z\",\n"
 + " \"numberOfSeats\":2,\n"
 + " \"ReserveeName\":\"Peter Hansen\",\n"
 + " \"ReservePhone\":\"12345678\",\n"
 + " \"ReserveeEmail\":\"peter@peter.dk\",\n"
 + " \"Passengers\":[\n"
 + " {\"firstName\":\"Peter\",\"lastName\":\"Peterson\"},\n"
 + " {\"firstName\":\"Jane\",\"lastName\":\"Peterson\"}\n"
 + " ]\n"
 + "}";
 public static void main(String[] args) throws MalformedURLException, IOException {
 HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
 con.setRequestProperty("Content-Type", "application/json;");
 con.setRequestProperty("Accept", "application/json");
 con.setRequestProperty("Method", "POST");
 con.setDoOutput(true);
 PrintWriter pw = new PrintWriter(con.getOutputStream());
 try (OutputStream os = con.getOutputStream()) {
 os.write(json.getBytes("UTF-8"));
 }
 int HttpResult = con.getResponseCode();
 InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8") :
 new InputStreamReader(con.getErrorStream(), "utf-8");
 Scanner responseReader = new Scanner(is);
 String response = "";
 while (responseReader.hasNext()) {
 response += responseReader.nextLine()+System.getProperty("line.separator");
 }
 System.out.println(response);
 System.out.println(con.getResponseCode());
 System.out.println(con.getResponseMessage());
 }
}