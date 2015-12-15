/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;
import deploy.DeploymentConfiguration;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Jonas
 */
public class FlightFacadeFromTo implements Callable<String> {

    Scraper scr;
    String url;
    String origin;
    String dest;
    String date;
    int seats;
    private EntityManagerFactory emf;
    private EntityManager em;

    public FlightFacadeFromTo(String url1, String origin1,String dest1, String date1, int seats1) {
        url = url1;
        origin = origin1;
        dest = dest1;
        date = date1;
        seats = seats1;
        scr = new Scraper();

    }

    @Override
    public String call() throws Exception {
        String response = null;
        try {
//    for (String url : urls) {
//      url
            String tempu = url + origin + "/"+ dest + "/" + date + "/" + seats;
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
                        res += responseReader.nextLine() + System.getProperty("line.separator");
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
        
        return response;
    }

}
