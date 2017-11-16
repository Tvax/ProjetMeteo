package sample;

import org.json.JSONObject;

import java.net.MalformedURLException;

import static sample.JsonReader.readJsonFromUrl;
import static sample.StringChecker.IsNullOrWhiteSpace;

public class City {

    private String name;
    private String temp;
    private String apiKey = "d82b125f9ed47887afc80e5304bbf603";
    private String urlBase = "http://api.openweathermap.org/data/2.5/weather?";

    public String getName() { return name; }
    public String getTemp() { return temp; }

    public void setName(String name){ this.name = name; }

    public City (String name){
        this.name = name;
        try {
            this.CallAPI();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void CallAPI() throws Exception {
        if(IsNullOrWhiteSpace(name)){
            throw new Exception("City name invalid");
        }
        try {
            //String url = new String (urlBase + "q=" + name + "&appid=" + apiKey + "&units=metric");
            String url = new String("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");

            JSONObject json = readJsonFromUrl(url);
            this.name = json.get("name").toString();
            this.temp = json.getJSONObject("main").get("temp").toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.name + " : " + this.temp;
    }
}

