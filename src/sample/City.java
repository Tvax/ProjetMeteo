package sample;

import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import static sample.JsonReader.readJsonFromUrl;
import static sample.StringChecker.IsNullOrWhiteSpace;

public class City {

    private OpenWeatherMap openWeatherMap;

    private String urlBaseImage = "https://api.unsplash.com/photos/random?query=";
    private String cityImage;

    public String getCityImage() { return cityImage; }

    public void setName(String name){ this.name = name; }

    public City (String name) throws Exception{
        if(IsNullOrWhiteSpace(name)){
            throw new Exception("City name invalid");
        }

        this.name = name;
        try {
            this.callAPI();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private void callAPI() throws Exception {

        try {


            //unsplash
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

