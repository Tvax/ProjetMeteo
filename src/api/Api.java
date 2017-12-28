package api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import org.json.JSONObject;

import java.io.IOException;

import static util.JsonReader.getJSONFile;
import static util.JsonReader.getJSONFileFromUnsplash;
import static util.StringChecker.IsNullOrWhiteSpace;

/**
 * Creer un objet permettant d'acceder a une API
 */
public abstract class Api {

    private static final String ERROR_CITYNAME = "City name invalid";

    private String name;
    private String lng;
    private String lat;

    public String getName() { return name; }
    public String getLng(){ return lng; }
    public String getLat(){ return lat; }

    public void setName(String name) { this.name = name; }
    void setLng(String lng) { this.lng = lng; }
    void setLat(String lat) { this.lat = lat; }

    private boolean error = false;
    boolean isError(){ return error; }
    private void setError(){ this.error = true; }


    private SimpleStringProperty tempProperty;
    private ObjectProperty<Image> weatherImageProperty;
    private SimpleStringProperty weatherDescriptionProperty;
    public ObjectProperty<Image> weatherImagePropertyProperty() { return weatherImageProperty; }
    public SimpleStringProperty tempPropertyProperty() { return tempProperty; }
    public SimpleStringProperty weatherDescriptionPropertyProperty() { return weatherDescriptionProperty; }
    void setTempProperty(SimpleStringProperty tempProperty) { this.tempProperty = tempProperty; }
    void setWeatherImageProperty(ObjectProperty<Image> weatherImageProperty) { this.weatherImageProperty = weatherImageProperty; }
    void setWeatherDescriptionProperty(SimpleStringProperty weatherDescriptionProperty) { this.weatherDescriptionProperty = weatherDescriptionProperty; }

    private SimpleStringProperty timeProperty;
    public SimpleStringProperty timePropertyProperty() { return timeProperty; }
    void setTimeProperty(SimpleStringProperty timeProperty) { this.timeProperty = timeProperty; }

    private SimpleStringProperty backgroundCityImageProperty;
    public SimpleStringProperty backgroundCityImagePropertyProperty() { return backgroundCityImageProperty; }
    void setBackgroundCityImageProperty(SimpleStringProperty backgroundCityImageProperty) { this.backgroundCityImageProperty = backgroundCityImageProperty; }

    /**
     * Constructeur de OpenWeatherMap
     * @param name le nom de la ville saisie
     * @throws IOException si le nom de la ville est nulle ou est composee d'espaces
     */
    Api(String name) throws IOException{
        if(IsNullOrWhiteSpace(name)){ throw new IOException(ERROR_CITYNAME); }
        setName(name);
        try {
            setVariables(getJSONFile(buildURL())); }
        catch (Exception e){
            setError();
        }
    }

    /**
     * Constructeur de TimeZoneDB
     * @param lng la longitude de la ville saisie
     * @param lat la latitude de la ville saisie
     */
    Api(String lng, String lat){
        setLat(lat);
        setLng(lng);
        try {
            setVariables(getJSONFile(buildURL())); }
        catch (Exception e){
            setError();
        }
    }

    /**
     *Constructeur de Unsplash
     *@param openWeatherMap les informations venant de l'api d'OpenWeatherMap
     */
    Api(Api openWeatherMap){
        setName(openWeatherMap.getName());
        try {
            setVariables(getJSONFileFromUnsplash(buildURL())); }
        catch (Exception e){
            setError();
        }
    }

    /**
     *Construit l'URL pour acceder a l'API
     * @return l'URL pour acceder a l'API
     */
    abstract String buildURL();

    /**
     * Associe les attributs avec les objets JSON de jsonObject
     * @param jsonObject l'ensemble des objets JSON de l'API
     */
    abstract void setVariables(JSONObject jsonObject) throws Exception;
}