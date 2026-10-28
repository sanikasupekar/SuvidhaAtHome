package prerana.example.view;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FieldValue;
import com.google.firebase.cloud.FirestoreClient;

import java.util.HashMap;
import java.util.Map;

public class CartManager {

    // ---------------------- ADD TO CART (with date and time) ----------------------
    public static void addToCart(String userEmail, String serviceName, String price, String timeRequired, String serviceDate, String serviceTime) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("serviceName", serviceName);
            cartItem.put("price", price);
            cartItem.put("timeRequired", timeRequired);
            cartItem.put("date", serviceDate);   // ✅ Store selected date
            cartItem.put("time", serviceTime);   // ✅ Store selected time
            cartItem.put("timestamp", System.currentTimeMillis()); // 🔒 Useful for ordering

            db.collection("Carts")
              .document(userEmail)
              .collection("items")
              .add(cartItem)
              .get(); // Make sure to wait for the operation to complete

            System.out.println("✅ Service added to cart: " + serviceName + " on " + serviceDate + " at " + serviceTime);

        } catch (Exception e) {
            System.err.println("❌ Error adding service to cart:");
            e.printStackTrace();
        }
    }

    // ---------------------- CREATE BOOKING ----------------------
    public static void createBooking(String userEmail, String serviceName, String serviceDate, String serviceTime) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            Map<String, Object> booking = new HashMap<>();
            booking.put("userEmail", userEmail);
            booking.put("serviceName", serviceName);
            booking.put("date", serviceDate);
            booking.put("time", serviceTime);
            booking.put("status", "PENDING_ADMIN");
            booking.put("timestamp", FieldValue.serverTimestamp()); // Firestore native timestamp

            db.collection("bookings")
              .add(booking)
              .get(); // Wait to ensure consistency

            System.out.println("✅ Booking created for user: " + userEmail + 
                ", service: " + serviceName + 
                ", on " + serviceDate + " at " + serviceTime);

        } catch (Exception e) {
            System.err.println("❌ Error creating booking:");
            e.printStackTrace();
        }
    }

    // ---------------------- UPDATE BOOKING STATUS ----------------------
    public static void updateBookingStatus(String userEmail, String newStatus) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            db.collection("bookings")
              .whereEqualTo("userEmail", userEmail)
              .get()
              .get()
              .forEach(doc -> {
                  db.collection("bookings")
                    .document(doc.getId())
                    .update("status", newStatus);
              });

            System.out.println("✅ Booking status updated to '" + newStatus + "' for user: " + userEmail);
        } catch (Exception e) {
            System.err.println("❌ Error updating booking status:");
            e.printStackTrace();
        }
    }
}
