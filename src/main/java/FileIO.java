import java.io.*;

/**
 * Handles input and output operations from file.
 */
public class FileIO implements IOInterface {

    /**
     * Writes the given object to file.
     */
    public void writeObjectToFile(Object o, String filepath) throws IOException {
        int count = 0;
        int maxTries = 2;
        while (true) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filepath);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(o);
                objectOut.close();
                System.out.println("Data was successfully written to " + filepath);
                break;
            } catch (FileNotFoundException e) {
                System.out.println(filepath + " not found, attempting to create a new file.");
                createFile(filepath);
                System.out.println("Retrying writing to file...");
                writeObjectToFile(o, filepath);
                if (++count == maxTries) {
                    throw new IOException("Could not create new file.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * |
     * Reads a object from file
     */
    public Object readObjectFromFile(String filepath) throws IOException {
        System.out.println("Attempting to read data from " + filepath);
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object o = objectIn.readObject();
            System.out.println("Existing data found in " + filepath);
            objectIn.close();
            return o;

        } catch (InvalidClassException | ClassNotFoundException ex) {
            System.out.println("Saved-data might be corrupt:");
            System.out.println(ex);
            System.out.println("Resolve the issue and try again.");
            System.exit(0);
            return null;
        }
    }

    /**
     * Creates a new file if one does not already exist.
     */
    private void createFile(String filepath) {
        try {
            File myFile = new File(filepath);
            if (myFile.createNewFile()) {
                System.out.println("File created: " + filepath);
            } else {
                System.out.println(filepath + " already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
