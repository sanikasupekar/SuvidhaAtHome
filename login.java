package prerana.example.view;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class login {

    private VBox formBox;
    private Stage primaryStage;
    private StackPane rootLayer;

    public login(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Main layout
        HBox mainContainer = new HBox();
        mainContainer.setPrefSize(screenWidth, screenHeight);

        // Left image
       // Left image with top-centered text
ImageView leftImage = new ImageView(new Image(getClass().getResource("/asset/spacombofemale/bg logiiinpageee.jpg").toExternalForm()));
leftImage.setFitWidth(screenWidth / 2);
leftImage.setFitHeight(screenHeight);
leftImage.setPreserveRatio(false);

// Slogan label
Label sloganLabel = new Label("Suvidha at Home – Where Comfort Meets Convenience.");
sloganLabel.setFont(Font.font("Arial", 28));
sloganLabel.setTextFill(Color.BLACK);
//sloganLabel.setStyle("-fx-effect: dropshadow(one-pass-box, black, 5, 0, 2, 2);");
sloganLabel.setPadding(new Insets(120, 20, 20, 20));

// StackPane to overlay text on image
StackPane leftImageContainer = new StackPane(leftImage, sloganLabel);
StackPane.setAlignment(sloganLabel, Pos.TOP_CENTER);

        

        // Form box with curved border
        formBox = new VBox(20);
        formBox.setPadding(new Insets(40));
        formBox.setAlignment(Pos.CENTER_LEFT);

        // Wrap the formBox inside a container with curved background
        VBox formContainer = new VBox(formBox);
        formContainer.setPadding(new Insets(40));
        formContainer.setAlignment(Pos.CENTER_LEFT);
        formContainer.setMaxWidth(500);
        formContainer.setMaxHeight(600);
        formContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(20), Insets.EMPTY)));
        formContainer.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(2))));

        HBox rightContainer = new HBox(formContainer);
        rightContainer.setPrefWidth(screenWidth / 2);
        rightContainer.setAlignment(Pos.CENTER);
         // Center the box in right container
         // Center the box in right container
         rightContainer.setStyle("-fx-background-color: #d3cfcfff;");

        mainContainer.getChildren().addAll(leftImageContainer, rightContainer);
        rootLayer = new StackPane(mainContainer);

        Scene scene = new Scene(rootLayer, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        loadLoginForm();
    }

    private void loadLoginForm() {
        formBox.getChildren().clear();

        Label mainTitle = new Label("Welcome to Suvidha@Home");
        mainTitle.setFont(new Font("Arial", 26));

        Label subTitle = new Label("User Login");
        subTitle.setFont(new Font("Arial", 20));
        subTitle.setTextFill(Color.GREY);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Email");
        usernameField.setMaxWidth(250);
        usernameField.setMaxHeight(50);
        usernameField.setStyle("-fx-font-size: 18px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);
        passwordField.setStyle("-fx-font-size: 18px;");

        CheckBox showPassword = new CheckBox("Show Password");
        showPassword.setStyle("-fx-font-size: 12px;");
        TextField visiblePassword = new TextField();
        visiblePassword.setManaged(false);
        visiblePassword.setVisible(false);
        visiblePassword.setMaxWidth(350);
        visiblePassword.setStyle("-fx-font-size: 15px;");
        passwordField.textProperty().bindBidirectional(visiblePassword.textProperty());

        showPassword.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            passwordField.setVisible(!isSelected);
            passwordField.setManaged(!isSelected);
            visiblePassword.setVisible(isSelected);
            visiblePassword.setManaged(isSelected);
        });

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(350);
        loginBtn.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 18px;");

        loginBtn.setOnAction(e -> {
            String email = usernameField.getText();
            String passwordInput = passwordField.isVisible() ? passwordField.getText() : visiblePassword.getText();

            if (email.isEmpty() || passwordInput.isEmpty()) {
                showAlert("Login Failed", "Please enter both email and password.");
                return;
            }

            try {
                Firestore db = FirebaseInitializer.initialize();
                DocumentSnapshot doc = db.collection("Customer").document(email).get().get();

                if (!doc.exists()) {
                    showAlert("Login Failed", "No account found for this email.");
                    return;
                }

                String storedPassword = doc.getString("password");
                if (storedPassword != null && storedPassword.equals(passwordInput)) {
                    CurrentSession.setCurrentUserEmail(email);
                    Map<String, Object> activeUserData = new HashMap<>();
                    activeUserData.put("email", email);
                    activeUserData.put("loginTime", System.currentTimeMillis());
                    db.collection("ActiveUsers").document(email).set(activeUserData).get();

                    showAlert("Login Successful", "Welcome back, " + doc.getString("username") + "!");
                    goToHomePage();
                } else {
                    showAlert("Login Failed", "Incorrect password.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Failed to log in: " + ex.getMessage());
            }
        });

        Label switchLabel = new Label("Don't have an account? Sign up");
        switchLabel.setTextFill(Color.BLUE);
        switchLabel.setFont(new Font(16));
        switchLabel.setOnMouseClicked(e -> loadSignupForm());

        formBox.getChildren().addAll(
                mainTitle, subTitle,
                usernameField, passwordField, visiblePassword,
                showPassword, loginBtn, switchLabel
        );
    }

    private void loadSignupForm() {
        formBox.getChildren().clear();

        Label mainTitle = new Label("Welcome to Suvidha@Home");
        mainTitle.setFont(new Font("Arial", 26));

        Label subTitle = new Label("User Sign Up");
        subTitle.setFont(new Font("Arial", 20));
        subTitle.setTextFill(Color.GREY);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(250);
        usernameField.setStyle("-fx-font-size: 18px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(250);
        emailField.setStyle("-fx-font-size: 18px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);
        passwordField.setStyle("-fx-font-size: 18px;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(250);
        confirmPasswordField.setStyle("-fx-font-size: 18px;");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setMaxWidth(250);
        addressField.setStyle("-fx-font-size: 18px;");

        TextField contactField = new TextField();
        contactField.setPromptText("Contact Number");
        contactField.setMaxWidth(250);
        contactField.setStyle("-fx-font-size: 18px;");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setPrefWidth(250);
        signupBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 18px;");

        signupBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String address = addressField.getText();
            String contact = contactField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || email.isEmpty() || address.isEmpty() || contact.isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty()) {
                showAlert("Signup Failed", "All fields are required.");
            } else if (!password.equals(confirmPassword)) {
                showAlert("Signup Failed", "Passwords do not match.");
            } else {
                try {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("email", email);
                    userData.put("password", password);
                    userData.put("address", address);
                    userData.put("contact", contact);

                    Firestore db = FirebaseInitializer.initialize();
                    db.collection("Customer").document(email).set(userData).get();

                    CurrentSession.setCurrentUserEmail(email);
                    showAlert("Signup Successful", "Account created for " + username + "!");
                    goToHomePage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Failed to save data: " + ex.getMessage());
                }
            }
        });

        Label switchLabel = new Label("Already have an account? Login");
        switchLabel.setTextFill(Color.BLUE);
        switchLabel.setFont(new Font(16));
        switchLabel.setOnMouseClicked(e -> loadLoginForm());

        formBox.getChildren().addAll(
                mainTitle, subTitle,
                usernameField, emailField,
                passwordField, confirmPasswordField,
                addressField, contactField,
                signupBtn, switchLabel
        );
    }

    private void goToHomePage() {
        HomePageUI homePage = new HomePageUI();
        Scene homeScene = homePage.createScene();
        primaryStage.setScene(homeScene);
        primaryStage.setMaximized(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getRoot() {
        return rootLayer;
    }
}
              