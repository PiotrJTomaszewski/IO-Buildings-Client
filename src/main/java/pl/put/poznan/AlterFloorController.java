package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class AlterFloorController {
    @FXML
    private Spinner<Integer> floorIdInput; // TODO: Fix error if user entered letters

    @FXML
    private TextField floorNameInput;

    @FXML
    private boolean accepted;

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000, 0); // params: step by, max min
        valueFactory.setValue(id);
        floorIdInput.setValueFactory(valueFactory);
        floorNameInput.setText(name);
    }

    @FXML
    private void confirm() {
        accepted = true;
        floorIdInput.getScene().getWindow().hide();
    }

    @FXML
    private void cancel() {
        accepted = false;
        floorIdInput.getScene().getWindow().hide();

    }

    public Integer getId() {
        return accepted ? floorIdInput.getValue() : null;
    }

    public String getName() {
        return accepted ? floorNameInput.getText() : null;
    }

    private static int id;

    private static String name;

    public static void setInitialId(int _id) {
        id = _id;
    }

    public static void setInitialData(int _id, String _name) {
        id = _id;
        name = _name;
    }
}
