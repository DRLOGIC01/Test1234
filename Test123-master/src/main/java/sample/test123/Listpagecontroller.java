package sample.test123;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Listpagecontroller implements Initializable {

    @FXML
    private BottomNavigationButton BtHome;

    @FXML
    private BottomNavigationButton BtUser;

    @FXML
    private ListView<String> ListItem01 = new ListView<String>();

    private ObservableList<String> testlijst = FXCollections.observableArrayList();

    @FXML
    private ListView<?> ListItem02;

    @FXML
    private Label Label02;

    @FXML
    private Label Label01;

    @FXML
    private Label Label03;

    @FXML
    private ListView<?> ListItem03;


    @FXML
    void GotoHome(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("hello-view");
    }

    @FXML
    void GotoUser(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("userpage");
    }

    @FXML
    void Gotoshop(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("List page");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ResultSet recipes = MyJDBC.communicateWithDatabase("select * from recept");
            while (recipes.next()) {
//                Recept recept = new Recept();
//                recept.id = recipes.getInt("Receptnr");
//                recept.naam = recipes.getString("Naam");
                testlijst.add(recipes.getString("Naam"));

            }

            ListItem01.setItems(testlijst);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
