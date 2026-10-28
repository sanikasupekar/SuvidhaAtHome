package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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

public class bathroom {

    public Scene createScene() {
        // ===== Get full screen size =====
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Main content layout
        VBox content = new VBox(30);
        content.setPadding(new Insets(30));
        content.setAlignment(Pos.TOP_CENTER);

        // ===== Back Button =====
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
            CleaningStartPage cleaningPage = new CleaningStartPage();
            Scene cleaningScene = cleaningPage.createScene();
            startpage.primaryStage.setScene(cleaningScene);
        });

        HBox topBar = new HBox(backBtn);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10));

        // ===== Tagline =====
        Label tagline = new Label("Make Your Bathroom Shine Like New!");
        tagline.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        tagline.setTextFill(Color.web("#2C3E50"));

        // ===== Heading =====
        Label heading = new Label("Bathroom Cleaning Services");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 32));
        heading.setTextFill(Color.web("#2C3E50"));

        // ===== Main Image =====
        ImageView mainImage = createImageView("/asset//housecleanning//bathroom.jpg", 600, 250);

        // ===== Offer Banner =====
        Label offerBanner = new Label("🔥 20% OFF on First Booking 🔥");
        offerBanner.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        offerBanner.setTextFill(Color.WHITE);
        offerBanner.setStyle("-fx-background-color: #e74c3c; -fx-padding: 10 20; -fx-background-radius: 8;");

        // ===== Sub-heading =====
        Label subHeading = new Label("Our Services");
        subHeading.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        subHeading.setTextFill(Color.web("#2C3E50"));

        // ===== Horizontal Service Section =====
        HBox servicesSection = new HBox(25);
        servicesSection.setAlignment(Pos.CENTER);
        servicesSection.setPadding(new Insets(10));

        VBox deepCleaning = createServiceBox(
                "Deep Cleaning",
                "/asset//housecleanning//deepclean2.jpg",
                "2 Hours",
                "₹1200",
                "#f9f9f9"
        );

        VBox floorCleaning = createServiceBox(
                "Floor Cleaning",
                "/asset//housecleanning//floorclean.jpg",
                "1.5 Hours",
                "₹800",
                "#e6f7ff"
        );

        VBox mirrorPolishing = createServiceBox(
                "Mirror Polishing",
                "/asset//housecleanning//mirrorcleanning.jpg",
                "1 Hour",
                "₹500",
                "#fff5e6"
        );

        servicesSection.getChildren().addAll(deepCleaning, floorCleaning, mirrorPolishing);

        // ===== Additional Information Section =====
        Label additionalInfoHeading = new Label("Why Choose Us?");
        additionalInfoHeading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        additionalInfoHeading.setTextFill(Color.web("#2C3E50"));

        Label additionalInfo = new Label(
                "• 100% eco-friendly cleaning solutions.\n" +
                        "• Trained and experienced staff.\n" +
                        "• Affordable prices with no hidden charges.\n" +
                        "• Flexible scheduling and instant booking.\n" +
                        "• Satisfaction guaranteed or free re-clean."
        );
        additionalInfo.setFont(Font.font("Arial", 16));
        additionalInfo.setTextFill(Color.web("black"));
        additionalInfo.setWrapText(true);

        // ===== Extra Offers Section =====
        Label offersHeading = new Label("Special Deals");
        offersHeading.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        offersHeading.setTextFill(Color.web("black"));

        Label offers = new Label(
                "🎁 Book 2 services together and get an extra 10% OFF!\n" +
                        "🎁 Free bathroom freshener with every booking this month."
        );
        offers.setFont(Font.font("Arial", 16));
        offers.setTextFill(Color.web("black"));
        offers.setWrapText(true);

        // ===== Footer =====
        Label footer = new Label("Contact us: 1800-123-456 | service@suvidha.com");
        footer.setFont(Font.font("Arial", 14));
        footer.setTextFill(Color.web("black"));

        // Add everything to content
        content.getChildren().addAll(tagline, heading, mainImage, offerBanner, subHeading, servicesSection,
                additionalInfoHeading, additionalInfo, offersHeading, offers, footer);

        // ===== ScrollPane =====
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: lightgrey;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);
        root.setStyle("-fx-background-color: lightgrey;");

        // Return Scene with full screen size
        return new Scene(root, screenWidth, screenHeight);
    }

    private ImageView createImageView(String path, double width, double height) {
        Image image;
        try {
            image = new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            image = new Image("https://via.placeholder.com/250x140.png?text=Image+Not+Found");
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private VBox createServiceBox(String title, String imagePath, String time, String price, String bgColor) {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(15));
        box.setPrefWidth(280);
        box.setStyle(
                "-fx-background-color: " + bgColor + "; -fx-background-radius: 12; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 8, 0, 0, 4);"
        );

        ImageView imageView = createImageView(imagePath, 250, 140);

        Label serviceTitle = new Label(title);
        serviceTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        serviceTitle.setTextFill(Color.web("black"));

        Label description = new Label("High-quality, eco-friendly cleaning with expert care.");
        description.setFont(Font.font("Arial", 13));
        description.setWrapText(true);
        description.setTextFill(Color.web("black"));

        HBox rating = new HBox(3);
        rating.setAlignment(Pos.CENTER);
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(new Image("https://img.icons8.com/emoji/48/000000/star-emoji.png"));
            star.setFitWidth(18);
            star.setFitHeight(18);
            rating.getChildren().add(star);
        }

        Label serviceTime = new Label("Time: " + time);
        serviceTime.setFont(Font.font("Arial", 14));
        serviceTime.setTextFill(Color.web("black"));

        Label servicePrice = new Label("Price: " + price);
        servicePrice.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        servicePrice.setTextFill(Color.web("black"));

        Button addToCart = new Button("Add to Cart");
        addToCart.setStyle(
                "-fx-background-color: #16a085; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-padding: 8 20; -fx-background-radius: 6; -fx-cursor: hand;"
        );
        addToCart.setOnAction(e -> System.out.println(title + " added to cart!"));

        box.getChildren().addAll(imageView, serviceTitle, description, rating, serviceTime, servicePrice, addToCart);
        return box;
    }
}
