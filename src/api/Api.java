package api;

import org.json.JSONObject;

import java.io.IOException;

import static util.JsonReader.readJsonFromUrl;

public abstract class Api {

    public JSONObject getJSONFile(String urlJson) throws IOException {
        return readJsonFromUrl(urlJson);
    }
}
