package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.util.Optional;

public class FacialHaircutComboPage {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double commonImageWidth = screenWidth * 0.25;

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey;");

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
            beautyType typeUI = new beautyType();
            Scene typeScene = typeUI.createScene();
            startpage.primaryStage.setScene(typeScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        // ImageView banner = new ImageView(loadImage("/asset/facehaircombofemale/facialhairheader.jpg"));
        // banner.setFitWidth(commonImageWidth);
        // banner.setPreserveRatio(true);

        Label title = new Label("Facial + Haircut Combo Offer");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 1 hr 15 mins");
        Label pricevalue = new Label("₹799    ₹1299   ✔ 39% OFF");
        Label rating = new Label("★★★★☆");

        Label overviewTitle = new Label("Combo Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("A rejuvenating facial treatment combined with a stylish haircut – perfect for a refreshing makeover and glowing skin.");
        overviewText.setWrapText(true);
         overviewTitle.setFont(Font.font("Arial",18));

        Label productTitle = new Label("Products & Tools Used");
        productTitle.setFont(Font.font("Arial", 24));

        ImageView productImg = new ImageView(loadImage("/asset/facehaircombofemale/facialhairheader.jpg"));
        productImg.setFitWidth(commonImageWidth);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label("Includes skin cleansers, scrub, massage cream, face pack, scissors, trimmer and styling gel.");
        productDesc.setWrapText(true);
         productDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep(
            "/asset/facehaircombofemale/fcleansing.jpg",
            "Cleansing & Exfoliation",
            "Gentle face cleansing followed by exfoliation to remove dead cells.",
            commonImageWidth
        ));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep(
            "/asset/facehaircombofemale/ffacemsg.jpg",
            "Massage & Pack",
            "Relaxing face massage with nourishing cream and pack application.",
            commonImageWidth
        ));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep(
            "/asset/facehaircombofemale/fhairconsult.jpg",
            "Haircut Consultation",
            "Discussion to finalize haircut style based on face shape.",
            commonImageWidth
        ));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep(
            "/asset/facehaircombofemale/prdtsused.jpg",
            "Hair Trimming & Styling",
            "Precision haircut followed by setting and styling.",
            commonImageWidth
        ));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3, step4);
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
            String serviceName = "Facial & Haircut Combo";
            String price = "799";
            String timeRequired = "1 hrs 15 min";

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
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/300x200.png?text=Image+Not+Found");
        }
    }
}
