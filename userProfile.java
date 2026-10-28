package prerana.example.view;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import java.util.concurrent.CompletableFuture;

public class userProfile {

    private String username = "Loading...";
    private String email = "Loading...";
    private String address = "Loading...";
    private String contact = "Loading...";

    private Label usernameLabel = new Label("username: " + username);
    private Label emailLabel = new Label("email: " + email);
    private Label addressLabel = new Label("address: " + address);
    private Label contactLabel = new Label("contact: " + contact);

    public Scene createScene() {
        BorderPane root = new BorderPane();
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
            HomePageUI homePage = new HomePageUI();
            startpage.primaryStage.setScene(homePage.createScene());
        });

        Label profileLogo = new Label("👤");
        profileLogo.setStyle("-fx-font-size: 120px; -fx-text-fill: #2C3E50;");

        String labelStyle = "-fx-font-size: 22px; -fx-font-family: 'Arial'; -fx-text-fill: #34495E; -fx-font-weight: bold;";
        usernameLabel.setStyle(labelStyle);
        emailLabel.setStyle(labelStyle);
        addressLabel.setStyle(labelStyle);
        contactLabel.setStyle(labelStyle);

        VBox centerBox = new VBox(20, profileLogo, usernameLabel, emailLabel, addressLabel, contactLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(30));

        root.setTop(backBtn);
        root.setCenter(centerBox);
        BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
        BorderPane.setMargin(backBtn, new Insets(10));
        root.setStyle("-fx-background-color:lightgrey ; -fx-padding: 20;");

        // Fetch user data
        fetchDataFromFirestore(CurrentSession.getCurrentUserEmail());

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    public void fetchDataFromFirestore(String userId) {
        if (userId == null || userId.isEmpty()) {
            usernameLabel.setText("Invalid User ID");
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                FirebaseInitializer.initialize();
                Firestore db = FirestoreClient.getFirestore();

                DocumentReference docRef = db.collection("Customer").document(userId);
                DocumentSnapshot document = docRef.get().get();

                if (document.exists()) {
                    username = document.getString("username");
                    email = document.getString("email");
                    address = document.getString("address");
                    contact = document.getString("contact");

                    Platform.runLater(() -> {
                        usernameLabel.setText("username: " + username);
                        emailLabel.setText("email: " + email);
                        addressLabel.setText("address: " + address);
                        contactLabel.setText("contact: " + contact);
                    });
                } else {
                    Platform.runLater(() -> usernameLabel.setText("User not found!"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> usernameLabel.setText("Error fetching data!"));
            }
        });
    }
}
