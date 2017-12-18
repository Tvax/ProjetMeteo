package window;

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

    @FXML
    private void handleButtonSearchAction(){
        try {
            cityList.add(new City(cityTextField.getText()));
            cityTextField.setText(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ALERT_TITLE);
            alert.setHeaderText(ALERT_DIALOG);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

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

    /*
    @FXML
    private void handleButtonRemove(ActionEvent e){
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
                if (selectedIdx != -1) {
                    City itemToRemove = listView.getSelectionModel().getSelectedItem();

                    final int newSelectedIdx =
                            (selectedIdx == listView.getItems().size() - 1)
                                    ? selectedIdx - 1
                                    : selectedIdx;

                    listView.getItems().remove(selectedIdx);
                    listView.getSelectionModel().select(newSelectedIdx);
                }
            }
        });
    }
    */

    public void initialize() {
        bindFXMlWithProperties();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != null){ unbindAll(); }
            if(newValue != null){ bindCity(newValue); }
            if(cityList.isEmpty()){ bindEmptyCityProperties(); }
        });
    }

    private void bindFXMlWithProperties(){
        //Binding FXML (Label...) -> Property
        listView.itemsProperty().bind(listProperty);

        time.textProperty().bindBidirectional(timeProperty);
        temperature.textProperty().bind(temperatureProperty);
        weather.textProperty().bind(weatherProperty);
        weatherIcon.imageProperty().bind(weatherIconProperty);
        borderPane.styleProperty().bind(borderPaneProperty);
    }

    private void unbindAll(){
        timeProperty.unbind();
        temperatureProperty.unbind();
        weatherProperty.unbind();
        weatherIconProperty.unbind();
        borderPaneProperty.unbind();
    }

    private void bindCity(City city){
        timeProperty.bindBidirectional(city.getTimeZoneDB().timePropertyProperty());
        temperatureProperty.bind(city.getOpenWeatherMap().tempPropertyProperty());
        weatherProperty.bind(city.getOpenWeatherMap().weatherDescriptionPropertyProperty());
        weatherIconProperty.bind(city.getOpenWeatherMap().weatherImagePropertyProperty());
        borderPaneProperty.bind(city.getUnsplash().backgroundCityImagePropertyProperty());
    }

    private void bindEmptyCityProperties(){
        SimpleStringProperty tmp = new SimpleStringProperty();
        ObjectProperty tmpImg = new SimpleObjectProperty();
        timeProperty.bindBidirectional(tmp);
        temperatureProperty.bind(tmp);
        weatherProperty.bind(tmp);
        weatherIconProperty.bind(tmpImg);
        borderPaneProperty.bind(tmpImg);
    }

    @FXML
    private void handleButtonAction() {
        Platform.exit();
    }
}