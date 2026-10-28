package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class babysitterviewpage {

    public Scene createScene() {
        // Get full screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Heading
        Label heading = new Label("Babysitter Booking");
        heading.setFont(new Font("Arial", 30));
        heading.setTextFill(Color.DARKBLUE);

        // Example Content
        Label info = new Label("Fill in your details to book a babysitter.");
        info.setFont(new Font("Arial", 18));

        // Back Button
        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: orange; -fx-text-fill: white;");
        backBtn.setOnAction(e -> {
            HomePageUI homePage = new HomePageUI();
            Scene homeScene = homePage.createScene();
            startpage.primaryStage.setScene(homeScene);
        });

        // Layout
        VBox content = new VBox(20, backBtn, heading, info);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        BorderPane root = new BorderPane(content);
        root.setPadding(new Insets(20));

        // Return a full-screen Scene
        Scene scene = new Scene(root, screenWidth, screenHeight);
        startpage.primaryStage.setMaximized(true);  // Ensure primary stage is maximized
        return scene;
    }
}
