package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;
import util.JsonReader;

import java.io.IOException;

public class OpenWeatherMap extends Api{

    private static final String API_KEY_WEATHER = "d82b125f9ed47887afc80e5304bbf603";
    private static final String URL_BASE_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_BASE_WEATHER_IMAGE = "https://openweathermap.org/img/w/";

    public OpenWeatherMap(String name) throws IOException {
        super(name);
    }

    String buildURL(){
        return URL_BASE_WEATHER + "q=" + getName() + "&appid=" + API_KEY_WEATHER + "&units=metric";
    }

    void setVariables(JSONObject jsonObject){
        this.setName(jsonObject.get("name").toString());
        this.setLng(jsonObject.optJSONObject("coord").get("lon").toString());
        this.setLat(jsonObject.optJSONObject("coord").get("lat").toString());

        this.setName(this.getName() + ", " + jsonObject.getJSONObject("sys").get("country").toString());
        this.setTempProperty(new SimpleStringProperty(jsonObject.getJSONObject("main").get("temp").toString() + " C"));
        this.setWeatherDescriptionProperty( new SimpleStringProperty(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString()));

        String weatherImage = URL_BASE_WEATHER_IMAGE + jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString() + ".png";
        this.setWeatherImageProperty(new SimpleObjectProperty<>(new Image(weatherImage)));
    }
}