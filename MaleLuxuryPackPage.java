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

public class MaleLuxuryPackPage {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double commonImageWidth = screenWidth * 0.25;

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey;");

        // Back Button
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

        Label title = new Label("Luxury Grooming Pack for Men");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 90 mins");
        Label price = new Label("₹1499    ₹1999   ✔ 25% OFF");
        Label rating = new Label("★★★★★");

        Label overviewTitle = new Label("Package Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("A complete luxury grooming experience for men including haircut, facial, massage and cleanup.");
        overviewText.setWrapText(true);

        Label includesTitle = new Label("What’s Included?");
        includesTitle.setFont(Font.font("Arial", 24));

        ImageView includesImg = new ImageView(loadImage("/assets/maleluxurypack/charcoal3.jpg"));
        includesImg.setFitWidth(commonImageWidth);
        includesImg.setPreserveRatio(true);

        Label includesDesc = new Label("✔ Haircut\n✔ Charcoal Facial\n✔ Full Body Massage\n✔ Face Cleanup");
        includesDesc.setWrapText(true);
        includesDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep("/asset/maleluxurypack/imageshaircut.jpg", "Haircut", "Professional hair styling as per your preferred look.", commonImageWidth));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep("/asset/maleluxurypack/download facecleanup.jpg", "Face Cleanup", "Deep cleansing with charcoal wash and peel-off mask.", commonImageWidth));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep("/asset/maleluxurypack/download facemask.jpg", "Facial", "Massage and glow pack for fresh, smooth skin.", commonImageWidth));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep("/asset/maleluxurypack/download msg therepy.jpg", "Massage Therapy", "Full-body oil massage to relax muscles and relieve stress.", commonImageWidth));
        VBox step5 = new VBox(5, new Label("Step 5"), createProcedureStep("/asset/maleluxurypack/download final touch.jpg", "Finishing Touch", "Toning and moisturizing for a polished final look.", commonImageWidth));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3, step4);
        row2.setAlignment(Pos.CENTER);
        HBox row3 = new HBox(20, step5);
        row3.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(20, row1, row2, row3);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));

        ImageView setupImg = new ImageView(loadImage("/assets/maleluxurypack/setup.jpg"));
        setupImg.setFitWidth(commonImageWidth);
        setupImg.setPreserveRatio(true);

        // Label setupLabel = new Label("Setup at Home");
        // setupLabel.setFont(Font.font("Arial", 24));

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
                String serviceName = "Male Luxury Pack";
                String priceValue = "1499";
                String timeRequired = "90 min";
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
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/300x200.png?text=Image+Missing");
        }
    }
}
