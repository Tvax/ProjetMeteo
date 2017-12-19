package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.io.IOException;

import static util.JsonReader.readJsonFromUrl;

public class TimeZoneDB {

    //private static final String ERROR_MSG = "There's been an error fetching data from TZDB. Try again later.";
    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";
    private static final String URL_BASE = "https://api.timezonedb.com/v2/get-time-zone?key=";
    private static final int DELETE_SECONDS = 3;
    private static final int DELETE_DATE = 1;
    private static final int STRING_BEG_INDEX = 0;

    private boolean error = false;
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
        String urlBaseTimeZoneDB = URL_BASE + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=";
        urlJsonTimeZoneDB = urlBaseTimeZoneDB + this.lat + "&lng=" + this.lng;
    }

    private void getJSONFile() throws IOException { jsonObject = readJsonFromUrl(urlJsonTimeZoneDB); }

    private void setVariables(){
        String time = jsonObject.get("formatted").toString();
        time = time.substring(time.indexOf(' ') + DELETE_DATE);
        time = time.substring(STRING_BEG_INDEX, time.length() - DELETE_SECONDS);
        this.timeProperty = new SimpleStringProperty(time);
    }
}