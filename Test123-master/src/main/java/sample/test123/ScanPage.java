package sample.test123;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ScanPage {

    @FXML
    private Button Gotomanual;

    @FXML
    private Button BtBack;

    @FXML
    void GoBack(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("hello-view");
    }

    @FXML
    void GotoManual(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("");
    }

}
