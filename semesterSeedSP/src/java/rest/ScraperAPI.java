/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import facades.Scraper;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author noncowi
 */
@Path("Scraper")
public class ScraperAPI {
    Scraper sc = new Scraper();
@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("Airlines/{from}/{to}/{date}/{persons}")
    public String getAirlines(@PathParam("from") String from, @PathParam("to") String to, @PathParam("date") String date,@PathParam("persons") int persons){
        String airlines = "";
        try{
            airlines = sc.getOffers(from, to, date, persons);
        } catch (IOException e){
        }
        if(airlines.equals("") || airlines.equals("[]"))
        {return "Flight not found";
        }
        return airlines;
        }
    }

