package prerana.example.view;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
//import prerana.example.view.CurrentSession;

import java.util.concurrent.CompletableFuture;

public class serviceProviderProfile {

    private Label usernameLabel;
    private Label emailLabel;
    private Label addressLabel;
    private Label contactLabel;
    private Label businessLabel;
    private Label serviceTypeLabel;
    private Label experienceLabel;

    public Scene createScene(Stage primaryStage) {
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
            ServiceProviderDashboardView dash = new ServiceProviderDashboardView(primaryStage);
            primaryStage.setScene(new Scene(dash.getRoot(),
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight()));
        });

        Label profileLogo = new Label("👤");
        profileLogo.setStyle("-fx-font-size: 120px; -fx-text-fill: #2C3E50;");

        usernameLabel = new Label("Username: Loading...");
        emailLabel = new Label("Email: Loading...");
        addressLabel = new Label("Address: Loading...");
        contactLabel = new Label("Contact: Loading...");
        businessLabel = new Label("Business Name: Loading...");
        serviceTypeLabel = new Label("Service Type: Loading...");
        experienceLabel = new Label("Experience: Loading...");

        String style = "-fx-font-size: 22px; -fx-text-fill: #34495E; -fx-font-weight: bold;";
        usernameLabel.setStyle(style);
        emailLabel.setStyle(style);
        addressLabel.setStyle(style);
        contactLabel.setStyle(style);
        businessLabel.setStyle(style);
        serviceTypeLabel.setStyle(style);
        experienceLabel.setStyle(style);

        VBox center = new VBox(15, profileLogo, usernameLabel, emailLabel,
            addressLabel, contactLabel, businessLabel, serviceTypeLabel, experienceLabel);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(30));

        root.setTop(backBtn);
        root.setCenter(center);
        BorderPane.setAlignment(backBtn, Pos.TOP_LEFT);
        BorderPane.setMargin(backBtn, new Insets(10));

        root.setStyle("-fx-background-color: linear-gradient(to bottom, lightgrey, lightgrey); -fx-padding: 20;");

        // Fetch the current provider's data
        String email = CurrentSession.getCurrentServiceProviderEmail();
        fetchProviderData(email);

        Scene scene = new Scene(root,
            Screen.getPrimary().getBounds().getWidth(),
            Screen.getPrimary().getBounds().getHeight());
        primaryStage.setFullScreen(true);
        return scene;
    }

    private void fetchProviderData(String email) {
        if (email == null || email.isEmpty()) {
            usernameLabel.setText("No provider logged in!");
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                Firestore db = FirestoreClient.getFirestore();  // initialized using FirebaseInitializer earlier
                DocumentSnapshot doc = db.collection("ServiceProvider")
                                         .document(email)
                                         .get()
                                         .get();

                if (doc.exists()) {
                    String username = doc.getString("username");
                    String addr = doc.getString("address");
                    String contact = doc.getString("contact");
                    String biz = doc.getString("businessName");
                    String svc = doc.getString("serviceType");
                    String exp = doc.getString("experience");

                    Platform.runLater(() -> {
                        usernameLabel.setText("Username: " + username);
                        emailLabel.setText("Email: " + email);
                        addressLabel.setText("Address: " + addr);
                        contactLabel.setText("Contact: " + contact);
                        businessLabel.setText("Business Name: " + biz);
                        serviceTypeLabel.setText("Service Type: " + svc);
                        experienceLabel.setText("Experience: " + exp + " years");
                    });
                } else {
                    Platform.runLater(() -> usernameLabel.setText("Profile not found!"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> usernameLabel.setText("Error loading profile."));
            }
        });
    }
}
