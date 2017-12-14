package city;

import api.OpenWeatherMap;
import api.TimeZoneDB;
import api.Unsplash;

import static util.StringChecker.IsNullOrWhiteSpace;

public class City{

    private static final String ERROR_CITYNAME = "City name invalid";
    private static final String ERROR_OWM = "There's been an error fetching data from OWM.";
    private static final String ERROR_UNSPLASH = "There's been an error fetching data from Unsplash.";
    private static final String ERROR_TZDB = "There's been an error fetching data from TZDB.";

    private OpenWeatherMap openWeatherMap;
    private Unsplash unsplash;
    private TimeZoneDB timeZoneDB;

    public OpenWeatherMap getOpenWeatherMap() { return openWeatherMap; }
    public Unsplash getUnsplash() { return unsplash; }
    public TimeZoneDB getTimeZoneDB() { return timeZoneDB; }

    public City (String name) throws Exception {
        if(IsNullOrWhiteSpace(name)){
            throw new Exception(ERROR_CITYNAME);
        }
        
        try {
            callApiOpenWeatherMap(name);
            callApiTimeZoneDB();
            callApiUnsplash();
        }
        catch (Exception e){ throw e;}

    }

    private void callApiTimeZoneDB() throws Exception {
        new Thread(() -> {
            try {
                timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }){{start();}}.join();

        if (timeZoneDB.isError()) {
            throw new Exception(ERROR_TZDB);
        }
    }

    private void callApiUnsplash() throws Exception {
        new Thread(() -> {
            try {
                unsplash = new Unsplash(openWeatherMap.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }){{start();}}.join();

        if (unsplash.isError()){
            throw new Exception(ERROR_UNSPLASH);
        }
    }

    private void callApiOpenWeatherMap(String name) throws Exception {
        openWeatherMap = new OpenWeatherMap(name);
        if (openWeatherMap.isError()){
            throw new Exception(ERROR_OWM);
        }
    }

    @Override
    public String toString(){
        return openWeatherMap.getName();
    }
}