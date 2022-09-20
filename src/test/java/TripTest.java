import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


public class TripTest {

    Trip t;

    @BeforeEach
    public void setupTrip() {
        t = new Trip("myTrip");
        t.setInfo("test info");
    }

    @Test
    public void testTrip() {
        assertEquals("myTrip", t.getTripName());
        assertEquals("test info", t.getInfo());
        assertNotNull(t.tripDetails());
        assertNotNull(t.getParticipantList());

        Trip t2 = new Trip("Alex's test, -ayy");
        assertEquals("Alex's test, -ayy", t2.getTripName());
    }

    @Test
    public void testTripCharacterLimit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Trip t1 = new Trip("Hello, my name is longer than 31 characters");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception.getMessage());
    }

    @Test
    public void testTripIllegalCharacter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Trip t1 = new Trip("My name contains ? and !");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception.getMessage());
    }

    @Test
    public void testTripEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Trip t1 = new Trip("");
        });
        assertEquals("Trip name cannot be empty", exception.getMessage());
    }

    @Test
    public void testTripWhiteCharacters() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Trip t1 = new Trip(" Whitespace in front");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Trip t2 = new Trip("Whitespace in back ");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception2.getMessage());
    }

    @Test
    public void testTripParticipantList() {
        t.addParticipant("Pia");
        t.addParticipant("Alex");
        assertEquals(2, t.getParticipantList().size());

        final Iterator<Participant> it1 = t.getParticipantList().iterator();

        Participant pia = it1.next();
        assertEquals("Pia", pia.getName());

        Participant alex = it1.next();
        assertEquals("Alex", alex.getName());

        t.removeParticipant(pia);
        assertEquals(1, t.getParticipantList().size());

        final Iterator<Participant> it2 = t.getParticipantList().iterator();

        Participant current = it2.next();
        assertEquals("Alex", current.getName());
    }
}
