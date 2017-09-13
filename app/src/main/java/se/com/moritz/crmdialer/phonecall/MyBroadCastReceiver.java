package se.com.moritz.crmdialer.phonecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import se.com.moritz.crmdialer.activity.AlertScreen;
import se.com.moritz.crmdialer.crm.ContactInfoUpdater;

import static java.lang.Thread.sleep;

public class MyBroadCastReceiver extends BroadcastReceiver {
    Intent crmReplyAlertScreenIntent;
    private static final String TAG = "MyBroadCastReceiver";
    public static String phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String incomingNumber = extras.getString("incoming_number");
        String state = extras.getString(TelephonyManager.EXTRA_STATE);
        if (state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            try {
                if(!ContactInfoUpdater.contactInfoExists()){
                    for (int i = 0; i<3; i++) {
                        sleep(1500);
                        Log.i(TAG, ("No contact info, waiting another second..."));
                        if (ContactInfoUpdater.contactInfoExists()) {
                            Log.i(TAG, "Adding number to local contact info and answering call");
                            ContactInfoUpdater.addContactInfo(incomingNumber);
                            break;
                        }
                    }

                    crmReplyAlertScreenIntent = new Intent(context, AlertScreen.class);
                    context.startActivity(crmReplyAlertScreenIntent);
                    Toast.makeText(context, "Incoming call", Toast.LENGTH_SHORT).show();
                }else{
                    ContactInfoUpdater.addContactInfo(incomingNumber);
                    crmReplyAlertScreenIntent = new Intent(context, AlertScreen.class);
                    context.startActivity(crmReplyAlertScreenIntent);
                    Log.i(TAG, "incoming, info updated");
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception");
                e.printStackTrace();
            }
        } else if (state != null && state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            ContactInfoUpdater.clearContactInfo();
        }
    }
}
