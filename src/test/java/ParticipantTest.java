import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParticipantTest {

    @Test
    public void testParticipant() {
        Participant ptp1 = new Participant("dummy name");
        assertEquals("dummy name", ptp1.getName());

        Participant ptp2 = new Participant("Alex's test, -ayy");
        assertEquals("Alex's test, -ayy", ptp2.getName());
    }

    @Test
    public void testParticipantCharacterLimit() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Participant ptp = new Participant("Hello, my name is longer than 31 characters");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception.getMessage());
    }

    @Test
    public void testParticipantIllegalCharacter() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Participant ptp = new Participant("My name contains ? and !");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception.getMessage());
    }

    @Test
    public void testParticipantEmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Participant ptp = new Participant("");
        });
        assertEquals("Participant name cannot be empty", exception.getMessage());
    }

    @Test
    public void testTripWhiteCharacters() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            Participant ptp1 = new Participant(" Whitespace in front");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            Participant ptp2 = new Participant("Whitespace in back ");
        });
        assertEquals("Name must be less than 31 characters, can only contain letters, numbers and ['.-]", exception2.getMessage());
    }
}


