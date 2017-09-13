package se.com.moritz.crmdialer.phonecall;

import android.telecom.Call;
import android.util.Log;

import se.com.moritz.crmdialer.crm.ContactInfoUpdater;

public abstract class CallHandler {
    private static Call call;
    private static final String TAG = "CallHandler";

    static void setCall(Call incomingCall){
        call = incomingCall;
    }

    public static Call getCall() {
        return call;
    }

    public static void acceptCall(int videoState){
        call.answer(videoState);
        Log.i(TAG, "Call answered with videoState: " + videoState);
    }

    public static void rejectCall(String rejectMessage){
        call.reject(true, rejectMessage);
        call = null;
        Log.i(TAG, "Call rejected with message");
    }

    public static void rejectCall(){
        call.reject(false, "No message");
        call = null;
        Log.i(TAG, "Call rejected without message");
        ContactInfoUpdater.clearContactInfo();
    }

    public static void disconnectCall(){
        call.disconnect();
        Log.i(TAG, "Call disconnected");
        ContactInfoUpdater.clearContactInfo();
    }

}
