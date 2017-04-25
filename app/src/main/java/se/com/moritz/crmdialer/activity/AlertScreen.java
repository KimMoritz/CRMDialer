package se.com.moritz.crmdialer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telecom.VideoProfile;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import se.com.moritz.crmdialer.phonecall.CallHandler;
import se.com.moritz.crmdialer.crm.ContactInfoUpdater;
import se.com.moritz.crmdialer.broadcast.MyBroadCastReceiver;
import se.com.moritz.crmdialer.R;
import se.com.moritz.crmdialer.phonecall.TelephonyManagerHandler;

public class AlertScreen extends AppCompatActivity {

    ListView callerInfoListView;
    Toast toast;
    int toastDuration;
    public static final int REQUEST_CODE_FOR_PHONE=1;
    public static final int REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE=2;
    public static final int REQUEST_CODE_FOR_CALL_PHONE=3;
    Context context;
    MyBroadCastReceiver myBroadCastReceiver;
    TelephonyManagerHandler telephonyManagerHandler;    //TODO: Needed?
    public final String TAG = "AlertScreen" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_screen);
        toastDuration = Toast.LENGTH_SHORT;

        //objects
        myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter stateChangeIntentFilter =
                new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_RINGING);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_IDLE);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_OFFHOOK);
        IntentFilter dialIntentFilter =
                new IntentFilter(Intent.ACTION_DIAL);   //TODO: Only needed in dialer screen?
        context = getApplicationContext();
        context.registerReceiver(myBroadCastReceiver, stateChangeIntentFilter);
        telephonyManagerHandler = new TelephonyManagerHandler(this);

        checkPermissions(this, android.Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_FOR_PHONE);
        checkPermissions(this, android.Manifest.permission.CALL_PHONE, REQUEST_CODE_FOR_CALL_PHONE);
        checkPermissions(this, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,
                REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE);

        //Widgets
        FloatingActionButton fabAccept = (FloatingActionButton) findViewById(R.id.fab_accept);
        FloatingActionButton fabDeny = (FloatingActionButton) findViewById(R.id.fab_reject);
        callerInfoListView = (ListView) findViewById(R.id.callerInfoListView);
        updateContactListView();
        //Button listeners
        fabAccept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context = getApplicationContext();
                        if (CallHandler.getCall() != null) {
                            CallHandler.acceptCall(VideoProfile.STATE_AUDIO_ONLY);
                            Intent inCallActivityIntent = new Intent(AlertScreen.this, InCallActivity.class);
                            startActivity(inCallActivityIntent);
                        } else {
                            Toast.makeText(context, "There is no call to answer", toastDuration).show();
                        }
                    }
                });

        fabDeny.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context = getApplicationContext();
                        toast = Toast.makeText(context, R.string.rejecting_call, toastDuration);
                        toast.show();
                        if (CallHandler.getCall() != null) {
                            CallHandler.rejectCall();
                            Log.i(TAG, "Call rejected");
                        } else {
                            Toast.makeText(context, "There is no call to reject", toastDuration).show();
                        }
                    }
                });

        String Token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FireBase token: " + Token);
    }

    public static void checkPermissions(Activity activity, String permission, int requestCode){
        if (ActivityCompat.checkSelfPermission(
                activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
        }
    }

    public void updateContactListView(){
        ContactInfoUpdater.updateListView(callerInfoListView, AlertScreen.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}