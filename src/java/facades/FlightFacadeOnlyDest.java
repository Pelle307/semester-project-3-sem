/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jonas
 */
public class FlightFacadeOnlyDest implements Callable<JsonObject> {

    Scraper scr;
    String url;
    String origin;
    String date;
    int seats;
    private EntityManagerFactory emf;
    private EntityManager em;

    public FlightFacadeOnlyDest(String url1, String origin1, String date1, int seats1) {
        url = url1;
        origin = origin1;
        date = date1;
        seats = seats1;
        scr = new Scraper();

    }

    @Override
    public JsonObject call() throws Exception {
        JsonObject jo;
        String response = null;
        try {
//    for (String url : urls) {
//      url
            String tempu = url + origin + "/" + date + "/" + seats;
            HttpURLConnection con = (HttpURLConnection) new URL(tempu).openConnection();
            con.setRequestProperty("Content-Type", "application/json;");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Method", "GET");
            try {
                int HttpResult = con.getResponseCode();
                if (HttpResult == 200) {
                    Scanner responseReader = new Scanner(new InputStreamReader(con.getInputStream(), "utf-8"));
                    String res = "";
                    while (responseReader.hasNext()) {
                        res += responseReader.nextLine();
                        
                    }
                    response = res;
                } //If you wan't to do something with the error response
                else if (HttpResult >= 400) {
          // Scanner errorResponseReader = new Scanner(new InputStreamReader(con.getErrorStream(), "utf-8"));
                    //...
                }

            } catch (UnknownHostException e) {
                //Figure our how to report this
            }
//    }
        } catch (MalformedURLException e) {
        }
        JsonElement je = new JsonParser().parse(response);
                jo = je.getAsJsonObject();
        return jo;
    }

}
