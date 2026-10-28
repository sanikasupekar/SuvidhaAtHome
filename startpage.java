package prerana.example.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class startpage {

    public static Stage primaryStage;

    public startpage(Stage stage) {
        primaryStage = stage;
        Scene scene = createFullScreenScene(createUI());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
    }

    private BorderPane createUI() {
        BorderPane root = new BorderPane();

        // Screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Background Image
        Image bgImage = new Image("/asset/aboutus/trial3.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
            bgImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(screenWidth, screenHeight, false, false, false, false)
        );
        root.setBackground(new Background(backgroundImage));

        // Labels with animation
        Label welcomeLabel = createAnimatedLabel("      Welcome", 0);
        welcomeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 70));

        Label suvidhaLabel = createAnimatedLabel("       Suvidha at Home", 1);
        suvidhaLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));

        Label sloganLabel = new Label("");
        sloganLabel.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 15));
        sloganLabel.setTextFill(Color.DARKSLATEGRAY);

        sloganLabel.setTranslateX(-1000);
        TranslateTransition slideSlogan = new TranslateTransition(Duration.seconds(2), sloganLabel);
        slideSlogan.setToX(0);
        slideSlogan.setDelay(Duration.seconds(1.5));
        slideSlogan.play();

        Timeline sloganTimeline = new Timeline();
        String sloganText = "                                                 Care That Comes Home";
        for (int i = 0; i < sloganText.length(); i++) {
            final int index = i;
            KeyFrame frame = new KeyFrame(Duration.millis(50 * i), e -> {
                sloganLabel.setText(sloganText.substring(0, index + 1));
            });
            sloganTimeline.getKeyFrames().add(frame);
        }
        sloganTimeline.setDelay(Duration.seconds(1.5));
        sloganTimeline.play();

        // Centered VBox for all labels
        VBox centerBox = new VBox(10, welcomeLabel, suvidhaLabel, sloganLabel);
        centerBox.setAlignment(Pos.CENTER);

        StackPane centerPane = new StackPane(centerBox);
        centerPane.setAlignment(Pos.CENTER_RIGHT);
        centerPane.setPadding(new Insets(0, screenWidth / 10, 0, screenWidth / 2));

        root.setCenter(centerPane);

        // Buttons
        Button userLoginBtn = createStyledButton("Login as User", e -> {
            login loginPage = new login(primaryStage);
            Scene loginScene = createFullScreenScene(loginPage.getRoot());
            primaryStage.setScene(loginScene);
            primaryStage.setMaximized(true);
        });

        Button providerLoginBtn = createStyledButton("Service Provider", e -> {
            CurrentSession.clearSession();
            new ServiceProviderlogin(primaryStage);
            primaryStage.setMaximized(true);
        });

        Button adminLoginBtn = createStyledButton("Admin", e -> {
            adminlogin adminLoginPage = new adminlogin(primaryStage);
            Scene adminLoginScene = adminLoginPage.createScene();
            primaryStage.setScene(adminLoginScene);
            primaryStage.setMaximized(true);
        });

        HBox topRightButtons = new HBox(15, userLoginBtn, providerLoginBtn, adminLoginBtn);
        topRightButtons.setAlignment(Pos.TOP_RIGHT);
        topRightButtons.setPadding(new Insets(20, 30, 0, 0));

        BorderPane topPane = new BorderPane();
        topPane.setRight(topRightButtons);

        root.setTop(topPane);

        return root;
    }

    private Label createAnimatedLabel(String fullText, int lineIndex) {
        Label label = new Label("");
        label.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));
        label.setTextFill(Color.BLACK);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.GRAY);
        label.setEffect(shadow);

        // Slide-in animation
        label.setTranslateX(-1000);
        TranslateTransition slide = new TranslateTransition(Duration.seconds(2), label);
        slide.setToX(0);
        slide.setDelay(Duration.seconds(0.5 * lineIndex));
        slide.play();

        // Typewriter animation
        Timeline typewriter = new Timeline();
        for (int i = 0; i < fullText.length(); i++) {
            final int index = i;
            KeyFrame frame = new KeyFrame(Duration.millis(100 * i), e -> {
                label.setText(fullText.substring(0, index + 1));
            });
            typewriter.getKeyFrames().add(frame);
        }
        typewriter.setDelay(Duration.seconds(0.5 * lineIndex));
        typewriter.play();

        return label;
    }

    private Button createStyledButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button btn = new Button(text);
        btn.setStyle("""
            -fx-background-color: black;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-font-weight: bold;
            -fx-padding: 10 20;
            -fx-background-radius: 10;
        """);
        btn.setOnAction(action);
        return btn;
    }

    Scene createFullScreenScene(Parent root) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    public Parent getRoot() {
        return createUI();
    }
}
