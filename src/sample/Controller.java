package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
    private void handleButtonSearchAction(ActionEvent e){
        City city = new City(cityTextField.getText());

        initialize(city);

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Platform.exit();
    }

    public void initialize(City city) {
        borderPane.setStyle("-fx-background-image: url(\"" + city.getUnsplash().getUrlCityImage() + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;");
        //city.getCityImage();
        cityName.setText(city.getOpenWeatherMap().getName() + ", " + city.getOpenWeatherMap().getCountry());
        time.setText(city.getTimeZoneDB().getTime());
        temperature.setText(city.getOpenWeatherMap().getTemp());
        weather.setText(city.getOpenWeatherMap().getWeatherDescription());
        weatherIcon.setImage(new Image(city.getOpenWeatherMap().getWeatherImage()));

    }

}
