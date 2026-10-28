package prerana.example.view;

import javafx.geometry.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Screen;

public class AboutUsPage {

    public Scene createScene() {
        Parent root = createUI();

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

        VBox layout = new VBox(10, backBtn, root);
        layout.setPadding(new Insets(10));
        layout.setStyle("-fx-background-color: lightgrey;");

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(layout, screenBounds.getWidth(), screenBounds.getHeight());
    }

    public Parent createUI() {
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: lightgrey;");

        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setPadding(new Insets(20));
        scroll.setContent(wrapper);

        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(800);
        card.setStyle("-fx-background-color: lightgrey; -fx-background-radius: 12; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 12,0,0,6);");

        // Top Banner Image
        ImageView bannerImage = createSafeImageView("/asset/c2w/WhatsApp Image 2025-07-29 at 9.59.22 AM.jpeg", 400, 500);

        // Project Title
        Label projectTitle = new Label("Suvidha at Home");
        projectTitle.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 30));
        projectTitle.setTextFill(Color.web("#16a085"));

        // Description
        Label description = new Label(
            "Suvidha @ Home is a smart solution to bring professional beauty and wellness services "
            + "right to your doorstep. Our platform allows users to seamlessly book a wide variety "
            + "of grooming services from the comfort of their homes.\n\n"
            + "Built with a focus on user experience, Suvidha @ Home streamlines the process of "
            + "booking, managing, and delivering services with efficiency and reliability."
        );
        description.setFont(Font.font("Georgia", FontPosture.REGULAR, 16));
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        description.setTextFill(Color.web("#2d3436"));
        description.setMaxWidth(700);

        // Team Members Title
        Label teamTitle = new Label("Team Members");
        teamTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        teamTitle.setTextFill(Color.web("#2c3e50"));

        // Team Members in a row with square images
        HBox teamRow = new HBox(30);
        teamRow.setAlignment(Pos.CENTER);
        teamRow.setPadding(new Insets(10, 0, 0, 0));

        VBox member1 = createTeamMember("/asset/aboutus/prerana4.jpg", "Prerana Kulsange");
        VBox member2 = createTeamMember("/asset/aboutus/sanikaaaa2.jpg", "Sanika Supeker");
        VBox member3 = createTeamMember("/asset/aboutus/mansi1.jpeg", "Mansi Velse");

        teamRow.getChildren().addAll(member1, member2, member3);

        // Mentor Info
        Label mentorName = new Label("Team Lead - Punam Khedker");
        mentorName.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        mentorName.setTextFill(Color.web("#16a085"));
        mentorName.setPadding(new Insets(30, 0, 0, 0));

        card.getChildren().addAll(
            bannerImage,
            projectTitle,
            description,
            teamTitle,
            teamRow,
            mentorName
        );

        wrapper.getChildren().add(card);
        return scroll;
    }

    private VBox createTeamMember(String imagePath, String name) {
    double size = 150;

    ImageView imageView = createSafeImageView(imagePath, size, size);
    imageView.setFitWidth(size);
    imageView.setFitHeight(size);
    imageView.setPreserveRatio(false); // Force square image

    imageView.setStyle("""
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 2);
        -fx-border-color: #ccc;
        -fx-border-width: 1;
        -fx-border-radius: 4;
    """);

    // Wrap imageView in a StackPane to enforce size visually
    StackPane imageBox = new StackPane(imageView);
    imageBox.setPrefSize(size, size);
    imageBox.setMaxSize(size, size);
    imageBox.setMinSize(size, size);

    Label nameLabel = new Label(name);
    nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    nameLabel.setTextFill(Color.web("#2d3436"));
    nameLabel.setAlignment(Pos.CENTER);

    VBox memberBox = new VBox(8, imageBox, nameLabel);
    memberBox.setAlignment(Pos.CENTER);
    return memberBox;
}

private ImageView createSafeImageView(String path, double width, double height) {
    ImageView imageView = new ImageView();
    try {
        Image img = new Image(getClass().getResource(path).toExternalForm(), width, height, false, true);
        imageView.setImage(img);
    } catch (Exception e) {
        System.out.println("Image not found at: " + path);
        // Use a gray rectangle fallback
        imageView.setImage(new WritableImage((int) width, (int) height)); // blank placeholder
        imageView.setStyle("-fx-background-color: gray;");
    }
    return imageView;
}
}
