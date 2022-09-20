import java.io.Serializable;

/**
 * Stores a location
 */
public class Location implements Serializable {

    private String location = "";

    public Location() {
    }

    public void setLocation(String location) {
        // No validation to allow for full user flexibility (address, coordinates, etc...)
        this.location = location;
    }

    @Override
    public String toString() {
        return location;
    }
}