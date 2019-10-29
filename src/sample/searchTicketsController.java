package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class searchTicketsController {
    String id;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button search;

    @FXML
    private Button close;

    @FXML
    private Text depar;

    @FXML
    private TextField dep_field;

    @FXML
    private Text arr;

    @FXML
    private TextField arr_field;

    @FXML
    private Text date;

    @FXML
    private TextField date_field;

    @FXML
    private Text info;

    @FXML
    void initialize() {
        search.setOnAction(event -> {
            try {
            String departure = dep_field.getText().trim();
            String arrive = arr_field.getText().trim();
            String data = date_field.getText().trim();
                SimpleDateFormat format_in = new SimpleDateFormat("dd.MM.yyyy");
            Date date_ = new Date();
            Calendar instance = Calendar.getInstance();
            instance.setTime(date_); //устанавливаем сегодняшнюю дату
                Date date1 = format_in.parse(data);
                System.out.println(date1);

                Calendar instance1 = Calendar.getInstance();
                instance1.setTime(date_); //устанавливаем дату, с которой будет производить операции
                instance1.add(Calendar.DAY_OF_MONTH, 1);// прибавляем 3 дня к установленной дате
                Date new1 = instance1.getTime();//получили дату днем вперед

            DatabaseHandler dbhandler = new DatabaseHandler();
                Statement s; //подготавливаем для получения информации о полетах
                s = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                String result = "select * from flight";
                ResultSet results;
                results = s.executeQuery(result);

            Statement set; //подготавливаем для получения информации о городах
            set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String to_result = "select * from city";
            ResultSet resultset;
            resultset = set.executeQuery(to_result);

            boolean trig1 = false; //зарезервирован для того, что бы определить, существует ли такой город
                boolean trig2 = false; //зарезервирован для того, что бы определить, существует ли такой город назначения
                resultset.first();
            while (resultset.next()){
                if(resultset.getString(2).equalsIgnoreCase(departure)){
                   trig1 = true; //такой город отправления существует
                }
                if(resultset.getString(2).equalsIgnoreCase(arrive)){
                    trig2 = true; //такой город назначения существует
                }
            }

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String data_set; //создаем для того, что бы сравнить с существующими полетами
                Date date_in; //дата введенная
                //date1 дата введенная

            boolean trig_date = false;
            while (results.next()){
                data_set = results.getString(2);
                date_in = format.parse(data_set);
                if(date_in.compareTo(date1) == 0){
                    trig_date = true; //билеты на такую дату существуют
                }
            }

            if (!departure.equals("") || !arrive.equals("") || !data.equals("")){
                Pattern regexp1 = Pattern.compile("[a-zA-Z]+");
                Matcher m1 = regexp1.matcher(departure);
                Matcher m2 = regexp1.matcher(arrive);
                boolean b1 = m1.find();
                boolean b2 = m2.find();
                if (!b1 && !b2) {
                    if (trig1) {
                        if (trig2) {
                            System.out.println(1);
                            if (date1.compareTo(new1) >= 0) { //сравниваем, не ввел ди пользователь слишком раннюю дату
                                if (trig_date) {
                                    System.out.println(2);
                                    //search.getScene().getWindow().hide();
                                    FXMLLoader fxmlLoader = new FXMLLoader();
                                    fxmlLoader.setLocation(getClass().getResource("avalibleTickets.fxml"));
                                    /*
                                     * if "fx:controller" is not set in fxml
                                     * fxmlLoader.setController(NewWindowController);
                                     */
                                    Scene scene = null;
                                    try {
                                        scene = new Scene(fxmlLoader.load(), 871, 423);
                                        AvailableTicketsController controller = fxmlLoader.getController();
                                        controller.setId(id);
                                        controller.setDate(date1);
                                        controller.setCity_from(departure);
                                        controller.setCity_to(arrive);
                                        controller.initData();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Stage stage = new Stage();
                                    stage.setTitle("Продажа билетов на авиарейсы");
                                    stage.setScene(scene);
                                    stage.setResizable(false);
                                    stage.show();
                                }
                                else{
                                    info.setText("На такую дату билетов нет");
                                }
                            }
                            else{
                                info.setText("Билеты на такую дату недоступны");
                            }
                        }
                        else{
                            info.setText("Такого пункта назначения нет");
                        }
                    }
                    else{
                        info.setText("Такого пункта отправления нет");
                    }
                }
                else{
                    info.setText("В названии мест отравления и назначения должны быть русские буквы");
                }
            }
            else{
                info.setText("Поля не должны быть пустыми");
            }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    public void setId(String id) {
        this.id = id;
    }
}
