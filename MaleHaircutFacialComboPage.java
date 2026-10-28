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
import javafx.util.Pair;
import java.time.LocalDate;

public class MaleHaircutFacialComboPage {

    public Scene createScene() {
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
            MaleServiceUi maleService = new MaleServiceUi();
            Scene maleScene = maleService.createScene();
            startpage.primaryStage.setScene(maleScene);
            startpage.primaryStage.setMaximized(true);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Men's Haircut + Facial Combo");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 70 mins");
        Label price = new Label("₹799    ₹1049    ✔ 25% OFF");
        Label rating = new Label("★★★★★");

        Label overviewTitle = new Label("Combo Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("This combo includes a stylish haircut and rejuvenating facial treatment tailored for men's skin. Get groomed and glowing in one session!");
        overviewText.setWrapText(true);

        Label productTitle = new Label("Combo Benefits");
        productTitle.setFont(Font.font("Arial", 24));

        ImageView productImg = new ImageView(loadImage("/asset/malehaiecutfacecombo/combohead.jpg"));
        productImg.setFitWidth(commonImageWidth);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label("Professional haircut + deep facial cleanse with charcoal scrub, massage, and glow pack. Ideal for events, office look, or pampering.");
        productDesc.setWrapText(true);
        productDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep(
            "/asset/malehaiecutfacecombo/mhaircut.jpg",
            "Haircut",
            "Hair trimming and styling as per face shape with hygiene tools.",
            commonImageWidth
        ));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep(
            "/asset/malehaiecutfacecombo/malefacecleansing.jpg",
            "Face Cleansing",
            "Washes off dirt, sweat, and excess oil.",
            commonImageWidth
        ));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep(
            "/asset/malehaiecutfacecombo/charcoalmask.jpg",
            "Charcoal Scrub",
            "Exfoliates dead skin and unclogs pores.",
            commonImageWidth
        ));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep(
            "/asset/malehaiecutfacecombo/malefacemasg.jpg",
            "Face Massage",
            "Relieves tension and boosts blood flow.",
            commonImageWidth
        ));
        VBox step5 = new VBox(5, new Label("Step 5"), createProcedureStep(
            "/asset/malehaiecutfacecombo/mglowpack.jpg",
            "Glow Pack",
            "A skin-brightening pack for a fresh look.",
            commonImageWidth
        ));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3, step4);
        row2.setAlignment(Pos.CENTER);
        HBox row3 = new HBox(20, step5);
        row3.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(20, row1, row2, row3);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);
        HBox btnBox = new HBox(addToCart);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(20, 0, 10, 0));

        addToCart.setOnAction(e -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Select Date and Time");

            ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

            DatePicker datePicker = new DatePicker();
            datePicker.setPromptText("Select Date");

            TextField timeField = new TextField();
            timeField.setPromptText("Enter Time (e.g., 3:00 PM)");

            VBox dialogContent = new VBox(10);
            dialogContent.setPadding(new Insets(20, 150, 10, 10));
            dialogContent.getChildren().addAll(new Label("Choose Date:"), datePicker, new Label("Enter Time:"), timeField);

            dialog.getDialogPane().setContent(dialogContent);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmButtonType) {
                    LocalDate date = datePicker.getValue();
                    String time = timeField.getText().trim();
                    if (date != null && !time.isEmpty()) {
                        return new Pair<>(date.toString(), time);
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(dateTime -> {
                String userEmail = CurrentSession.getCurrentUserEmail();
                String serviceName = "Male Haircut and Facial Combo";
                String priceValue = "799";
                String timeRequired = "70 min";
                String selectedDate = dateTime.getKey();
                String selectedTime = dateTime.getValue();

                CartManager.addToCart(userEmail, serviceName, priceValue, timeRequired, selectedDate, selectedTime);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                alert.showAndWait();
            });
        });

        root.getChildren().addAll(
            backBox, title, duration, price, rating,
            overviewTitle, overviewText,
            productTitle, productImg, productDesc,
            procedureLabel, procedures,
            btnBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: lihgtgrey;");

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
            return new Image("https://via.placeholder.com/300x200.png?text=Image+Missing");
        }
    }
}
