/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
    public String getAirlines(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date, @PathParam("persons") int persons) {
        System.out.println("hej i api");
        JsonObject errorObj = new JsonObject();
        Scraper sc = new Scraper();
        String airlines = "";
        try {
            airlines = sc.getOffers(from, to, date, persons);
        } catch (IOException e) {
            return gson.toJson("hej");
        }
        if (airlines.equals("") || airlines.equals("[]")) {
            System.out.println("virker jeg i if?");
            errorObj.addProperty("httpError", 404);
            errorObj.addProperty("message", "No flights for the given data");
            return errorObj.toString();
        }
        return airlines;
    }
    @GET
    @Produces("application/json")
    @Path("airlines/{from}/{date}/{persons}")
    public String getAirlines(@PathParam("from") String from, @PathParam("date") String date, @PathParam("persons") int persons) {
        System.out.println("hej i api");
        JsonObject errorObj = new JsonObject();
        Scraper sc = new Scraper();
        String airlines = "";
        try {
            airlines = sc.getOffers(from, date, persons);
        } catch (IOException e) {
            return gson.toJson("hej");
        }
        if (airlines.equals("") || airlines.equals("[]")) {
            System.out.println("virker jeg i if?");
            errorObj.addProperty("httpError", 404);
            errorObj.addProperty("message", "No flight for the given date/seats/destination");
            return errorObj.toString();
        }
        return airlines;
    }
}
