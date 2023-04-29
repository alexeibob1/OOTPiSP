package com.lab2.trains;

import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable {
    private LocalDate departureDate;
    private LocalDate destinationDate;
    private String departure;
    private String destination;

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(LocalDate destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Schedule(String departure, String destination, LocalDate departureDate, LocalDate destinationDate) {
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.destinationDate = destinationDate;
    }

    public Schedule() {}

    public String toTableString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Departure from ");
        sb.append(this.departure);
        sb.append(" on ");
        sb.append(this.departureDate.toString());
        sb.append(". Arriving to ");
        sb.append(this.destination);
        sb.append(" on ");
        sb.append(this.destinationDate.toString());
        return sb.toString();
    }
}
