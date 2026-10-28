package prerana.example.view;

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

import java.net.URL;
//import java.time.LocalDate;

public class BeardCleanupComboPage {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double commonImageWidth = screenWidth * 0.25;

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color:lightgrey;");

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
            MaleServiceUi maleService = new MaleServiceUi();
            Scene maleScene = maleService.createScene();
            startpage.primaryStage.setScene(maleScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Beard Styling + Face Cleanup");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 45 mins");
        Label price = new Label("₹549    ₹749   ✔ 27% OFF");
        Label rating = new Label("★★★★☆");

        Label overviewTitle = new Label("Combo Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("A combo designed for modern men! Sharp beard styling combined with a refreshing face cleanup leaves you looking groomed and fresh.");
        overviewText.setWrapText(true);

        Label includesTitle = new Label("What’s Included?");
        includesTitle.setFont(Font.font("Arial", 24));

        ImageView includesImg = new ImageView(loadImage("/asset/beardcleanup/included.jpg"));
        includesImg.setFitWidth(commonImageWidth);
        includesImg.setPreserveRatio(true);

        Label includesDesc = new Label("✔ Beard Trim & Style\n✔ Deep Cleansing\n✔ Blackhead Removal\n✔ Moisturizer & Toner Finish");
        includesDesc.setWrapText(true);
        includesDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep("/asset/beardcleanup/beardtrim.jpg", "Beard Trim & Line-Up", "Precision trimming & styling using trimmer and razor.", commonImageWidth));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep("/asset/beardcleanup/faceclean1.jpg", "Face Cleansing", "Removes dust and oil using a charcoal face wash.", commonImageWidth));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep("/asset/beardcleanup/blackheadscrub.jpg", "Blackhead Scrub", "Gentle exfoliation around nose & forehead.", commonImageWidth));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep("/asset/beardcleanup/cooling.jpg", "Cooling Toner & Moisturizer", "Final step to soothe and hydrate skin.", commonImageWidth));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3, step4);
        row2.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(20, row1, row2);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));

         ImageView setupImg = new ImageView(loadImage("/assets/malebeautyimage/homebeard.jpg"));
         setupImg.setFitWidth(screenWidth * 0.4);
         setupImg.setPreserveRatio(true);

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);
        HBox btnBox = new HBox(addToCart);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(20, 0, 10, 0));

        addToCart.setOnAction(e -> {
            String userEmail = "user@example.com";
            String serviceName = "Beard Cleanup Combo";
            String priceVal = "549";
            String timeRequired = "45 mins";

            TextInputDialog dateDialog = new TextInputDialog();
            dateDialog.setTitle("Select Date");
            dateDialog.setHeaderText("Enter date (YYYY-MM-DD):");
            var dateOpt = dateDialog.showAndWait();
            if (dateOpt.isEmpty()) return;

            TextInputDialog timeDialog = new TextInputDialog();
            timeDialog.setTitle("Select Time");
            timeDialog.setHeaderText("Enter time (HH:MM):");
            var timeOpt = timeDialog.showAndWait();
            if (timeOpt.isEmpty()) return;

            CartManager.addToCart(userEmail, serviceName, priceVal, timeRequired, dateOpt.get().trim(), timeOpt.get().trim());

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
            alert.showAndWait();
        });

        root.getChildren().addAll(
            backBox, title, duration, price, rating,
            overviewTitle, overviewText,
            includesTitle, includesImg, includesDesc,
            procedureLabel, procedures,
            setupImg,
            btnBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: lightblue;");

        return new Scene(scrollPane, screenWidth, screenHeight);
    }

    private VBox createProcedureStep(String imagePath, String title, String description, double commonWidth) {
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
        URL imageUrl = getClass().getResource(path);
        if (imageUrl == null) {
            System.err.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/300x200.png?text=Image+Missing");
        }
        return new Image(imageUrl.toExternalForm());
    }
}
