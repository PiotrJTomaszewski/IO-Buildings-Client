package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class AddRoomController {
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
        roomIdInput.setValueFactory(valueFactory);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        roomAreaInput.setValueFactory(valueFactory1);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        roomCubeInput.setValueFactory(valueFactory2);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        roomLightInput.setValueFactory(valueFactory3);

        SpinnerValueFactory<Double> valueFactory4 = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 1000, 1);
        roomHeatingInput.setValueFactory(valueFactory4);
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
}
