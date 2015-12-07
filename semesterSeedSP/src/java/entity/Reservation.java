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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author noncowi
 */
@Entity
@Table(name="Reservation")
@NamedQueries({
    @NamedQuery(name = "Reservation.findAll", query="SELECT r FROM Reservation r"),
    @NamedQuery(name = "Reservation.findReservationsByFlightInstance", query="SELECT r FROM Reservation r WHERE r.flightInstance = :flightInstance")
})
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="price")
    private double price;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "flightInstance", referencedColumnName = "id")
    private FlightInstance flightInstance;
    @OneToMany(cascade=CascadeType.ALL, mappedBy="reservation")
    private List<Passenger> passengers;

    public Reservation() {
    }

    public Reservation(double price, FlightInstance flightInstance, List<Passenger> passengers) {
        this.price = price;
        this.flightInstance = flightInstance;
        this.passengers = passengers;
    }

    public Reservation(double price, FlightInstance flightInstance) {
        this.price = price;
        this.flightInstance = flightInstance;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
    
    
    
}