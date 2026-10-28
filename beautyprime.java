package prerana.example.view;

// import javafx.animation.FadeTransition;
// import javafx.animation.KeyFrame;
// import javafx.animation.ScaleTransition;
// import javafx.animation.SequentialTransition;
// import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
//import javafx.util.Duration;


public class beautyprime {

    public Scene createScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        VBox content = new VBox(40);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: lightgrey;");

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

        Text headingText = new Text("Glow Up with Confidence, Grace, and Style.");
headingText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
headingText.setStyle("-fx-fill: #090709ff;");

HBox headingBox = new HBox(headingText);
headingBox.setAlignment(Pos.CENTER);


        // ----------- Description -----------
        Label description = new Label(
            "Pamper yourself with our handpicked beauty services designed just for women.\n" +
            "From glowing facials to flawless makeovers, we bring the salon experience to your doorstep."
        );
        description.setFont(Font.font("Segoe UI", 18));
        description.setStyle("-fx-text-fill: #333;");
        description.setWrapText(true);
        description.setAlignment(Pos.CENTER);

        VBox headerSection = new VBox(10, headingBox, description);
        headerSection.setAlignment(Pos.CENTER);
        headerSection.setPadding(new Insets(20));

        // ----------- Services -----------
        Label servicesLabel = new Label("Services");
        servicesLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #333; -fx-font-weight: bold;");

        HBox servicesBox = new HBox(20);
        servicesBox.setAlignment(Pos.CENTER_LEFT);
        ScrollPane servicesScroll = new ScrollPane(servicesBox);
        servicesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        servicesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        servicesScroll.setPrefHeight(220);
        servicesScroll.setStyle("-fx-background-color: lightblue;");

        String[][] services = {
            {"Spa", "asset\\femalebeautyimage\\femalespa.jpg"},
            {"Facial", "asset\\femalebeautyimage\\femalefacial.jpg"},
            {"Haircut", "asset\\femalebeautyimage\\haircut.jpg"},
            {"Waxing", "asset\\femalebeautyimage\\waxing.jpg"},
            {"Manicure", "asset\\femalebeautyimage\\manicure.jpg"},
            {"Pedicure", "asset\\femalebeautyimage\\pedicure.jpg"},
            {"Threading", "asset\\femalebeautyimage\\threading.jpg"}
        };

        for (String[] s : services) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            ImageView img = new ImageView(new Image(s[1]));
            img.setFitWidth(200);
            img.setFitHeight(140);

            Button btn = new Button(s[0], img);
            btn.setContentDisplay(ContentDisplay.TOP);
            btn.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI';");
            btn.setPrefWidth(150);

            switch (s[0]) {
                case "Spa" -> btn.setOnAction(e -> startpage.primaryStage.setScene(new SpaServiceDetailsPage().createScene()));
                case "Facial" -> btn.setOnAction(e -> startpage.primaryStage.setScene(new FacialServiceDetailsPage().createScene()));
                case "Haircut" -> btn.setOnAction(e -> startpage.primaryStage.setScene(new HaircutServiceDetailsPage().createScene()));
                case "Waxing" -> btn.setOnAction(e -> startpage.primaryStage.setScene(new WaxingServiceDetailsPage().createScene()));
                case "Manicure", "Pedicure" -> btn.setOnAction(e -> startpage.primaryStage.setScene(new ManicureServiceDetailsPage().createScene()));
            }

            card.getChildren().add(btn);
            servicesBox.getChildren().add(card);
        }

        // ----------- Offers Section (Using FlowPane to Avoid Horizontal Scroll) -----------
        Label offersLabel = new Label("Offers & Combos");
        offersLabel.setStyle("-fx-font-size: 28px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #333; -fx-font-weight: bold;");

        FlowPane offersFlow = new FlowPane();
        offersFlow.setHgap(20);
        offersFlow.setVgap(20);
        offersFlow.setPadding(new Insets(10));
        offersFlow.setPrefWrapLength(screenWidth - 100); // wrap cards within screen width
        offersFlow.setStyle("-fx-background-color: white;");

        String[][] offers = {
            {"Spa Combo", "asset\\femalebeautyimage\\spacombo.jpg", "999"},
            {"Facial + Haircut", "asset\\femalebeautyimage\\femalebeauty.jpg", "799"},
            {"Wax Pack", "asset\\femalebeautyimage\\waxpack.jpg", "499"},
            {"Mani + Pedi", "asset\\femalebeautyimage\\manipedi.jpg", "899"}
        };

        for (String[] o : offers) {
            VBox card = new VBox(10);
            card.setAlignment(Pos.CENTER);
            card.setPrefWidth(180);
            card.setStyle("-fx-background-color: lightgrey; -fx-border-color: #ddd; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;");

            ImageView img = new ImageView(new Image(o[1]));
            img.setFitWidth(170);
            img.setFitHeight(150);

            Label name = new Label(o[0]);
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #444;");

            Label price = new Label("₹" + o[2]);
            price.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");

            Button bookBtn = new Button("Book Now");
            bookBtn.setStyle("-fx-background-color: #d42996ff; -fx-text-fill: white; -fx-font-size: 14px;");

            switch (o[0]) {
                case "Spa Combo" -> bookBtn.setOnAction(e -> startpage.primaryStage.setScene(new SpaComboServiceDetailsPage().createScene()));
                case "Facial + Haircut" -> bookBtn.setOnAction(e -> startpage.primaryStage.setScene(new FacialHaircutComboPage().createScene()));
                case "Wax Pack" -> bookBtn.setOnAction(e -> startpage.primaryStage.setScene(new WaxPackServiceDetailsPage().createScene()));
                case "Mani + Pedi" -> bookBtn.setOnAction(e -> startpage.primaryStage.setScene(new ManiPediComboPage().createScene()));
            }

            card.getChildren().addAll(img, name, price, bookBtn);
            offersFlow.getChildren().add(card);
        }

        // Final Assembly
        content.getChildren().addAll(
            backBox,
            headerSection,
            servicesLabel,
            servicesScroll,
            offersLabel,
            offersFlow
        );

        ScrollPane mainScroll = new ScrollPane(content);
        mainScroll.setFitToWidth(true);
        mainScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        return new Scene(mainScroll, screenWidth, screenHeight);
    }
}
