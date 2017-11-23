package sample;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class Controller extends Parent {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField cityTextField;

    @FXML
    private Label cityName;

    @FXML
    private Label time;

    @FXML
    private Label temperature;

    @FXML
    private Label weather;

    @FXML
    private javafx.scene.image.ImageView weatherIcon;

    @FXML
    private ListView<City> listView;

    //private StringProperty temperature;



    protected ObservableList<City> cityList = FXCollections.observableArrayList();
    protected ListProperty<City> listProperty = new SimpleListProperty<>(cityList);

    @FXML
    private void handleButtonSearchAction(ActionEvent e){
        City c = new City(cityTextField.getText());
        addCityToListView(c);
        displayCity(c);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Platform.exit();
    }


    private void addCityToListView(City city){
        cityList.add(city);
    }

    public void displayCity(City city){


        //borderPane.setStyle("-fx-background-image: url(\"" + city.getUnsplash().getUrlCityImage() + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;");
        cityName.setText(city.getOpenWeatherMap().getName() + ", " + city.getOpenWeatherMap().getCountry());
        //time.setText(city.getTimeZoneDB().getTime());
        temperature.setText(city.getOpenWeatherMap().getTemp());
        weather.setText(city.getOpenWeatherMap().getWeatherDescription());
        //weatherIcon.setImage(new Image(city.getOpenWeatherMap().getWeatherImage()));


        //temperature.textProperty().bind();
    }


    public void initialize() {
        listView.itemsProperty().bind(listProperty);
  //      listView.getSelectionModel().getSelectedItem();
    }
}