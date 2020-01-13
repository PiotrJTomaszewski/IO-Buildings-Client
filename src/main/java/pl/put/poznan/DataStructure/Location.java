package pl.put.poznan.DataStructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Room.class, name = "room"),
        @JsonSubTypes.Type(value = Building.class, name = "building"),
        @JsonSubTypes.Type(value = Floor.class, name = "floor")
})
public abstract class Location {
    /**
     * Location's id
     */
    private int id;

    /**
     * Location's name (optional)
     */
    private String name;

    @JsonIgnore
    private Location parentLocation;

    public Location() {}

    /**
     * Create new location instance
     *
     * @param id   The location's id
     * @param name The location's name
     */
    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Create new location instance
     *
     * @param id The location's id
     */
    public Location(int id) {
        this.id = id;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    @JsonIgnore
    public int getParentLocationId() {
        return parentLocation.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if (name != null) {
            return id + ": " + name;
        } else {
            return Integer.toString(id);
        }
    }
    public abstract void delete();
}
