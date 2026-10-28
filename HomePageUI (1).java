package prerana.example.view;

import com.google.cloud.firestore.Firestore;
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
import javafx.stage.Screen;

public class HomePageUI {

    public Scene createScene() {

        // Top Navigation Bar
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
            startpage sp = new startpage(startpage.primaryStage);
            Scene startScene = sp.createFullScreenScene(sp.getRoot());
            startpage.primaryStage.setScene(startScene);
        });

        Label title = new Label("Suvidha at Home");
        title.setStyle("-fx-font-size: 26px; -fx-text-fill: white; -fx-font-weight: bold;");

        Button aboutUsBtn = new Button("About Us");
        Button cartBtn = new Button("Cart");
       // Button historyBtn = new Button("History");
        Button profileBtn = new Button("Profile");
        Button logoutBtn = new Button("Logout");

        String topBtnStyle = "-fx-background-color: grey; -fx-text-fill: white; -fx-font-weight: bold;";
        aboutUsBtn.setStyle(topBtnStyle);
        cartBtn.setStyle(topBtnStyle);
        //historyBtn.setStyle(topBtnStyle);
        profileBtn.setStyle(topBtnStyle);
        logoutBtn.setStyle(topBtnStyle);

        aboutUsBtn.setOnAction(e -> startpage.primaryStage.setScene(new AboutUsPage().createScene()));
        cartBtn.setOnAction(e -> {
            CartPage cartPage = new CartPage();
            String userEmail = CurrentSession.getCurrentUserEmail();
            startpage.primaryStage.setScene(cartPage.createScene(userEmail));
        });
        // historyBtn.setOnAction(e -> startpage.primaryStage.setScene(new HistoryPage().createScene()));
        profileBtn.setOnAction(e -> startpage.primaryStage.setScene(new userProfile().createScene()));

        logoutBtn.setOnAction(e -> {
            try {
                Firestore db = FirebaseInitializer.initialize();
                String email = CurrentSession.getCurrentUserEmail();
                if (email != null) {
                    db.collection("ActiveUsers").document(email).delete();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            CurrentSession.clearSession();
            login loginPage = new login(startpage.primaryStage);
            Scene loginScene = new Scene(loginPage.getRoot(), Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
            startpage.primaryStage.setScene(loginScene);
        });

        HBox topRightBtns = new HBox(10, aboutUsBtn, cartBtn,  profileBtn, logoutBtn);
        topRightBtns.setAlignment(Pos.CENTER_RIGHT);

        HBox topLeftBox = new HBox(10, backBtn, title);
        topLeftBox.setAlignment(Pos.CENTER_LEFT);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(topLeftBox);
        topBar.setRight(topRightBtns);
        topBar.setPadding(new Insets(20, 10, 20, 10));
        topBar.setStyle("-fx-background-color: linear-gradient(to right, #040404ff, #070707ff); -fx-border-color: #333; -fx-border-width: 0 0 2 0;");

        // Slogan Section
        Label slogan = new Label("Bringing Services Delivered at Your Doorstep");
        slogan.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold;");
        slogan.setAlignment(Pos.CENTER);

        Label slogan2 = new Label("Suvidha at Home connects users with trusted home service providers for beauty,\n"
                + "cleaning, cooking & more. Easily book services, track bookings,\n"
                + "and manage everything from one place.");
        slogan2.setStyle("-fx-font-size: 22px; -fx-text-fill: #555;");
        slogan2.setAlignment(Pos.CENTER);

        VBox headingBox = new VBox(30, slogan, slogan2);
        headingBox.setAlignment(Pos.CENTER);
        headingBox.setPadding(new Insets(30, 10, 20, 10));

        // Service Buttons (2 Rows, reduced size)
        HBox serviceRow1 = new HBox(20,
                createServiceButton("Male Beauty", "/asset//malebeautyimage//male.jpg"),
                createServiceButton("Female Beauty", "/asset//c2w//femalebeuty.jpg")
        );
        serviceRow1.setAlignment(Pos.CENTER_LEFT);

        HBox serviceRow2 = new HBox(20,
                createServiceButton("Cleaning", "/asset/images/headerhouseimage.jpg"),
                createServiceButton("Cooking", "/asset//cookimage//foodcook1.jpg")
        );
        serviceRow2.setAlignment(Pos.CENTER_LEFT);

        VBox servicesBox = new VBox(20, serviceRow1, serviceRow2);
        servicesBox.setAlignment(Pos.CENTER_LEFT);

        // Banner Image
        ImageView bannerImage = new ImageView(loadImage("/asset/babysitter/homepage.jpg"));
        bannerImage.setFitWidth(550);
        bannerImage.setFitHeight(800);
        bannerImage.setPreserveRatio(false);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox middleSplit = new HBox(40, servicesBox, spacer, bannerImage);
        middleSplit.setAlignment(Pos.CENTER_LEFT);
        middleSplit.setPadding(new Insets(100));

        // Why Choose Us Section
        Label whyChooseUs = new Label("Why Choose Us?");
        whyChooseUs.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #060606ff;");
        Label reasons = new Label("\u2022 Trusted and verified professionals\n\u2022 On-time services\n\u2022 Affordable pricing\n\u2022 Easy booking and cancellation\n\u2022 24/7 customer support");
        reasons.setStyle("-fx-font-size: 18px; -fx-text-fill: #444;");
        VBox whyBox = new VBox(10, whyChooseUs, reasons);
        whyBox.setPadding(new Insets(20));
        whyBox.setAlignment(Pos.CENTER);

        // About Us Section
        Label aboutUs = new Label("About Us");
        aboutUs.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #020202ff;");
        Label aboutInfo = new Label("Suvidha at Home is your one-stop platform for all essential home services. From beauty and wellness to cleaning and cooking, we ensure top-notch professionals delivered to your doorstep.");
        aboutInfo.setWrapText(true);
        aboutInfo.setMaxWidth(800);
        aboutInfo.setStyle("-fx-font-size: 18px; -fx-text-fill: #444;");
        VBox aboutBox = new VBox(10, aboutUs, aboutInfo);
        aboutBox.setPadding(new Insets(20));
        aboutBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(20, topBar, headingBox, middleSplit, whyBox, aboutBox);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-background-color: lightgrey");

        ScrollPane mainScroll = new ScrollPane(content);
        mainScroll.setFitToWidth(true);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mainScroll.setStyle("-fx-background: lightgrey ; -fx-background-color: lightgrey;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(mainScroll, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private VBox createServiceButton(String name, String imagePath) {
        ImageView icon = new ImageView(loadImage(imagePath));
        icon.setFitHeight(250);
        icon.setFitWidth(280);

        Label label = new Label(name);
         label.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;"); // Bold text
        VBox box = new VBox(5, icon, label);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-border-color: gray; -fx-padding: 8; -fx-background-color: #ffffffcc; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, lightgray, 2, 0.2, 1, 1);");

        box.setOnMouseClicked(e -> {
            switch (name) {
                case "Male Beauty":
                    startpage.primaryStage.setScene(new MaleServiceUi().createScene());
                    break;
                case "Female Beauty":
                    startpage.primaryStage.setScene(new beautyType().createScene());
                    break;
                case "Cleaning":
                    startpage.primaryStage.setScene(new CleaningStartPage().createScene());
                    break;
                case "Cooking":
                    startpage.primaryStage.setScene(new startpagecooking().createScene());
                    break;
            }
        });

        return box;
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResource(path).toExternalForm());
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            return new Image("https://via.placeholder.com/100.png?text=No+Image");
        }
    }
}
