package sample;

import api.OpenWeatherMap;
import api.TimeZoneDB;
import api.Unsplash;
import com.sun.deploy.uitoolkit.impl.fx.ui.FXMessageDialog;

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
                try {
                    timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }){{start();}}.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void callApiUnsplash(){
        try {
            new Thread(() ->{
                try {
                    unsplash = new Unsplash(openWeatherMap.getNameProperty());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Override
    public String toString(){
        return openWeatherMap.getNameProperty();
    }

}

