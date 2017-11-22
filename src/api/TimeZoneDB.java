package api;

import org.json.JSONObject;

import java.io.IOException;

import static api.JsonReader.readJsonFromUrl;

public class TimeZoneDB {

    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";

    private String urlBaseTimeZoneDB = new String("https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=");
    private String urlJsonTimeZoneDB;
    private String time;
    private JSONObject jsonObject;
    private String lng;
    private String lat;

    public String getTime() { return time; }

    public TimeZoneDB(String lng, String lat){
        this.lng = lng;
        this.lat = lat;

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
       urlJsonTimeZoneDB = new String(urlBaseTimeZoneDB + this.lat + "&lng=" + this.lng);
    }

    private void getJSONFile() throws IOException {
        jsonObject = readJsonFromUrl(urlJsonTimeZoneDB);
    }

    private void setVariables(){
        this.time = jsonObject.get("formatted").toString();
    }

}
