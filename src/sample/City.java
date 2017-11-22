package sample;

import api.OpenWeatherMap;
import api.TimeZoneDB;
import api.Unsplash;

import static sample.StringChecker.IsNullOrWhiteSpace;

public class City{

    private OpenWeatherMap openWeatherMap;
    private Unsplash unsplash;
    private TimeZoneDB timeZoneDB;

    public OpenWeatherMap getOpenWeatherMap() { return openWeatherMap; }
    public Unsplash getUnsplash() { return unsplash; }
    public TimeZoneDB getTimeZoneDB() { return timeZoneDB; }


    public City (String name) {

        if(IsNullOrWhiteSpace(name)){
            //throw new Exception("City name invalid");
            //TODO:ou afficher directement fenetre erreur ici ??
        }

        callApiOpenWeatherMap(name);
        callApiTimeZoneDB();
        callApiUnsplash();

    }

    private void callApiTimeZoneDB(){
        try {
            new Thread(() ->{
                timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat());
            }){{start();}}.join();
        }
        catch (Exception e){
            //TODO: do not display time
        }
    }

    private void callApiUnsplash(){
        try {
            new Thread(() ->{
                unsplash = new Unsplash(openWeatherMap.getName());
            }){{start();}}.join();
        }
        catch (Exception e){
            //TODO: do not display time
        }
    }

    private void callApiOpenWeatherMap(String name){
        try {
            openWeatherMap = new OpenWeatherMap(name);
        }
        catch (Exception e){
            //TODO: error window with "error with opw website pls try again"
        }
    }

}

