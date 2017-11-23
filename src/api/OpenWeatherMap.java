package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.IOException;

import static api.JsonReader.readJsonFromUrl;

public class OpenWeatherMap {

    private String name;
    private String lng;
    private String lat;

    private static final String API_KEY_WEATHER = "d82b125f9ed47887afc80e5304bbf603";
    private static final String URL_BASE_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_BASE_WEATHER_IMAGE = "https://openweathermap.org/img/w/";
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


    public OpenWeatherMap(String name) throws Exception {
        this.name = name;
        buildURL();

        try {
            getJSONFile();
        }
        catch (Exception e){
            throw new Exception("Error with ur internet connection");
        }

        setVariables();

    }

    private void buildURL(){ urlJsonWeather = new String (URL_BASE_WEATHER + "q=" + name + "&appid=" + API_KEY_WEATHER + "&units=metric"); }

    private void getJSONFile() throws IOException { jsonObject = readJsonFromUrl(urlJsonWeather); }

    private void setVariables(){
        this.name = jsonObject.get("name").toString();
        String country = jsonObject.getJSONObject("sys").get("country").toString();
        String temp = jsonObject.getJSONObject("main").get("temp").toString();
        String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString();
        this.lng = jsonObject.optJSONObject("coord").get("lon").toString();
        this.lat = jsonObject.optJSONObject("coord").get("lat").toString();

        String imageID = jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString();
        String weatherImage = URL_BASE_WEATHER_IMAGE + imageID + ".png";

        this.nameProperty = new SimpleStringProperty(this.name + ", " + country);
        this.tempProperty = new SimpleStringProperty(temp);
        this.weatherDescriptionProperty = new SimpleStringProperty(weatherDescription);
        this.weatherImageProperty = new SimpleObjectProperty<Image>(new Image(weatherImage));
        }
    }
