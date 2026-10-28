package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class laundrypage {

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
            CleaningStartPage cleaningPage = new CleaningStartPage();
            Scene cleaningScene = cleaningPage.createScene();
            startpage.primaryStage.setScene(cleaningScene);
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        Label heading = new Label("Premium Laundry Services");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        heading.setTextFill(Color.web("#2c3e50"));

        Label ratings = new Label("★★★★★");
        ratings.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        ratings.setTextFill(Color.web("#ffb300"));

        Label worksHeading = new Label("Our Services");
        worksHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        worksHeading.setTextFill(Color.web("#1a237e"));

        HBox worksBox = new HBox(30);
        worksBox.setAlignment(Pos.CENTER);

        VBox work1 = createWorkBox("Dry Cleaning", "/asset/laundry/drycleanning.jpg", "Estimated Time: 24 hours", "₹250", "★★★★☆", "24 hrs");
        VBox work2 = createWorkBox("Washing & Ironing", "/asset/laundry/ironning.jpg", "Estimated Time: 12 hours", "₹150", "★★★★★", "12 hrs");
        VBox work3 = createWorkBox("Stain Removal", "/asset/laundry/stainremoval.jpg", "Estimated Time: 6 hours", "₹100", "★★★☆☆", "6 hrs");

        worksBox.getChildren().addAll(work1, work2, work3);

        Button addToCart = new Button("Add to Cart");
        addToCart.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12 30; -fx-background-radius: 8;");
        addToCart.setOnAction(e -> System.out.println("Laundry items added to cart!"));

        root.getChildren().addAll(heading, ratings, worksHeading, worksBox, addToCart);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        return new Scene(mainLayout, screenWidth, screenHeight);
    }

    private VBox createWorkBox(String title, String imagePath, String time, String price, String rating, String timeRequired) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(280);
        box.setStyle("-fx-background-color: lightblue; -fx-border-radius: 12; -fx-background-radius: 12; -fx-border-color: #dcdcdc; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 0, 3);");

        ImageView imageView;
        try {
            imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
            imageView = new ImageView(new Image("https://via.placeholder.com/250x180.png?text=No+Image"));
        }
        imageView.setFitWidth(250);
        imageView.setFitHeight(180);

        Label label = new Label(title);
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        label.setTextFill(Color.web("#2c3e50"));

        Label priceLabel = new Label("Price: " + price);
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        priceLabel.setTextFill(Color.web("#1a237e"));

        Label timeLabel = new Label(time);
        timeLabel.setFont(Font.font("Arial", 15));
        timeLabel.setTextFill(Color.DIMGRAY);

        Label ratingLabel = new Label(rating);
        ratingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        ratingLabel.setTextFill(Color.web("#ffb300"));

        Button addtocart = new Button("Add To Cart");
        addtocart.setStyle("-fx-background-color: #16a085; -fx-text-fill: white; -fx-padding: 8 20; -fx-font-size: 14px; -fx-background-radius: 8;");
        addtocart.setOnAction(e -> {
            // Date Picker
            DatePicker datePicker = new DatePicker();
            Alert dateAlert = new Alert(Alert.AlertType.CONFIRMATION);
            dateAlert.setTitle("Select Date");
            dateAlert.setHeaderText("Please select a preferred date for service:");
            dateAlert.getDialogPane().setContent(datePicker);

            Optional<ButtonType> result = dateAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                LocalDate selectedDate = datePicker.getValue();

                if (selectedDate == null) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Date not selected!", ButtonType.OK);
                    errorAlert.showAndWait();
                    return;
                }

                List<String> timeSlots = Arrays.asList("10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM");
                ChoiceDialog<String> timeDialog = new ChoiceDialog<>(timeSlots.get(0), timeSlots);
                timeDialog.setTitle("Select Time");
                timeDialog.setHeaderText("Choose preferred time slot");

                Optional<String> selectedTime = timeDialog.showAndWait();
                if (selectedTime.isPresent()) {
                    String userEmail = "user@example.com"; // Replace with actual logged-in user email
                    String date = selectedDate.toString();
                    String timeStr = selectedTime.get();

                    CartManager.addToCart(userEmail, title, price.replace("₹", ""), timeRequired, date, timeStr);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        box.getChildren().addAll(imageView, label, priceLabel, timeLabel, ratingLabel, addtocart);
        return box;
    }
}
