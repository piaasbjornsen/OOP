import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Contains all the information about a specific trip.
 */
public class Trip implements Serializable {

    private final String tripName;
    private String info;
    private int expenses;
    private final TripDetails tripDetails = new TripDetails();
    private final Collection<Participant> participantList = new ArrayList<>();

    /**
     * @param tripName name of the trip.
     * @throws IllegalArgumentException if tripName is empty.
     */
    public Trip(String tripName) {
        if (tripName.equals("")) {
            throw new IllegalArgumentException("Trip name cannot be empty");
        }

        // Regex checks for characters, numbers, and some basic 'special characters', as well as length between 1-31 and no whitespace at start and end.
        else if (!tripName.matches("^(?! )[A-Za-z0-9 ,'-]{1,31}(?<! )$")) {
            throw new IllegalArgumentException("Name must be less than 31 characters, can only contain letters, numbers and ['.-]");
        }
        this.tripName = tripName;
    }

    public String getTripName() {
        return tripName;
    }

    /**
     * @param info about the trip
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setExpenses(int expenses){
        this.expenses = expenses;
    }

    public int getExpenses() {
        return expenses;
    }

    /**
     * @return expenses divided on the number of participants.
     */
    public int getSharedExpenses() {
        try{
            return expenses / participantList.size();
        }catch (ArithmeticException ae){
            return expenses;
        }
    }

    /**
     * @param name of the participant to add to the list of participants.
     */
    public void addParticipant(String name) {
        participantList.add(new Participant(name));
    }

    /**
     * @param participant name of the participant to remove from the list of participants.
     */
    public void removeParticipant(Participant participant) {
        participantList.remove(participant);
    }

    public Collection<Participant> getParticipantList() {
        return participantList;
    }

    public TripDetails tripDetails() {
        return tripDetails;
    }
}