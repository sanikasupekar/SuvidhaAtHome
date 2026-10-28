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

public class HouseCleaningPage {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey");

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
            CleaningStartPage cleaningPage = new CleaningStartPage();
            Scene cleaningScene = cleaningPage.createScene();
            startpage.primaryStage.setScene(cleaningScene);
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        // HEADER IMAGE
        ImageView headerImage = new ImageView(new Image("asset\\housecleanning\\headerhouseimage.jpg"));
        headerImage.setFitWidth(screenWidth * 0.4);
        headerImage.setFitHeight(screenHeight * 0.20);

        Label heading = new Label("Professional House Cleaning Services");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        heading.setTextFill(Color.DARKGREEN);

        Label ratings = new Label("★★★★★");
        ratings.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        ratings.setTextFill(Color.GOLD);

        Label worksHeading = new Label("Our Works");
        worksHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        worksHeading.setTextFill(Color.DARKBLUE);

        HBox worksBox = new HBox(30);
        worksBox.setAlignment(Pos.CENTER);

        VBox work1 = createWorkBox("Kitchen Cleaning", "asset\\housecleanning\\kitchenimage.jpg", "Estimated Time: 2 hours,price:500");
        VBox work2 = createWorkBox("Floor Cleaning", "asset\\housecleanning\\floork.jpg", "Estimated Time: 1.5 hours,price:500");
        VBox work3 = createWorkBox("Living Room Cleaning", "asset\\housecleanning\\livingroom.jpg", "Estimated Time: 2.5 hours,price:500");

        worksBox.getChildren().addAll(work1, work2, work3);

        // ADD TO CART BUTTON
        Button addToCart = new Button("Add to Cart");
        addToCart.setStyle("""
            -fx-background-color: #ff6600;
            -fx-text-fill: white;
            -fx-font-size: 18px;
            -fx-padding: 12 30;
            -fx-background-radius: 8;
        """);

        addToCart.setOnAction(e -> {
            // Step 1: Ask for date
            DatePicker datePicker = new DatePicker();
            Alert dateAlert = new Alert(Alert.AlertType.CONFIRMATION);
            dateAlert.setTitle("Select Date");
            dateAlert.setHeaderText("Please select a preferred date for the service:");
            dateAlert.getDialogPane().setContent(datePicker);

            Optional<ButtonType> result = dateAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate == null) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Date not selected!", ButtonType.OK);
                    errorAlert.showAndWait();
                    return;
                }

                // Step 2: Ask for time
                List<String> timeSlots = Arrays.asList("10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM");
                ChoiceDialog<String> timeDialog = new ChoiceDialog<>(timeSlots.get(0), timeSlots);
                timeDialog.setTitle("Select Time");
                timeDialog.setHeaderText("Choose preferred time slot");

                Optional<String> selectedTime = timeDialog.showAndWait();
                if (selectedTime.isPresent()) {
                    String userEmail = "user@example.com"; // Replace with actual user email
                    String actualPrice = "500";
                    String selectedDateStr = selectedDate.toString();

                    CartManager.addToCart(userEmail, "House Cleaning", actualPrice, "2 hrs", selectedDateStr, selectedTime.get());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        root.getChildren().addAll(topBar, headerImage, heading, ratings, worksHeading, worksBox, addToCart);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: lightblue;");

        Scene scene = new Scene(scrollPane, screenWidth, screenHeight);
        return scene;
    }

    private VBox createWorkBox(String title, String imagePath, String time) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        box.setPrefWidth(300);
        box.setStyle(
            "-fx-background-color:grey; -fx-border-radius: 12; -fx-background-radius: 12; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);"
        );

        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(250);
        imageView.setFitHeight(180);

        Label label = new Label(title);
        label.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        label.setTextFill(Color.DARKRED);

        Label timeLabel = new Label(time);
        timeLabel.setFont(Font.font("Arial", 16));
        timeLabel.setTextFill(Color.DARKGRAY);

        Button bookNow = new Button("Book Now");
        bookNow.setStyle("""
            -fx-background-color: #28a745;
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-padding: 8 20;
            -fx-background-radius: 8;
        """);
        bookNow.setOnAction(e -> System.out.println(title + " booked!"));

        box.getChildren().addAll(imageView, label, timeLabel, bookNow);
        return box;
    }
}
