package api;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Unsplash {

    private String name;
    private static final String URL_BASE_UNSPLASH = "https://api.unsplash.com/photos/random?query=";
    private static final String API_KEY_UNSPLASH = "d1d21525dd7d52dc4f608a06c458031ac4a427cc06de40b347eb90802a1d1fa7";
    private String urlCityImage;
    private String urlAPIUnsplash;
    private JSONObject jsonObject;

    public String getUrlCityImage() { return urlCityImage; }


    public Unsplash(String name){
        this.name = name;
        buildURL();

        try {
            getJsonFile();
        }
        catch (Exception e){
            //TODO: retourner l'erreur "check connection internet"
        }

        try {
            setVariables();
        }
        catch (Exception e){
            //TODO: ville non exsistante
        }
    }

    private void buildURL(){
        urlAPIUnsplash  = new String(URL_BASE_UNSPLASH + this.name);
    }

    private void getJsonFile() throws IOException {
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
        urlCityImage = jsonObject.getJSONObject("urls").get("raw").toString();
    }


}
