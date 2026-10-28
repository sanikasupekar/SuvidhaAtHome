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

public class RegularMealPage {

    public Scene createScene() {
        // Get screen size for full-screen layout
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Root layout
        VBox root = new VBox(25);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: white;");

        // Back Button
        Button backBtn = new Button("← Back");
        backBtn.setStyle(
                "-fx-background-color: #E67E22; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-padding: 5 15; -fx-background-radius: 6; -fx-cursor: hand;"
        );
        backBtn.setOnAction(e -> {
            startpagecooking cookPage = new startpagecooking();
            Scene cookScene = cookPage.createScene();
            startpage.primaryStage.setScene(cookScene);
        });

        VBox scrollContent = new VBox(25);
        scrollContent.setAlignment(Pos.TOP_CENTER);

        // Heading
        Label heading = new Label("Regular Meal");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 34));
        heading.setTextFill(Color.web("#8e44ad"));

        // Meal Image (dynamic width)
        ImageView mealImage = new ImageView(new Image("asset\\cookimage\\regularmeal_image.jpg"));
        mealImage.setFitWidth(screenWidth * 0.6);
        mealImage.setPreserveRatio(true);

        // Cook Boxes Section (2 rows of 3 boxes each)
        GridPane cookGrid = new GridPane();
        cookGrid.setAlignment(Pos.CENTER);
        cookGrid.setHgap(20);
        cookGrid.setVgap(20);
        cookGrid.setPadding(new Insets(20));

        cookGrid.add(createCookBox("1-2 People", "★★★★★", "₹200"), 0, 0);
        cookGrid.add(createCookBox("3-5 People", "★★★★☆", "₹300"), 1, 0);
        cookGrid.add(createCookBox("5-7 People", "★★★★☆", "₹500"), 2, 0);
        cookGrid.add(createCookBox("7-10", "★★★☆☆", "₹700"), 0, 1);
        cookGrid.add(createCookBox("10-12 People", "★★★★★", "₹800"), 1, 1);
        cookGrid.add(createCookBox("12-15", "★★★★☆", "₹900"), 2, 1);

        // Add everything to scrollable content
        scrollContent.getChildren().addAll(heading, mealImage, cookGrid);

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(scrollContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: lightblue;");

        // Add everything to root
        root.getChildren().addAll(backBtn, scrollPane);

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
        ratingLabel.setTextFill(Color.web("#ff9800"));

        Label priceLabel = new Label("Rate: " + price);
        priceLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        priceLabel.setTextFill(Color.web("#27ae60"));

        Button booknow = new Button("Book Now");
        booknow.setStyle(
                "-fx-background-color: #8e44ad; -fx-text-fill: white; -fx-padding: 8 20; -fx-font-size: 14px; " +
                "-fx-background-radius: 6; -fx-cursor: hand;"
        );
        booknow.setOnAction(e -> {
            dinnerregularmeal dinnerPage = new dinnerregularmeal();
            Scene dinnerScene = dinnerPage.createScene();
            startpage.primaryStage.setScene(dinnerScene);
        });

        box.getChildren().addAll(nameLabel, ratingLabel, priceLabel, booknow);
        return box;
    }
}
