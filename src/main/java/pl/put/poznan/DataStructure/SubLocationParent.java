package pl.put.poznan.DataStructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public abstract class SubLocationParent extends Location {
    //TODO: Add documentation
    @JsonProperty("locations")
    private ArrayList<Location> subLocations;

    public SubLocationParent() {
        subLocations = new ArrayList<>();
    }

    public SubLocationParent(int id, String name) {
        super(id, name);
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

    public void delete() {  // Delete all children
        for (Location l : subLocations) {
            l.delete();
        }
    }
}
