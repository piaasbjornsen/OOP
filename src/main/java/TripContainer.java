import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A container class, containing all the trips created by the user.
 */
public class TripContainer implements Serializable {

    private List<Trip> trips = new ArrayList<>();

    public TripContainer() {
    }

    /**
     * Sets a list of trips to the container.
     *
     * @param trips the list of trips to set to the container.
     * @throws NullPointerException if provided list of trips is null.
     */
    public void setTrips(List<Trip> trips) {
        if (trips == null) {
            throw new NullPointerException("trips cannot be null");
        }
        this.trips = trips;
    }

    /**
     * Adds a trip to the container.
     *
     * @param trip, the trip to add to the container.
     * @throws NullPointerException if trip is null.
     * @throws IllegalArgumentException if a trip with that name already exists.
     */
    public void addTrip(Trip trip) {
        if (trip == null) {
            throw new NullPointerException("trip cannot be null");
        } else if (this.trips.stream().anyMatch(x -> x.getTripName().equals(trip.getTripName()))) {
            throw new IllegalArgumentException("Duplicate trip names");
        }
        this.trips.add(trip);
    }

    /**
     * @param trip the trip to be removed from the container.
     */
    public void removeTrip(Trip trip) {
        this.trips.remove(trip);
    }

    /**
     * @param name of the trip to be returned.
     * @return the trip with the given name.
     */
    public Trip getTrip(String name) {
        return trips.stream().filter(x -> x.getTripName().equals(name)).findFirst().orElse(null);
    }

    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * @return a list of all the names of all the trips.
     */
    public List<String> getTripNames() {
        List<String> newList = new ArrayList<>();

        for (Trip trip : trips) {
            newList.add(trip.getTripName());
        }
        return newList;
    }
}
