package pl.put.poznan;

import java.util.ArrayList;

public abstract class SubLocationParent extends Location {
    //TODO: Add documentation
    private ArrayList<Location> subLocations;

    public SubLocationParent() {
        subLocations = new ArrayList<>();
    }

    public ArrayList<Location> getSubLocations() {
        return subLocations;
    }

    public Location getSubLocationById(int id) {
        for (Location location : subLocations) {
            if (location.getId() == id) {
                return location;
            }
        }
        return null;
    }

    public void addSubLocation(Location sublocation) {
        subLocations.add(sublocation);
    }

    public void removeSubLocation(Location sublocation) {
        subLocations.remove(sublocation);
    }
}
