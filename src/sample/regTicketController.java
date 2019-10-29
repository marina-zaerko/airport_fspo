package sample;

import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regTicketController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back_reg;

    @FXML
    private TextField passport_reg;

    @FXML
    private RadioButton business_reg;

    @FXML
    private RadioButton econom_reg;

    @FXML
    private Button its_okey_reg;

    @FXML
    private Text be_careful_reg;

    @FXML
    private Text passport_1;

    @FXML
    private Text first_name_1;

    @FXML
    private Text second_name_1;

    @FXML
    private TextField second_name_reg;

    @FXML
    private TextField first_name_reg1;

    @FXML
    private Text num_raice;

    @FXML
    private Text point_departure;

    @FXML
    private Text point_arrive;

    @FXML
    private Text time_departure;

    @FXML
    private Text time_arrive;

    @FXML
    private Text date;

    @FXML
    private Text price;

    public String id; // id of flight
    private String user_id; //user id
    private String clas;
    public String place;
    public int sum_b;
    public int sum_e;

    @FXML
    void initialize() {

        System.out.println(id);
        ToggleGroup group = new ToggleGroup();
        business_reg.setToggleGroup(group);
        econom_reg.setToggleGroup(group);
        //point_departure.setText("");

        business_reg.setOnAction(event -> {
            RadioButton selection = (RadioButton) group.getSelectedToggle();
            selection.getText();
            if (selection.getText().equals("Бизнес класс")) {
                clas = "Business";
            } else {
                clas = "Econom";
            }
            price.setText("Стоимость: " + sum_b);
            try {
                define_places(clas, id);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        econom_reg.setOnAction(event -> {
            RadioButton selection = (RadioButton) group.getSelectedToggle();
            selection.getText();
            if (selection.getText().equals("Бизнес класс")) {
                clas = "Business";
            } else {
                clas = "Econom";
            }
            price.setText("Стоимость: " + sum_e);
            try {
                define_places(clas, id);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        its_okey_reg.setOnAction(event -> {
            try {
                String name = second_name_reg.getText() + " " + first_name_reg1.getText();
                String pass = passport_reg.getText().trim();
                DatabaseHandler dbhandler = new DatabaseHandler();
                int id_tick = dbhandler.setIdPlusUser("passanger");
                //define_places(clas,id);
                if (!second_name_reg.getText().trim().equals("") && !first_name_reg1.getText().trim().equals("") && !pass.equals("") && clas != null) { //&& clas != null (place != null)
                    define_places(clas, id);
                    Pattern regexp1 = Pattern.compile("[a-zA-Z]+");
                    Matcher m1 = regexp1.matcher(name);
                    boolean b1 = m1.find();
                    if (!b1) {

                        Pattern regexp = Pattern.compile("[0-9]+");
                        Matcher m = regexp.matcher(name);
                        boolean b = m.find();
                        if (!b) {
                            m = regexp.matcher(pass);
                            b = m.matches();
                            if (b) {
                                if (pass.length() == 10) {
                                    if (define_places(clas, id) == 0) {
                                        PreparedStatement seet = dbhandler.getDbConnection().prepareStatement("INSERT INTO passanger (id_pass, passport_pass, fio_pass, place_num, type_place, flight_id_flight, user_id_user) VALUES (?, ?, ?, ?, ?, ?, ?)");
                                        seet.setInt(1, id_tick);
                                        seet.setString(2, pass);
                                        seet.setString(3, name);
                                        seet.setInt(4, Integer.parseInt(place));
                                        seet.setString(5, clas);
                                        seet.setInt(6, Integer.parseInt(id));
                                        seet.setInt(7, Integer.parseInt(user_id));
                                        seet.executeUpdate();
                                        seet.close();

                                        //reg_on.getScene().getWindow().hide();
                                        FXMLLoader fxmlLoader = new FXMLLoader();
                                        fxmlLoader.setLocation(getClass().getResource("succsessful.fxml"));
                                        /*
                                         * if "fx:controller" is not set in fxml
                                         * fxmlLoader.setController(NewWindowController);
                                         */
                                        Scene scene = null;
                                        try {
                                            scene = new Scene(fxmlLoader.load(), 328, 272);
                                            succsessfulController controller = new succsessfulController();
                                            controller.set_(its_okey_reg);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Stage stage = new Stage();
                                        stage.setTitle("Продажа билетов на авиарейсы");
                                        stage.setScene(scene);
                                        stage.setResizable(false);
                                        stage.show();
                                    } else {
                                        be_careful_reg.setText("Для данного полета забронированы все места такого класса, попробуйте в другой раз");
                                    }
                                } else {
                                    be_careful_reg.setText("Некорректная длина номера паспорта");
                                }
                            } else {
                                be_careful_reg.setText("В номере паспорта должны быть только числа");
                            }
                        } else {
                            be_careful_reg.setText("В имени не должно быть чисел");
                        }
                    } else {
                        be_careful_reg.setText("В имени должна быть только кириллица");
                    }
                } else {
                    be_careful_reg.setText("Заполните все поля");
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });


        back_reg.setOnAction(event -> {
            back_reg.getScene().getWindow().hide();
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

    @FXML
    void initData() {
        try {
            Statement set;
            DatabaseHandler dbhandler = new DatabaseHandler();
            set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String to_result = "select * from flight where id_flight = " + id;
            ResultSet resultset;
            resultset = set.executeQuery(to_result);
            resultset.first();
            num_raice.setText("Номер рейса: " + id);
            point_departure.setText("Пункт отправления: " + resultset.getString(3));
            point_arrive.setText("Пункт назначения: " + resultset.getString(4));
            time_departure.setText("Время отправления: " + resultset.getString(11));
            time_arrive.setText("Время прибытия: ");
            date.setText("Дата: " + resultset.getString(2));
            price.setText("Стоимость: " + "Сначала выберите класс!");
            sum_b = Integer.parseInt(resultset.getString(8));
            sum_e = Integer.parseInt(resultset.getString(7));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public int define_places(String clas, String flight) throws ClassNotFoundException, SQLException {
        //нужно проверить, какие билеты еще не заняты для данного рейса + "достать" количество мест для данного класса
        DatabaseHandler dbhandler = new DatabaseHandler();
        Statement set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String to_result = "SELECT * FROM passanger WHERE flight_id_flight = " + "'" + flight + "'" + " AND type_place = " + "'" + clas + "'";
        ResultSet resultset = set.executeQuery(to_result);

        Statement s = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String to_result2 = "SELECT * FROM plane WHERE bort_number = (SELECT plane_bort_number FROM flight WHERE id_flight = " + flight + " );";
        ResultSet resultset2 = s.executeQuery(to_result2);
        int num_places;
        resultset2.first();
        if (clas.equals("Business")) {
            num_places = Integer.parseInt(resultset2.getString(3));
        } else {
            num_places = Integer.parseInt(resultset2.getString(4));
        }
        int trig = 1;

            while (resultset.next()) {
                if (num_places ==  Integer.parseInt(resultset.getString(4))){ //if there no places for flight
                    return 1;
                }
                else {
                    int place_1 = Integer.parseInt(resultset.getString(4));
                    place_1 += 1;
                    System.out.println(place_1);
                    String plac = Integer.toString(place_1);
                    this.place = plac;
                    trig = 0;
                    System.out.println(place);
                }
            }

        if (trig==0){return 0;}// else if (trig==1){return 1;}
        return 2;
    }
    public void setId (String id){
        this.id = id;
    }

    public void setUser (String user_id){
        this.user_id = user_id;
    }

    /*
    public void setPlace (String place){
        this.place = place;
        System.out.println("1")
    }
     */
}
