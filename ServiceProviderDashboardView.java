package prerana.example.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.Executors;

public class ServiceProviderDashboardView {

    private VBox mainLayout;
    private HBox topBar;
    private Button profileButton;
    private VBox allRequestsBox;
    private VBox acceptedRequestsBox;

    private ScrollPane allRequestsScroll;
    private ScrollPane acceptedRequestsScroll;

    public ServiceProviderDashboardView(Stage primaryStage) {
        createTopBar(primaryStage);
        createMainSections();
        setupLayout();
        fetchAssignedRequests();
    }

    private void createTopBar(Stage primaryStage) {
        topBar = new HBox();
        topBar.setStyle("-fx-background-color: lightgrey;");
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);
        topBar.setSpacing(10);

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
            // 
            CurrentSession.clearSession();
new ServiceProviderlogin(primaryStage); // ✔️ This alone is enough

        });

        Text title = new Text("Suvidha at Home - Service Provider");
        title.setStyle("-fx-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        profileButton = new Button("My Profile");
        profileButton.setStyle("-fx-background-color: white; -fx-text-fill: #6200EA; -fx-font-weight: bold;");
        profileButton.setOnAction(e -> {
            serviceProviderProfile profile = new serviceProviderProfile();
            Scene newScene = profile.createScene(primaryStage);
            primaryStage.setScene(newScene);
            //primaryStage.setFullScreen(true);
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: white; -fx-text-fill: #6200EA; -fx-font-weight: bold;");
        logoutButton.setOnAction(e -> {
            try {
                Firestore db = FirebaseInitializer.initialize();
                String email = CurrentSession.getCurrentServiceProviderEmail();
                if (email != null) {
                    db.collection("ActiveProviders").document(email).delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            CurrentSession.clearSession();
            new ServiceProviderlogin(primaryStage); // ✔️ This alone is enough

        });

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        topBar.getChildren().addAll(backBtn, leftSpacer, title, rightSpacer, profileButton, logoutButton);
    }

    private void createMainSections() {
        allRequestsBox = new VBox(10);
        allRequestsBox.setPadding(new Insets(15));
        allRequestsBox.setStyle("-fx-background-color: #f0f0ff; -fx-border-radius: 10; -fx-background-radius: 10;");
        Label allLabel = new Label("All Requests");
        allLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        allRequestsBox.getChildren().add(allLabel);

        allRequestsScroll = new ScrollPane(allRequestsBox);
        allRequestsScroll.setFitToWidth(true);

        acceptedRequestsBox = new VBox(10);
        acceptedRequestsBox.setPadding(new Insets(15));
        acceptedRequestsBox.setStyle("-fx-background-color: #e8f5e9; -fx-border-radius: 10; -fx-background-radius: 10;");
        Label acceptedLabel = new Label("Accepted Requests");
        acceptedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        acceptedRequestsBox.getChildren().add(acceptedLabel);

        acceptedRequestsScroll = new ScrollPane(acceptedRequestsBox);
        acceptedRequestsScroll.setFitToWidth(true);
    }

    private void setupLayout() {
        HBox content = new HBox(20, allRequestsScroll, acceptedRequestsScroll);
        content.setPadding(new Insets(15));
        HBox.setHgrow(allRequestsScroll, Priority.ALWAYS);
        HBox.setHgrow(acceptedRequestsScroll, Priority.ALWAYS);

        mainLayout = new VBox(topBar, content);
        VBox.setVgrow(content, Priority.ALWAYS);
    }

    public Parent getRoot() {
        return mainLayout;
    }

    public Scene createFullScreenScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        return new Scene(getRoot(), screenWidth, screenHeight);
    }

    private void fetchAssignedRequests() {
        String providerEmail = CurrentSession.getCurrentServiceProviderEmail();
        if (providerEmail == null) {
            System.out.println("No provider email in session.");
            return;
        }

        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Firestore db = FirebaseInitializer.initialize();
                ApiFuture<QuerySnapshot> query = db.collection("provider_requests")
                        .whereEqualTo("assignedProvider", providerEmail)
                        .get();

                List<QueryDocumentSnapshot> documents = query.get().getDocuments();
                System.out.println("Fetched " + documents.size() + " requests for provider: " + providerEmail);

                Platform.runLater(() -> {
                    allRequestsBox.getChildren().clear();
                    Label allLabel = new Label("All Requests");
                    allLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                    allRequestsBox.getChildren().add(allLabel);

                    acceptedRequestsBox.getChildren().clear();
                    Label acceptedLabel = new Label("Accepted Requests");
                    acceptedLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                    acceptedRequestsBox.getChildren().add(acceptedLabel);

                    for (QueryDocumentSnapshot doc : documents) {
                        String serviceName = doc.getString("serviceName");
                        String userEmail = doc.getString("userEmail");
                        String date = doc.getString("date");
                        String time = doc.getString("time");
                        String status = doc.getString("status");

                        // Fallback if null
                        if (serviceName == null) serviceName = "Unknown Service";
                        if (userEmail == null) userEmail = "Unknown User";
                        if (date == null) date = "N/A";
                        if (time == null) time = "N/A";
                        if (status == null) status = "PENDING";

                        VBox card = createRequestCard(serviceName, userEmail, date, time, status, doc.getId());

                        if ("ACCEPTED".equalsIgnoreCase(status)) {
                            acceptedRequestsBox.getChildren().add(card);
                        } else {
                            allRequestsBox.getChildren().add(card);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private VBox createRequestCard(String serviceName, String userEmail, String date, String time, String status, String requestId) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label serviceLbl = new Label("Service: " + serviceName);
        Label userLbl = new Label("User: " + userEmail);
        Label dateLbl = new Label("Date: " + date);
        Label timeLbl = new Label("Time: " + time);
        Label statusLbl = new Label("Status: " + status);

        HBox btnBox = new HBox(10);
        Button acceptBtn = new Button("Accept");
        acceptBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        acceptBtn.setOnAction(e -> acceptRequest(requestId, card));

        Button rejectBtn = new Button("Reject");
        rejectBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        rejectBtn.setOnAction(e -> rejectRequest(requestId, card));

        btnBox.getChildren().addAll(acceptBtn, rejectBtn);

        card.getChildren().addAll(serviceLbl, userLbl, dateLbl, timeLbl, statusLbl, btnBox);
        return card;
    }

    private void rejectRequest(String requestId, VBox card) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Firestore db = FirebaseInitializer.initialize();
                db.collection("provider_requests")
                        .document(requestId)
                        .update("status", "REJECTED")
                        .get();

                Platform.runLater(() -> allRequestsBox.getChildren().remove(card));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void acceptRequest(String requestId, VBox card) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Firestore db = FirebaseInitializer.initialize();
                db.collection("provider_requests")
                        .document(requestId)
                        .update("status", "ACCEPTED")
                        .get();

                Platform.runLater(() -> {
                    allRequestsBox.getChildren().remove(card);
                    for (javafx.scene.Node node : card.getChildren()) {
                        if (node instanceof Label && ((Label) node).getText().startsWith("Status:")) {
                            ((Label) node).setText("Status: ACCEPTED");
                        }
                    }
                    acceptedRequestsBox.getChildren().add(card);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
    }
    
}
