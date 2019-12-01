package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class AddBuildingController {
    @FXML
    private Spinner<Integer> buildingIdInput;

    @FXML
    private TextField buildingNameInput;

    @FXML
    private boolean accepted;

    @FXML
    private void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10000, 0); // params: step by, max min
        buildingIdInput.setValueFactory(valueFactory);
    }

    @FXML
    private void confirm() {
        accepted = true;
        buildingIdInput.getScene().getWindow().hide();
    }

    @FXML
    private void cancel() {
        accepted = false;
        buildingIdInput.getScene().getWindow().hide();

    }

    public Integer getId() {
        return accepted ? buildingIdInput.getValue() : null;
    }

    public String getName() {
        return accepted ? buildingNameInput.getText() : null;
    }
}
