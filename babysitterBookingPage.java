// package prerana.example.view;



// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.DatePicker;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.stage.Stage;

// public class babysitterBookingPage extends Application {

//     Stage mainStage;

//     public babysitterBookingPage() {
//         this.mainStage = mainStage;
//     }

//     @Override
//     public void start(Stage bookingStage) {
        
//         Color textColor = Color.web("black"); 

//         Label nameLabel = new Label("Enter Your Name:");
//         nameLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: " + toRgbString(textColor) + "; -fx-font-weight: bold;");

//         TextField nameField = new TextField();
//         nameField.setPromptText("Enter your name");
//         nameField.setMaxWidth(320);
//         nameField.setPrefHeight(35);
//         nameField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8; -fx-padding: 0 10 0 10;");

//         Label dateLabel = new Label("Booking Date:");
//         dateLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: " + toRgbString(textColor) + "; -fx-font-weight: bold;");

//         DatePicker datePicker = new DatePicker();
//         datePicker.setMaxWidth(320);
//         datePicker.setPrefHeight(35);
//         datePicker.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

//         Label agePreferenceLabel = new Label("Preferred Babysitter Age:");
//         agePreferenceLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: " + toRgbString(textColor) + "; -fx-font-weight: bold;");

//         ComboBox<String> ageComboBox = new ComboBox<>();
//         ageComboBox.getItems().addAll(
//                 "18 - 22",
//                 "23 - 28",
//                 "29 - 35",
//                 "36+"
//         );
//         ageComboBox.setPromptText("Select Age Group");
//         ageComboBox.setMaxWidth(320);
//         ageComboBox.setPrefHeight(35);
//         ageComboBox.setStyle(
//             "-fx-font-size: 16px;" +
//             "-fx-background-radius: 8;" +
//             "-fx-border-radius: 8;"
//         );

//         Button confirmButton = new Button("Confirm Booking");
//         confirmButton.setMaxWidth(320);
//         confirmButton.setPrefHeight(40);
//         confirmButton.setStyle(
//             "-fx-background-color: rgba(0, 30, 255, 0.8);" +
//             "-fx-text-fill: #002233;" +
//             "-fx-font-weight: bold;" +
//             "-fx-background-radius: 10;"
//         );
//         confirmButton.setOnMouseEntered(e -> 
//             confirmButton.setStyle(
//                 "-fx-background-color: rgba(0, 20, 204, 0.9);" +
//                 "-fx-text-fill: #001a29;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;"
//             )
//         );
//         confirmButton.setOnMouseExited(e -> 
//             confirmButton.setStyle(
//                 "-fx-background-color: rgba(0, 4, 255, 0.8);" +
//                 "-fx-text-fill: #002233;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;"
//             )
//         );

//         Button homeButton = new Button("Go to Home Page");
//         homeButton.setMaxWidth(320);
//         homeButton.setPrefHeight(40);
//         homeButton.setStyle(
//             "-fx-background-color: rgba(0, 77, 64, 0.8);" +
//             "-fx-text-fill: #aaffff;" +
//             "-fx-font-weight: bold;" +
//             "-fx-background-radius: 10;"
//         );
//         homeButton.setVisible(false);
//         homeButton.setOnMouseEntered(e -> 
//             homeButton.setStyle(
//                 "-fx-background-color: rgba(0, 51, 46, 0.9);" +
//                 "-fx-text-fill: #88cccc;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;"
//             )
//         );
//         homeButton.setOnMouseExited(e -> 
//             homeButton.setStyle(
//                 "-fx-background-color: rgba(0, 77, 64, 0.8);" +
//                 "-fx-text-fill: #aaffff;" +
//                 "-fx-font-weight: bold;" +
//                 "-fx-background-radius: 10;"
//             )
//         );

//         confirmButton.setOnAction(e -> {
//             String name = nameField.getText();
//             String date = datePicker.getValue() != null ? datePicker.getValue().toString() : "No date selected";
//             String ageGroup = ageComboBox.getValue() != null ? ageComboBox.getValue() : "No age selected";

//             if (!name.isEmpty() && datePicker.getValue() != null && ageComboBox.getValue() != null) {
//                 Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
//                 confirmAlert.setTitle("Confirm Booking");
//                 confirmAlert.setHeaderText("Are you sure you want to confirm this booking?");
//                 confirmAlert.setContentText("Name: " + name + "\nDate: " + date + "\nPreferred Age: " + ageGroup);

//                 confirmAlert.showAndWait().ifPresent(response -> {
//                     if (response.getText().equals("OK")) {
//                         Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                         alert.setTitle("Booking Confirmed");
//                         alert.setHeaderText(null);
//                         alert.setContentText("Booking Successful!\nThank you, " + name + "!");
//                         alert.showAndWait();
//                         homeButton.setVisible(true);
//                     }
//                 });

//             } else {
//                 Alert alert = new Alert(Alert.AlertType.WARNING);
//                 alert.setTitle("Incomplete Form");
//                 alert.setHeaderText(null);
//                 alert.setContentText("Please fill all fields before confirming.");
//                 alert.showAndWait();
//             }
//         });

//         homeButton.setOnAction(e -> {
//             bookingStage.close();
//             mainStage.show();
//         });

//         VBox layout = new VBox(20);
//         layout.getChildren().addAll(
//                 nameLabel, nameField,
//                 dateLabel, datePicker,
//                 agePreferenceLabel, ageComboBox,
//                 confirmButton, homeButton
//         );
//         layout.setPadding(new Insets(30));
//         layout.setAlignment(Pos.CENTER);

        
//         layout.setStyle(
//             "-fx-background-image: url('Assets/Images/bg.jpg');" +
//             "-fx-background-size: cover;" +
//             "-fx-background-repeat: no-repeat;" +
//             "-fx-background-position: center center;"
//         );

//         Scene scene = new Scene(layout, 900, 600);
//         bookingStage.setTitle("Book Babysitter");
//         bookingStage.setScene(scene);
//         bookingStage.show();
//     }

    
//     private String toRgbString(Color c) {
//         return String.format("rgba(%d, %d, %d, %.2f)", 
//             (int)(c.getRed()*255), 
//             (int)(c.getGreen()*255), 
//             (int)(c.getBlue()*255),
//             c.getOpacity()
//         );
//     }
// }
