import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class TripDetailsTest {

    TripDetails td1;
    TripDetails td2;


    @BeforeEach
    public void setupTripDetails() {
        td1 = new TripDetails();
        td1.setDepartureLoc("Hesthagen");
        td1.setDepartureDate(LocalDate.of(2021, 4, 16));
        td1.setDepartureTime(LocalTime.of(12, 52));
        td1.setReturnDate(LocalDate.of(2021, 4, 18));
        td1.setReturnTime(LocalTime.of(19, 15));

        td2 = new TripDetails();
        td2.setDepartureLoc("Tronheim S");
        td2.setDepartureDate(LocalDate.of(2021, 3, 5));
        td2.setReturnDate(LocalDate.of(2021, 3, 7));
    }

    @Test
    public void testTripDetailsGetters() {
        assertEquals("Hesthagen", td1.getDepartureLoc().toString());
        assertEquals(LocalDate.of(2021, 4, 16), td1.getDepartureDate());
        assertEquals(LocalTime.of(12, 52), td1.getDepartureTime());
        assertEquals(LocalDate.of(2021, 4, 18), td1.getReturnDate());
        assertEquals(LocalTime.of(19, 15), td1.getReturnTime());

        td1.setDepartureLoc("Strindheim");
        td1.setDepartureDate(LocalDate.of(2021, 3, 5));
        td1.setDepartureTime(LocalTime.of(17, 0));
        td1.setReturnDate(LocalDate.of(2021, 3, 7));
        td1.setReturnTime(LocalTime.of(21, 15));

        assertEquals("Strindheim", td1.getDepartureLoc().toString());
        assertEquals(LocalDate.of(2021, 3, 5), td1.getDepartureDate());
        assertEquals(LocalTime.of(17, 0), td1.getDepartureTime());
        assertEquals(LocalDate.of(2021, 3, 7), td1.getReturnDate());
        assertEquals(LocalTime.of(21, 15), td1.getReturnTime());
    }

    @Test
    public void testTripDetailsDuration() {
        assertEquals(195780, td1.getDuration().getSeconds());

        assertNull(td2.getDuration());
    }

    @Test
    public void testTripDetailsTimeUntil() {
        assertEquals(
                Duration.between(LocalDateTime.now(), td1.getDepartureDate().atTime(td1.getDepartureTime())).getSeconds()
                , td1.getTimeUntil().getSeconds());

        assertNull(td2.getDuration());
    }


    @Test
    public void testTripDurationToString() {
        assertEquals("2 dager", td1.durationToString(td1.getDuration()));
        td1.setReturnDate(LocalDate.of(2021, 4, 16));
        assertEquals("6 timer", td1.durationToString(td1.getDuration()));
        td1.setReturnTime(LocalTime.of(13, 22));
        assertEquals("30 minutter", td1.durationToString(td1.getDuration()));
        td1.setReturnTime(LocalTime.of(12, 52, 15));
        assertEquals("15 sekunder", td1.durationToString(td1.getDuration()));
        td1.setReturnTime(LocalTime.of(12, 51, 30));
        assertEquals("Nå!", td1.durationToString(td1.getDuration()));
        td1.setReturnDate(LocalDate.of(2021, 4, 14));
        assertEquals("2 dager siden", td1.durationToString(td1.getDuration()));

        assertNull(td2.durationToString(td2.getDuration()));

    }
}
