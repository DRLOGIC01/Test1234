package sample.test123;

import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class UserPageController {
    public void Gotoshop(MouseEvent mouseEvent) throws IOException {
        HelloApplication.gotoscreen("List page");
    }

    public void GotoHome(MouseEvent mouseEvent) throws IOException {
        HelloApplication.gotoscreen("hello-view");
    }

    public void GoToUser(MouseEvent mouseEvent) throws IOException {
        HelloApplication.gotoscreen("userpage");
    }
}
