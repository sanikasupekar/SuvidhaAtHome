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

public class ManiPediComboPage {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double commonImageWidth = screenWidth * 0.25;

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color:lightgrey;");

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
            beautyprime typeUI = new beautyprime();
            Scene typeScene = typeUI.createScene();
            startpage.primaryStage.setScene(typeScene);
            startpage.primaryStage.setMaximized(true);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Manicure + Pedicure Combo");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 1 hr");
        Label price = new Label("₹899    ₹1299   ✔ 30% OFF");
        Label rating = new Label("★★★★★");

        Label overviewTitle = new Label("Combo Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label(
            "A relaxing combo session that pampers both your hands and feet with soaking, scrubbing, nail shaping, and polish application."
        );
        overviewText.setWrapText(true);

        Label productTitle = new Label("Products & Tools Used");
        productTitle.setFont(Font.font("Arial", 24));

        ImageView productImg = new ImageView(loadImage("/asset/menipedicombo/toolsinmenipedi.jpg"));
        productImg.setFitWidth(commonImageWidth);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label(
            "Includes soaking salts, scrubs, buffers, nail cutter, cuticle remover, moisturizer, and polish kit."
        );
        productDesc.setWrapText(true);
        productDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep(
            "/asset/menipedicombo/socking.jpg",
            "Soaking & Cleansing",
            "Hands and feet are soaked in warm water with salts to soften the skin.",
            commonImageWidth
        ));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep(
            "/asset/menipedicombo/scrubbingmenipedi.jpg",
            "Scrubbing & Exfoliation",
            "Dead skin and tan are removed using scrub and pumice.",
            commonImageWidth
        ));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep(
            "/asset/menipedicombo/nailcare2.jpg",
            "Nail Care",
            "Trimming, filing, and cuticle cleanup for clean nails.",
            commonImageWidth
        ));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep(
            "/asset/menipedicombo/nailpolish.jpg",
            "Moisturizing & Polish",
            "Massage with cream and optional polish application.",
            commonImageWidth
        ));

        HBox row1 = new HBox(20, step1, step2);
        row1.setAlignment(Pos.CENTER);
        HBox row2 = new HBox(20, step3, step4);
        row2.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(20, row1, row2);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));

        // Label setupLabel = new Label("Setup at Home");
        // setupLabel.setFont(Font.font("Arial", 24));
        // ImageView setupImg = new ImageView(loadImage("/asset/menipedicombo/setuphomemenipedi.jpg"));
        // setupImg.setFitWidth(commonImageWidth * 1.5);
        // setupImg.setPreserveRatio(true);

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
                String serviceName = "Manicure Pedicure Combo";
                String priceValue = "899";
                String timeRequired = "1 hr";
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
            //setupLabel, setupImg,
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
