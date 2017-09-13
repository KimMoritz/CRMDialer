package se.com.moritz.crmdialer.firebase_messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIDService";
    private static String firebaseToken = FirebaseInstanceId.getInstance().getToken();

    public static String getFirebaseToken() {
        return firebaseToken;
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        new SalesforceIdUpdater(token).execute();
    }

}
