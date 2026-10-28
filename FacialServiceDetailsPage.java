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

public class FacialServiceDetailsPage {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        double commonImageWidth = screenWidth * 0.25;

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey;");

        // Back Button with Icon
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

        Label title = new Label("Glow & Radiance Facial");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("⏱ 1 hr 10 mins");
        Label pricevalue = new Label("₹999    ₹1499   ✔ 33% OFF");
        Label rating = new Label("★★★★☆");

        Label overviewTitle = new Label("Facial Therapy: An Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("A rejuvenating facial designed to cleanse, exfoliate and nourish your skin, giving it a healthy glow and softness.");
        overviewText.setWrapText(true);

        Label productTitle = new Label("Your Facial Products");
        productTitle.setFont(Font.font("Arial", 24));

        ImageView productImg = new ImageView(new Image("asset//femalefacial//productsfacial.jpg"));
        productImg.setFitWidth(commonImageWidth);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label("We use natural cleansers, exfoliants, and brightening packs to revive and hydrate your skin.");
        productDesc.setWrapText(true);
        productDesc.setFont(Font.font("Arial", 18));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep(
            "asset//femalefacial//facialscrub.jpg",
            "Cleansing",
            "Deep cleansing using gentle cleanser.",
            commonImageWidth
        ));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep(
            "asset//femalefacial//scrubbingfacial.jpg",
            "Scrubbing",
            "Mild exfoliation to remove dead skin.",
            commonImageWidth
        ));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep(
            "asset//femalefacial//facialmsg.jpg",
            "Massage & Pack",
            "Facial massage & skin-brightening pack.",
            commonImageWidth
        ));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep(
            "asset//femalefacial//setuphome.jpg",
            "Setup at Home",
            "Comfortable setup with hygienic tools at your home.",
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
            Dialog<Pair<LocalDate, String>> dialog = new Dialog<>();
            dialog.setTitle("Select Date and Time");

            DatePicker datePicker = new DatePicker();
            TextField timeField = new TextField();
            timeField.setPromptText("Enter time (e.g. 3:00 PM)");

            VBox dialogVBox = new VBox(10, new Label("Select Date:"), datePicker, new Label("Enter Time:"), timeField);
            dialogVBox.setPadding(new Insets(10));

            dialog.getDialogPane().setContent(dialogVBox);
            ButtonType okButtonType = new ButtonType("OK", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            dialog.setResultConverter(dialogBtn -> {
                if (dialogBtn == okButtonType) {
                    return new Pair<>(datePicker.getValue(), timeField.getText());
                }
                return null;
            });

            dialog.showAndWait().ifPresent(result -> {
                LocalDate selectedDate = result.getKey();
                String selectedTime = result.getValue();

                if (selectedDate != null && selectedTime != null && !selectedTime.trim().isEmpty()) {
                    String userEmail = CurrentSession.getCurrentUserEmail();

                    CartManager.addToCart(
                        userEmail,
                        "Glow & Radiance Facial",
                        "₹999",
                        "1 hr 10 mins",
                        selectedDate.toString(),
                        selectedTime
                    );

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please select both date and time.", ButtonType.OK);
                    alert.showAndWait();
                }
            });
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
        ImageView img = new ImageView(new Image(imagePath));
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
}
