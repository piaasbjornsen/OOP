import java.io.IOException;

public interface IOInterface {

    void writeObjectToFile(Object o, String filepath) throws IOException;

    Object readObjectFromFile(String filepath) throws IOException;
}
