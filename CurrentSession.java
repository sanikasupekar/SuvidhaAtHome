package prerana.example.view;

public class CurrentSession {
    private static String currentUserEmail;
    private static String currentServiceProviderEmail;

    public static String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public static void setCurrentUserEmail(String email) {
        currentUserEmail = email;
    }

    public static String getCurrentServiceProviderEmail() {
        return currentServiceProviderEmail;
    }

    public static void setCurrentServiceProviderEmail(String email) {
        currentServiceProviderEmail = email;
    }

    public static boolean isUserLoggedIn() {
        return currentUserEmail != null && !currentUserEmail.isEmpty();
    }

    public static boolean isServiceProviderLoggedIn() {
        return currentServiceProviderEmail != null && !currentServiceProviderEmail.isEmpty();
    }

    public static void clearSession() {
        currentUserEmail = null;
        currentServiceProviderEmail = null;
    }
}
