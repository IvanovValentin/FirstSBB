package com.javaschool.ivanov.Domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


@Entity
public class Direction {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "duration")
    @NotNull
    private Timestamp duration;

    @ManyToOne
    @JoinColumn(name = "station_from")
    @NotNull
    private Station stationFrom;

    @ManyToOne
    @JoinColumn(name = "station_to")
    @NotNull
    private Station stationTo;

    @OneToMany(mappedBy = "direction")
    private List<Schedule> schedules;


    public Direction() {
    }
    public Direction(Timestamp duration, Station stationFrom, Station stationTo) {
        this.duration = duration;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
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

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
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

        Direction direction = (Direction) o;

        if (id != direction.id) return false;
        if (duration != null ? !duration.equals(direction.duration) : direction.duration != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }
}
