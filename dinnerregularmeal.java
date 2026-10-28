package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class dinnerregularmeal {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: lightgrey");

        // Back Button
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
            RegularMealPage regularMealPage = new RegularMealPage();
            Scene regularScene = regularMealPage.createScene();
            startpage.primaryStage.setScene(regularScene);
        });

        // Heading
        Label heading = new Label("Dinner Regular Meal");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, screenHeight * 0.035));
        heading.setTextFill(Color.web("#33691e"));

        // Description
        Label description = new Label("Enjoy a delicious and healthy dinner meal, prepared with fresh ingredients by our expert cooks. Perfect for a comforting end to your day.");
        description.setWrapText(true);
        description.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        description.setTextFill(Color.web("#444"));
        description.setMaxWidth(screenWidth * 0.6);
        description.setAlignment(Pos.CENTER);

        // Add to Cart Button
        Button addToCart = new Button("Add To Cart");
        addToCart.setStyle("-fx-background-color: #B1005E; -fx-text-fill: white; -fx-font-size: 18;");
        addToCart.setMinWidth(250);

        addToCart.setOnAction(e -> {
            String userEmail = CurrentSession.getCurrentUserEmail();
            if (userEmail == null || userEmail.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please log in first to add to cart.");
                return;
            }

            // Select Date
            DatePicker datePicker = new DatePicker();
            Alert dateAlert = new Alert(Alert.AlertType.CONFIRMATION);
            dateAlert.setTitle("Select Date");
            dateAlert.setHeaderText("Choose your preferred date:");
            dateAlert.getDialogPane().setContent(datePicker);

            Optional<ButtonType> dateResult = dateAlert.showAndWait();
            if (dateResult.isPresent() && dateResult.get() == ButtonType.OK) {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate == null) {
                    showAlert(Alert.AlertType.ERROR, "Please select a valid date.");
                    return;
                }

                // Select Time
                List<String> timeSlots = Arrays.asList("09:00 AM", "11:00 AM", "01:00 PM", "03:00 PM");
                ChoiceDialog<String> timeDialog = new ChoiceDialog<>(timeSlots.get(0), timeSlots);
                timeDialog.setTitle("Select Time");
                timeDialog.setHeaderText("Choose your preferred time:");

                Optional<String> selectedTime = timeDialog.showAndWait();
                if (selectedTime.isPresent()) {
                    String serviceName = "Dinner Regular Meal";
                    String price = "549";
                    String timeRequired = "45 mins";

                    CartManager.addToCart(userEmail, serviceName, price, timeRequired,
                            selectedDate.toString(), selectedTime.get());

                    showAlert(Alert.AlertType.INFORMATION, "Service added to cart successfully.");
                }
            }
        });

        root.getChildren().addAll(backBtn, heading, description, addToCart);
        return new Scene(root, screenWidth, screenHeight);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}
