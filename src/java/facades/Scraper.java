package facades;

import URLS.TempUrls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
        String response = tester.getOffers("CDG","CPH", "2016-01-06T00:00:00.000Z", 2);
        System.out.println(response);
    }

    public List<JsonObject> getOffers(String origin, String date, int seats) throws IOException{
        
        List<Future<JsonObject>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<JsonObject> response = new ArrayList();
        urls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
            for (String url : urls) {
                Future<JsonObject> future = executor.submit(new FlightFacadeOnlyDest(url, origin, date, seats));
            list.add(future);

            }
            for (Future<JsonObject> future : list) {
           
            try {
                if (future.get() != null)
                    
                    response.add(future.get());
            } catch (ExecutionException ex) {
                Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
            }catch (InterruptedException e){
                
            }
                
        }
           
            
        executor.shutdown();
        return response;
    }

    

    public String getOffers(String origin, String dest, String date, int seats) throws IOException {
        List<Future<String>> list = new ArrayList();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        String response = "[";
        urls.add("http://angularairline-plaul.rhcloud.com/api/flightinfo/");
            for (String url : urls) {
                Future<String> future = executor.submit(new FlightFacadeFromTo(url, origin,dest, date, seats));
            list.add(future);

            }
            for (Future<String> future : list) {
           
            try {
                if (future.get() != null)
                    
                    response = response + future.get() +"";
            } catch (ExecutionException ex) {
                Logger.getLogger(Scraper.class.getName()).log(Level.SEVERE, null, ex);
            }catch (InterruptedException e){
                
            }
                
        }
           
            
        executor.shutdown();
        response = response + "]";
        return response;
    }
}
