package city;

import api.OpenWeatherMap;
import api.TimeZoneDB;
import api.Unsplash;

import java.io.IOException;

import static util.StringChecker.IsNullOrWhiteSpace;

public class City{

    private static final String ERROR_CITYNAME = "City name invalid";
    private static final String ERROR_OWM = "There's been an error fetching data from OWM. Try again later.";
    private static final String ERROR_UNSPLASH = "There's been an error fetching data from Unsplash. Try again later.";
    private static final String ERROR_TZDB = "There's been an error fetching data from TZDB. Try again later.";

    private OpenWeatherMap openWeatherMap;
    private Unsplash unsplash;
    private TimeZoneDB timeZoneDB;

    public OpenWeatherMap getOpenWeatherMap() { return openWeatherMap; }
    public Unsplash getUnsplash() { return unsplash; }
    public TimeZoneDB getTimeZoneDB() { return timeZoneDB; }

    public City (String name) throws Exception {
        if(IsNullOrWhiteSpace(name)){ throw new IOException(ERROR_CITYNAME); }

        //http://www.oodesign.com/open-close-principle.html
        callApiOpenWeatherMap(name);
        callApiTimeZoneDB();
        callApiUnsplash();
    }

    private void callApiTimeZoneDB() throws IOException, InterruptedException {
        new Thread(() -> timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat())){{start();}}.join();

        if (timeZoneDB.isError()) {
            throw new IOException(ERROR_TZDB);
        }
    }

    private void callApiUnsplash() throws IOException, InterruptedException {
        new Thread(() -> unsplash = new Unsplash(openWeatherMap.getName())){{start();}}.join();

        if (unsplash.isError()){
            throw new IOException(ERROR_UNSPLASH);
        }
    }

    private void callApiOpenWeatherMap(String name) throws Exception {
        openWeatherMap = new OpenWeatherMap(name);
        if (openWeatherMap.isError()){
            throw new IOException(ERROR_OWM);
        }
    }

    @Override
    public String toString(){ return openWeatherMap.getName(); }
}