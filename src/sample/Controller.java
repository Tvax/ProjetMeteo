package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    protected ObservableList<City> cityList = FXCollections.observableArrayList();
    protected ListProperty<City> listProperty = new SimpleListProperty<>(cityList);

    private StringProperty cityNameProperty= new SimpleStringProperty();
    private StringProperty timeProperty = new SimpleStringProperty();
    private StringProperty temperatureProperty = new SimpleStringProperty();
    private StringProperty weatherProperty = new SimpleStringProperty();

    private ObjectProperty<Image> weatherIconProperty = new SimpleObjectProperty<>();
    private StringProperty borderPaneProperty  = new SimpleStringProperty();


    @FXML
    private void handleButtonSearchAction(ActionEvent e){
        cityList.add(new City(cityTextField.getText()));
        cityTextField.setText(null);
    }

    public void initialize() {
        listView.itemsProperty().bind(listProperty);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != null){
                cityNameProperty.unbind();
                timeProperty.unbind();
                temperatureProperty.unbind();
                weatherProperty.unbind();
                weatherIconProperty.unbind();
                borderPaneProperty.unbind();
            }
            if(newValue != null){
                cityNameProperty.bind(newValue.getOpenWeatherMap().namePropertyProperty());
                timeProperty.bind(newValue.getTimeZoneDB().timePropertyProperty());
                temperatureProperty.bind(newValue.getOpenWeatherMap().tempPropertyProperty());
                weatherProperty.bind(newValue.getOpenWeatherMap().weatherDescriptionPropertyProperty());
                weatherIconProperty.bind(newValue.getOpenWeatherMap().weatherImagePropertyProperty());
                borderPaneProperty.bind(newValue.getUnsplash().backgroundCityImagePropertyProperty());
            }
        });


        //Binding FXML (Label...) -> Property
        cityName.textProperty().bind(cityNameProperty);
        time.textProperty().bind(timeProperty);
        temperature.textProperty().bind(temperatureProperty);
        weather.textProperty().bind(weatherProperty);
        weatherIcon.imageProperty().bind(weatherIconProperty);
        borderPane.styleProperty().bind(borderPaneProperty);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Platform.exit();
    }
}