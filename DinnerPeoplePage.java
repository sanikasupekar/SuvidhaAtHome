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

public class DinnerPeoplePage {

    private boolean fromPartyMeal;

    public Scene createSceneFromRegularMeal() {
        fromPartyMeal = false;
        return createScene();
    }

    public Scene createSceneFromPartyMeal() {
        fromPartyMeal = true;
        return createScene();
    }

    private Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color:lightgrey;");

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
            if (fromPartyMeal) {
                PartyMealPage partyMealPage = new PartyMealPage();
                Scene partyMealScene = partyMealPage.createScene();
                startpage.primaryStage.setScene(partyMealScene);
            } else {
                RegularMealPage regularMealPage = new RegularMealPage();
                Scene regularMealScene = regularMealPage.createScene();
                startpage.primaryStage.setScene(regularMealScene);
            }
        });

        // Heading
        Label heading = new Label("Dinner Meal Service");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, screenHeight * 0.045));
        heading.setTextFill(Color.web("#33691e"));

        // Description
        Label description = new Label("Enjoy a chef-prepared dinner service perfect for individuals, families, or small gatherings. Fresh, delicious, and hassle-free.");
        description.setWrapText(true);
        description.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        description.setTextFill(Color.web("#444"));
        description.setMaxWidth(screenWidth * 0.6);
        description.setAlignment(Pos.CENTER);

        // Add to Cart Button
        Button addToCart = new Button("Add to Cart");
        addToCart.setStyle(
                "-fx-background-color: #558b2f; -fx-text-fill: white; -fx-font-size: 18px; " +
                        "-fx-padding: 12 24; -fx-background-radius: 8; -fx-cursor: hand;"
        );
        addToCart.setOnAction(e -> {
            // Select Date
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

                // Select Time
                List<String> timeSlots = Arrays.asList("10:00 AM", "12:00 PM", "2:00 PM", "4:00 PM", "6:00 PM");
                ChoiceDialog<String> timeDialog = new ChoiceDialog<>(timeSlots.get(0), timeSlots);
                timeDialog.setTitle("Select Time");
                timeDialog.setHeaderText("Choose preferred time slot");

                Optional<String> selectedTime = timeDialog.showAndWait();
                if (selectedTime.isPresent()) {
                    String selectedDateStr = selectedDate.toString();
                    String price = "1500";
                    String timeRequired = "3 hrs";
                    String serviceName = "party meal service";

                    CartManager.addToCart(CurrentSession.getCurrentUserEmail(), serviceName, price, timeRequired, selectedDateStr, selectedTime.get());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Added to cart successfully!", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

        root.getChildren().addAll(backBtn, heading, description, addToCart);
        return new Scene(root, screenWidth, screenHeight);
    }
}
