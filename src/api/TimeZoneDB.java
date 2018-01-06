package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

/**
 * Objet permettant d'accéder aux objets JSON de l'API de TimeZoneDB
 */

public class TimeZoneDB extends Api{

    private static final String ERROR_TZDB = "There's been an error fetching data from TZDB.";
    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";
    private static final int RM_DATE= 1;
    private static final int INDEX_START= 0;
    private static final int RM_SECONDS= 3;

    public TimeZoneDB(String lng, String lat) throws Exception {
        super(lng, lat);
        if(isError()){
            throw new Exception(ERROR_TZDB);
        }
    }

    String buildURL(){
        String urlBaseTimeZoneDB = "https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=";
        return urlBaseTimeZoneDB + this.getLat() + "&lng=" + this.getLng();
    }

    void setVariables(JSONObject jsonObject) {
        String time = jsonObject.get("formatted").toString();
        time = time.substring(time.indexOf(' ') + RM_DATE);
        time = time.substring(INDEX_START, time.length() - RM_SECONDS);
        this.setTimeProperty(new SimpleStringProperty(time));
    }
}