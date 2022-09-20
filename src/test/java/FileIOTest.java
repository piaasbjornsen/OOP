import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FileIOTest {


    final static Path tempFile = Path.of("tempData.dat");
    IOInterface IO;
    TripContainer tc1;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @BeforeEach
    public void setupTripContainer() throws IOException {
        tempFile.toFile().createNewFile();
        IO = new FileIO();
        tc1 = new TripContainer();
        Trip t1 = new Trip("Telin");
        Trip t2 = new Trip("Lyngli");
        tc1.addTrip(t1);
        tc1.addTrip(t2);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @AfterAll
    static void cleanUp() {
        tempFile.toFile().delete();
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFileIOSaveAndRead() throws IOException {

        List<String> lines = Arrays.asList("1", "2", "3");
        IO.writeObjectToFile(lines, tempFile.toString());

        assertTrue(Files.exists(tempFile), "File should exist");
        assertLinesMatch(lines, (List<String>) IO.readObjectFromFile(tempFile.toString()));

        IO.writeObjectToFile(tc1, tempFile.toString());
        assertEquals(tc1.getClass(), IO.readObjectFromFile(tempFile.toString()).getClass());

        TripContainer loaded = (TripContainer) IO.readObjectFromFile(tempFile.toString());
        assertEquals(tc1.getTripNames(), loaded.getTripNames());


    }

    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
    @Test
    void testFileCreationByWrite() throws IOException {
        tempFile.toFile().delete();

        List<String> lines = Arrays.asList("1", "2", "3");
        IO.writeObjectToFile(lines, tempFile.toString());

        assertTrue(Files.exists(tempFile), "File should exist");
        assertLinesMatch(lines, (List<String>) IO.readObjectFromFile(tempFile.toString()));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    void testFileReadWithNoFile() {
        tempFile.toFile().delete();

        Exception exception = assertThrows(FileNotFoundException.class, () -> IO.readObjectFromFile(tempFile.toString()));
        assertEquals(FileNotFoundException.class, exception.getClass());
    }
}
