package prerana.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import prerana.example.view.login;
import prerana.example.view.startpage;

public class Main extends Application {
     @Override
    public void start(Stage primaryStage) {
        startpage startPage = new startpage(primaryStage);
        Scene startScene = new Scene(startPage.getRoot(), 800, 600);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("Suvidha@Home");
        primaryStage.show();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
