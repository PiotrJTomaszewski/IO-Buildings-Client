package pl.put.poznan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class PrimaryController {

    @FXML
    private ListView<Location> buildingsList;

    @FXML
    private ListView<Location> floorsList;

    @FXML
    private ListView<Location> roomsList;

    @FXML
    private void initialize() {
        Building a = App.addBuilding(1, "asd");
        buildingsList.getItems().add(a);
        a = App.addBuilding(2, "es");
        buildingsList.getItems().add(a);

        System.out.println(App.addFloor(10, "zxc", 1));
        App.addFloor(11, "zaxc", 1);
        App.addFloor(12, "zaxc", 2);
        App.addFloor(13, "zcxc", 2);
        App.addFloor(14, "zxzc", 2);

        App.addRoom(100, "qwe", 1,10);
        App.addRoom(101, "exz", 1, 10);
        App.addRoom(102, "zxac",1, 11);
        App.addRoom(103, "zzxc",2, 12);
        App.addRoom(104, "zdxc",2, 13);


        buildingsList.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    int selectedBuildingId = buildingsList.getSelectionModel().getSelectedItem().getId();
                    ArrayList<Location> floors = App.getFloors(selectedBuildingId);
                    floorsList.getItems().clear();
                    floorsList.getItems().addAll(floors);
                    System.out.println(buildingsList.getSelectionModel().getSelectedItem());
                }
        );

//        floorsList.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//                    int selectedBuildingId = buildingsList.getSelectionModel().getSelectedItem().getId();
//                    int selectedFloorId = floorsList.getSelectionModel().getSelectedItem().getId();
//                    ArrayList<Location> rooms = App.getRooms(selectedBuildingId, selectedFloorId);
//                    roomsList.getItems().clear();
//                    roomsList.getItems().addAll(rooms);
//                    System.out.println(floorsList.getSelectionModel().getSelectedItem());
//                }
//        );

//        roomsList.getSelectionModel().selectedIndexProperty().addListener(
//                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
//                    System.out.println(roomsList.getSelectionModel().getSelectedItem());
//                }
//        );

    }

    public static class buildingsListListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue observableValue, Number oldValue, Number newValue) {

        }

    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void addBuilding() {
        Building building = App.addBuilding(123, "qwer");
        if (building != null) {
            buildingsList.getItems().add(building);
        }
    }

    @FXML
    private void addFloor() {
        Floor floor = App.addFloor(1234, "asd", 123);
        if (floor != null) {
            floorsList.getItems().add(floor);
        }
    }

//
//    buildingsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
//        @Override
//        public void changed(ObservableValue<? extends Person> observable,
//                Person oldValue, Person newValue) {
//
//            outputTextArea.appendText("ListView Selection Changed (newValue: " + newValue + ")\n");
//        }
//    });
}
