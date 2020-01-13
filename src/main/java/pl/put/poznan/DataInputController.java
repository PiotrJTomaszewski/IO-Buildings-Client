package pl.put.poznan;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import pl.put.poznan.DataStructure.*;

public class DataInputController {
    @FXML
    private Label buildingId;

    @FXML
    private Label buildingName;

    @FXML
    private ListView<Location> floorsList;

    @FXML
    private ListView<Location> roomsList;

    @FXML
    private ListView<String> roomPropertiesList;

    @FXML
    private Spinner locationIdSpinner;

    @FXML
    private ComboBox queryTypeBox;

    @FXML
    private TextField serverAddressBox;

    @FXML
    private Spinner serverPortBox;

    @FXML
    private TextArea resultBox;

    @FXML
    private Spinner energyThresholdBox;

    private boolean checkIdUniqness(int id) {
        Building building = AppData.getBuilding();
        if (id == building.getId())
            return false;
        ArrayList<Location> floors = building.getSubLocations();
        for (Location floor : floors) {
            if (id == floor.getId())
                return false;
            ArrayList<Location> roomsAtFloor = ((Floor) floor).getSubLocations();
            for (Location room : roomsAtFloor) {
                if (id == room.getId())
                    return false;
            }
        }
        return true;
    }

    private int getNextAvailableId() {
        int maxId = AppData.getBuilding().getId();
        ArrayList<Location> floors = AppData.getBuilding().getSubLocations();
        for (Location floor : floors) {
            if (floor.getId() > maxId)
                maxId = floor.getId();
            ArrayList<Location> roomsAtFloor = ((Floor) floor).getSubLocations();
            for (Location room : roomsAtFloor) {
                if (room.getId() > maxId)
                    maxId = room.getId();
            }
        }
        return maxId + 1;
    }

    private void refreshFloorsList() {
        floorsList.getItems().clear();
        ArrayList<Location> floors = AppData.getBuilding().getSubLocations();
        floorsList.getItems().addAll(floors);
        refreshRoomsList();
    }

    private void refreshRoomsList() {
        Floor selectedFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        roomsList.getItems().clear();
        if (selectedFloor != null) {  // If a floor is selected
            ArrayList<Location> rooms = selectedFloor.getSubLocations();
            roomsList.getItems().addAll(rooms);
        }
        refreshRoomPropertiesList();
    }

    private void refreshRoomPropertiesList() {
        Room selectedRoom = (Room) roomsList.getSelectionModel().getSelectedItem();
        roomPropertiesList.getItems().clear();
        if (selectedRoom != null) {  // If a room is selected
            ArrayList<String> roomProperties = selectedRoom.getProperties();
            roomPropertiesList.getItems().addAll(roomProperties);
        }
    }

    @FXML
    private void initialize() {
        Integer defaultBuildingId = AppData.getBuilding().getId();
        buildingId.setText(defaultBuildingId.toString());
        floorsList.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    refreshRoomsList();
                }
        );

        roomsList.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    refreshRoomPropertiesList();
                }
        );
        SpinnerValueFactory<Integer> locationIdFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 0); // params: step by, max min
        locationIdFactory.setValue(1);
        locationIdSpinner.setValueFactory(locationIdFactory);

        ObservableList<String> queryTypes =
                FXCollections.observableArrayList(
                        "Get area",
                        "Get cube",
                        "Get lighting power",
                        "Get heating power per volume",
                        "Get rooms that use too much energy"
                );
        queryTypeBox.setItems(queryTypes);

        SpinnerValueFactory<Integer> energyThresholdFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 0);
        energyThresholdFactory.setValue(10);
        energyThresholdBox.setValueFactory(energyThresholdFactory);

        serverAddressBox.setText("127.0.0.1");
        SpinnerValueFactory<Integer> portFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 65535, 1); // params: step by, max min
        portFactory.setValue(8080);
        serverPortBox.setValueFactory(portFactory);
    }

    private FXMLLoader showDialog(String dialogName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(dialogName));
        Scene newScene;
        newScene = new Scene(loader.load());
        Stage inputStage = new Stage();
        inputStage.initOwner(App.getStage());
        inputStage.setScene(newScene);
        inputStage.showAndWait();
        return loader;
    }

    @FXML
    private void addFloor() throws IOException {
        AlterFloorController.setInitialData(getNextAvailableId(), null);
        FXMLLoader loader = showDialog("alterFloorDialog.fxml");
        Integer id = loader.<AlterFloorController>getController().getId();
        String name = loader.<AlterFloorController>getController().getName();
        if (id != null) {  // Add new floor only if user pressed confirm
            if (checkIdUniqness(id)) { // If the id is unique
                Floor floor = AppData.addFloor(id, name);
                if (floor != null) {
                    floorsList.getItems().add(floor);
                }
            } else {
                WarningDialogController.setInitialData("Id has to be unique");
                showDialog("warningDialog.fxml");
            }
        }
    }

    @FXML
    private void addRoom() throws IOException {
        AlterRoomController.setInitialData(getNextAvailableId(), null, 1, 1, 1, 1);
        Floor parentingFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        if (parentingFloor == null) {
            WarningDialogController.setInitialData("No floor selected");
            showDialog("warningDialog.fxml");
            return;
        }
        FXMLLoader loader = showDialog("alterRoomDialog.fxml");
        Integer id = loader.<AlterRoomController>getController().getId();
        String name = loader.<AlterRoomController>getController().getName();
        int area = loader.<AlterRoomController>getController().getArea();
        int cube = loader.<AlterRoomController>getController().getCube();
        float heating = (float) loader.<AlterRoomController>getController().getHeating();
        int light = loader.<AlterRoomController>getController().getLight();
        if (id != null) {  // Add new room only if user pressed confirm
            if (checkIdUniqness(id)) {
                Room room = AppData.addRoom(id, name, area, cube, heating, light, parentingFloor);
                if (room != null) {
                    roomsList.getItems().add(room);
                }
            } else {
                WarningDialogController.setInitialData("Id has to be unique");
                showDialog("warningDialog.fxml");
            }
        }
    }

    @FXML
    private void deleteFloor() {
        Floor selectedFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        if (selectedFloor != null) {
            AppData.deleteLocation(selectedFloor);
            refreshFloorsList();
        }
    }

    @FXML
    private void deleteRoom() {
        Room selectedRoom = (Room) roomsList.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            AppData.deleteLocation(selectedRoom);
            refreshRoomsList();
        }
    }

    @FXML
    private void editBuilding() throws IOException {
        int oldId = AppData.getBuilding().getId();
        AlterBuildingController.setInitialData(oldId, AppData.getBuilding().getName());
        FXMLLoader loader = showDialog("alterBuildingDialog.fxml");
        Integer id = loader.<AlterBuildingController>getController().getId();
        String name = loader.<AlterBuildingController>getController().getName();
        if (id != null) {  // Edit building only if user pressed confirm
            if (checkIdUniqness(id) || id == oldId) {
                AppData.setBuilding(id, name);
                buildingId.setText(id.toString());
                buildingName.setText(name);
            } else {
                WarningDialogController.setInitialData("Id has to be unique");
                showDialog("warningDialog.fxml");
            }
        }
    }

    @FXML
    private void editFloor() throws IOException {
        Floor selectedFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        if (selectedFloor != null) {
            int oldId = selectedFloor.getId();
            AlterFloorController.setInitialData(oldId, selectedFloor.getName());
            FXMLLoader loader = showDialog("alterFloorDialog.fxml");
            Integer id = loader.<AlterFloorController>getController().getId();
            String name = loader.<AlterFloorController>getController().getName();
            if (id != null) {  // Edit floor only if user pressed confirm
                if (checkIdUniqness(id) || id == oldId) {
                    selectedFloor.setId(id);
                    selectedFloor.setName(name);
                    floorsList.getItems().remove(selectedFloor);
                    floorsList.getItems().add(selectedFloor);
                } else {
                    WarningDialogController.setInitialData("Id has to be unique");
                    showDialog("warningDialog.fxml");
                }
            }
        } else {
            WarningDialogController.setInitialData("No floor selected");
            showDialog("warningDialog.fxml");
        }
    }

    @FXML
    private void editRoom() throws IOException {
        Room selectedRoom = (Room) roomsList.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            int oldId = selectedRoom.getId();
            AlterRoomController.setInitialData(
                    oldId, selectedRoom.getName(),
                    selectedRoom.getArea(), selectedRoom.getCube(),
                    selectedRoom.getHeating(), selectedRoom.getLight());
            FXMLLoader loader = showDialog("alterRoomDialog.fxml");
            Integer id = loader.<AlterRoomController>getController().getId();
            String name = loader.<AlterRoomController>getController().getName();
            int area = loader.<AlterRoomController>getController().getArea();
            int cube = loader.<AlterRoomController>getController().getCube();
            float heating = (float) loader.<AlterRoomController>getController().getHeating();
            int light = loader.<AlterRoomController>getController().getLight();
            if (id != null) {  // Edit room only if user pressed confirm
                if (checkIdUniqness(id) || id == oldId) {
                    selectedRoom.setId(id);
                    selectedRoom.setName(name);
                    selectedRoom.setArea(area);
                    selectedRoom.setCube(cube);
                    selectedRoom.setHeating(heating);
                    selectedRoom.setLight(light);
                    roomsList.getItems().remove(selectedRoom);
                    roomsList.getItems().add(selectedRoom);
                }
            } else {
                WarningDialogController.setInitialData("Id has to be unique");
                showDialog("warningDialog.fxml");
            }
        } else {
            WarningDialogController.setInitialData("No room selected");
            showDialog("warningDialog.fxml");
        }
    }

    @FXML
    private void sendQuery() {
        String serverAddress = serverAddressBox.getText();
        int serverPort = (int) serverPortBox.getValue();
        int locationId = (int) locationIdSpinner.getValue();
        int selectedQueryType = queryTypeBox.getSelectionModel().getSelectedIndex();
        int energyThreshold = (int) energyThresholdBox.getValue();
        // All of the locations in the building
        Building building = AppData.getBuilding();
        ArrayList<Location> floors = building.getSubLocations();
        ArrayList<Location> rooms = new ArrayList<>();
        for (Location floor : floors) {
            rooms.addAll(((Floor) floor).getSubLocations());
        }
        for (Location room : rooms) {
            System.out.println(room.getName());
        }

        String result = null;
        Connection connection = new Connection(serverAddress, serverPort);
        switch (selectedQueryType) {
            case 0:
                result = Integer.toString(connection.getArea(building, locationId));
                break; // Get area
            case 1:
                break; // Get cube
            case 2:
                break; // Get heating power per volume
            case 3:
                break; // Get rooms that use too much energy
            default:
                break;
        }
        resultBox.setText(result);
    }
}
