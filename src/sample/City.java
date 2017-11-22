package sample;

import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import static sample.JsonReader.readJsonFromUrl;
import static sample.StringChecker.IsNullOrWhiteSpace;

public class City {

    private String name;
    private String country;
    private String temp;
    private String weather;
    private String weatherDetails;
    private String apiKeyWeather = "d82b125f9ed47887afc80e5304bbf603";
    private String urlBaseWeather = "http://api.openweathermap.org/data/2.5/weather?";
    private String weatherBase = "https://openweathermap.org/img/w/";

    private String urlBaseTimeZoneDB = "https://api.timezonedb.com/v2/get-time-zone?key=B1ZJLC3ORUD5&format=json&fields=formatted&by=position&lat=";
    private String time;

    private String urlBaseImage = "https://api.unsplash.com/photos/random?query=";
    private String cityImage;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getTemp() { return temp; }
    public String getWeather() { return weather; }
    public String getWeatherDetails() { return weatherDetails; }
    public String getTime() { return time; }
    public String getCityImage() { return cityImage; }

    public void setName(String name){ this.name = name; }

    public City (String name){
        this.name = name;
        try {
            this.callAPI();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void callAPI() throws Exception {
        if(IsNullOrWhiteSpace(name)){
            throw new Exception("City name invalid");
        }
        try {
            String url = new String (urlBaseWeather + "q=" + name + "&appid=" + apiKeyWeather + "&units=metric");
            //String url = new String("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1");

            JSONObject json = readJsonFromUrl(url);
            this.name = json.get("name").toString();
            this.country = json.getJSONObject("sys").get("country").toString();
            this.temp = json.getJSONObject("main").get("temp").toString();
            this.weatherDetails = json.getJSONArray("weather").getJSONObject(0).get("description").toString();

            String icoID = json.getJSONArray("weather").getJSONObject(0).get("icon").toString();
            this.weather = weatherBase + icoID + ".png";

            String urlTime = new String(urlBaseTimeZoneDB + json.optJSONObject("coord").get("lat") + "&lng=" + json.optJSONObject("coord").get("lon"));
            JSONObject jsonTime = readJsonFromUrl(urlTime);
            this.time = jsonTime.get("formatted").toString();


            URL url1 = new URL(urlBaseImage + this.name);
            HttpURLConnection urlConn = (HttpURLConnection) url1.openConnection();
            urlConn.setRequestProperty("Authorization", "Bearer d1d21525dd7d52dc4f608a06c458031ac4a427cc06de40b347eb90802a1d1fa7");
            urlConn.setRequestMethod("GET");
            urlConn.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader((urlConn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            cityImage = jsonObject.getJSONObject("urls").get("raw").toString();


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

