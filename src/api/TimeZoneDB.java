package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

public class TimeZoneDB extends Api{

    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";

    public TimeZoneDB(String lng, String lat){
        super(lng, lat);
    }

    String buildURL(){
        String urlBaseTimeZoneDB = "https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=";
        return urlBaseTimeZoneDB + this.getLat() + "&lng=" + this.getLng();
    }

    void setVariables(JSONObject jsonObject){
        String time = jsonObject.get("formatted").toString();
        time = time.substring(time.indexOf(' ') + 1);
        time = time.substring(0, time.length() - 3);
        this.setTimeProperty(new SimpleStringProperty(time));
    }
}