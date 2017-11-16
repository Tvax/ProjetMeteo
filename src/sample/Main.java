package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*
        Map<String, ImageView> map = new HashMap<String, ImageView>();
        map.put("sunny", new ImageView("https://s18.postimg.org/80z9zv7y1/wsymbol_0001_sunny.png"));
        map.put("cloud", new ImageView("https://s18.postimg.org/vf79bvxm1/wsymbol_0003_white_cloud.png"));
        map.put("rain", new ImageView("https://s18.postimg.org/ftpxrzqtl/wsymbol_0018_cloudy_with_heavy_rain.pngg"));

        VBox layout = new VBox();
        layout.getChildren().addAll(
                map.get("cloud")
                //webview

        );

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.show();*/


        Parent root = FXMLLoader.load(getClass().getResource("MeteoMain.fxml"));
        primaryStage.setTitle("Weather Viewer");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

//City city = new City("agadir");
//        System.out.println(city.toString());

    }
}
