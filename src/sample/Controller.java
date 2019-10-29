package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Controller {
    String id_;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enter;

    @FXML
    private Button reg;

    @FXML
    private TextField login_info;

    @FXML
    private Text enter_info;

    @FXML
    private PasswordField passwd_info;

    @FXML
    void initialize() {
        enter.setOnAction(event -> {
            DatabaseHandler dbhandler = new DatabaseHandler();
            String loginText = login_info.getText().trim();
            String loginPassword = passwd_info.getText().trim();

            if ( !loginText.equals("") && !loginPassword.equals("")) {
                Statement set;
                try {
                    set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);

                    ResultSet resultset_user = set.executeQuery("select * from users");

                        while (resultset_user.next()) {
                            String login = resultset_user.getString(2);
                            String passwd = resultset_user.getString(3);
                            String id = resultset_user.getString(1);
                            id_ = id;
                            if (login.equals(loginText) && passwd.equals(loginPassword)){
                                enter.getScene().getWindow().hide();
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("mainUser.fxml"));
                                /*
                                 * if "fx:controller" is not set in fxml
                                 * fxmlLoader.setController(NewWindowController);
                                 */
                                Scene scene = null;
                                try {
                                    scene = new Scene(fxmlLoader.load(), 400, 300);
                                    mainUserController controller = fxmlLoader.getController();
                                    controller.setId(id_);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Stage stage = new Stage();
                                stage.setTitle("Продажа билетов на авиарейсы");
                                stage.setScene(scene);
                                stage.setResizable(false);
                                stage.show();
                            }
                            else {
                                enter_info.setText("Проверьте правильность заполнения логина и пароля");
                            }
                        }

                }
                catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                if (!login_info.getText().trim().equals("") && passwd_info.getText().trim().equals("")) {
                    enter_info.setText("Введите пароль");
                } else if (login_info.getText().trim().equals("") && !passwd_info.getText().trim().equals("")) {
                    enter_info.setText("Введите логин");
                } else {
                    enter_info.setText("Введите значения");
                }
            }
            //enter.getScene().getWindow();
        });

        reg.setOnAction(event -> {
            reg.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("registration.fxml"));
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
            stage.show();
        });

    }



}
