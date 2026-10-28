package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class HistoryPage {

    public Scene createScene() {
        // Get full-screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Heading
        Label heading = new Label("History");
        heading.setFont(new Font("Arial", 32));
        heading.setTextFill(Color.DARKBLUE);

        // Top Layout with Back Button and Heading
        Button backBtn = new Button("←");
   backBtn.setStyle("""
    -fx-background-color: black;
    -fx-text-fill: white;
    -fx-font-size: 18px;
    -fx-font-weight: bold;
    -fx-padding: 5 15;
    -fx-background-radius: 8;
""");

        // Back button action: Navigate to HomePage
        backBtn.setOnAction(e -> {
            HomePageUI homePage = new HomePageUI();
            Scene homeScene = homePage.createScene();
            startpage.primaryStage.setScene(homeScene);
        });

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(10);
        topBar.getChildren().addAll(backBtn);

        // Place heading at center of top
        BorderPane topLayout = new BorderPane();
        topLayout.setLeft(backBtn);
        topLayout.setCenter(heading);
        topLayout.setPadding(new Insets(10));

        // Main Content Area (you can fill booking history here)
        VBox content = new VBox();
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(20));
        content.setSpacing(10);
        Label placeholder = new Label("Your booking history will appear here.");
        placeholder.setFont(new Font("Arial", 18));
        content.getChildren().add(placeholder);

        // Main Layout
        BorderPane root = new BorderPane();
        root.setTop(topLayout);
        root.setCenter(content);
        root.setStyle("-fx-background-color: lightgrey;");

        // Full-screen Scene
        Scene scene = new Scene(root, screenWidth, screenHeight);
        startpage.primaryStage.setMaximized(true);
        return scene;
    }
}
