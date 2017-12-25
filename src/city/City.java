package city;

import api.Api;
import api.Apis;

import java.util.HashMap;

public class City{

    private static final String ERROR_OWM = "There's been an error fetching data from OWM. Try again later.";
    private static final String ERROR_UNSPLASH = "There's been an error fetching data from Unsplash. Try again later.";
    private static final String ERROR_TZDB = "There's been an error fetching data from TZDB. Try again later.";

    private HashMap<Apis, Api> listApi;

    public HashMap<Apis, Api> getListApi() { return listApi; }

    public City (HashMap<Apis, Api> listApi) {
        this.listApi = listApi;
    }
//
//    //Specifier name pas dans le constructeur mais dans une methode de la classe
//
//    private void callApiTimeZoneDB() throws IOException, InterruptedException {
//        new Thread(() -> timeZoneDB = new TimeZoneDB(openWeatherMap.getLng(), openWeatherMap.getLat())){{start();}}.join();
//
//        if (timeZoneDB.isError()) {
//            throw new IOException(ERROR_TZDB);
//        }
//    }
//
//    private void callApiUnsplash() throws IOException, InterruptedException {
//        new Thread(() -> unsplash = new Unsplash(openWeatherMap.getName())){{start();}}.join();
//
//        if (unsplash.isError()){
//            throw new IOException(ERROR_UNSPLASH);
//        }
//    }
//
//    private void callApiOpenWeatherMap(String name) throws Exception {
//        openWeatherMap = new OpenWeatherMap(name);
//        if (openWeatherMap.isError()){
//            throw new IOException(ERROR_OWM);
//        }
//    }

    @Override
    public String toString(){ return listApi.get(Apis.OPENWEATHERMAP).getName(); }
}