package se.com.moritz.crmdialer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import se.com.moritz.crmdialer.activity.AlertScreen;
import se.com.moritz.crmdialer.crm.ContactInfoUpdater;

public class MyBroadCastReceiver extends BroadcastReceiver {
    Intent crmReplyAlertScreenIntent;
    private static final String TAG = "MyBroadCastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String incomingNumber = extras.getString("incoming_number");
        String state = extras.getString(TelephonyManager.EXTRA_STATE);
        if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            try {
                ContactInfoUpdater.addContactInfo(incomingNumber);
                crmReplyAlertScreenIntent = new Intent(context, AlertScreen.class);
                context.startActivity(crmReplyAlertScreenIntent);
                Toast.makeText(context, "Incoming call", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d(TAG, "Exception");
                e.printStackTrace();
            }
        } else if (state != null && state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            //TODO: Clears info if phone goes to idle
            ContactInfoUpdater.clearContactInfo();
        }
    }
}
