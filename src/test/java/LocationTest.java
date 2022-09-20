import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void testLocation() {
        Location loc = new Location();
        Assertions.assertEquals("", loc.toString());


        // test for location
        loc.setLocation("dummy location");
        Assertions.assertEquals("dummy location", loc.toString());

        // test for coordinates
        loc.setLocation("63.417515026611014, 10.402815230336408");
        Assertions.assertEquals("63.417515026611014, 10.402815230336408", loc.toString());

    }

}
