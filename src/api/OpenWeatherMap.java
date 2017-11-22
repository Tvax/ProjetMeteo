package api;

import org.json.JSONObject;

import java.io.IOException;

import static api.JsonReader.readJsonFromUrl;

public class OpenWeatherMap {

    private String name;
    private String country;
    private String temp;
    private String weatherImage;
    private String weatherDescription;
    private String lng;
    private String lat;

    private static final String API_KEY_WEATHER = "d82b125f9ed47887afc80e5304bbf603";
    private static final String URL_BASE_WEATHER = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String URL_BASE_WEATHER_IMAGE = "https://openweathermap.org/img/w/";
    private String urlJsonWeather;

    private JSONObject jsonObject;


    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getTemp() { return temp; }
    public String getWeatherImage() { return weatherImage; }
    public String getWeatherDescription() { return weatherDescription; }
    public String getLng(){return lng;}
    public String getLat(){return lat;}


    public OpenWeatherMap(String name){
        this.name = name;
        buildURL();

        try {
            getJSONFile();
        }
        catch (Exception e){
            //TODO: ouvrir fenetre erreur "check connection internet"
        }

        try {
            setVariables();
        }
        catch (Exception e){
            //TODO: ville non exsistante
        }
    }

    private void buildURL(){
         urlJsonWeather = new String (URL_BASE_WEATHER + "q=" + name + "&appid=" + API_KEY_WEATHER + "&units=metric");
    }

    private void getJSONFile() throws IOException {
        jsonObject = readJsonFromUrl(urlJsonWeather);
    }

    private void setVariables(){
        this.name = jsonObject.get("name").toString();
        this.country = jsonObject.getJSONObject("sys").get("country").toString();
        this.temp = jsonObject.getJSONObject("main").get("temp").toString();
        this.weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString();
        this.lng = jsonObject.optJSONObject("coord").get("lon").toString();
        this.lat = jsonObject.optJSONObject("coord").get("lat").toString();

        String imageID = jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString();
        this.weatherImage = URL_BASE_WEATHER_IMAGE + imageID + ".png";
    }
}
