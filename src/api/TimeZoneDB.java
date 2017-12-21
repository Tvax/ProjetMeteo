package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

public class TimeZoneDB extends Api{

    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";

    private boolean error = false;
    private JSONObject jsonObject;
    private String lng;
    private String lat;

    private SimpleStringProperty timeProperty;

    public SimpleStringProperty timePropertyProperty() { return timeProperty; }

    public boolean isError(){return error;}

    public TimeZoneDB(String lng, String lat){
        this.lng = lng;
        this.lat = lat;

        try {
            jsonObject = getJSONFile(buildURL()); }
        catch (Exception e){
            error = true;
            return;
        }
        setVariables();
    }

    private String buildURL(){
        String urlBaseTimeZoneDB = "https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=";
        return urlBaseTimeZoneDB + this.lat + "&lng=" + this.lng;
    }

    private void setVariables(){
        String time = jsonObject.get("formatted").toString();
        time = time.substring(time.indexOf(' ') + 1);
        time = time.substring(0, time.length() - 3);
        this.timeProperty = new SimpleStringProperty(time);
    }
}