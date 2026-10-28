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
import javafx.util.Pair;
// import prerana.example.controller.CartManager;
// import prerana.example.controller.CurrentSession;

import java.time.LocalDate;

public class HaircutServiceDetailsPage {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

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
            beautyprime primeUI = new beautyprime();
            Scene primeScene = primeUI.createScene();
            startpage.primaryStage.setScene(primeScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);
        backBox.setPadding(new Insets(10));

        ImageView banner = new ImageView(loadImage(""));
        banner.setFitWidth(screenWidth * 0.6);
        banner.setPreserveRatio(true);

        Label title = new Label("Trendy Haircut");
        title.setFont(Font.font("Arial", screenHeight * 0.04));

        Label duration = new Label("30 mins");
        duration.setStyle("-fx-font-size: 16; -fx-text-fill: gray;");

        Label price = new Label("₹299    ₹499   ✔ 40% OFF");
        price.setStyle("-fx-font-size: 18; -fx-text-fill: green;");

        Label rating = new Label("★★★★☆");
        rating.setStyle("-fx-font-size: 18;");

        Label overviewTitle = new Label("Haircut: An Overview");
        overviewTitle.setFont(Font.font("Arial", screenHeight * 0.03));

        Label overviewText = new Label("A stylish and refreshing haircut session with consultation and finishing touches for a personalized look.");
        overviewText.setWrapText(true);
        overviewText.setMaxWidth(screenWidth * 0.7);

        Label productTitle = new Label("Tools Used");
        productTitle.setFont(Font.font("Arial", screenHeight * 0.03));

        ImageView productImg = new ImageView(loadImage("/asset/babysitter/hairtool.jpg"));
        productImg.setFitWidth(screenWidth * 0.3);
        productImg.setPreserveRatio(true);

        Label productDesc = new Label("Professional scissors, trimmers, combs, and styling gel are used to deliver the perfect haircut.");
        productDesc.setWrapText(true);
        productDesc.setMaxWidth(screenWidth * 0.6);

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", screenHeight * 0.03));

        HBox procedures = new HBox(20);
        procedures.setAlignment(Pos.CENTER);
        procedures.setPadding(new Insets(10));
        procedures.getChildren().addAll(
            createProcedureStep("/asset/femalebeautyimage/femalebeauty.jpg", "Consultation", "Understanding face shape, style preference, and hair texture."),
            createProcedureStep("/asset/babysitter/stylinghair.jpg", "Trimming & Styling", "Final touch-up and setting using gel or spray.")
        );

        Label setupLabel = new Label("Setup at Home");
        setupLabel.setFont(Font.font("Arial", screenHeight * 0.03));

        ImageView setupImg = new ImageView(loadImage("/asset/babysitter/haicutbanner.jpg"));
        setupImg.setFitWidth(screenWidth * 0.3);
        setupImg.setPreserveRatio(true);

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);
        addToCart.setOnAction(e -> {
            Dialog<Pair<LocalDate, String>> dialog = new Dialog<>();
            dialog.setTitle("Select Date and Time");

            DatePicker datePicker = new DatePicker();
            TextField timeField = new TextField();
            timeField.setPromptText("Enter time (e.g. 3:00 PM)");

            VBox dialogVBox = new VBox(10, new Label("Select Date:"), datePicker, new Label("Enter Time:"), timeField);
            dialogVBox.setPadding(new Insets(10));

            dialog.getDialogPane().setContent(dialogVBox);
            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
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
                    String userEmail = CurrentSession.getCurrentUserEmail(); // Replace with actual login logic
                    CartManager.addToCart(
                        userEmail,
                        "Haircut Service",
                        "₹299",
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

        HBox btnBox = new HBox(addToCart);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(20, 0, 10, 0));

        root.getChildren().addAll(
            backBox, banner, title, duration, price, rating,
            overviewTitle, overviewText,
            productTitle, productImg, productDesc,
            procedureLabel, procedures,
            setupLabel, setupImg,
            btnBox
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color:white;");

        return new Scene(scrollPane, screenWidth, screenHeight);
    }

    private VBox createProcedureStep(String imagePath, String title, String description) {
        ImageView img = new ImageView(loadImage(imagePath));
        img.setFitHeight(300);
        img.setFitWidth(400);
        img.setPreserveRatio(true);

        Label stepTitle = new Label(title);
        stepTitle.setFont(Font.font("Arial", 16));

        Label stepDesc = new Label(description);
        stepDesc.setWrapText(true);

        VBox stepBox = new VBox(8, img, stepTitle, stepDesc);
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
