package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import pl.put.poznan.DataStructure.Room;

public class AlterRoomController {
    @FXML
    private Spinner<Integer> roomIdInput;// TODO: Fix error if user entered letters

    @FXML
    private TextField roomNameInput;

    @FXML
    private Spinner<Integer> roomAreaInput; // TODO: Fix error if user entered letters

    @FXML
    private Spinner<Integer> roomCubeInput; // TODO: Fix error if user entered letters

    @FXML
    private Spinner<Double> roomHeatingInput; // TODO: Fix error if user entered letters

    @FXML
    private Spinner<Integer> roomLightInput; // TODO: Fix error if user entered letters

    @FXML
    private boolean accepted;

    @FXML
    private void initialize() {
        // Assign value factories to spinners
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000, 0); // params: step by, max min
        valueFactory.setValue(id);
        roomIdInput.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        valueFactory1.setValue(area);
        roomAreaInput.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        valueFactory2.setValue(cube);
        roomCubeInput.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        valueFactory3.setValue(light);
        roomLightInput.setValueFactory(valueFactory3);

        SpinnerValueFactory<Double> valueFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000, 1);
        valueFactory4.setValue(heating);
        roomHeatingInput.setValueFactory(valueFactory4);

        roomNameInput.setText(name);

    }

    @FXML
    private void confirm() {
        accepted = true;
        roomIdInput.getScene().getWindow().hide();
    }

    @FXML
    private void cancel() {
        accepted = false;
        roomIdInput.getScene().getWindow().hide();

    }

    public Integer getId() {
        return accepted ? roomIdInput.getValue() : null;
    }

    public String getName() {
        return accepted ? roomNameInput.getText() : null;
    }

    public int getArea() {
        return roomAreaInput.getValue();
    }

    public int getCube() {
        return roomCubeInput.getValue();
    }

    public double getHeating() {
        return roomHeatingInput.getValue();
    }

    public int getLight() {
        return roomLightInput.getValue();
    }

    private static String name;

    private static int id, area, cube, light;
    private static double heating;

    public static void setInitialId(int _id) {
        id = _id;
    }

    public static void setInitialData(int _id, String _name, int _area, int _cube, double _heating, int _light) {
        id = _id;
        name = _name;
        area = _area;
        cube = _cube;
        heating = _heating;
        light = _light;
    }
}
