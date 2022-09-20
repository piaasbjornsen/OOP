import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TripContainerTest {

    TripContainer tc1;
    TripContainer tc2;
    Trip t1;
    Trip t2;

    @BeforeEach
    public void setupTripContainer() {
        tc1 = new TripContainer();
        t1 = new Trip("Telin");
        t2 = new Trip("Lyngli");
        tc1.addTrip(t1);
        tc1.addTrip(t2);

        tc2 = new TripContainer();

    }

    @Test
    public void testTripContainerGetters() {
        assertEquals(t1, tc1.getTrips().get(0));
        assertEquals(t2, tc1.getTrips().get(1));
        assertEquals(t1, tc1.getTrip("Telin"));
        assertEquals(t2, tc1.getTrip("Lyngli"));
        assertNull(tc1.getTrip("Finnes ikke"));

        assertEquals("Telin", tc1.getTripNames().get(0));
        assertEquals("Lyngli", tc1.getTripNames().get(1));


        assertNotNull(tc1.getTripNames());
    }

    @Test
    public void testTripContainerAddTrips() {
        Trip t3 = new Trip("test1");

        tc1.addTrip(t3);
        assertEquals("test1", tc1.getTripNames().get(2));

        Exception exception1 = assertThrows(NullPointerException.class, () -> tc1.addTrip(null));
        assertEquals("trip cannot be null", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> tc1.addTrip(t3));
        assertEquals("Duplicate trip names", exception2.getMessage());
    }

    @Test
    public void testTripContainerRemoveTrips() {
        tc1.removeTrip(t1);
        assertEquals("Lyngli", tc1.getTripNames().get(0));
    }

    @Test
    public void testTripContainerSetTrips() {
        Trip t3 = new Trip("test1");
        Trip t4 = new Trip("test2");

        tc1.setTrips(Arrays.asList(t3, t4));
        assertEquals("test1", tc1.getTripNames().get(0));
        assertEquals("test2", tc1.getTripNames().get(1));
        assertEquals(t3, tc1.getTrips().get(0));
        assertEquals(t4, tc1.getTrips().get(1));

        Exception exception2 = assertThrows(NullPointerException.class, () -> tc1.setTrips(null));
        assertEquals("trips cannot be null", exception2.getMessage());
    }
}

