package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Unsplash {

    //private static final String ERROR_MSG = "There's been an error fetching data from Unsplash. Try again later.";
    private static final String URL_BASE_UNSPLASH = "https://api.unsplash.com/photos/random?query=";
    private static final String API_KEY_UNSPLASH = "d1d21525dd7d52dc4f608a06c458031ac4a427cc06de40b347eb90802a1d1fa7";

    private String name;
    private boolean error = false;
    private String urlAPIUnsplash;
    private JSONObject jsonObject;

    private SimpleStringProperty backgroundCityImageProperty;

    public SimpleStringProperty backgroundCityImagePropertyProperty() { return backgroundCityImageProperty; }

    public boolean isError(){return error;}

    public Unsplash(String name) {
        this.name = name;
        buildURL();

        try { getJsonFile(); }
        catch (Exception e){ error = true; }

        setVariables();
    }

    private void buildURL(){
        urlAPIUnsplash  = URL_BASE_UNSPLASH + this.name;
    }

    private void getJsonFile() throws Exception {
        URL url = new URL(urlAPIUnsplash);
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
        jsonObject = new JSONObject(sb.toString());
    }

    private void setVariables(){
        String urlCityImage = jsonObject.getJSONObject("urls").get("raw").toString();
        backgroundCityImageProperty = new SimpleStringProperty("-fx-background-image: url(\"" + urlCityImage + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;");
    }
}
