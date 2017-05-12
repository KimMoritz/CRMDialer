package se.com.moritz.crmdialer.phonecall;

import android.telecom.Call;
import android.telecom.InCallService;
import android.util.Log;

public class MyInCallService extends InCallService {
    private static final String TAG= "MyInCallService";
    private static Call incomingCall;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onCallAdded(Call call) {
        incomingCall = call;
        Log.i(TAG, "Call added.");
        super.onCallAdded(call);
        switch (call.getState()){
            case Call.STATE_RINGING:
                Log.i(TAG, "Call in ringing state.");
                CallHandler.setCall(call);
            case Call.STATE_ACTIVE:
        }
    }

    @Override
    public void onCallRemoved(Call call){
        incomingCall = null;
    }

    public static boolean hasCall() {
            return incomingCall!=null;
    }
}