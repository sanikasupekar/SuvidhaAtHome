package prerana.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class beautyPremium {
    
    public Scene createScene() {
        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        VBox content = new VBox(60);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: #fafeffff;");

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
            beautyType typeUI = new beautyType();
            Scene typeScene = typeUI.createScene();
            startpage.primaryStage.setScene(typeScene);
        });

        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);
        backBox.setPadding(new Insets(10));

        // Top Beauty Image Banner
        ImageView topBanner = new ImageView(new Image("asset\\femalebeautyimage\\femalebeauty.jpg"));
        topBanner.setFitWidth(screenWidth * 0.8);
        topBanner.setFitHeight(300);
        topBanner.setPreserveRatio(true);
        HBox bannerWrapper = new HBox(topBanner);
        bannerWrapper.setAlignment(Pos.CENTER);
        bannerWrapper.setPrefWidth(Double.MAX_VALUE);

        // "Services" Label
        Label servicesLabel = new Label("Services");
        servicesLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #333; -fx-font-weight: bold;");

        // Services Scroll
        HBox servicesBox = new HBox(20);
        servicesBox.setAlignment(Pos.CENTER_LEFT);
        ScrollPane servicesScroll = new ScrollPane(servicesBox);
        servicesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        servicesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        servicesScroll.setPrefHeight(220);
        servicesScroll.setStyle("-fx-background-color: transparent;");

        String[][] services = {
            {"Spa", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Facial", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Haircut", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Waxing", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Manicure", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Pedicure", "asset\\femalebeautyimage\\femalebeauty.jpg"},
            {"Threading", "asset\\femalebeautyimage\\femalebeauty.jpg"}
        };

        for (String[] s : services) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            ImageView img = new ImageView(new Image(s[1]));
            img.setFitWidth(180);
            img.setFitHeight(120);

            Button btn = new Button(s[0], img);
            btn.setContentDisplay(ContentDisplay.TOP);
            btn.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI';");
            btn.setPrefWidth(150);

            // Button actions
            if ("Spa".equals(s[0])) {
                btn.setOnAction(e -> {
                    SpaServiceDetailsPage spaPage = new SpaServiceDetailsPage();
                    startpage.primaryStage.setScene(spaPage.createScene());
                });
            } else if ("Facial".equals(s[0])) {
                btn.setOnAction(e -> {
                    FacialServiceDetailsPage facialPage = new FacialServiceDetailsPage();
                    startpage.primaryStage.setScene(facialPage.createScene());
                });
            } else if ("Haircut".equals(s[0])) {
                btn.setOnAction(e -> {
                    HaircutServiceDetailsPage haircutPage = new HaircutServiceDetailsPage();
                    startpage.primaryStage.setScene(haircutPage.createScene());
                });
            } else if ("Waxing".equals(s[0])) {
                btn.setOnAction(e -> {
                    WaxingServiceDetailsPage waxPage = new WaxingServiceDetailsPage();
                    startpage.primaryStage.setScene(waxPage.createScene());
                });
            } else if ("Manicure".equals(s[0]) || "Pedicure".equals(s[0])) {
                btn.setOnAction(e -> {
                    ManicureServiceDetailsPage maniPadiPage = new ManicureServiceDetailsPage();
                    startpage.primaryStage.setScene(maniPadiPage.createScene());
                });
            }

            card.getChildren().add(btn);
            servicesBox.getChildren().add(card);
        }

        // "Offers & Combos" Label
        Label offersLabel = new Label("Offers & Combos");
        offersLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #333; -fx-font-weight: bold;");

        // Offers Scroll
        HBox offersBox = new HBox(20);
        offersBox.setAlignment(Pos.CENTER_LEFT);
        ScrollPane offersScroll = new ScrollPane(offersBox);
        offersScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        offersScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        offersScroll.setPrefHeight(280);
        offersScroll.setStyle("-fx-background-color: lightblue;");

        String[][] offers = {
            {"Spa Combo", "asset\\femalebeautyimage\\femalebeauty.jpg", "999"},
            {"Facial + Haircut", "asset\\femalebeautyimage\\femalebeauty.jpg", "799"},
            {"Wax Pack", "asset\\femalebeautyimage\\femalebeauty.jpg", "499"},
            {"Mani + Pedi", "asset\\femalebeautyimage\\femalebeauty.jpg", "899"}
        };

        for (String[] o : offers) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            card.setPrefWidth(180);
            card.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;");

            ImageView img = new ImageView(new Image(o[1]));
            img.setFitWidth(170);
            img.setFitHeight(150);

            Label name = new Label(o[0]);
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #444;");

            Label price = new Label("₹" + o[2]);
            price.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

            Button bookBtn = new Button("Book Now");
            bookBtn.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white; -fx-font-size: 14px;");

            if ("Spa Combo".equals(o[0])) {
                bookBtn.setOnAction(e -> {
                    SpaComboServiceDetailsPage spaComboPage = new SpaComboServiceDetailsPage();
                    startpage.primaryStage.setScene(spaComboPage.createScene());
                });
            } else if ("Facial + Haircut".equals(o[0])) {
                bookBtn.setOnAction(e -> {
                    FacialHaircutComboPage facialHaircutPage = new FacialHaircutComboPage();
                    startpage.primaryStage.setScene(facialHaircutPage.createScene());
                });
            } else if ("Wax Pack".equals(o[0])) {
                bookBtn.setOnAction(e -> {
                    WaxPackServiceDetailsPage waxPackPage = new WaxPackServiceDetailsPage();
                    startpage.primaryStage.setScene(waxPackPage.createScene());
                });
            } else if ("Mani + Pedi".equals(o[0])) {
                bookBtn.setOnAction(e -> {
                    ManiPediComboPage maniPediPage = new ManiPediComboPage();
                    startpage.primaryStage.setScene(maniPediPage.createScene());
                });
            }

            card.getChildren().addAll(img, name, price, bookBtn);
            offersBox.getChildren().add(card);
        }

        // Add All to Content
        content.getChildren().addAll(backBox, bannerWrapper, servicesLabel, servicesScroll, offersLabel, offersScroll);

        // Wrap in ScrollPane
        ScrollPane mainScroll = new ScrollPane(content);
        mainScroll.setFitToWidth(true);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Return full-screen Scene
        return new Scene(mainScroll, screenWidth, screenHeight);
    }
}
