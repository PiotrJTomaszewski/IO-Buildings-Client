package pl.put.poznan;

import java.io.IOException;
import javafx.fxml.FXML;

public class QueryController {

    @FXML
    private void switchToPrimary() throws IOException {  // TODO: Change name
        App.setRoot("dataInput");
    }
}