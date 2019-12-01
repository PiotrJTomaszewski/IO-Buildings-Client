package pl.put.poznan;

public abstract class Location {
    /**
     * Location's id
     */
    private int id;

    /**
     * Location's name (optional)
     */
    private String name;

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
}
