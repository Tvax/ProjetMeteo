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

    @Override
    public String toString(){ return listApi.get(Apis.OPENWEATHERMAP).getName(); }
}