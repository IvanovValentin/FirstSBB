package com.javaschool.ivanov.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "sequence_number")
    @NotNull
    private int sequenceNumber;

    @ManyToOne
    @JoinColumn(name = "route_id")
    @NotNull
    private Route route;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    @NotNull
    private Direction direction;

    public Schedule() {
    }

    public Schedule(int sequenceNumber, Route route, Direction direction) {
        this.sequenceNumber = sequenceNumber;
        this.route = route;
        this.direction = direction;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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

        Schedule schedule = (Schedule) o;

        if (id != schedule.id) return false;
        if (sequenceNumber != schedule.sequenceNumber) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + sequenceNumber;
        return result;
    }
}
