package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;

public class OpenWeatherMap extends Api{

    private static final String API_KEY_WEATHER = "d82b125f9ed47887afc80e5304bbf603";
    private static final String URL_BASE_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_BASE_WEATHER_IMAGE = "https://openweathermap.org/img/w/";

    private String name;
    private String lng;
    private String lat;
    private boolean error = false;
    private JSONObject jsonObject;

    private SimpleStringProperty tempProperty;
    private ObjectProperty<Image> weatherImageProperty;
    private SimpleStringProperty weatherDescriptionProperty;

    public ObjectProperty<Image> weatherImagePropertyProperty() { return weatherImageProperty; }
    public SimpleStringProperty tempPropertyProperty() { return tempProperty; }
    public SimpleStringProperty weatherDescriptionPropertyProperty() { return weatherDescriptionProperty; }

    public String getName() { return name; }
    public String getLng(){return lng;}
    public String getLat(){return lat;}
    public boolean isError(){return error;}

    public OpenWeatherMap(String name) {
        this.name = name;

        try {
            jsonObject = getJSONFile(buildURL()); }
        catch (Exception e){
            error = true;
            return;
        }
        setVariables();
    }

    private String buildURL(){
        return URL_BASE_WEATHER + "q=" + name + "&appid=" + API_KEY_WEATHER + "&units=metric";
    }

    private void setVariables(){
        this.name = jsonObject.get("name").toString();
        this.lng = jsonObject.optJSONObject("coord").get("lon").toString();
        this.lat = jsonObject.optJSONObject("coord").get("lat").toString();

        this.name = this.name + ", " + jsonObject.getJSONObject("sys").get("country").toString();
        this.tempProperty = new SimpleStringProperty(jsonObject.getJSONObject("main").get("temp").toString() + " C");
        this.weatherDescriptionProperty = new SimpleStringProperty(jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString());

        String weatherImage = URL_BASE_WEATHER_IMAGE + jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString() + ".png";
        this.weatherImageProperty = new SimpleObjectProperty<>(new Image(weatherImage));
    }
}