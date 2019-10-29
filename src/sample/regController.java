package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import sample.DatabaseHandler;

public class regController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button reg_on;

    @FXML
    private TextField login_info_reg;

    @FXML
    private Text enter_info_reg;

    @FXML
    private PasswordField passwd_info_reg;


    @FXML
    void initialize() {
        DatabaseHandler dbhandler = new DatabaseHandler();

        reg_on.setOnAction(event -> {
            try {
                Statement set;
                int trig = 1;
                String login;
                set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultset_user = set.executeQuery("select * from users");


                    if (!login_info_reg.getText().trim().equals("") && !passwd_info_reg.getText().trim().equals("")) {

                        if (!dbhandler.isCyrillic(login_info_reg.getText().trim()) && !dbhandler.isCyrillic(passwd_info_reg.getText().trim())) {
                            while (resultset_user.next()) {
                                login = resultset_user.getString(2);
                                if (login.equals(login_info_reg.getText())) {
                                    trig = 2;
                                }

                            }
                            if (trig == 2) {
                                enter_info_reg.setText("Такой логин уже существует, попробуйте ввести другой");
                            }
                            else{
                                dbhandler.signUpUser(login_info_reg.getText().trim(), passwd_info_reg.getText().trim());
                                reg_on.getScene().getWindow().hide();
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
                            }
                        } else { enter_info_reg.setText("В логине должна быть латиница!"); }
                    } else {
                        if (!login_info_reg.getText().trim().equals("") && passwd_info_reg.getText().trim().equals("")) {
                            enter_info_reg.setText("Введите пароль");
                        } else if (login_info_reg.getText().trim().equals("") && !passwd_info_reg.getText().trim().equals("")) {
                            enter_info_reg.setText("Введите логин");
                        } else {
                            enter_info_reg.setText("Введите значения");
                        }
                    }



            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
