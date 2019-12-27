package pl.put.poznan;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WarningDialogController {
    @FXML
    private Text warningText;

    @FXML
    private boolean accepted;

    private static String warning;

    @FXML
    private void close() {
        warningText.getScene().getWindow().hide();
    }

    @FXML
    private void initialize() {
        warningText.setText(warning);
    }

    public static void setInitialData(String _warning) {
        warning = _warning;
    }
}
