package pl.put.poznan;

import java.util.ArrayList;

public class Room extends Location {
    private int area;
    private int cube;
    private double heating;
    private int light;

    public Room(int id, int area, int cube, double heating, int light) {
        super(id);
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
    }

    public Room(int id, String name, int area, int cube, double heating, int light) {
        super(id, name);
        this.area = area;
        this.cube = cube;
        this.heating = heating;
        this.light = light;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getCube() {
        return cube;
    }

    public void setCube(int cube) {
        this.cube = cube;
    }

    public double getHeating() {
        return heating;
    }

    public void setHeating(double heating) {
        this.heating = heating;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public ArrayList<String> getProperties() {
        ArrayList<String> array = new ArrayList<>();
        array.add("Area: " + area);
        array.add("Cube: " + cube);
        array.add("Heating: " + heating);
        array.add("Light: " + light);
        return array;
    }

    public void delete(){};
}
