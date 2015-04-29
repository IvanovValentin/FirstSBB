package com.javaschool.ivanov.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "departure")
    @NotNull
    private Timestamp departure;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @NotNull
    private Train train;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @NotNull
    private Route route;

    @OneToMany(mappedBy = "trip")
    private List<Ticket> tickets;


    public Trip() {
    }

    public Trip(Timestamp departure, Train train, Route route) {
        this.departure = departure;
        this.train = train;
        this.route = route;
    }

    public Timestamp getDeparture() {
        return departure;
    }

    public void setDeparture(Timestamp departure) {
        this.departure = departure;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trip trip = (Trip) o;

        if (id != trip.id) return false;
        if (departure != null ? !departure.equals(trip.departure) : trip.departure != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        return result;
    }
}
