/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.Scraper;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author noncowi
 */
@Path("scraper")
public class ScraperAPI {
        private static Gson gson = new GsonBuilder()
             .setPrettyPrinting()
             .create();
@GET
@Produces("application/json")
@Path("airlines/{from}/{to}/{date}/{persons}")
    public String getAirlines(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date,@PathParam("persons") int persons){
        Scraper sc = new Scraper();
        String airlines = "";
        try{
            airlines = sc.getOffers(from, to, date, persons);
        } catch (IOException e){
            return gson.toJson("hej");
        }
        if(airlines.equals("") || airlines.equals("[]"))
        {return gson.toJson("Flight not found");
        }
        return gson.toJson(airlines);
        }
    }

