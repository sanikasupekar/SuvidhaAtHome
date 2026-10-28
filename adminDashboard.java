package prerana.example.view;

import com.google.cloud.firestore.*;
//import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class adminDashboard {

    private final Stage primaryStage;
    private ListView<String> userListView;
    private ListView<String> providerListView;
    private final Firestore db;

    public adminDashboard(Stage stage) {
        this.primaryStage = stage;
        //this.db = FirestoreClient.getFirestore();
         Firestore tempDb = null;
    try {
        tempDb = FirebaseInitializer.initialize(); // ✅ Ensure Firebase is initialized here
    } catch (Exception e) {
        e.printStackTrace();
    }

    this.db = tempDb;
    }

    /** Creates and returns the Admin Dashboard Scene */
    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        BorderPane root = new BorderPane();
        root.setPrefSize(screenWidth, screenHeight);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #283E51, #485563);");

        // Title
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");
        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        BorderPane.setMargin(titleLabel, new Insets(20));
        root.setTop(titleLabel);

        // User and Provider Lists
        userListView = new ListView<>();
        VBox userBox = createListBox("Users Logged In", userListView, "#FFD700");

        providerListView = new ListView<>();
        VBox providerBox = createListBox("Service Providers Logged In", providerListView, "#00CED1");

        HBox contentBox = new HBox(50, userBox, providerBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(40));
        root.setCenter(contentBox);

        // Buttons (Bottom)
        Button viewBookingsBtn = new Button("View Bookings");
        viewBookingsBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px;");
        viewBookingsBtn.setOnAction(e -> {
            AdminBookingPage bookingPage = new AdminBookingPage();
            primaryStage.setScene(bookingPage.createScene(primaryStage));
        });

        Button logoutButton = new Button("Back to Start Page");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 18px;");
        logoutButton.setOnAction(e -> {
            startpage start = new startpage(primaryStage);
            primaryStage.setScene(start.createFullScreenScene(start.getRoot()));
            primaryStage.setMaximized(true);
        });

        HBox bottomBox = new HBox(30, viewBookingsBtn, logoutButton);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        root.setBottom(bottomBox);

        startRealTimeListeners();

        return new Scene(root, screenWidth, screenHeight);
    }

    /** Creates a ListBox with a title and ListView */
    private VBox createListBox(String title, ListView<String> listView, String color) {
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: " + color + "; -fx-font-weight: bold;");
        listView.setPrefHeight(300);

        VBox box = new VBox(10, label, listView);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-background-radius: 12;");
        box.setPrefWidth(400);
        return box;
    }

    /** Starts real-time Firestore listeners */
    private void startRealTimeListeners() {
    // Real-time updates for Active Users
    db.collection("Customer").addSnapshotListener((snapshots, e) -> {
        if (e != null || snapshots == null) {
            System.err.println("Error fetching users: " + e);
            return;
        }

        snapshots.getDocuments().forEach(doc -> {
            System.out.println("User Doc Data: " + doc.getData());
            System.out.println("Email Field: " + doc.getString("email"));
        });

        List<String> users = snapshots.getDocuments().stream()
                .map(doc -> {
                    String name = doc.getString("username") != null ? doc.getString("username") : "Unknown";
                    String email = doc.getString("email") != null ? doc.getString("email") : "Unknown";
                    return name + " - " + email;
                })
                .collect(Collectors.toList());

        Platform.runLater(() -> userListView.getItems().setAll(users));
    });

    // Real-time updates for Active Providers
    db.collection("ActiveProviders").addSnapshotListener((snapshots, e) -> {
        if (e != null || snapshots == null) {
            System.err.println("Error fetching providers: " + e);
            return;
        }

        snapshots.getDocuments().forEach(doc -> System.out.println("Provider Doc Data: " + doc.getData()));

       List<String> providers = snapshots.getDocuments().stream()
    .map(doc -> {
        if (doc == null || !doc.exists()) return "Unknown Provider";

        String name = doc.getString("name") != null ? doc.getString("name") : "Unknown";

        String service;
        if (doc.contains("serviceType")) {
            Object serviceObj = doc.get("serviceType");
            if (serviceObj instanceof List<?>) {
                List<?> serviceList = (List<?>) serviceObj;
                service = serviceList.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
            } else {
                service = serviceObj.toString();
            }
        } else if (doc.contains("service")) {
            service = doc.getString("service");
        } else {
            service = "Unknown";
        }

        return name + " - " + service;
    })
    .collect(Collectors.toList());

// Update JavaFX UI
Platform.runLater(() -> providerListView.getItems().setAll(providers));

    });
}
}

