package pl.put.poznan.DataStructure;

public class AppData {
    private static Building building;

    public AppData() {
        building = new Building(1, null);
    }

//    // TODO: Add checking for unique id
//    public static Building addBuilding(int id, String name) {
//        Building building = new Building(id, name);
//        realEstate.addSubLocation(building);
//        building.setParentLocation(realEstate);
//        return building;
//    }

    public static void setBuilding(int id, String name) {
        building.setId(id);
        building.setName(name);
    }

    public static Building getBuilding() {
        return building;
    }

    public static Floor addFloor(int id, String name) {
        Floor floor = new Floor(id, name);
        building.addSubLocation(floor);
        floor.setParentLocation(building);
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
