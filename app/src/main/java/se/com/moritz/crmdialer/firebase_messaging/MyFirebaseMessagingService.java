package se.com.moritz.crmdialer.firebase_messaging;

import android.util.JsonReader;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.com.moritz.crmdialer.crm.ContactInfoUpdater;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFireBaseMessagingServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String messageBody = remoteMessage.getNotification().getBody();
            JSONObject messagejsonObject = null;
            try {
                messagejsonObject = new JSONObject(messageBody);
                JSONObject dataJsonObject = messagejsonObject.getJSONObject("data");
                ContactInfoUpdater.addContactInfo(dataJsonObject.getString("Name"));
                //ContactInfoUpdater.addContactInfo(remoteMessage.getNotification().getBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateListView(){
    }
}
