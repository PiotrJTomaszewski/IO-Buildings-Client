package pl.put.poznan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static AppData appData;

    @Override
    public void start(Stage stage) throws IOException {
        appData = new AppData();
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Building addBuilding(int id, String name) {
        Building building = new Building();
        building.setId(id);
        building.setName(name);
        if (appData.getSubLocationById(id) == null) {
            appData.addSubLocation(building);
            return building;
        } else {
            return null;
        }
    }

    public static Floor addFloor(int id, String name, int parentId) {
        Floor floor = new Floor();
        floor.setId(id);
        floor.setName(name);
        Building parent = (Building) appData.getSubLocationById(parentId);
        if (parent == null) {
            return null;
        }
        floor.setParentLocation(parent);
        if (parent.getSubLocationById(id) == null) {
            parent.addSubLocation(floor);
            return floor;
        } else {
            return null;
        }
    }

    public static Room addRoom(int id, String name, int parentBuildingId, int parentFloorId) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        Building parentBuilding = (Building) appData.getSubLocationById(parentBuildingId);
        Floor parentFloor = (Floor) parentBuilding.getSubLocationById(parentFloorId);

        if (parentFloor == null) {
            return null;
        }
        room.setParentLocation(parentFloor);
        if (parentFloor.getSubLocationById(id) == null) {
            parentFloor.addSubLocation(room);
            return room;
        } else {
            return null;
        }
    }

    public static AppData getAppData() {
        return appData;
    }

    public static ArrayList<Location> getFloors(int buildingId) {
        Building building = (Building) appData.getSubLocationById(buildingId);
        return building.getSubLocations();
    }

    public static ArrayList<Location> getRooms(int buildingId, int floorId) {
        Building building = (Building) appData.getSubLocationById(buildingId);
        Floor floor = (Floor) building.getSubLocationById(floorId);
        return floor.getSubLocations();
    }
}