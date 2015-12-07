/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author noncowi
 */
@Entity
@Table(name="Airline")
@NamedQueries({
    @NamedQuery(name = "Airline.findAll", query="SELECT a FROM Airline a")
})
public class Airline implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="airline")
    private List<Flight> flights;

    public Airline() {
    }

    public Airline(String name) {
        this.name = name;
    }
    
    public Airline(String name, List<Flight> flights) {
        this.name = name;
        this.flights = flights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
    
}