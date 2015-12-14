package facades;

import URLS.TempUrls;
import deploy.DeploymentConfiguration;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Scraper {
    
    List<String> urls;
    private EntityManagerFactory emf;
    private EntityManager em;
        

    public Scraper() {
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        em = emf.createEntityManager();
        Query query = em.createNamedQuery("url.findAll");
        urls = query.getResultList();
    }
    private static final String[] airlines = {
        "http://angularairline-plaul.rhcloud.com/api/flightinfo/",
        "http://angularairline-plaul.rhcloud.com/api/flightinfo/",
        "http://SorryIDontExistK.com"};

    public static void main(String[] args) throws IOException {
        Scraper tester = new Scraper();
        String response = tester.getOffers("CDG", "2016-01-06T00:00:00.000Z", 2);
        System.out.println(response);
    }

    public String getOffers(String origin, String date, int seats) throws IOException{
        List<Future<String>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        String response = "[";
            for (String url : urls) {
                System.out.println(urls);
                System.out.println(url);
                Future<String> future = executor.submit(new FlightFacade(url, origin, date, seats));
            list.add(future);

            }
            for (Future<String> future : list) {
           
            try {
                if (future.get() != null)
                    
                    response = response + future.get() +",";
            } catch (ExecutionException ex) {
                Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
            }catch (InterruptedException e){
                
            }
                
        }
           
            
        
        response = response + "]";
        return response;
    }

    

    public String getOffers(String origin, String dest, String date, int seats) throws IOException {
        String response = "[";
        boolean isFirst = true;
        try {
            for (String url : TempUrls.urls) {
                url += origin + "/" + dest + "/" + date + "/" + seats;
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
        } catch (MalformedURLException e) {

        }
        response = response + "]";
        return response;
    }
}
