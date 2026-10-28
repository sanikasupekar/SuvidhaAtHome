package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class CleaningStartPage {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // MAIN LAYOUT
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(30));
        mainLayout.setStyle("-fx-background-color: lightgrey;");

        // ===== BACK BUTTON =====
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
            Scene homeScene = homePage.createScene();
            startpage.primaryStage.setScene(homeScene);
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        // ===== CONTENT =====
        VBox leftContent = new VBox(30);
        leftContent.setAlignment(Pos.TOP_CENTER);
        leftContent.setPadding(new Insets(20));

        // HEADING
        Label heading = new Label("Cleaning Service at Home");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, screenHeight * 0.05)); // responsive font size
        heading.setTextFill(Color.web("black"));

        // MAIN IMAGE
        ImageView mainImage = createImageView("/asset/housecleanning/bucketimage.jpg", screenWidth * 0.8, 250);

        // OUR SERVICES
        Label subHeading = new Label("Our Services");
        subHeading.setFont(Font.font("Arial", FontWeight.BOLD, screenHeight * 0.03));
        subHeading.setTextFill(Color.web("black"));

        // ===== SERVICE BOXES =====
        VBox servicesColumn = new VBox(20);
        servicesColumn.setAlignment(Pos.CENTER);
        servicesColumn.getChildren().addAll(
                createServiceBox("Bathroom Cleaning", "/asset/housecleanning/deepclean2.jpg", "₹1200", "2 Hours"),
                createServiceBox("House Cleaning", "/asset/housecleanning/headerhouseimage.jpg", "₹2000", "3 Hours"),
                createServiceBox("Laundry", "/asset//laundry//laundry.jpg", "₹500", "1 Hour")
        );

        // Add all components to leftContent
        leftContent.getChildren().addAll(heading, mainImage, subHeading, servicesColumn);

        // Add ScrollPane
        ScrollPane scrollPane = new ScrollPane(leftContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: lightgrey; -fx-background-color: lightgrey;");

        // SCENE
        mainLayout.setTop(topBar);
        mainLayout.setCenter(scrollPane);

        return new Scene(mainLayout, screenWidth, screenHeight);
    }

    // Create ImageView with fallback
    private ImageView createImageView(String path, double width, double height) {
        Image image;
        try {
            image = new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            image = new Image("https://via.placeholder.com/800x250.png?text=Image+Not+Found");
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    // Create a service box
    private VBox createServiceBox(String title, String imagePath, String price, String time) {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(15));
        box.setPrefWidth(300);
        box.setStyle(
                "-fx-background-color: lightgrey; " +
                "-fx-background-radius: 12; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 4);"
        );

        Label serviceTitle = new Label(title);
        serviceTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        serviceTitle.setTextFill(Color.web("black"));

        ImageView imageView = createImageView(imagePath, 250, 150);

        Label priceLabel = new Label("Price: " + price);
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        priceLabel.setTextFill(Color.web("black"));

        Label timeLabel = new Label("Time: " + time);
        timeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        timeLabel.setTextFill(Color.web("#37474f"));

        Button addToCart = new Button("View Details →");
        addToCart.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        addToCart.setStyle(
                "-fx-background-color: #16a085; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-radius: 6;"
        );

        addToCart.setOnAction(e -> navigateToPage(title));
        box.setOnMouseClicked(e -> navigateToPage(title));

        box.getChildren().addAll(serviceTitle, imageView, priceLabel, timeLabel, addToCart);
        return box;
    }

    // Navigation Method
    private void navigateToPage(String title) {
        switch (title) {
            case "Bathroom Cleaning":
                bathroom bathroomPage = new bathroom();
                startpage.primaryStage.setScene(bathroomPage.createScene());
                break;
            case "House Cleaning":
                HouseCleaningPage housePage = new HouseCleaningPage();
                startpage.primaryStage.setScene(housePage.createScene());
                break;
            case "Laundry":
                laundrypage laundryPage = new laundrypage();
                startpage.primaryStage.setScene(laundryPage.createScene());
                break;
            default:
                System.out.println("No navigation set for: " + title);
        }
    }
}
