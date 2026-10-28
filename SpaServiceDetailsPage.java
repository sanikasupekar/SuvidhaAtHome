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

import java.time.LocalDate;

public class SpaServiceDetailsPage {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        VBox mainVBox = new VBox(20);
        mainVBox.setPadding(new Insets(20));
        mainVBox.setAlignment(Pos.TOP_CENTER);
        mainVBox.setStyle("-fx-background-color:lightgrey;");

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

        // Top Banner Image
        ImageView topImage = loadImage("/asset/spaservicefemale/spa_banner.jpg", screenWidth * 0.3);

        // Title & Info
        Label title = new Label("Head To Toe Pain Relief");
        title.setFont(new Font("Arial", 28));

        Label duration = new Label("1 hr 30 mins");
        duration.setFont(new Font("Arial", 18));

        Label priceLabel = new Label("₹1379   ₹2500   44% OFF");
        priceLabel.setFont(new Font("Arial", 18));

        Label rating = new Label("★★★★☆");
        rating.setFont(new Font("Arial", 18));

        // Overview
        Label overviewHeading = new Label("Pain Relief Therapy: An Overview");
        overviewHeading.setFont(new Font("Arial", 22));

        Label overviewText = new Label(
            "Full body therapy with head and foot massage to relieve soreness caused by stress or workout.");
        overviewText.setWrapText(true);

        // Oil Section
        Label oilHeading = new Label("Your Spa Oil");
        oilHeading.setFont(new Font("Arial", 22));

        ImageView oilImg = loadImage("/asset/spaservicefemale/spaoil.jpg", screenWidth * 0.3);

        Label oilText = new Label(
            "Pain Relief Oil - Reduces soreness. FYC Amla Oil - Strengthens hair & improves moisture.");
        oilText.setWrapText(true);

        // Procedure
        Label procedureHeading = new Label("Procedure");
        procedureHeading.setFont(new Font("Arial", 22));

        GridPane procedureGrid = new GridPane();
        procedureGrid.setHgap(20);
        procedureGrid.setVgap(20);
        procedureGrid.setPadding(new Insets(10));
        procedureGrid.setAlignment(Pos.CENTER);

        VBox step1 = createProcedureStep("/asset/spaservicefemale/backmsgspa.jpg", "Stomach", "Kneading on stomach to improve gut health.");
        VBox step2 = createProcedureStep("/asset/spaservicefemale/spaarm.jpg", "Arms & Decollete", "Palm and finger rotations, biceps & forearm kneading.");
        VBox step3 = createProcedureStep("/asset/spaservicefemale/backmsgspa.jpg", "Back & Leg Massage", "Kneading and thumb movements on back and legs.");
        VBox step4 = createProcedureStep("/asset/spaservicefemale/spahead3.jpg", "Head Massage with Oil", "Zig-zag motion on scalp to relieve tension.");
        VBox step5 = createProcedureStep("/asset/spaservicefemale/spafeet.jpg", "Feet Massage", "Thumb movement and pressure on soles.");

        procedureGrid.add(step1, 0, 0);
        procedureGrid.add(step2, 1, 0);
        procedureGrid.add(step3, 0, 1);
        procedureGrid.add(step4, 1, 1);
        procedureGrid.add(step5, 0, 2, 2, 1); // Span across two columns

        // Add to Cart Button
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("""
            -fx-background-color: #B1005E;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-padding: 10 30;
        """);
        addToCart.setOnAction(e -> {
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(LocalDate.now());

            TextField timeField = new TextField();
            timeField.setPromptText("Enter time (e.g. 4:00 PM)");

            VBox vbox = new VBox(10, new Label("Select Booking Date:"), datePicker,
                    new Label("Enter Booking Time:"), timeField);
            vbox.setPadding(new Insets(10));

            Alert inputDialog = new Alert(Alert.AlertType.CONFIRMATION);
            inputDialog.setTitle("Booking Details");
            inputDialog.getDialogPane().setContent(vbox);
            inputDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String selectedDate = datePicker.getValue().toString();
                    String selectedTime = timeField.getText();
                    if (selectedTime != null && !selectedTime.trim().isEmpty()) {
                        String userEmail = "user@example.com"; // Replace this with actual email
                        String serviceName = "Spa Service Details";
                        String actualPrice = "1379";
                        String timeRequired = "1 hrs 30 min";

                        CartManager.addToCart(userEmail, serviceName, actualPrice, timeRequired, selectedDate, selectedTime);

                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                        alert.showAndWait();
                    } else {
                        Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a valid time.", ButtonType.OK);
                        error.showAndWait();
                    }
                }
            });
        });

        buttonBox.getChildren().add(addToCart);

        mainVBox.getChildren().addAll(
            backBox, topImage, title, duration, priceLabel, rating,
            overviewHeading, overviewText,
            oilHeading, oilImg, oilText,
            procedureHeading, procedureGrid,
            buttonBox
        );

        ScrollPane scrollPane = new ScrollPane(mainVBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color:white;");

        return new Scene(scrollPane, screenWidth, screenHeight);
    }

    private ImageView loadImage(String path, double width) {
        try {
            Image img = new Image(getClass().getResource(path).toExternalForm());
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(width);
            imageView.setPreserveRatio(true);
            return imageView;
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            return new ImageView();
        }
    }

    private VBox createProcedureStep(String imagePath, String title, String description) {
        ImageView img = loadImage(imagePath, 300);

        Label heading = new Label(title);
        heading.setFont(new Font("Arial", 18));

        Label desc = new Label(description);
        desc.setWrapText(true);

        VBox stepBox = new VBox(8, img, heading, desc);
        stepBox.setPadding(new Insets(10));
        stepBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 10; -fx-background-radius: 10;");
        return stepBox;
    }
}
