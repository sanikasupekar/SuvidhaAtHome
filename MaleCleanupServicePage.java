package prerana.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
// import prerana.example.session.CurrentSession;
// import prerana.example.util.CartManager;

import java.time.LocalDate;

public class MaleCleanupServicePage extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Male Cleanup Service");

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey;");

        ImageView banner = new ImageView(new Image("asset\\malebeautyimage\\malebeautyparlour.jpg"));
        banner.setFitWidth(screenWidth * 0.8);
        banner.setFitHeight(screenHeight * 0.25);
        banner.setPreserveRatio(true);

        Label title = new Label("Men's Face Cleanup");
        title.setFont(Font.font("Arial", 32));

        Label duration = new Label("30 mins");
        Label price = new Label("₹399    ₹549   ✔ 27% OFF");
        Label rating = new Label("★★★★☆");

        Label overviewTitle = new Label("Service Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label("This face cleanup service is designed especially for men to deeply cleanse and rejuvenate the skin, removing tan, excess oil, and impurities.");
        overviewText.setWrapText(true);

        Label productTitle = new Label("Products Used");
        productTitle.setFont(Font.font("Arial", 24));
        ImageView productImg = new ImageView(new Image("file:assets/male_cleanup/products.jpg"));
        productImg.setFitWidth(400);
        productImg.setPreserveRatio(true);
        Label productDesc = new Label("Charcoal face wash, scrub, toner, cleansing gel, peel-off mask, and moisturizer.");
        productDesc.setWrapText(true);

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox procedures = new VBox(20);
        procedures.setAlignment(Pos.CENTER_LEFT);
        procedures.setPadding(new Insets(10));
        procedures.getChildren().addAll(
                createProcedureStep("file:assets/male_cleanup/step1.jpg", "Face Wash", "Cleansing the face with charcoal wash to remove oil and dirt."),
                createProcedureStep("file:assets/male_cleanup/step2.jpg", "Exfoliation", "Scrub gently removes blackheads and dead skin."),
                createProcedureStep("file:assets/male_cleanup/step3.jpg", "Toning & Peel-Off Mask", "Toner applied followed by a mask that clears clogged pores."),
                createProcedureStep("file:assets/male_cleanup/step4.jpg", "Moisturizing", "Skin is soothed and hydrated with lightweight moisturizer.")
        );

        Label setupLabel = new Label("Setup at Home");
        setupLabel.setFont(Font.font("Arial", 24));
        ImageView setupImg = new ImageView(new Image("file:assets/male_cleanup/setup.jpg"));
        setupImg.setFitWidth(500);
        setupImg.setPreserveRatio(true);

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);
        HBox btnBox = new HBox(addToCart);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(20, 0, 10, 0));

        addToCart.setOnAction(e -> {
            // Dialog for date and time
            Dialog<Pair<LocalDate, String>> dialog = new Dialog<>();
            dialog.setTitle("Select Date and Time");

            DatePicker datePicker = new DatePicker();
            TextField timeField = new TextField();
            timeField.setPromptText("Enter time (e.g. 3:00 PM)");

            VBox dialogVBox = new VBox(10, new Label("Select Date:"), datePicker, new Label("Enter Time:"), timeField);
            dialogVBox.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(dialogVBox);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
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
                            "Male CleanUp Service",
                            "399",
                            "30 min",
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
                banner, title, duration, price, rating,
                overviewTitle, overviewText,
                productTitle, productImg, productDesc,
                procedureLabel, procedures,
                setupLabel, setupImg,
                btnBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: lightgrey;");

        Scene scene = new Scene(scrollPane, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setResizable(true);
        stage.show();
    }

    private VBox createProcedureStep(String imagePath, String title, String description) {
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

    public static void main(String[] args) {
        launch(args);
    }
}
