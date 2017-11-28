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


    public City (String name) throws Exception {

        if(IsNullOrWhiteSpace(name)){
            throw new Exception("City name invalid");
        }

        try {
            callApiOpenWeatherMap(name);
            callApiTimeZoneDB();
            callApiUnsplash();
        }catch (Exception e){
            throw e;
        }
    }

    private void callApiTimeZoneDB() throws Exception {
        try {
            timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat());
        } catch (Exception e) {
            throw e;
        }
    }

    private void callApiUnsplash() throws Exception {
        try {
            unsplash = new Unsplash(openWeatherMap.getNameProperty());
        }catch (Exception e) {
            throw e;
        }
    }

    private void callApiOpenWeatherMap(String name) throws Exception {
        try {
            openWeatherMap = new OpenWeatherMap(name);
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public String toString(){
        return openWeatherMap.getNameProperty();
    }

}