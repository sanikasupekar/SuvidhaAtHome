package prerana.example.view;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.InputStream;

public class FirebaseInitializer {

    private static Firestore firestoreInstance;

    public static Firestore initialize() throws Exception {
        if (firestoreInstance != null) {
            return firestoreInstance;  // Reuse existing Firestore instance
        }

        InputStream serviceAccount = null;
        try {
            // Try loading from resources first
            serviceAccount = FirebaseInitializer.class.getClassLoader()
                .getResourceAsStream("my-project-3b4af-firebase-adminsdk-fbsvc-1c257f84d4.json");

            if (serviceAccount == null) {
                // Fallback to file system if not found in resources
                serviceAccount = new FileInputStream(
                    "src/main/resources/my-project-3b4af-firebase-adminsdk-fbsvc-1c257f84d4.json"
                );
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("my-project-3b4af")
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully!");
            } else {
                System.out.println("Firebase already initialized.");
            }

            firestoreInstance = FirestoreClient.getFirestore();
            return firestoreInstance;

        } finally {
            if (serviceAccount != null) {
                serviceAccount.close(); // Close InputStream
            }
        }
    }
}
