import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class HyttappController {

    @FXML
    private Text welcomeMessage;

    @FXML
    private TextFlow welcomeInfo;

    @FXML
    private ListView<String> participantListView;

    @FXML
    private TextField participantInput;

    @FXML
    private Text depDate;

    @FXML
    private Text depTime;

    @FXML
    private Text depLoc;

    @FXML
    private Text retDate;

    @FXML
    private Text retTime;

    @FXML
    private TextArea welcomeInfoInput;

    @FXML
    private DatePicker depDatePicker;

    @FXML
    private TextField depLocInput;

    @FXML
    private TextField depTimeInput;

    @FXML
    private DatePicker retDatePicker;

    @FXML
    private TextField retTimeInput;

    @FXML
    private ListView<String> tripListView;

    @FXML
    private TextField tripNameInput;

    @FXML
    private Text errorDialogue;

    @FXML
    private Text appClock;

    @FXML
    private TextField expensesInput;

    @FXML
    private Text totalExpenses;

    @FXML
    private Text sharedExpenses;

    @FXML
    private Text timeToDeparture;

    @FXML
    private Text duration;

    private final IOInterface fileHandler = new FileIO();
    private final TripContainer trips = new TripContainer();
    private final String saveFilePath = "saveData.dat";
    private Timeline clock = new Timeline();

    /**
     * Initializes the application.
     * Loads saved state from file if it exists, else creates a new file.
     */
    @FXML
    void initialize() {
        // Load saved state
        try {
            TripContainer fromFile = (TripContainer) fileHandler.readObjectFromFile(saveFilePath);
            if (fromFile != null) {
                trips.setTrips(fromFile.getTrips());
                System.out.println("Existing data successfully loaded.");
            }
        } catch (Exception e) {
            System.out.println("Creating a new save-file.");
            saveState(); // To create new file.
            errorDialogue.setText(e.getMessage());
        }

        // General setup
        setupListViewListener();
        updateTripListView();
        selectFirstTrip();

        System.out.println("Setup complete");
        System.out.println("-----------------------------");

    }

    /**
     * Adds a new Trip to the tripListView and tripContainer.
     */
    @FXML
    private void addTrip() {

        String tripName = tripNameInput.getText();
        try {
            trips.addTrip(new Trip(tripName));
            tripNameInput.setText("");

            updateTripListView();
            saveState();

            // Selects the newly created trip
            tripListView.getSelectionModel().select(trips.getTrips().size() - 1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            errorDialogue.setText(e.getMessage());
        }

    }

    /**
     * Removes the selected Trip from the tripListView and tripContainer.
     */
    @FXML
    private void removeTrip() {

        // Saves the index of the selected trip.
        int currentSelection = tripListView.getSelectionModel().getSelectedIndex();

        trips.removeTrip(getSelectedTrip());

        // Selects the previous trip in the tripListView (or the first)
        if (currentSelection - 1 > 0) {
            tripListView.getSelectionModel().select(currentSelection - 1);
        } else {
            selectFirstTrip();
        }

        updateTripListView();
        saveState();
    }

    /**
     * Adds a new Participant to the selected Trip.
     */
    @FXML
    private void addParticipant() {

        String participantName = participantInput.getText();
        try {
            getSelectedTrip().addParticipant(participantName);
            participantInput.setText("");

            updateOverview();
            updateParticipantsListView();
            saveState();

        } catch (Exception e) {
            e.printStackTrace();
            errorDialogue.setText(e.getMessage());
        }

    }

    /**
     * Removes the selected Participant from the selected Trip.
     */
    @FXML
    private void removeParticipant() {

        getSelectedTrip().removeParticipant(getSelectedParticipant());
        updateParticipantsListView();
        saveState();
    }

    /**
     * Sets the Info for the selected Trip.
     */
    @FXML
    private void editWelcomeInfo() {

        getSelectedTrip().setInfo(welcomeInfoInput.getText());

        updateOverview();
        saveState();
    }

    /**
     * Sets the expenses for the selected Trip.
     */
    @FXML
    private void setExpenses() {
        getSelectedTrip().setExpenses(Integer.parseInt(expensesInput.getText()));

        updateOverview();
        saveState();
    }

    /**
     * Sets the times and dates for the selected Trip.
     */
    @FXML
    private void editTripDetails() {
        Trip trip = getSelectedTrip();

        // Set date for the selected Trip
        if (depDatePicker.getValue() != null) {
            trip.tripDetails().setDepartureDate(depDatePicker.getValue());
        }

        if (retDatePicker.getValue() != null) {
            trip.tripDetails().setReturnDate(retDatePicker.getValue());
        }

        // Set time for selected Trip
        // Regex tests for: 1-2 digits, followed by a ':', then exactly 2 digits. Anything before and after invalidates the regex.
        if (depTimeInput.getText().matches("^[\\d]{1,2}[:][\\d]{2}$")) {
            List<String> time = Arrays.asList(depTimeInput.getText().split(":"));
            int hours = Integer.parseInt(time.get(0));
            int minutes = Integer.parseInt(time.get(1));

            try {
                trip.tripDetails().setDepartureTime(LocalTime.of(hours, minutes));
            } catch (DateTimeException e) {
                e.printStackTrace();
                errorDialogue.setText(e.getMessage());
            }
        } else if (!depTimeInput.getText().equals("")) {
            System.out.println("Time must be in format of 'HH:MM'");
        } else {
            trip.tripDetails().setDepartureTime(null);

        }

        if (retTimeInput.getText().matches("^[\\d]{0,2}[:][\\d]{2}$")) {
            int hours = Integer.parseInt(Arrays.asList(retTimeInput.getText().split(":")).get(0));
            int minutes = Integer.parseInt(Arrays.asList(retTimeInput.getText().split(":")).get(1));

            trip.tripDetails().setReturnTime(LocalTime.of(hours, minutes));
        } else if (!retTimeInput.getText().equals("")) {
            System.out.println("Time must be in format of 'HH:MM'");
        } else {
            trip.tripDetails().setReturnTime(null);

        }

        // Set location for selected Trip
        trip.tripDetails().setDepartureLoc(depLocInput.getText());

        updateOverview();
        saveState();
    }

    /**
     * Updates the tripListView from the tripContainer.
     */
    private void updateTripListView() {

        // Clears the existing list and puts the new list.
        // ObservableList is used to be able to listen to changes to selection of Trips in the listView.
        tripListView.setItems(FXCollections.observableArrayList(FXCollections.observableList(trips.getTripNames())));
    }

    /**
     * Updates the participantListView based on the selected Trip.
     */
    private void updateParticipantsListView() {

        // Creates a new list containing just the names of the all the participants in the selected trip.
        List<String> newList = getSelectedTrip().getParticipantList().stream().map(Participant::getName).collect(Collectors.toList());
        participantListView.setItems(FXCollections.observableArrayList(FXCollections.observableList(newList)));
    }

    /**
     * @return the selected Participant from the selected Trip in the tripListView.
     */
    private Participant getSelectedParticipant() {

        // Streams through the list of Participants, and selects the one where the name equals the one selected in the listView.
        // NOTE: Duplicate names might pose an issue if Participants contains more information aside from 'name'
        //  as the wrong Participant might be removed. -> disallow duplicates or use unique identifier instead of 'name'
        return getSelectedTrip().getParticipantList().stream().filter(x -> x.getName().equals(participantListView.getSelectionModel().getSelectedItem())).findFirst().orElse(null);
    }

    /**
     * Saves the current state of the tripContainer to file.
     */
    private void saveState() {

        try {
            fileHandler.writeObjectToFile(trips, saveFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            errorDialogue.setText(e.getMessage());
        }
    }

    /**
     * @return The selected Trip in the tripListView
     */
    private Trip getSelectedTrip() {
        // Streams through the list of Trips, and selects the one where the name equals the one selected in the listView.
        Trip trip = trips.getTrips().stream()
                .filter(x -> x.getTripName().equals(tripListView.getSelectionModel().getSelectedItem()))
                .findFirst()
                .orElse(null);

        if (trip == null) {
            errorDialogue.setText("No trip selected");
        }
        return trip;
    }

    /**
     * Starts the clock and counter for time-until-departure.
     *
     * @param trip the trip containing the departure-time to count down to.
     */
    private void startCounters(Trip trip) {

        clock.stop();
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            timeToDeparture.setText(trip.tripDetails().durationToString(trip.tripDetails().getTimeUntil()));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
            appClock.setText(dateFormat.format(new Date()));
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * Selects the first trip in the listView
     */
    private void selectFirstTrip() {

        if (trips.getTrips().size() > 0) {
            tripListView.getSelectionModel().selectFirst();
        }
    }

    /**
     * Sets listeners for the tripListView
     */
    private void setupListViewListener() {

        tripListView.getSelectionModel()
                .selectedItemProperty()
                .addListener(tripViewListener);
    }

    /**
     * Updates the text fields in the 'overview' section of the app.
     */
    private void updateOverview() {
        Trip trip = getSelectedTrip();
        if (trip != null) {

            participantListView.setItems(FXCollections.observableList(trip.getParticipantList().stream().map(Participant::getName).collect(Collectors.toList())));
            welcomeInfo.getChildren().clear();
            welcomeInfo.getChildren().add(new Text(trip.getInfo()));

            //Objects.toString() calls on the first arguments .toString() method, if the first argument == null, it returns the second argument.
            depDate.setText(Objects.toString(trip.tripDetails().getDepartureDate(), ""));
            depTime.setText(Objects.toString(trip.tripDetails().getDepartureTime(), ""));
            depLoc.setText(Objects.toString(trip.tripDetails().getDepartureLoc(), ""));

            retDate.setText(Objects.toString(trip.tripDetails().getReturnDate(), ""));
            retTime.setText(Objects.toString(trip.tripDetails().getReturnTime(), ""));
            welcomeMessage.setText("Hei, velkommen til '" + trip.getTripName() + "'");
            errorDialogue.setText("");

            totalExpenses.setText(Objects.toString(trip.getExpenses(), ""));
            sharedExpenses.setText(Objects.toString(trip.getSharedExpenses(), ""));

            try {
                duration.setText(trip.tripDetails().durationToString(trip.tripDetails().getDuration()));
            } catch (NullPointerException e) {
                duration.setText("");
            }

            startCounters(trip);
        } else {
            welcomeMessage.setText("Hei, velkommen til hyttapp!");
            welcomeInfo.getChildren().clear();
            retDate.setText("");
            retTime.setText("");
            depDate.setText("");
            depTime.setText("");
            depLoc.setText("");
            duration.setText("");
            totalExpenses.setText("");
            sharedExpenses.setText("");


        }
    }

    /**
     * Updates the text fields in the 'edit info' section of the app (for quality of life reasons).
     */
    private void updateEditInfo() {
        Trip trip = getSelectedTrip();
        if (trip != null) {
            try {
                depDatePicker.setValue(trip.tripDetails().getDepartureDate());
            } catch (NullPointerException e) {
                depDatePicker.setValue(null);
            }
            depTimeInput.setText(Objects.toString(trip.tripDetails().getDepartureTime(), ""));
            depLocInput.setText(Objects.toString(trip.tripDetails().getDepartureLoc(), ""));
            try {
                retDatePicker.setValue(trip.tripDetails().getReturnDate());
            } catch (NullPointerException e) {
                retDatePicker.setValue(null);
            }
            retTimeInput.setText(Objects.toString(trip.tripDetails().getReturnTime(), ""));
            expensesInput.setText(Objects.toString(trip.getExpenses(), ""));
        }
    }

    /**
     * Listens to changes in the tripListView and executes when change is detected.
     */
    private final ChangeListener<String> tripViewListener = (observableValue, s, t1) -> {
        updateOverview();
        updateEditInfo();
    };
}


