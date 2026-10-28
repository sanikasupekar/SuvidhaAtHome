package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;

public class startpagecooking {

    public Scene createScene() {
        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
// BACK BUTTON
        Button backBtn = new Button("←");
   backBtn.setStyle("""
    -fx-background-color: black;
    -fx-text-fill: white;
    -fx-font-size: 18px;
    -fx-font-weight: bold;
    -fx-padding: 5 15;
    -fx-background-radius: 8;
""");
        
        backBtn.setOnAction(e -> {
            HomePageUI homePage = new HomePageUI();
            Scene homeScene = homePage.createScene();
            startpage.primaryStage.setScene(homeScene);
            startpage.primaryStage.setMaximized(true); // Full-screen
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 0, 10, 10));

        // ====== Left Side: Circular Food Image ======
        ImageView foodImage = createCircularImage("/asset/cookimage/image.png",
                (int) (screenWidth * 0.25)); // Image size relative to screen

        // ====== Right Side: Text + Buttons ======
        Label heading = new Label("Welcome to Food Service at Home");
        heading.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #D35400;");

        Label tagline = new Label("Delicious food, cooked just for you.");
        tagline.setStyle("-fx-font-size: 20px; -fx-text-fill: #2C3E50;");

        Label servicesHeading = new Label("Our Services");
        servicesHeading.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #27AE60;");

        // Buttons for services
        Button regularMealBtn = createStyledButton("🍽 Regular Meal");
        Button partyMealBtn = createStyledButton("🎉 Party Meal");

        regularMealBtn.setOnAction(e -> {
            RegularMealPage regularMealPage = new RegularMealPage();
            Scene regularScene = regularMealPage.createScene();
            startpage.primaryStage.setScene(regularScene);
            startpage.primaryStage.setMaximized(true);
        });
        partyMealBtn.setOnAction(e -> {
            PartyMealPage partyMealPage = new PartyMealPage();
            Scene partyScene = partyMealPage.createScene();
            startpage.primaryStage.setScene(partyScene);
            startpage.primaryStage.setMaximized(true);
        });

        VBox rightContent = new VBox(25, heading, tagline, servicesHeading, regularMealBtn, partyMealBtn);
        rightContent.setAlignment(Pos.CENTER_LEFT);
        rightContent.setPadding(new Insets(20));

        HBox centerContent = new HBox(80, foodImage, rightContent);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(50));

        // ====== Main Layout Using BorderPane ======
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(centerContent);
        root.setStyle("-fx-background-color: lightgrey;");

        return new Scene(root, screenWidth, screenHeight);
    }

    // Method to create a circular image
    private ImageView createCircularImage(String imagePath, int diameter) {
        ImageView imageView = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
            imageView.setFitWidth(diameter);
            imageView.setFitHeight(diameter);
            imageView.setPreserveRatio(true);

            Circle clip = new Circle(diameter / 2.0, diameter / 2.0, diameter / 2.0);
            imageView.setClip(clip);
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }
        return imageView;
    }

    // Styled Button with hover effect
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: #E67E22; -fx-text-fill: white; -fx-font-size: 18px; " +
            "-fx-padding: 12px 25px; -fx-background-radius: 20px; -fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
            "-fx-background-color: #CA6F1E; -fx-text-fill: white; -fx-font-size: 18px; " +
            "-fx-padding: 12px 25px; -fx-background-radius: 20px; -fx-cursor: hand;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
            "-fx-background-color: #E67E22; -fx-text-fill: white; -fx-font-size: 18px; " +
            "-fx-padding: 12px 25px; -fx-background-radius: 20px; -fx-cursor: hand;"
        ));

        return button;
    }
}
