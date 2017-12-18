package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.IOException;

import static util.JsonReader.readJsonFromUrl;

public class OpenWeatherMap {

    //private static final String ERROR_MSG = "There's been an error fetching data from OWM. Try again later.";
    private static final String API_KEY_WEATHER = "d82b125f9ed47887afc80e5304bbf603";
    private static final String URL_BASE_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_BASE_WEATHER_IMAGE = "https://openweathermap.org/img/w/";

    private String name;
    private String lng;
    private String lat;
    private boolean error = false;
    private String urlJsonWeather;
    private JSONObject jsonObject;

    private SimpleStringProperty tempProperty;
    private ObjectProperty<Image> weatherImageProperty;
    private SimpleStringProperty nameProperty;
    private SimpleStringProperty weatherDescriptionProperty;

    public ObjectProperty<Image> weatherImagePropertyProperty() { return weatherImageProperty; }
    public SimpleStringProperty tempPropertyProperty() { return tempProperty; }
    public SimpleStringProperty namePropertyProperty() { return nameProperty; }
    public SimpleStringProperty weatherDescriptionPropertyProperty() { return weatherDescriptionProperty; }

    public String getNameProperty() { return nameProperty.get(); }
    public String getLng(){return lng;}
    public String getLat(){return lat;}
    public boolean isError(){return error;}

    public OpenWeatherMap(String name) {
        this.name = name;
        buildURL();

        try { getJSONFile(); }
        catch (Exception e){ error = true; }

        setVariables();
    }

    private void buildURL(){ urlJsonWeather = URL_BASE_WEATHER + "q=" + name + "&appid=" + API_KEY_WEATHER + "&units=metric"; }

    private void getJSONFile() throws IOException { jsonObject = readJsonFromUrl(urlJsonWeather); }

    private void setVariables(){
        this.name = jsonObject.get("name").toString();
        this.lng = jsonObject.optJSONObject("coord").get("lon").toString();
        this.lat = jsonObject.optJSONObject("coord").get("lat").toString();

        this.nameProperty = new SimpleStringProperty(this.name + ", " + jsonObject.getJSONObject("sys").get("country").toString());
        this.tempProperty = new SimpleStringProperty(jsonObject.getJSONObject("main").get("temp").toString());
        this.weatherDescriptionProperty = new SimpleStringProperty(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString());

        String weatherImage = URL_BASE_WEATHER_IMAGE + jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString() + ".png";
        //this.weatherImageProperty = new SimpleObjectProperty<Image>(new Image(weatherImage));
        this.weatherImageProperty = new SimpleObjectProperty<>(new Image(weatherImage));
    }
}
