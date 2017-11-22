package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import sun.plugin.javascript.navig.Anchor;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

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
        borderPane.setStyle("-fx-background-image: url(\"" + city.getCityImage() + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;");
        city.getCityImage();
        cityName.setText(city.getName() + ", " + city.getCountry());
        time.setText(city.getTime());
        temperature.setText(city.getTemp());
        weather.setText(city.getWeatherDetails());
        weatherIcon.setImage(new Image(city.getWeather()));

    }

}
