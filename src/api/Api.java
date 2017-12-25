package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;
import util.JsonReader;

import java.io.IOException;

import static util.StringChecker.IsNullOrWhiteSpace;

public abstract class Api extends JsonReader {

    private static final String ERROR_CITYNAME = "City name invalid";

    //All
    private boolean error = false;
    public boolean isError(){return error;}
    public void setError(boolean error){this.error = error;}

    //OWM

    private SimpleStringProperty tempProperty;
    private ObjectProperty<Image> weatherImageProperty;
    private SimpleStringProperty weatherDescriptionProperty;
    public ObjectProperty<Image> weatherImagePropertyProperty() { return weatherImageProperty; }
    public SimpleStringProperty tempPropertyProperty() { return tempProperty; }
    public SimpleStringProperty weatherDescriptionPropertyProperty() { return weatherDescriptionProperty; }

    private String name;

    void setTempProperty(SimpleStringProperty tempProperty) {
        this.tempProperty = tempProperty;
    }

    void setWeatherImageProperty(ObjectProperty<Image> weatherImageProperty) {
        this.weatherImageProperty = weatherImageProperty;
    }

    void setWeatherDescriptionProperty(SimpleStringProperty weatherDescriptionProperty) {
        this.weatherDescriptionProperty = weatherDescriptionProperty;
    }

    public void setName(String name) {
        this.name = name;
    }

    void setLng(String lng) {
        this.lng = lng;
    }

    void setLat(String lat) {
        this.lat = lat;
    }

    private String lng;
    private String lat;

    public String getName() { return name; }
    public String getLng(){return lng;}
    public String getLat(){return lat;}


    //Time
    private SimpleStringProperty timeProperty;
    public SimpleStringProperty timePropertyProperty() { return timeProperty; }
    public void setTimeProperty(SimpleStringProperty timeProperty) {
        this.timeProperty = timeProperty;
    }

    //Unsplash
    private SimpleStringProperty backgroundCityImageProperty;
    public SimpleStringProperty backgroundCityImagePropertyProperty() { return backgroundCityImageProperty; }
    public void setBackgroundCityImageProperty(SimpleStringProperty backgroundCityImageProperty) {
        this.backgroundCityImageProperty = backgroundCityImageProperty;
    }

    //owm
    Api(String name) throws IOException{
        if(IsNullOrWhiteSpace(name)){ throw new IOException(ERROR_CITYNAME); }
        this.name = name;

        try {
            setVariables(getJSONFile(buildURL())); }
        catch (Exception e){
            error = true;
        }
    }

    //time
    Api(String lng, String lat){
        setLat(lat);
        setLng(lng);

        try {
            setVariables(getJSONFile(buildURL())); }
        catch (Exception e){
            error = true;
        }
    }

    //unsplash
    Api(Api openWeatherMap){
        setName(openWeatherMap.getName());
    }

    Api(){

    }

    abstract String buildURL();
    abstract void setVariables(JSONObject jsonObject);


}