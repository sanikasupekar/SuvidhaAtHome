package prerana.example.view;

import java.util.*;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ServiceProviderlogin {

    private BorderPane root;
    private VBox formBox;
    private Stage primaryStage;

    public ServiceProviderlogin(Stage stage) {
        this.primaryStage = stage;
        root = new BorderPane();
        root.setStyle("-fx-background-color: black;");

        formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);

        root.setCenter(formBox);
        BorderPane.setAlignment(formBox, Pos.CENTER);

        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);

        loadLoginForm();
    }

    private void loadLoginForm() {
        formBox.getChildren().clear();
        formBox.setPrefWidth(400);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: lightgrey; -fx-background-radius: 10;");

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
            startpage start = new startpage(primaryStage);
            Scene startScene = new Scene(start.getRoot(),
                    Screen.getPrimary().getVisualBounds().getWidth(),
                    Screen.getPrimary().getVisualBounds().getHeight());
            primaryStage.setScene(startScene);
           // primaryStage.setFullScreen(true);
        });

        Label mainTitle = new Label("Suvidha@Home");
        mainTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        mainTitle.setTextFill(Color.BLACK);

        Label subTitle = new Label("Sign in to your account");
        subTitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subTitle.setTextFill(Color.DARKGRAY);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(300);
        loginBtn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold;");
loginBtn.setOnAction(e -> {
    String username = usernameField.getText().trim();
    String password = passwordField.getText().trim();

    if (username.isEmpty() || password.isEmpty()) {
        showAlert("Login Failed", "Please enter both username and password.");
        return;
    }

    try {
        Firestore db = FirebaseInitializer.initialize();
        QuerySnapshot snapshot = db.collection("ServiceProvider")
                .whereEqualTo("username", username)
                .whereEqualTo("password", password)
                .get().get();

        if (snapshot.isEmpty()) {
            showAlert("Login Failed", "Invalid username or password.");
            return;
        }

        DocumentSnapshot doc = snapshot.getDocuments().get(0);
        if (doc == null || !doc.exists()) {
            showAlert("Login Failed", "User data not found.");
            return;
        }

        String actualEmail = doc.contains("email") ? doc.getString("email") : null;
        Object serviceTypeObj = doc.contains("serviceType") ? doc.get("serviceType") : null;
        String businessName = doc.contains("businessName") ? doc.getString("businessName") : null;

        if (actualEmail == null || serviceTypeObj == null || businessName == null) {
            showAlert("Login Failed", "Incomplete provider data. Please check Firestore records.");
            return;
        }

        CurrentSession.setCurrentServiceProviderEmail(actualEmail);

        List<String> serviceTypes = new ArrayList<>();
        if (serviceTypeObj instanceof List<?>) {
            for (Object item : (List<?>) serviceTypeObj) {
                if (item instanceof String) {
                    serviceTypes.add((String) item);
                }
            }
        } else if (serviceTypeObj instanceof String) {
            serviceTypes = Collections.singletonList((String) serviceTypeObj);
        }

        Map<String, Object> providerLoginData = new HashMap<>();
        providerLoginData.put("name", username);
        providerLoginData.put("email", actualEmail);
        providerLoginData.put("businessName", businessName);
        providerLoginData.put("serviceType", serviceTypes);
        providerLoginData.put("loginTime", System.currentTimeMillis());

        db.collection("ActiveProviders")
                .document(actualEmail)
                .set(providerLoginData)
                .get();

        showAlert("Login Successful", "Welcome, " + username + "!");

        ServiceProviderDashboardView dashboard = new ServiceProviderDashboardView(primaryStage);
        Scene dashboardScene = new Scene(dashboard.getRoot(),
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(dashboardScene);
        primaryStage.setFullScreen(true);

    } catch (Exception ex) {
        ex.printStackTrace();
        showAlert("Error", "Could not login. Please try again.\nReason: " + ex.getMessage());
    }
});


        Label switchLabel = new Label("Don't have an account? Sign up");
        switchLabel.setTextFill(Color.DARKBLUE);
        switchLabel.setStyle("-fx-underline: true;");
        switchLabel.setOnMouseClicked(e -> loadSignupForm());

        formBox.getChildren().addAll(
                backBtn, mainTitle, subTitle,
                usernameField, passwordField,
                loginBtn, switchLabel
        );
    }

    private void loadSignupForm() {
        formBox.getChildren().clear();
        formBox.setPrefWidth(600);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label mainTitle = new Label("Suvidha@Home");
        mainTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        mainTitle.setTextFill(Color.BLACK);

        Label subTitle = new Label("Create a new account");
        subTitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        subTitle.setTextFill(Color.DARKGRAY);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(400);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(400);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(400);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(400);

        TextField businessNameField = new TextField();
        businessNameField.setPromptText("Business Name");
        businessNameField.setMaxWidth(400);

        Label servicesLabel = new Label("Select Service Types:");
        servicesLabel.setTextFill(Color.BLACK);
        servicesLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

        // Updated layout for services
        TilePane serviceTypePane = new TilePane();
        serviceTypePane.setAlignment(Pos.TOP_CENTER);
        serviceTypePane.setHgap(20);
        serviceTypePane.setVgap(10);
        serviceTypePane.setPrefColumns(3);
        serviceTypePane.setPadding(new Insets(5));

        List<CheckBox> serviceCheckboxes = new ArrayList<>();
        String[] services = {
            "Facial & Haircut Combo", "Glow & Radiance Facial", "Haircut Service",
            "Male CleanUp Service", "Men's Facial Service", "Male Haircut and Facial Combo",
            "Male Luxury Pack", "Male Massage Service", "Male Premium Facial",
            "Manicure Service", "Manicure Pedicure Combo", "Pedicure Service",
            "Spa Combo", "Spa Service Details", "Full Body Waxing", "Wax Pack Combo",
            "Male Grooming Service", "Beard Cleanup Combo", "Men's Haircut & Styling"
        };

        for (String s : services) {
            CheckBox cb = new CheckBox(s);
            cb.setTextFill(Color.BLACK);
            cb.setStyle("-fx-font-size: 13px;");
            serviceCheckboxes.add(cb);
            serviceTypePane.getChildren().add(cb);
        }

        ScrollPane scrollPane = new ScrollPane(serviceTypePane);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(180);
        scrollPane.setStyle("-fx-background: transparent;");

        VBox servicesContainer = new VBox(6, servicesLabel, scrollPane);
        servicesContainer.setAlignment(Pos.CENTER_LEFT);

        TextField addressField = new TextField();
        addressField.setPromptText("Address / Location");
        addressField.setMaxWidth(400);

        TextField contactField = new TextField();
        contactField.setPromptText("Contact Info");
        contactField.setMaxWidth(400);

        TextField experienceField = new TextField();
        experienceField.setPromptText("Years of Experience");
        experienceField.setMaxWidth(400);

        Button signupBtn = new Button("Register");
        signupBtn.setPrefWidth(400);
        signupBtn.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-weight: bold;");

        signupBtn.setOnAction(e -> {
            if (usernameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                    passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty() ||
                    businessNameField.getText().isEmpty() ||
                    addressField.getText().isEmpty() || contactField.getText().isEmpty() ||
                    experienceField.getText().isEmpty()) {

                showAlert("Registration Failed", "All fields are required.");
                return;
            } else if (!passwordField.getText().equals(confirmPasswordField.getText())) {
                showAlert("Registration Failed", "Passwords do not match.");
                return;
            }

            List<String> selectedServices = new ArrayList<>();
            for (CheckBox cb : serviceCheckboxes) {
                if (cb.isSelected()) {
                    selectedServices.add(cb.getText());
                }
            }

            if (selectedServices.isEmpty()) {
                showAlert("Registration Failed", "Please select at least one service.");
                return;
            }

            Map<String, Object> serviceProviderData = new HashMap<>();
            serviceProviderData.put("username", usernameField.getText());
            serviceProviderData.put("email", emailField.getText());
            serviceProviderData.put("password", passwordField.getText());
            serviceProviderData.put("businessName", businessNameField.getText());
            serviceProviderData.put("serviceType", selectedServices);
            serviceProviderData.put("address", addressField.getText());
            serviceProviderData.put("contact", contactField.getText());
            serviceProviderData.put("experience", experienceField.getText());

            try {
                Firestore db = FirebaseInitializer.initialize();

                db.collection("ServiceProvider")
                        .document(emailField.getText())
                        .set(serviceProviderData)
                        .get();

                Map<String, Object> activeProviderData = new HashMap<>();
                activeProviderData.put("username", usernameField.getText());
                activeProviderData.put("email", emailField.getText());
                activeProviderData.put("serviceType", selectedServices);
                activeProviderData.put("businessName", businessNameField.getText());
                activeProviderData.put("loginTime", System.currentTimeMillis());

                db.collection("ActiveProviders")
                        .document(emailField.getText())
                        .set(activeProviderData)
                        .get();

                showAlert("Registration Successful", "Welcome, " + businessNameField.getText() + "!");

                CurrentSession.setCurrentServiceProviderEmail(emailField.getText());

                ServiceProviderDashboardView dashboard = new ServiceProviderDashboardView(primaryStage);
                Scene dashboardScene = new Scene(dashboard.getRoot(),
                        Screen.getPrimary().getVisualBounds().getWidth(),
                        Screen.getPrimary().getVisualBounds().getHeight());
                primaryStage.setScene(dashboardScene);
                primaryStage.setFullScreen(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to save data: " + ex.getMessage());
            }
        });

        Label switchLabel = new Label("Already have an account? Login");
        switchLabel.setTextFill(Color.DARKBLUE);
        switchLabel.setStyle("-fx-underline: true;");
        switchLabel.setOnMouseClicked(e -> loadLoginForm());

        formBox.getChildren().addAll(
                mainTitle, subTitle,
                usernameField, emailField, passwordField, confirmPasswordField,
                businessNameField, servicesContainer,
                addressField, contactField, experienceField,
                signupBtn, switchLabel
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getRoot() {
        return root;
    }
}
