package window;

import api.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import city.City;

import java.util.HashMap;

/**
 * Controlle la fenêtre MeteoMain.fxml
 */

public class Controller extends Parent {

    private final static String ALERT_TITLE = "Error Dialog";
    private final static String ALERT_DIALOG = "Error Fetching Data";

    @FXML
    private Button removeButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField cityTextField;

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

    private ObservableList<City> cityList = FXCollections.observableArrayList();
    private ListProperty<City> listProperty = new SimpleListProperty<>(cityList);

    private StringProperty timeProperty = new SimpleStringProperty();
    private StringProperty temperatureProperty = new SimpleStringProperty();
    private StringProperty weatherProperty = new SimpleStringProperty();

    private ObjectProperty<Image> weatherIconProperty = new SimpleObjectProperty<>();
    private StringProperty borderPaneProperty  = new SimpleStringProperty();

    /**
     * Ajoute dans listView la ville saisie
     */
    @FXML
    private void handleButtonSearchAction(){
        try {
            HashMap<Apis, Api> map = new HashMap<>();
            Api owm = new OpenWeatherMap(cityTextField.getText());
            Api time = new TimeZoneDB(owm.getLng(), owm.getLat());
            Api unsplash = new Unsplash(owm);

            map.put(Apis.OPENWEATHERMAP,owm);
            map.put(Apis.TIMEZONDEDB,time);
            map.put(Apis.UNSPLASH,unsplash);

            cityList.add(new City(map));
            cityTextField.setText(null);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ALERT_TITLE);
            alert.setHeaderText(ALERT_DIALOG);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Supprime la ville selectionnée dans listView
     */
    @FXML
    private void handleButtonRemove(){
        removeButton.setOnAction(event -> {
            final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
            if (selectedIdx != -1) {

                final int newSelectedIdx =
                        (selectedIdx == listView.getItems().size() - 1)
                                ? selectedIdx - 1
                                : selectedIdx;

                listView.getItems().remove(selectedIdx);
                listView.getSelectionModel().select(newSelectedIdx);
            }
        });
    }


    /**
     * Créer l'ensemble des bindings
     */
    public void initialize() {
        bindFXMlWithProperties();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != null){ unbindAll(); }
            if(newValue != null){ bindCity(newValue); }
            if(cityList.isEmpty()){ bindEmptyCityProperties(); }
        });
    }

    /**
     * Bind les propriétés avec les labels
     */
    private void bindFXMlWithProperties(){
        listView.itemsProperty().bind(listProperty);

        time.textProperty().bindBidirectional(timeProperty);
        temperature.textProperty().bind(temperatureProperty);
        weather.textProperty().bind(weatherProperty);
        weatherIcon.imageProperty().bind(weatherIconProperty);
        borderPane.styleProperty().bind(borderPaneProperty);
    }

    /**
     * Unbind toutes les propriétés
     */
    private void unbindAll(){
        timeProperty.unbind();
        temperatureProperty.unbind();
        weatherProperty.unbind();
        weatherIconProperty.unbind();
        borderPaneProperty.unbind();
    }

    /**
     * Bind une City avec les propriétés correspondantes
     * @param city la City a binder
     */
    private void bindCity(City city){
        timeProperty.bindBidirectional(city.getListApi().get(Apis.TIMEZONDEDB).timePropertyProperty());
        temperatureProperty.bind(city.getListApi().get(Apis.OPENWEATHERMAP).tempPropertyProperty());
        weatherProperty.bind(city.getListApi().get(Apis.OPENWEATHERMAP).weatherDescriptionPropertyProperty());
        weatherIconProperty.bind(city.getListApi().get(Apis.OPENWEATHERMAP).weatherImagePropertyProperty());
        borderPaneProperty.bind(city.getListApi().get(Apis.UNSPLASH).backgroundCityImagePropertyProperty());
    }

    /**
     * Bind une propriété vide sur toutes les propriétés
     */
    private void bindEmptyCityProperties(){
        SimpleStringProperty tmp = new SimpleStringProperty();
        ObjectProperty<? extends String> tmpImg = new SimpleObjectProperty<>();
        ObjectProperty<? extends Image> tmpBack = new SimpleObjectProperty<>();

        timeProperty.bindBidirectional(tmp);
        temperatureProperty.bind(tmp);
        weatherProperty.bind(tmp);
        weatherIconProperty.bind(tmpBack);
        borderPaneProperty.bind(tmpImg);
    }

    /**
     * Ferme l'application
     */
    @FXML
    private void handleButtonAction() {
        Platform.exit();
    }
}