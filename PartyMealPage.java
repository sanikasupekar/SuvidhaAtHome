package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class PartyMealPage {

    public Scene createScene() {
        // Get screen dimensions
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Root layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey;");

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
            startpagecooking cookingPage = new startpagecooking();
            Scene cookingScene = cookingPage.createScene();
            startpage.primaryStage.setScene(cookingScene);
        });

        // Scrollable Content
        VBox scrollContent = new VBox(25);
        scrollContent.setAlignment(Pos.TOP_CENTER);

        // Heading
        Label heading = new Label("Party Meal");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        heading.setTextFill(Color.web("#c0392b"));

        // Party Meal Image
        ImageView partyMealImage = new ImageView(new Image("asset\\cookimage\\partymeal_image.jpg"));
        partyMealImage.setFitWidth(screenWidth * 0.6);
        partyMealImage.setPreserveRatio(true);

        // Cook Boxes Section
        GridPane cookGrid = new GridPane();
        cookGrid.setAlignment(Pos.CENTER);
        cookGrid.setHgap(30);
        cookGrid.setVgap(30);
        cookGrid.setPadding(new Insets(20));

        cookGrid.add(createCookBox("20-30", "★★★★★", "₹5000"), 0, 0);
        cookGrid.add(createCookBox("30-50", "★★★★☆", "₹7000"), 1, 0);
        cookGrid.add(createCookBox("50-80", "★★★★☆", "₹12000"), 2, 0);

        cookGrid.add(createCookBox("80-100", "★★★☆☆", "₹20000"), 0, 1);
        cookGrid.add(createCookBox("100-200", "★★★★★", "₹25000"), 1, 1);
        cookGrid.add(createCookBox("more than 200", "★★★★☆", "₹35000"), 2, 1);

        // Add content to scroll
        scrollContent.getChildren().addAll(heading, partyMealImage, cookGrid);

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Add to root
        root.getChildren().addAll(backBtn, scrollPane);

        // Return full-screen Scene
        return new Scene(root, screenWidth, screenHeight);
    }

    // Create Cook Box
    private VBox createCookBox(String cookName, String rating, String price) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15));
        box.setPrefWidth(220);
        box.setPrefHeight(180);
        box.setStyle(
                "-fx-background-color: white; -fx-background-radius: 12; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 3);"
        );

        Label nameLabel = new Label(cookName);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.web("#2c3e50"));

        Label ratingLabel = new Label(rating);
        ratingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ratingLabel.setTextFill(Color.web("#e67e22"));

        Label priceLabel = new Label("Rate: " + price);
        priceLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        priceLabel.setTextFill(Color.web("#27ae60"));

        Button booknow = new Button("Book Now");
        booknow.setStyle(
                "-fx-background-color: #c0392b; -fx-text-fill: white; -fx-padding: 8 20; -fx-font-size: 14px; " +
                "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        booknow.setOnAction(e -> {
            DinnerPeoplePage dinnerPage = new DinnerPeoplePage();
            Scene dinnerScene = dinnerPage.createSceneFromPartyMeal();
            startpage.primaryStage.setScene(dinnerScene);
        });

        box.getChildren().addAll(nameLabel, ratingLabel, priceLabel, booknow);
        return box;
    }
}
