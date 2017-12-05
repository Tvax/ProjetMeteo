package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.io.IOException;

import static util.JsonReader.readJsonFromUrl;

public class TimeZoneDB {

    private static final String ERROR_MSG = "There's been an error fetching data from TZDB. Try again later.";
    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";

    private boolean error = false;
    private String urlBaseTimeZoneDB = new String("https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=");
    private String urlJsonTimeZoneDB;
    private JSONObject jsonObject;
    private String lng;
    private String lat;

    private SimpleStringProperty timeProperty;

    public SimpleStringProperty timePropertyProperty() { return timeProperty; }

    public boolean isError(){return error;}

    public TimeZoneDB(String lng, String lat){
        this.lng = lng;
        this.lat = lat;
        buildURL();

        try { getJSONFile(); }
        catch (Exception e){ error = true; }

        setVariables();
    }

    private void buildURL(){
       urlJsonTimeZoneDB = new String(urlBaseTimeZoneDB + this.lat + "&lng=" + this.lng);
    }

    private void getJSONFile() throws IOException { jsonObject = readJsonFromUrl(urlJsonTimeZoneDB); }

    private void setVariables(){
        String time = jsonObject.get("formatted").toString();
        time = time.substring(time.indexOf(' ') + 1);
        time = time.substring(0, time.length() - 3);
        this.timeProperty = new SimpleStringProperty(time);
    }
}
