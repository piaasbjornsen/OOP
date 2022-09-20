import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Stores details like departure time and departure location about a Trip
 */
public class TripDetails implements Serializable {

    private final Location departureLoc = new Location();
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalDate returnDate;
    private LocalTime returnTime;

    public TripDetails() {
    }

    public void setDepartureLoc(String departureLoc) {
        this.departureLoc.setLocation(departureLoc);
    }

    public Location getDepartureLoc() {
        return departureLoc;
    }


    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }


    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }


    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }


    public void setReturnTime(LocalTime returnTime) {
        this.returnTime = returnTime;
    }

    public LocalTime getReturnTime() {
        return returnTime;
    }


    /**
     * @return the duration between the departure and the return.
     */
    public Duration getDuration() {
        try {
            return Duration.between(departureDate.atTime(departureTime), returnDate.atTime(returnTime));
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @return the duration between now and the departure.
     */
    public Duration getTimeUntil() {                // Gir tiden mellom NÅ, og avreise.
        LocalDateTime now = LocalDateTime.now();
        try {
            return Duration.between(now, departureDate.atTime(departureTime));      //Hvis avreise ikke er satt, returnerer den null (hvis departureDate eller departureTime ikke er satt)

        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts a duration to a string in the most suitable time-format.
     *
     * @param duration to be converted into a string format
     * @return a String with the time and time-format.
     */
    public String durationToString(Duration duration) {
        if (duration == null) {
            return null;
        }
        if (duration.toDays() > 1) {
            return duration.toDays() + " dager";
        } else if (duration.toHours() > 1) {
            return duration.toHours() + " timer";
        } else if (duration.toMinutes() > 1) {
            return duration.toMinutes() + " minutter";
        } else if (duration.toSeconds() > 1) {
            return duration.toSeconds() + " sekunder";
        } else if (duration.isNegative() && duration.toSeconds() > -60) {
            return "Nå!";
        } else if (duration.isNegative()) {
            return duration.abs().toDays() + " dager siden";
        } else {
            return null;
        }
    }
}
