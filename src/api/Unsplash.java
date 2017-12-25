package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Unsplash extends Api{

    private static final String URL_BASE_UNSPLASH = "https://api.unsplash.com/photos/random?query=";
    private static final String API_KEY_UNSPLASH = "d1d21525dd7d52dc4f608a06c458031ac4a427cc06de40b347eb90802a1d1fa7";


    public Unsplash(Api openWeatherMap){
        super(openWeatherMap);
        try {
            setVariables(getJSONFileFromUnsplash(buildURL())); }
        catch (Exception e){
            setError(true);
        }
    }

    JSONObject getJSONFileFromUnsplash(String urlJson) throws Exception {
        URL url = new URL(urlJson);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("Authorization", "Bearer " + API_KEY_UNSPLASH);
        urlConn.setRequestMethod("GET");
        urlConn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader((urlConn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return new JSONObject(sb.toString());
    }

    String buildURL(){
        return URL_BASE_UNSPLASH + this.getName();
    }

    void setVariables(JSONObject jsonObject){
        String urlCityImage = jsonObject.getJSONObject("urls").get("regular").toString();
        setBackgroundCityImageProperty(new SimpleStringProperty("-fx-background-image: url(\"" + urlCityImage + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;"));
    }
}