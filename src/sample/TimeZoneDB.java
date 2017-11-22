package sample;

import org.json.JSONObject;

import java.io.IOException;

import static sample.JsonReader.readJsonFromUrl;

public class TimeZoneDB {

    private String urlBaseTimeZoneDB = "https://api.timezonedb.com/v2/get-time-zone?key=B1ZJLC3ORUD5&format=json&fields=formatted&by=position&lat=";
    private String time;
    private JSONObject jsonObject;
    private OpenWeatherMap openWeatherMap;

    public String getTime() { return time; }

    public TimeZoneDB(OpenWeatherMap openWeatherMap){
        this.openWeatherMap = openWeatherMap;
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
            //TODO: ouvrir fentre errer en gros jsonobject coorrespond pas
        }
    }

    private void buildURL(){
        //TODO: faire string avec l'objet openweathermap
        //prendre la lattitde de l'objet OpenWeatherMap donc mettre attribut lat et long dedans et le faire passer en param dans ce constructeur
        //et faire la requete final avec ces attributs
        String urlTime = new String(urlBaseTimeZoneDB + jsonObject.optJSONObject("coord").get("lat") + "&lng=" + jsonObject.optJSONObject("coord").get("lon"));
    }

    private void getJSONFile() throws IOException {
        jsonObject= readJsonFromUrl(urlBaseTimeZoneDB);
    }

    private void setVariables(){
        this.time = jsonObject.get("formatted").toString();
    }

}
