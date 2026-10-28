package prerana.example.view;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;

public class AdminBookingPage {

    private TableView<Map<String, Object>> table;
    private ObservableList<Map<String, Object>> bookingData = FXCollections.observableArrayList();

    @SuppressWarnings("unchecked")
    public Scene createScene(Stage primaryStage) {
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Map<String, Object>, String> userEmailCol = new TableColumn<>("User Email");
        userEmailCol.setMinWidth(150);
        userEmailCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> (String) cellData.getValue().get("userEmail")));

        TableColumn<Map<String, Object>, String> serviceNameCol = new TableColumn<>("Service Name");
        serviceNameCol.setMinWidth(150);
        serviceNameCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> (String) cellData.getValue().get("serviceName")));

        TableColumn<Map<String, Object>, String> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(100);
        dateCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> (String) cellData.getValue().get("date")));

        TableColumn<Map<String, Object>, String> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(
                () -> (String) cellData.getValue().get("time")));

        TableColumn<Map<String, Object>, String> statusCol = new TableColumn<>("Request Status");
        statusCol.setMinWidth(120);
        statusCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            Object status = cellData.getValue().get("status");
            return status != null ? status.toString() : "";
        }));

        // Action Button Column
        TableColumn<Map<String, Object>, Void> actionCol = new TableColumn<>("Action");
        actionCol.setMinWidth(120);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button sendRequestBtn = new Button("Send Request");

            {
                sendRequestBtn.setOnAction(event -> {
                    Map<String, Object> booking = getTableView().getItems().get(getIndex());
                    sendRequestToProvider(booking);
                    booking.put("status", "Request Sent");
                    table.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Map<String, Object> booking = getTableView().getItems().get(getIndex());
                    Object status = booking.get("status");
                    if ("Request Sent".equals(status)) {
                        sendRequestBtn.setText("Request Sent");
                        sendRequestBtn.setDisable(true);
                    } else {
                        sendRequestBtn.setText("Send Request");
                        sendRequestBtn.setDisable(false);
                    }
                    setGraphic(sendRequestBtn);
                }
            }
        });

        // Message Column
        TableColumn<Map<String, Object>, String> messageCol = new TableColumn<>("Message");
        messageCol.setMinWidth(180);
        messageCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            Object status = cellData.getValue().get("status");
            if ("Request Sent".equals(status)) {
                return "Request sent to provider";
            } else {
                return "";
            }
        }));

        table.getColumns().addAll(userEmailCol, serviceNameCol, dateCol, timeCol, statusCol, actionCol, messageCol);
        table.setItems(bookingData);

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> {
            adminDashboard dashboardView = new adminDashboard(primaryStage);
            Scene dashboardScene = dashboardView.createScene();
            primaryStage.setScene(dashboardScene);
        });

        Label title = new Label("Booking Orders");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox topBar = new HBox(10, backBtn, title);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 20, 10, 20));
        HBox.setMargin(title, new Insets(0, 0, 0, 20));

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30));
        layout.getChildren().addAll(topBar, table);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        loadBookings();

        return new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private void loadBookings() {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("bookings").get();

        new Thread(() -> {
            try {
                List<QueryDocumentSnapshot> documents = future.get().getDocuments();
                bookingData.clear();
                for (DocumentSnapshot doc : documents) {
                    Map<String, Object> booking = doc.getData();
                    bookingData.add(booking);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendRequestToProvider(Map<String, Object> booking) {
        Firestore db = FirestoreClient.getFirestore();

        String userEmail = (String) booking.get("userEmail");
        String bookingService = (String) booking.get("serviceName");
        String bookingDate = (String) booking.get("date");
        String bookingTime = (String) booking.get("time");

        ApiFuture<QuerySnapshot> checkQuery = db.collection("provider_requests")
                .whereEqualTo("userEmail", userEmail)
                .whereEqualTo("serviceName", bookingService)
                .whereEqualTo("date", bookingDate)
                .whereEqualTo("time", bookingTime)
                .get();

        try {
            List<QueryDocumentSnapshot> existingRequests = checkQuery.get().getDocuments();
            if (!existingRequests.isEmpty()) {
                System.out.println("Request already exists.");
                return;
            }

            CollectionReference providersRef = db.collection("ActiveProviders");
            ApiFuture<QuerySnapshot> allProvidersQuery = providersRef.get();
            List<QueryDocumentSnapshot> allProviders = allProvidersQuery.get().getDocuments();

            List<DocumentSnapshot> matchingProviders = new ArrayList<>();
            for (DocumentSnapshot provider : allProviders) {
                List<String> services = new ArrayList<>();
                Object serviceField = provider.get("serviceType");
                if (serviceField instanceof String) {
                    services = Collections.singletonList((String) serviceField);
                } else if (serviceField instanceof List) {
                    for (Object item : (List<?>) serviceField) {
                        if (item instanceof String) services.add((String) item);
                    }
                }
                if (services.contains(bookingService)) {
                    matchingProviders.add(provider);
                }
            }

            if (matchingProviders.isEmpty()) {
                System.out.println("No providers available for: " + bookingService);
                return;
            }

            DocumentSnapshot chosenProvider = matchingProviders.get(0);
            String chosenProviderEmail = chosenProvider.getString("email");

            Map<String, Object> providerRequest = new HashMap<>();
            providerRequest.put("serviceName", bookingService);
            providerRequest.put("userEmail", userEmail);
            providerRequest.put("date", bookingDate);
            providerRequest.put("time", bookingTime);
            providerRequest.put("status", "REQUEST_SENT");
            providerRequest.put("assignedProvider", chosenProviderEmail);

            db.collection("provider_requests").add(providerRequest);
            System.out.println("Request sent to: " + chosenProviderEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 