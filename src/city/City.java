package city;

import api.Api;
import api.Apis;

import java.util.HashMap;

/**
 * Objet qui permet de garder les différentes API à utiliser pour une ville
 */

public class City{

    private HashMap<Apis, Api> listApi;
    public HashMap<Apis, Api> getListApi() { return listApi; }

    /**
     * Constructeur de City
     * @param listApi correspond à la liste des API à garder en mémoire
     */
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