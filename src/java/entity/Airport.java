/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Airport")
@NamedQueries({
    @NamedQuery(name = "Airport.findAll", query="SELECT a FROM Airport a"),
        @NamedQuery(name = "Airport.findAirportByIATA", query="SELECT a FROM Airport a WHERE a.IATACode = :IATACode")
})
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String IATACode;
    @Column(name="name")
    private String name;
    @Column(name="city")
    private String city;
    @Column(name="timeZone")
    private String timeZone;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="destination")
    private List<FlightInstance> incomingFlights;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="origin")
    private List<FlightInstance> departingFlights;

    public Airport() {
    }

    public Airport(String IATACode, String name, String city, String timeZone, List<FlightInstance> incomingFlights, List<FlightInstance> departingFlights) {
        this.IATACode = IATACode;
        this.name = name;
        this.city = city;
        this.timeZone = timeZone;
        this.incomingFlights = incomingFlights;
        this.departingFlights = departingFlights;
    }

    public Airport(String IATACode, String name, String city, String timeZone) {
        this.IATACode = IATACode;
        this.name = name;
        this.city = city;
        this.timeZone = timeZone;
    }
    
    

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public List<FlightInstance> getIncomingFlights() {
        return incomingFlights;
    }

    public void setIncomingFlights(List<FlightInstance> incomingFlights) {
        this.incomingFlights = incomingFlights;
    }

    public List<FlightInstance> getDepartingFlights() {
        return departingFlights;
    }

    public void setDepartingFlights(List<FlightInstance> departingFlights) {
        this.departingFlights = departingFlights;
    }
    
}
