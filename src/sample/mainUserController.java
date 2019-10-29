package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mainUserController {
    String id;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buy_tick;

    @FXML
    private Button list_buy;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        exit.setOnAction(event -> {
            exit.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 400, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Продажа билетов на авиарейсы");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });

        buy_tick.setOnAction(event -> {
            //exit.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("searchTickets.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 400, 300);
                searchTicketsController controller = fxmlLoader.getController();
                controller.setId(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            stage.setTitle("Продажа билетов на авиарейсы");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }

    public void setId(String id) {
        this.id = id;
    }
}
