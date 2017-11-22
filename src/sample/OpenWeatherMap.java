package sample;

import com.sun.xml.internal.bind.v2.TODO;
import org.json.JSONObject;

import java.io.IOException;

import static sample.JsonReader.readJsonFromUrl;

public class OpenWeatherMap {

    private String name;
    private String country;
    private String temp;
    private String weatherImage;
    private String weatherDescription;

    private String apiKeyWeather = "d82b125f9ed47887afc80e5304bbf603";
    private String urlBaseWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String urlBaseweatherImage = "https://openweathermap.org/img/w/";
    private String urlJsonWeather = new String (urlBaseWeather + "q=" + name + "&appid=" + apiKeyWeather + "&units=metric");

    private JSONObject jsonObject;


    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getTemp() { return temp; }
    public String getWeatherImage() { return weatherImage; }
    public String getWeatherDescription() { return weatherDescription; }


    public OpenWeatherMap(String name){
        this.name = name;
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
            //TODO: ouvrir fentre errer en gros jsonobject coorrespond pas
        }
    }

    private void getJSONFile() throws IOException {
        jsonObject = readJsonFromUrl(urlJsonWeather);
    }

    private void setVariables(){
        this.name = jsonObject.get("name").toString();
        this.country = jsonObject.getJSONObject("sys").get("country").toString();
        this.temp = jsonObject.getJSONObject("main").get("temp").toString();
        this.weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).get("description").toString();

        String imageID = jsonObject.getJSONArray("weather").getJSONObject(0).get("icon").toString();
        this.weatherImage = urlBaseweatherImage + imageID + ".png";
    }
}
