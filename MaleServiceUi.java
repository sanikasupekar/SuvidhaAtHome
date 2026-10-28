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
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class MaleServiceUi {

    public Scene createScene() {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setStyle("-fx-background-color: lightgrey;");
        mainContent.setMaxWidth(screenWidth);

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
            HomePageUI homeUI = new HomePageUI();
            Scene homeScene = homeUI.createScene();
            startpage.primaryStage.setScene(homeScene);
        });
        HBox backBox = new HBox(backBtn);
        backBox.setAlignment(Pos.CENTER_LEFT);
        backBox.setMaxWidth(Double.MAX_VALUE);

        // ----------------- Slogan Above Services -----------------
        Label mainSlogan = new Label("✨ Look Sharp, Feel Sharper!");
        mainSlogan.setFont(new Font("Arial", 28));
        mainSlogan.setStyle("-fx-text-fill: #333;");
        mainSlogan.setAlignment(Pos.CENTER);

        // ----------------- Left Side: Services -----------------
        VBox leftSide = new VBox(20);
        leftSide.setAlignment(Pos.TOP_LEFT);
        leftSide.setPadding(new Insets(10));

        Label serviceLabel = new Label("Men's Services");
        serviceLabel.setFont(new Font("Arial", 30));
        serviceLabel.setStyle("-fx-text-fill: #2c3e50;");

        GridPane serviceGrid = new GridPane();
        serviceGrid.setHgap(20);
        serviceGrid.setVgap(20);

        Button service1 = createServiceCard("asset\\malebeautyimage\\menhaircut.jpg", "Haircut");
        Button service2 = createServiceCard("asset\\malebeautyimage\\mengroom.jpg", "Beard Grooming");
        Button service3 = createServiceCard("asset\\malebeautyimage\\menmassage.jpg", "Massage");
        Button service4 = createServiceCard("asset\\malebeautyimage\\menfacial.jpg", "Facial");

        serviceGrid.add(service1, 0, 0);
        serviceGrid.add(service2, 1, 0);
        serviceGrid.add(service3, 0, 1);
        serviceGrid.add(service4, 1, 1);

        leftSide.getChildren().addAll(serviceLabel, serviceGrid);

        // ----------------- Right Side: Top Banner -----------------
        ImageView topBanner = new ImageView(new Image("asset\\malebeautyimage\\hair2.jpg"));
        topBanner.setFitWidth(screenWidth * 0.4);
        topBanner.setPreserveRatio(true);

        VBox rightSide = new VBox(topBanner);
        rightSide.setAlignment(Pos.TOP_CENTER);
        rightSide.setPadding(new Insets(10));

        HBox topSection = new HBox(40, leftSide, rightSide);
        topSection.setAlignment(Pos.TOP_CENTER);

        
        Label offerLabel = new Label("Offers & Packages");
        offerLabel.setFont(new Font("Arial", 30));
        offerLabel.setStyle("-fx-text-fill: #2c3e50;");

        HBox offerRow = new HBox(20);
        offerRow.setPadding(new Insets(10));
        offerRow.setAlignment(Pos.TOP_LEFT);

        offerRow.getChildren().addAll(
            createOfferCard("asset\\malebeautyimage\\haircut facialoffer.jpg", "Haircut + Facial", "₹499"),
            createOfferCard("asset\\malebeautyimage\\luxrypack.jpg", "Luxury Pack", "₹899"),
            createOfferCard("asset\\malebeautyimage\\imagesprmium.jpg", "Premium Facial", "₹699"),
            createOfferCard("asset\\malebeautyimage\\beardclean.jpg", "Beard + Cleanup", "₹399")
        );

        ScrollPane offerScroll = new ScrollPane(offerRow);
        offerScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        offerScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        offerScroll.setFitToHeight(true);
        offerScroll.setPrefHeight(250);
        offerScroll.setStyle("-fx-background-color: white;");

        // Add all elements to main content
        mainContent.getChildren().addAll(backBox, mainSlogan, topSection,  offerLabel, offerScroll);

        // Wrap in a scrollable page
        ScrollPane pageScroll = new ScrollPane(mainContent);
        pageScroll.setFitToWidth(true);
        pageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return new Scene(pageScroll, screenWidth, screenHeight);
    }

    // --------- Service Card ---------
    private Button createServiceCard(String imagePath, String serviceName) {
        ImageView image = new ImageView(new Image(imagePath));
        image.setFitHeight(200);
        image.setFitWidth(300);

        Label label = new Label(serviceName);
        label.setFont(new Font("Arial", 16));

        VBox content = new VBox(10, image, label);
        content.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(content);
        button.setStyle("""
            -fx-background-color: #e0e0e0;
            -fx-border-color: #bbb;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 10;
            -fx-cursor: hand;
        """);

        button.setOnAction(e -> {
            if (serviceName.equals("Haircut")) {
                MaleHaircutServicePage haircutPage = new MaleHaircutServicePage();
                startpage.primaryStage.setScene(haircutPage.createScene());
            } else if (serviceName.equals("Beard Grooming")) {
                MaleGroomServicePage groomPage = new MaleGroomServicePage();
                startpage.primaryStage.setScene(groomPage.createScene());
            } else if (serviceName.equals("Massage")) {
                MaleMassageServicePage massagePage = new MaleMassageServicePage();
                startpage.primaryStage.setScene(massagePage.createScene());
            } else if (serviceName.equals("Facial")) {
                MaleFacialServicePage facialPage = new MaleFacialServicePage();
                startpage.primaryStage.setScene(facialPage.createScene());
            }
        });
        return button;
    }

    // --------- Offer Card ---------
    private Button createOfferCard(String imagePath, String offerText, String price) {
        ImageView image = new ImageView(new Image(imagePath));
        image.setFitHeight(200);
        image.setFitWidth(300);

        Label nameLabel = new Label(offerText);
        nameLabel.setFont(new Font("Arial", 16));
        nameLabel.setWrapText(true);

        Label priceLabel = new Label(price);
        priceLabel.setFont(new Font("Arial", 16));
        priceLabel.setStyle("-fx-text-fill: green;");

        VBox content = new VBox(10, image, nameLabel, priceLabel);
        content.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(content);
        button.setStyle("""
            -fx-background-color: white;
            -fx-border-color: #ccc;
            -fx-border-radius: 10;
            -fx-background-radius: 10;
            -fx-padding: 12;
            -fx-cursor: hand;
        """);

        button.setOnAction(e -> {
            if (offerText.equals("Haircut + Facial")) {
                startpage.primaryStage.setScene(new MaleHaircutFacialComboPage().createScene());
            } else if (offerText.equals("Luxury Pack")) {
                startpage.primaryStage.setScene(new MaleLuxuryPackPage().createScene());
            } else if (offerText.equals("Premium Facial")) {
                startpage.primaryStage.setScene(new MalePremiumFacialPackOfferPage().createScene());
            } else if (offerText.equals("Beard + Cleanup")) {
                startpage.primaryStage.setScene(new BeardCleanupComboPage().createScene());
            }
        });
        return button;
    }
}
