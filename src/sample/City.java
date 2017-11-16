package sample;

import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

public class City {

    private String name;
    private Float temp;
    private String apiKey = "apikey";
    private String urlBase = "http://openweathermap.org/data/2.5/weather";

    public String getName() { return name; }
    public Float getTemp() { return temp; }

    public void setName(String name){ this.name = name; }
    //public void setTemp(Float temp){ this.temp = temp; }

    public City (String name){
        this.name = name;
    }


    public void CallAPI() throws Exception {
        if(IsNullOrWhiteSpace(name)){
            throw new Exception("City name invalid");
        }

        Object json = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}";

        try {
            URL url = new URL(urlBase + "q=" + name + "&appid=" + apiKey);

            JSONObject jsonObject = new JSONObject(json);
            //this.temp = (Float) jsonObject.getJSONObject("main").get("temp");
            this.name = (String) jsonObject.get("name");
            //System.out.println(this.temp);
            System.out.println(this.name);

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //a caler dans interface
    public static boolean IsNullOrEmpty(String value) {
        if (value != null)
            return value.length() == 0;
        else
            return true;
    }

    public static boolean IsNullOrWhiteSpace(String value) {
        int index;
        if (value == null)
            return true;
        if (!isWhiteSpace(value))
            return false;

        return true;
    }

    private static boolean isWhiteSpace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (s.charAt(i) > ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    //    http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1
}

