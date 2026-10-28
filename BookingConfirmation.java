package prerana.example.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.util.Duration;

public class BookingConfirmation {

    public Parent createUI() {
        // Get full screen size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        BorderPane root = new BorderPane();
        root.setPrefSize(screenWidth, screenHeight);
        root.setStyle("-fx-background-color: lightgrey;");  // Subtle light background
        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8 16; -fx-background-radius: 5;");
        backBtn.setOnAction(e -> {
        CartPage cartPage = new CartPage();
        Scene cartScene = cartPage.createScene("user@example.com"); // Replace with actual user email if available
        startpage.primaryStage.setScene(cartScene);
    });

    BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
    BorderPane.setMargin(backBtn, new Insets(10));
    root.setTop(backBtn);

        VBox content = new VBox(screenHeight * 0.03); // Spacing relative to screen height
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(screenHeight * 0.05, 20, screenHeight * 0.05, 20));

        // Heading with glow + pop-in animation
        Label heading = new Label("Booking Confirmed!");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, screenHeight * 0.05)); // 5% of screen height
        heading.setTextFill(Color.web("#1e88e5"));

        Glow glow = new Glow(0.5);
        DropShadow glowShadow = new DropShadow(12, Color.web("#90caf9"));
        glow.setInput(glowShadow);
        heading.setEffect(glow);

        ScaleTransition headingAnim = new ScaleTransition(Duration.millis(600), heading);
        headingAnim.setFromX(0.5);
        headingAnim.setFromY(0.5);
        headingAnim.setToX(1);
        headingAnim.setToY(1);
        headingAnim.play();

        // Checkmark inside styled circle
        Circle backgroundCircle = new Circle(screenHeight * 0.08, Color.web("#e8f5e9")); // Dynamic radius
        backgroundCircle.setStroke(Color.web("#4caf50"));
        backgroundCircle.setStrokeWidth(4);

        Label checkIcon = new Label("✔");
        checkIcon.setFont(Font.font("Arial", FontWeight.BOLD, screenHeight * 0.10)); // 10% of height
        checkIcon.setTextFill(Color.web("#388e3c"));  // deeper green
        DropShadow checkShadow = new DropShadow(10, Color.web("#a5d6a7"));
        checkIcon.setEffect(checkShadow);

        StackPane checkPane = new StackPane(backgroundCircle, checkIcon);

        // Informative text lines
        Label message = new Label("Thank you for choosing Suvidha @ Home!\nYour comfort is our priority.");
        message.setFont(Font.font("Arial", FontWeight.NORMAL, screenHeight * 0.025)); // Scalable text
        message.setTextFill(Color.DARKSLATEGRAY);
        message.setTextAlignment(TextAlignment.CENTER);
        message.setWrapText(true);

        Label note = new Label("Stay tuned — your wellness journey begins shortly.");
        note.setFont(Font.font("Arial", FontWeight.NORMAL, screenHeight * 0.020));
        note.setTextFill(Color.GRAY);
        note.setTextAlignment(TextAlignment.CENTER);

        content.getChildren().addAll(heading, checkPane, message, note);
        root.setCenter(content);

        return root;
    }

    // Helper to create full-screen Scene
    public Scene createScene() {
        Parent root = createUI();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }
}
