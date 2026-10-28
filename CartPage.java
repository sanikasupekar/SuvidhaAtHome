package prerana.example.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.List;

public class CartPage {

    private VBox cartItemsBox;
    private Label totalPriceLabel;
    private double totalPrice = 0.0;
    private String userEmail;
    private String firstServiceName = null;
    private String selectedDate = null;
    private String selectedTime = null;

    public Scene createScene(String userEmail) {
        this.userEmail = userEmail;

        BorderPane root = new BorderPane();

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
            HomePageUI homePage = new HomePageUI();
            startpage.primaryStage.setScene(homePage.createScene());
        });

        Label title = new Label("🛒 Your Cart");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2C3E50;");

        cartItemsBox = new VBox(15);
        cartItemsBox.setAlignment(Pos.CENTER);
        cartItemsBox.setPadding(new Insets(20));

        totalPriceLabel = new Label("Total: ₹0.0");
        totalPriceLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #34495E;");

        Button placeOrderBtn = new Button("Place Order");
        placeOrderBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 8 16; -fx-background-radius: 5;");
        placeOrderBtn.setOnAction(e -> {
            if (selectedDate == null || selectedTime == null) {
                showAlert("Service date and time not found. Please try re-adding the item.");
            } else {
                placeOrder();
            }
        });

        VBox centerBox = new VBox(20, title, cartItemsBox, totalPriceLabel, placeOrderBtn);
        centerBox.setAlignment(Pos.CENTER);

        root.setTop(backBtn);
        root.setCenter(centerBox);
        BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
        BorderPane.setMargin(backBtn, new Insets(10));
        root.setStyle("-fx-background-color: lightgrey; -fx-padding: 20;");

        fetchCartFromFirestore();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private void fetchCartFromFirestore() {
        cartItemsBox.getChildren().clear();
        totalPrice = 0.0;
        totalPriceLabel.setText("Total: ₹0.0");
        firstServiceName = null;
        selectedDate = null;
        selectedTime = null;

        try {
            Firestore db = FirebaseInitializer.initialize();
            if (db == null) {
                System.err.println("Firestore initialization failed.");
                return;
            }

            ApiFuture<QuerySnapshot> future = db.collection("Carts").document(userEmail).collection("items").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                String serviceName = doc.getString("serviceName");
                String price = doc.getString("price");
                String timeRequired = doc.getString("timeRequired");
                String date = doc.getString("date");
                String time = doc.getString("time");

                if (firstServiceName == null) {
                    firstServiceName = serviceName;
                    selectedDate = date;
                    selectedTime = time;
                }

                double priceValue = 0;
                try {
                    priceValue = Double.parseDouble(price.replace("₹", "").trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                totalPrice += priceValue;

                String itemText = serviceName + " - " + price;
                if (timeRequired != null) itemText += " (" + timeRequired + ")";
                if (date != null && time != null) itemText += " | " + date + " at " + time;

                Label itemLabel = new Label(itemText);
                itemLabel.setStyle("-fx-font-size: 18px;");
                cartItemsBox.getChildren().add(itemLabel);
            }

            totalPriceLabel.setText("Total: ₹" + String.format("%.2f", totalPrice));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void placeOrder() {
        if (firstServiceName == null) {
            System.err.println("No service found in cart!");
            return;
        }

        CartManager.createBooking(userEmail, firstServiceName, selectedDate, selectedTime);

        String formattedTotalPrice = String.format("%.2f", totalPrice);
        PaymentPage paymentPage = new PaymentPage(userEmail, firstServiceName, selectedDate, selectedTime, formattedTotalPrice);
        Scene paymentScene = paymentPage.createScene();
        startpage.primaryStage.setScene(paymentScene);
    }

    public static void clearCart(String userEmail) {
        try {
            Firestore db = FirebaseInitializer.initialize();
            db.collection("Carts").document(userEmail).collection("items")
                .get()
                .get()
                .forEach(doc -> {
                    db.collection("Carts").document(userEmail).collection("items")
                        .document(doc.getId())
                        .delete();
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
