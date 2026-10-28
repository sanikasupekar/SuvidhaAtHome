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

import java.time.LocalDate;

public class MaleHaircutServicePage {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
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
            MaleServiceUi maleUI = new MaleServiceUi();
            Scene maleScene = maleUI.createScene();
            startpage.primaryStage.setScene(maleScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Men’s Haircut & Styling");
        title.setFont(Font.font("Arial", 32));
        Label duration = new Label("30 mins");
        Label price = new Label("₹299");
        Label rating = new Label("★★★★☆");

        Label overviewTitle = new Label("Service Overview");
        overviewTitle.setFont(Font.font("Arial", 24));
        Label overviewText = new Label(
            "Get a sharp, stylish haircut from professional barbers trained to work with all hair types and face shapes. Includes consultation and styling."
        );
        overviewText.setWrapText(true);
        overviewText.setFont(Font.font("Arial", 20));

        Label productTitle = new Label("Tools & Products Used");
        productTitle.setFont(Font.font("Arial", 24));
        ImageView productImg = new ImageView(loadImage("/asset/malehaircut/downloadproducts haircut.jpg"));
        productImg.setFitWidth(300);
        productImg.setFitHeight(250);
        productImg.setPreserveRatio(true);
        Label productDesc = new Label(
            "Hair cutting scissors, trimmer, comb, cape, styling gel, and disinfectant spray."
        );
        productDesc.setWrapText(true);
        productDesc.setFont(Font.font("Arial", 20));

        Label procedureLabel = new Label("Procedure");
        procedureLabel.setFont(Font.font("Arial", 24));

        VBox step1 = new VBox(5, new Label("Step 1"), createProcedureStep(
            "/asset/malehaircut/images haircusallation2.jpg",
            "Hair Consultation",
            "Understand your desired style and suggest suitable cuts for your face shape."
        ));
        VBox step2 = new VBox(5, new Label("Step 2"), createProcedureStep(
            "/asset/malehaircut/download hairtrimmimg.jpg",
            "Haircut & Trimming",
            "Hair is precisely cut using scissors/trimmer as per selected style."
        ));
        VBox step3 = new VBox(5, new Label("Step 3"), createProcedureStep(
            "/asset/malehaircut/images final touchhair.jpg",
            "Final Touch & Styling",
            "Styling with gel or cream followed by clean-up using disinfectant spray."
        ));
        VBox step4 = new VBox(5, new Label("Step 4"), createProcedureStep(
            "/asset/malehaircut/images beardtrimmimg.jpg",
            "Optional Beard Trim",
            "Professional beard trim and shaping if requested."
        ));

        VBox leftColumn = new VBox(20, step1, step3);
        VBox rightColumn = new VBox(20, step2, step4);
        leftColumn.setAlignment(Pos.CENTER);
        rightColumn.setAlignment(Pos.CENTER);

        HBox procedureRow = new HBox(40, leftColumn, rightColumn);
        procedureRow.setAlignment(Pos.CENTER);

        VBox procedures = new VBox(30, procedureRow);

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
                    String userEmail = CurrentSession.getCurrentUserEmail();
                    CartManager.addToCart(
                        userEmail,
                        "Men's Haircut & Styling",
                        "₹299",
                        "30 mins",
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
            backBox, title, duration, price, rating,
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

    private Image loadImage(String path) {
        try {
            var url = getClass().getResource(path);
            if (url == null) {
                System.err.println("[ERROR] Image not found at: " + path);
                return new Image("https://via.placeholder.com/300x250.png?text=Missing+Image");
            }
            return new Image(url.toExternalForm());
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load image: " + path + " - " + e.getMessage());
            return new Image("https://via.placeholder.com/300x250.png?text=Error+Loading");
        }
    }

    private VBox createProcedureStep(String imagePath, String title, String description) {
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
}
