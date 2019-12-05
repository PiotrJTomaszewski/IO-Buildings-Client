package pl.put.poznan;

import java.io.IOException;
import java.util.ArrayList;


import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


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
    private void addFloor() throws IOException {  // TODO: Add checking for unique id
        FXMLLoader loader = showDialog("alterFloorDialog.fxml");
        Integer id = loader.<AlterFloorController>getController().getId();
        String name = loader.<AlterFloorController>getController().getName();
        if (id != null) {  // Add new floor only if user pressed confirm
            Floor floor = AppData.addFloor(id, name);
            if (floor != null) {
                floorsList.getItems().add(floor);
            }
        }
    }

    @FXML
    private void addRoom() throws IOException {  // TODO: Add checking for unique id
        Floor parentingFloor = (Floor) floorsList.getSelectionModel().getSelectedItem();
        if (parentingFloor == null) { // TODO: Add a proper message
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
            Room room = AppData.addRoom(id, name, area, cube, heating, light, parentingFloor);
            if (room != null) {
                roomsList.getItems().add(room);
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
    private void editBuilding() throws IOException{
        AlterBuildingController.setInitialData(AppData.getBuilding().getId(), AppData.getBuilding().getName());
        FXMLLoader loader = showDialog("alterBuildingDialog.fxml");
        Integer id = loader.<AlterBuildingController>getController().getId();
        String name = loader.<AlterBuildingController>getController().getName();
        if (id != null) {  // Edit building only if user pressed confirm
            AppData.setBuilding(id, name);
            buildingId.setText(id.toString());
            buildingName.setText(name);
        }
    }

    // TODO: Allow modification of locations

}
