package api;

import javafx.beans.property.SimpleStringProperty;
import org.json.JSONObject;

/**
 * Objet permettant d'acceder aux objet de l'API d'Unsplash
 */

public class Unsplash extends Api{

    private static final String URL_DEFAULT_BACKGROUND = "https://unsplash.com/photos/Q1p7bh3SHj8/download?force=true";
    private static final String URL_BASE_UNSPLASH = "https://api.unsplash.com/photos/random?query=";

    public Unsplash(Api openWeatherMap){
        super(openWeatherMap);
        if(isError()){
            setDefaultBackground();
        }
    }

    String buildURL(){
        return URL_BASE_UNSPLASH + this.getName();
    }

    void setVariables(JSONObject jsonObject) {
        String urlCityImage = jsonObject.getJSONObject("urls").get("regular").toString();
        setBackgroundCityImageProperty(new SimpleStringProperty("-fx-background-image: url(\"" + urlCityImage + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;"));
    }

    /**
     * Met en background-image l'URL de URL_DEFAULT_BACKGROUND
     */
    private void setDefaultBackground(){
        setBackgroundCityImageProperty(new SimpleStringProperty("-fx-background-image: url(\"" + URL_DEFAULT_BACKGROUND + "\");-fx-background-size: 1920, 1080;-fx-background-repeat: no-repeat;"));
    }
}