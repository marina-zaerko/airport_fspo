package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class succsessfulController {
    private Button backto_;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backto_list_tickets;

    @FXML
    private Text succsess;

    @FXML
    void initialize() {
        backto_list_tickets.setOnAction(event -> {
            backto_.getScene().getWindow().hide();
            backto_list_tickets.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("avalibleTickets.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 871, 423);
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

    public void set_(Button id) {
        this.backto_ = id;
    }
}
