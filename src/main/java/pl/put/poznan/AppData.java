package pl.put.poznan;

import java.util.ArrayList;

public class AppData {
    private static RealEstate realEstate;

    public AppData() {
        realEstate = new RealEstate();
    }

    // TODO: Add checking for unique id
    public static Building addBuilding(int id, String name) {
        Building building = new Building(id, name);
        realEstate.addSubLocation(building);
        building.setParentLocation(realEstate);
        return building;
    }

    public static ArrayList<Location> getBuildings() {
        return realEstate.getSubLocations();
    }

    public static Floor addFloor(int id, String name, Building parentingBuilding) {
        Floor floor = new Floor(id, name);
        parentingBuilding.addSubLocation(floor);
        floor.setParentLocation(parentingBuilding);
        return floor;
    }

    public static Room addRoom(int id, String name, int area, int cube, double heating, int light, Floor parentingFloor) {
        Room room = new Room(id, name, area, cube, heating, light);
        parentingFloor.addSubLocation(room);
        room.setParentLocation(parentingFloor);
        return room;
    }

    public static void deleteLocation(Location location) {
        SubLocationParent parentingLocation = (SubLocationParent) location.getParentLocation();
        parentingLocation.removeSubLocation(location);
        location.delete();
    }
}
