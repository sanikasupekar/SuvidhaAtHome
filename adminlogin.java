package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class adminlogin {

    private Stage primaryStage;

    public adminlogin(Stage stage) {
        this.primaryStage = stage;
    }

    public Scene createScene() {
        // Get screen width and height for full-screen layout
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Root pane
        BorderPane root = new BorderPane();
        root.setPrefSize(screenWidth, screenHeight);

        // Set white background for the whole page
        root.setStyle("-fx-background-color: lightgrey;");

        // Create the login title
        Label titleLabel = new Label("Admin Login");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: black; -fx-font-weight: bold;");

        // Username
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter admin username");
        usernameField.setMaxWidth(220);

        // Password
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter admin password");
        passwordField.setMaxWidth(220);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin123")) {
                showAlert("Login Successful!", "Welcome Admin!");
                adminDashboard dashboard = new adminDashboard(primaryStage);
                Scene scene = dashboard.createScene();
                if (scene != null) {
                    primaryStage.setScene(scene);
                    primaryStage.setMaximized(true);
                } else {
                    showAlert("Error", "Failed to load Admin Dashboard.");
                }
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        // Back button
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
            primaryStage.setScene(start.createFullScreenScene(start.getRoot()));
            primaryStage.setMaximized(true);
        });

        // Buttons row
        HBox buttonBox = new HBox(10, loginButton, backBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // VBox containing all labels and fields
        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll(
                titleLabel,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                buttonBox
        );

        // Small box around the form
        VBox formContainer = new VBox(formBox);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setPadding(new Insets(20));
        formContainer.setMaxWidth(300); // <-- This keeps it small
        formContainer.setStyle(
                "-fx-background-color: #f0f0f0;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #cccccc;" +
                "-fx-border-radius: 12;" +
                "-fx-border-width: 1.5px;"
        );

        // Center everything
        StackPane centerPane = new StackPane(formContainer);
        centerPane.setAlignment(Pos.CENTER);

        root.setCenter(centerPane);

        return new Scene(root, screenWidth, screenHeight);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
