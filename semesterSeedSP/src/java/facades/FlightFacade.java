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
public class FlightFacade implements Callable<String>{
    Scraper scr;
    String url;
    String origin;
    String date;
    int seats;
    private EntityManagerFactory emf;
    private EntityManager em;
    public FlightFacade(String url, String origin, String date, int seats){
        url = url;
        origin = origin;
        date = date;
        seats = seats;
        scr = new Scraper();
        
    }

    @Override
    public String call() throws Exception {
         String response = "";
    boolean isFirst = true;
    try{
//    for (String url : urls) {
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
        } //If you wan't to do something with the error response
        else if (HttpResult >= 400) {
          // Scanner errorResponseReader = new Scanner(new InputStreamReader(con.getErrorStream(), "utf-8"));
          //...
        }

      } catch (UnknownHostException e) {
        //Figure our how to report this
      }
//    }
    } catch(MalformedURLException e){
    }
    response = response + "";
    return response;
    }
    
}
