// package prerana.example.view;

// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.geometry.Rectangle2D;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
// import javafx.scene.text.Font;
// import javafx.stage.Screen;
// import javafx.stage.Stage;

// public class babysitterview extends Application {

//     @Override
//     public void start(Stage primaryStage) {

//         Label heading = new Label("Babysitter in Pune");
//         heading.setFont(new Font("Arial", 50));
//         heading.setTextFill(Color.BLACK);

//         Label subHeading = new Label("Book a reliable babysitter for your young ones");
//         subHeading.setFont(new Font("Arial", 25));
//         subHeading.setTextFill(Color.GRAY);

//         Button bookBtn = new Button("Book Now");
//         bookBtn.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-size: 14px;");
//         bookBtn.setPadding(new Insets(10, 20, 10, 20));
//         bookBtn.setOnAction(e -> {
//             primaryStage.hide();
//             Stage bookingStage = new Stage();
//             babysitterBookingPage bookingPage = new babysitterBookingPage();
//             bookingPage.start(bookingStage);
//         });

//         VBox leftBox = new VBox(15, heading, subHeading, bookBtn);
//         leftBox.setPadding(new Insets(50));
//         leftBox.setAlignment(Pos.CENTER_LEFT);

//         Image image = new Image("Assets/Images/baby-sitter.jpg");
//         ImageView imageView = new ImageView(image);
//         imageView.setFitWidth(700);
//         imageView.setFitHeight(500);
//         imageView.setPreserveRatio(true);
//         imageView.setStyle("-fx-border-color: yellow; -fx-border-width: 4; -fx-padding: 5;");

//         VBox rightBox = new VBox(imageView);
//         rightBox.setPadding(new Insets(30));
//         rightBox.setAlignment(Pos.CENTER_RIGHT);

//         HBox centerBox = new HBox(50, leftBox, rightBox);
//         centerBox.setAlignment(Pos.CENTER);
//         centerBox.setPadding(new Insets(20));

//         Label workers = new Label("100000+ \nCertified Workers");
//         Label customers = new Label("7500+ \nHappy Customers");
//         Label pincodes = new Label("150+ \nPincodes & Counting");
//         workers.setFont(new Font(22));
//         customers.setFont(new Font(22));
//         pincodes.setFont(new Font(22));

//         HBox stats = new HBox(50, workers, customers, pincodes);
//         stats.setAlignment(Pos.CENTER);
//         stats.setPadding(new Insets(20));

//         BorderPane mainLayout = new BorderPane();
//         mainLayout.setCenter(centerBox);
//         mainLayout.setBottom(stats);
//         mainLayout.setPadding(new Insets(20));
//         mainLayout.setStyle(
//             "-fx-background-image: url('Assets/Images/light-yellow-colored-crumpled-paper-texture-background-for-design-decorative-free-photo.jpg');"
//             + "-fx-background-size: cover;"
//             + "-fx-background-repeat: no-repeat;"
//             + "-fx-background-position: center center;"
//         );

//         // Get the full screen size
//         Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
//         Scene scene = new Scene(mainLayout, screenBounds.getWidth(), screenBounds.getHeight());

//         // Set stage properties
//         primaryStage.setTitle("Suvidha@Home Babysitter");
//         primaryStage.setScene(scene);
//         primaryStage.setMaximized(true); // Open in full screen
//         primaryStage.show();
//     }

   
// } 
