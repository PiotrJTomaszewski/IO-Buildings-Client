package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class AddFloorController {
    @FXML
    private Spinner<Integer> floorIdInput;

    @FXML
    private TextField floorNameInput;

    @FXML
    private boolean accepted;

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000, 0); // params: step by, max min
        floorIdInput.setValueFactory(valueFactory);
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
}
