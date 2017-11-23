package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.io.IOException;

import static api.JsonReader.readJsonFromUrl;

public class TimeZoneDB {

    private static final String API_KEY_TIMEZONEDB = "B1ZJLC3ORUD5";

    private String urlBaseTimeZoneDB = new String("https://api.timezonedb.com/v2/get-time-zone?key=" + API_KEY_TIMEZONEDB + "&format=json&fields=formatted&by=position&lat=");
    private String urlJsonTimeZoneDB;
    private JSONObject jsonObject;
    private String lng;
    private String lat;

    private SimpleStringProperty timeProperty;
    public SimpleStringProperty timePropertyProperty() { return timeProperty; }

    public TimeZoneDB(String lng, String lat) throws Exception {

//
//        Alert alert = new Alert(Alert.AlertType.WARNING);
//        alert.setTitle("Information Dialog");
//        alert.setHeaderText("Look, an Information Dialog");
//        alert.setContentText("I have a great message for you!");
//
//        alert.showAndWait();

        this.lng = lng;
        this.lat = lat;
        buildURL();

        try {
            getJSONFile();
        }
        catch (Exception e){
            throw new Exception("Error with ur internet connection");
        }

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
