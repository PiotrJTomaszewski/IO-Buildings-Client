package pl.put.poznan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class DataInputController {

    @FXML
    private ListView<Location> buildingsList;

    @FXML
    private ListView<Location> floorsList;

    @FXML
    private ListView<Location> roomsList;

    @FXML
    private ListView<String> roomPropertiesList;

    private void refreshBuildingsList() {
        Building building = AppData.getBuilding();
        buildingsList.getItems().clear();
        buildingsList.getItems().add(building);
        refreshFloorsList();
    }

    private void refreshFloorsList() {
        Building selectedBuilding = (Building) buildingsList.getSelectionModel().getSelectedItem();
        floorsList.getItems().clear();
        if (selectedBuilding != null) {  // If a building is selected
            ArrayList<Location> floors = selectedBuilding.getSubLocations();
            floorsList.getItems().addAll(floors);
        }
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
        buildingsList.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    refreshFloorsList();
                }
        );

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

    }

    @FXML
    private void switchToSecondary() throws IOException { //TODO: Change name
        App.setRoot("querry");
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
    private void addBuilding() throws IOException {  // TODO: Add checking for unique id
        FXMLLoader loader = showDialog("addBuildingDialog.fxml");
        Integer id = loader.<AddBuildingController>getController().getId();
        String name = loader.<AddBuildingController>getController().getName();
        if (id != null) {  // Add new building only if user pressed confirm
            Building building = AppData.getBuilding();
            if (building != null) {
                buildingsList.getItems().add(building);
            }
        }
    }

    @FXML
    private void addFloor() throws IOException {  // TODO: Add checking for unique id
        Building parentBuilding = (Building) buildingsList.getSelectionModel().getSelectedItem();
        if (parentBuilding == null) {  //TODO: Add a proper message
            return;
        }
        FXMLLoader loader = showDialog("addFloorDialog.fxml");
        Integer id = loader.<AddFloorController>getController().getId();
        String name = loader.<AddFloorController>getController().getName();
        if (id != null) {  // Add new floor only if user pressed confirm
            Floor floor = AppData.addFloor(id, name, parentBuilding);
            if (floor != null) {
                floorsList.getItems().add(floor);
            }
        }
    }

    @FXML
    private void addRoom() throws IOException {  // TODO: Add checking for unique id
    Floor parentingFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        if(parentingFloor ==null)

    { // TODO: Add a proper message
        return;
    }

    FXMLLoader loader = showDialog("addRoomDialog.fxml");
    Integer id = loader.<AddRoomController>getController().getId();
    String name = loader.<AddRoomController>getController().getName();
    int area = loader.<AddRoomController>getController().getArea();
    int cube = loader.<AddRoomController>getController().getCube();
    float heating = (float) loader.<AddRoomController>getController().getHeating();
    int light = loader.<AddRoomController>getController().getLight();
        if(id !=null)

    {  // Add new room only if user pressed confirm
        Room room = AppData.addRoom(id, name, area, cube, heating, light, parentingFloor);
        if (room != null) {
            roomsList.getItems().add(room);
        }
    }

}

    @FXML
    private void deleteBuilding() {
        Building selectedBuilding = (Building) buildingsList.getSelectionModel().getSelectedItem();
        if (selectedBuilding != null) {
            AppData.deleteLocation(selectedBuilding);
            refreshBuildingsList();
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

    // TODO: Allow modification of locations

}
