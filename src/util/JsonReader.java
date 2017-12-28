package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Permet de retourner des JSONObject en fonction d'une URL
 */

public final class JsonReader {

    /**
     * Permet de convertir un Reader en String
     * @param rd le Reader devant se faire convertir en String
     * @return le String du Reader convertit
     * @throws IOException si la lecture du Reader est impossible
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Permet de récupérer un fichier JSON à partir de son URL
     * @param url URL du fichier JSON à récupérer
     * @return le JSONObject du fichier JSON
     * @throws IOException si la lecture du Reader est impossible, ou que l'URL est incorrecte
     * @throws JSONException si la création du JSONObject est impossible avec le contenu de jsonText
     */
    public static JSONObject getJSONFile(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    /**
     * Permet de récuperer les objets JSON depuis l'URL
     * @param urlJson URL désignant le fichier JSON à récupérer
     * @return un JSONObject du fichier JSON
     * @throws Exception si l'accés au fichier JSON de urlJson est impossible
     */
    public static JSONObject getJSONFileFromUnsplash(String urlJson) throws Exception {
        final String API_KEY_UNSPLASH = "d1d21525dd7d52dc4f608a06c458031ac4a427cc06de40b347eb90802a1d1fa7";

        URL url = new URL(urlJson);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setRequestProperty("Authorization", "Bearer " + API_KEY_UNSPLASH);
        urlConn.setRequestMethod("GET");
        urlConn.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader((urlConn.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return new JSONObject(sb.toString());
    }
}