package entity;

import java.io.Serializable;
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
import javax.persistence.Table;

@Entity
@Table(name="Passenger")
@NamedQueries({
    @NamedQuery(name = "Passenger.findAll", query="SELECT p FROM Passenger p"),
    @NamedQuery(name = "Passenger.findByReservation", query="SELECT p FROM Passenger p WHERE p.storedReservations = :storedReservations"),
    @NamedQuery(name = "Passenger.findPassengerByName", query="SELECT p FROM Passenger p WHERE p.firstName = :firstName AND p.lastName = :lastName")
})
public class Passenger implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "reservation", referencedColumnName = "id")
    private Reservation reservation;

    
    public Passenger() {
    }

    public Passenger(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Passenger(String firstName, String lastName, Reservation reservation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reservation = reservation;
    }
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
