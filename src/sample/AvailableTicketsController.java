package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AvailableTicketsController {
    private ObservableList<Flight> ticketsData = FXCollections.observableArrayList();
    private Map<Button, String> ids = new HashMap<>();
    String id;
    Date date;
    String city_from;
    String city_to;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button reboot;

    @FXML
    private Button backList;

    @FXML
    private TableView<Flight> tabletickets;

    @FXML
    private TableColumn<Flight, String> date_tickets;

    @FXML
    private TableColumn<Flight, String> departure_tickets;

    @FXML
    private TableColumn<Flight, String> arrive_tickets;

    @FXML
    private TableColumn<Flight, Float> last_tickets;

    @FXML
    private TableColumn<Flight, Integer> econom_tickets;

    @FXML
    private TableColumn<Flight, Integer> business_tickets;

    @FXML
    private TableColumn<Flight, String> time_tickets;

    @FXML
    private TableColumn<Flight, String> bort_tickets;

    @FXML
    private TableColumn<Flight, Void> buy;

    @FXML
    void initialize() {
        //initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        date_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("date_"));
        departure_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("departure_"));
        arrive_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("arrive_"));
        last_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Float>("last_"));
        econom_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("econom_"));
        business_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("business_"));
        time_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("time_depar"));
        bort_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("bort_"));
       // addButtonToTable("Купить");
        // заполняем таблицу данными
        tabletickets.setItems(ticketsData);

        backList.setOnAction(event -> {
            backList.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("mainUser.fxml"));
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

        reboot.setOnAction(event -> {
            initData();

            // устанавливаем тип и значение которое должно хранится в колонке
            date_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("date_"));
            departure_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("departure_"));
            arrive_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("arrive_"));
            last_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Float>("last_"));
            econom_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("econom_"));
            business_tickets.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("business_"));
            time_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("time_depar"));
            bort_tickets.setCellValueFactory(new PropertyValueFactory<Flight, String>("bort_"));
            // заполняем таблицу данными
            tabletickets.setItems(ticketsData);

        });

    }

    public void initData() {

        DatabaseHandler dbhandler = new DatabaseHandler();
        try {
            String date, arrive, departure, time_depar_, bort;
            Float last;
            int econom, business, dist, speed;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //String data = df.format(this.date);
            //System.out.println(data);
            String up_case, low_cases;
            System.out.println(city_from);
            up_case = city_from.substring(0,1).toUpperCase();
            low_cases = city_from.substring(1,city_from.length()).toLowerCase();
            city_from = up_case + low_cases;
            up_case = city_to.substring(0,1).toUpperCase();
            low_cases = city_to.substring(1,city_to.length()).toLowerCase();
            city_to = up_case + low_cases;
            Statement set;
            Statement s;
            set = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s = dbhandler.getDbConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String to_result = "select * from flight";
            String to_result2 = "select * from public.flight where fk_air_dep in (select id_ from public.airports where fk_city = (select id_ from public.city where name = '" + city_from + "')) " +
                    "and fk_air_arr in (select id_ from public.airports where fk_city = (select id_ from public.city where name = '" + city_to + "')) and date_flight = '" + this.date + "'";
            ResultSet resultset;
            ResultSet resultset2;
            resultset = set.executeQuery(to_result);
            resultset2 = s.executeQuery(to_result2);


            while (resultset2.next()) {
                    date = resultset2.getString(2);
                    departure = resultset2.getString(9);
                    arrive = resultset2.getString(10);
                    //dist = Integer.parseInt(resultset.getString(5));
                    //to_result2 = "select * from plane where bort_number = " + resultset.getString(10);
                    //resultset2 = set.executeQuery(to_result2);
                    //resultset2.first();
                    //speed = Integer.parseInt(resultset2.getString(6));
                    //last = (float)dist/(float)speed;
                    econom = Integer.parseInt(resultset2.getString(4));
                    business = Integer.parseInt(resultset2.getString(5));
                    time_depar_ = resultset2.getString(8);
                    bort = resultset2.getString(7);
                    System.out.println(resultset2.getString(1));
                    ticketsData.add(new Flight(date, departure, arrive, (float) 3.0, econom, business, time_depar_, bort));
                    addButtonToTable(resultset2.getString(1));


            }
        } catch ( SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void addButtonToTable(String sss) {

        Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>> cellFactory = new Callback<TableColumn<Flight, Void>, TableCell<Flight, Void>>() {
            @Override
            public TableCell<Flight, Void> call(final TableColumn<Flight, Void> param) {
                return new TableCell<Flight, Void>() {

                    private final Button btn = new Button("Купить");


                    {
                        ids.put(btn, sss);
                        btn.setOnAction(event -> {
                            btn.getScene().getWindow().hide();
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("regTicket.fxml"));
                            /*
                             * if "fx:controller" is not set in fxml
                             * fxmlLoader.setController(NewWindowController);
                             */
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 400, 300);
                                regTicketController controller = fxmlLoader.getController();
                                controller.setId(ids.get(btn));
                                controller.setUser(id);
                                controller.initData();
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

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        buy.setCellFactory(cellFactory);

        //tabletickets.getColumns().add(buy);

    }

    public void setId(String id) {
        this.id = id;
    }
    public void setDate(Date date){this.date = date;}
    public void setCity_from(String city_from){this.city_from = city_from;}
    public void setCity_to(String city_to){this.city_to = city_to;}
}
