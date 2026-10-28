package prerana.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Optional;

public class ThreadingServiceDetailsPage extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Threading Service Details");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double commonImageWidth = screenWidth * 0.25;

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
            beautyprime primeUI = new beautyprime();
            Scene primeScene = primeUI.createScene();
            startpage.primaryStage.setScene(primeScene);
            startpage.primaryStage.setMaximized(true);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Eyebrow & Upperlip Threading");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 15 mins");
        Label pricevalue = new Label("₹99    ₹149   ✔ 34% OFF");
        Label rating = new Label("★★★★★");

        Label overviewTitle = new Label("Threading: An Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("Precise and painless hair removal technique using thread for perfectly shaped eyebrows and clean upper lip.");
        overviewText.setWrapText(true);

        Label productTitle = new Label("Products Used");
        productTitle.setFont(Font.font("Arial", 24));

        ImageView productImg = new ImageView(loadImage("file:assets/threading/thread.jpg"));
        productImg.setFitWidth(commonImageWidth);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label("We use anti-bacterial cotton thread and soothing aloe-based gel for skin calmness.");
        productDesc.setWrapText(true);
        productDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep("file:assets/threading/step1.jpg", "Preparation", "Cleansing the skin to remove dirt/oil and mark shape.", commonImageWidth));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep("file:assets/threading/step2.jpg", "Threading", "Threading is done with gentle strokes to remove hair.", commonImageWidth));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep("file:assets/threading/step3.jpg", "Soothing Gel", "Applying aloe gel to cool and reduce redness.", commonImageWidth));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3);
        row2.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(20, row1, row2);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);
        HBox btnBox = new HBox(addToCart);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(20, 0, 10, 0));

        addToCart.setOnAction(e -> {
            String userEmail = "user@example.com";
            String serviceName = "Threading service";
            String price = "99";
            String timeRequired = "15 min";

            TextInputDialog dateDialog = new TextInputDialog();
            dateDialog.setTitle("Select Date");
            dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
            Optional<String> dateOpt = dateDialog.showAndWait();
            if (dateOpt.isEmpty()) return;

            TextInputDialog timeDialog = new TextInputDialog();
            timeDialog.setTitle("Select Time");
            timeDialog.setHeaderText("Enter time (HH:MM):");
            Optional<String> timeOpt = timeDialog.showAndWait();
            if (timeOpt.isEmpty()) return;

            String selectedDate = dateOpt.get().trim();
            String selectedTime = timeOpt.get().trim();

            CartManager.addToCart(userEmail, serviceName, price, timeRequired, selectedDate, selectedTime);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
            alert.showAndWait();
        });

        root.getChildren().addAll(
            backBox, title, duration, pricevalue, rating,
            overviewTitle, overviewText,
            productTitle, productImg, productDesc,
            procedureLabel, procedures,
            btnBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(scrollPane, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private VBox createProcedureStep(String imagePath, String title, String description, double width) {
        ImageView img = new ImageView(loadImage(imagePath));
        img.setFitHeight(250);
        img.setFitWidth(300);
        img.setPreserveRatio(true);

        Label stepTitle = new Label(title);
        stepTitle.setFont(Font.font("Arial", 20));

        Label stepDesc = new Label(description);
        stepDesc.setWrapText(true);

        VBox stepBox = new VBox(10, img, stepTitle, stepDesc);
        stepBox.setPadding(new Insets(10));
        stepBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10; -fx-background-radius: 10;");
        return stepBox;
    }

    private Image loadImage(String path) {
        try {
            return new Image(path);
        } catch (Exception e) {
            System.out.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/300x200.png?text=Image+Missing");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
