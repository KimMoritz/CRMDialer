package se.com.moritz.crmdialer.firebase_messaging;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

import se.com.moritz.crmdialer.activity.AlertScreen;
import se.com.moritz.crmdialer.crm.ContactInfoUpdater;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFireBaseMessagingServ";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload name: " + remoteMessage.getData().get("Name"));
            ContactInfoUpdater.addContactInfo(remoteMessage.getData().get("Name"));
            ContactInfoUpdater.addContactInfo(remoteMessage.getData().get("Phone"));
            ContactInfoUpdater.addContactInfo(remoteMessage.getData().get("Email"));
            ContactInfoUpdater.addContactInfo(remoteMessage.getData().get("MobilePhone"));
            ContactInfoUpdater.addAccountInfo(remoteMessage.getData().get("AccountName"));
            ContactInfoUpdater.addAccountInfo(remoteMessage.getData().get("AccountWebsite"));
            ContactInfoUpdater.addCaseInfo("Reason: " + remoteMessage.getData().get("CaseReason"));
            ContactInfoUpdater.addCaseInfo("Subject: " + remoteMessage.getData().get("CaseSubject"));
            ContactInfoUpdater.addCaseInfo("Description: " + remoteMessage.getData().get("CaseDescription"));
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            String messageBody = remoteMessage.getNotification().getBody();
            JSONObject messagejsonObject;
            try {
                messagejsonObject = new JSONObject(messageBody);
                JSONObject dataJsonObject = messagejsonObject.getJSONObject("data");
                ContactInfoUpdater.addContactInfo(dataJsonObject.getString("Name"));
                //ContactInfoUpdater.addContactInfo(remoteMessage.getNotification().getBody());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Context context = getApplicationContext();
        Intent crmReplyAlertScreenIntent = new Intent(context, AlertScreen.class);
        context.startActivity(crmReplyAlertScreenIntent);
    }

}
