package com.javaschool.ivanov.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id")
    @NotNull
    private Person person;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    @NotNull
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "station_from")
    @NotNull
    private Station stationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    @NotNull
    private Station stationTo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User userId;

    public Ticket() {
    }
    public Ticket(Person person, Trip trip, Station stationFrom, Station stationTo) {
        this.person = person;
        this.trip = trip;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station stationFrom) {
        this.stationFrom = stationFrom;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public void setStationTo(Station stationTo) {
        this.stationTo = stationTo;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
