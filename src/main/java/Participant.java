import java.io.Serializable;

/**
 * Stores a Participant
 */
public class Participant implements Serializable {

    private final String name;

    /**
     * Creates a participant.
     *
     * @param name the name of the participant to add.
     * @throws IllegalArgumentException if name is empty.
     */
    public Participant(String name) {
        if (name.equals("")) {
            throw new IllegalArgumentException("Participant name cannot be empty");
        }

        // Simple validation to give the user flexibility
        // Regex checks for characters, numbers, and some basic 'special characters', as well as length between 1-31 and no whitespace at start and end.
        else if (!name.matches("^(?! )[A-Za-z0-9 ,'-]{0,31}(?<! )$")) {
            throw new IllegalArgumentException("Name must be less than 31 characters, can only contain letters, numbers and ['.-]");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
