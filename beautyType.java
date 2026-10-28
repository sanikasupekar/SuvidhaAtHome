package prerana.example.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

public class beautyType {

    public Scene createScene() {
        // Full screen setup
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

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
            HomePageUI homeUI = new HomePageUI();
            Scene homeScene = homeUI.createScene();
            startpage.primaryStage.setScene(homeScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(10));

        // --- HEADING WITH ANIMATION ---
        Label animatedHeading = new Label();
        animatedHeading.setFont(Font.font("Arial", 50));
        animatedHeading.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        animateText(animatedHeading, "Because you deserve to shine.");

        // --- DESCRIPTION BELOW HEADING ---
        Label description = new Label("Pamper yourself with our handpicked beauty services designed just for women. From glowing facials to flawless makeovers, we bring the salon experience to your doorstep.");
        description.setWrapText(true);
        description.setFont(Font.font("Arial", 20));
        description.setStyle("-fx-text-fill: black;");
        description.setMaxWidth(screenWidth * 0.6);
        description.setAlignment(Pos.CENTER);

        VBox textBox = new VBox(20, animatedHeading, description);
        textBox.setAlignment(Pos.CENTER);

        // --- PRIME BUTTON ---
        Button primeBtn = new Button("Explore ->");
        primeBtn.setPrefSize(screenWidth * 0.10, screenHeight * 0.07);
        primeBtn.setStyle("-fx-font-size: 20px; -fx-background-color: #080a0fff; -fx-text-fill: white;");
        primeBtn.setOnAction(e -> {
            beautyprime primeUI = new beautyprime();
            Scene primeScene = primeUI.createScene();
            startpage.primaryStage.setScene(primeScene);
        });

        // --- CONTENT BOX (HEADING + DESC + BUTTON) ---
        VBox contentBox = new VBox(40, textBox, primeBtn);
        contentBox.setAlignment(Pos.CENTER);

        // --- BACKGROUND IMAGE ---
        Image backgroundImage = new Image("/asset/beautytypee/primeback.jpg", screenWidth, screenHeight, false, true);
        BackgroundImage bgImage = new BackgroundImage(backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(backBox, contentBox);
        root.setBackground(new Background(bgImage));

        return new Scene(root, screenWidth, screenHeight);
    }

    // --- TEXT ANIMATION METHOD ---
    private void animateText(Label label, String text) {
        final StringBuilder displayedText = new StringBuilder();
        Timeline timeline = new Timeline();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(80 * i), event -> {
                displayedText.append(text.charAt(index));
                label.setText(displayedText.toString());
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
}
