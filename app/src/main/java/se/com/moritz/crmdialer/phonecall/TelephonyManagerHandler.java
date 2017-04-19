package se.com.moritz.crmdialer.phonecall;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class TelephonyManagerHandler {

    private TelecomManager telecomManager;
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    private static final String TAG = "TelephonyManagerHandler";

    public TelephonyManagerHandler(Context context){
        telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new PhoneStateListener();
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
