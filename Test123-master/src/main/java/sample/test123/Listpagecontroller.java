package sample.test123;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Listpagecontroller {

    public TextArea ingredientInfoTextArea;
    public Button backButton;
    @FXML
    private ListView<String> ingredientList;
    @FXML
    private ListView<String> recipeList;
    @FXML
    private TextField ingredientInput;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button deleteButton;

    @FXML
    void goback(MouseEvent event) throws IOException {
        HelloApplication.gotoscreen("hello-view");
    }

    public void initialize() {
        addButton.setOnAction(event -> {
            String input = ingredientInput.getText();
            try {
                // Search for recipes that can be made with the ingredient
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alimento", "root", "firepad36");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT Naam FROM recept WHERE Ingrediënt LIKE '%" + input + "%'");
                recipeList.getItems().clear();
                if (!input.isEmpty()) {
                    if (!ingredientList.getItems().contains(input)) {
                        ingredientList.getItems().add(input);
                    } else {
                        int index = ingredientList.getItems().indexOf(input);
                        String stackedInput = ingredientList.getItems().get(index) + " x2";
                        ingredientList.getItems().remove(index);
                        ingredientList.getItems().add(stackedInput);
                    }
                }
                if (resultSet.next()) {
                    do {
                        recipeList.getItems().add(resultSet.getString("Naam"));
                    } while (resultSet.next());
                } else {
                    recipeList.getItems().add("No recipe found");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        clearButton.setOnAction(event -> {
            ingredientList.getItems().clear();
            recipeList.getItems().clear();
            ingredientInput.clear();
        });
        deleteButton.setOnAction(event -> {
            String selectedIngredient = ingredientList.getSelectionModel().getSelectedItem();

            if (selectedIngredient != null) {
                try {
                    // Delete the ingredient from the database
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/alimento", "root", "firepad36");
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("DELETE FROM recept WHERE Ingrediënt = '" + selectedIngredient + "'");

                    // Remove the ingredient from the ingredient list
                    ingredientList.getItems().remove(selectedIngredient);

                    // Search for recipes that can still be made with the remaining ingredients
                    ObservableList<String> remainingIngredients = ingredientList.getItems();
                    recipeList.getItems().clear();

                    if (!remainingIngredients.isEmpty()) {
                        String query = "SELECT Naam FROM recept WHERE Ingrediënt LIKE '%" + remainingIngredients.get(0) + "%'";
                        for (int i = 1; i < remainingIngredients.size(); i++) {
                            query += " OR Ingrediënt LIKE '%" + remainingIngredients.get(i) + "%'";
                        }
                        ResultSet resultSet = statement.executeQuery(query);
                        if (resultSet.next()) {
                            do {
                                recipeList.getItems().add(resultSet.getString("Naam"));
                            } while (resultSet.next());
                        } else {
                            recipeList.getItems().add("No recipe found");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}