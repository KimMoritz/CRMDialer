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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.iid.FirebaseInstanceId;
import se.com.moritz.crmdialer.phonecall.CallHandler;
import se.com.moritz.crmdialer.crm.ContactInfoUpdater;
import se.com.moritz.crmdialer.broadcast.MyBroadCastReceiver;
import se.com.moritz.crmdialer.R;
import se.com.moritz.crmdialer.phonecall.TelephonyManagerHandler;

public class AlertScreen extends AppCompatActivity {

    ListView callerInfoListView,accountInfoListView, caseInfoListView;
    int toastDuration;
    public static final int REQUEST_CODE_FOR_PHONE=1;
    public static final int REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE=2;
    public static final int REQUEST_CODE_FOR_CALL_PHONE=3;
    Context context;
    MyBroadCastReceiver myBroadCastReceiver;
    TelephonyManagerHandler telephonyManagerHandler;    //TODO: Needed?
    public final String TAG = "AlertScreen" ;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //variables
        super.onCreate(savedInstanceState);
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        setContentView(R.layout.activity_alert_screen);
        toastDuration = Toast.LENGTH_SHORT;

        //objects
        myBroadCastReceiver = new MyBroadCastReceiver();
        IntentFilter stateChangeIntentFilter =
                new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_RINGING);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_IDLE);
        stateChangeIntentFilter.addAction(TelephonyManager.EXTRA_STATE_OFFHOOK);
        context = getApplicationContext();
        context.registerReceiver(myBroadCastReceiver, stateChangeIntentFilter);
        telephonyManagerHandler = new TelephonyManagerHandler(this);

        checkPermissions(this, android.Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_FOR_PHONE);
        checkPermissions(this, android.Manifest.permission.CALL_PHONE, REQUEST_CODE_FOR_CALL_PHONE);
        checkPermissions(this, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,
                REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE);

        //Widgets
        final FloatingActionButton fabAccept = (FloatingActionButton) findViewById(R.id.fab_accept);
        final FloatingActionButton fabDeny = (FloatingActionButton) findViewById(R.id.fab_reject);
        callerInfoListView = (ListView) findViewById(R.id.callerInfoListView);
        accountInfoListView = (ListView) findViewById(R.id.account_info_listview);
        caseInfoListView = (ListView) findViewById(R.id.case_info_listview);
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
                            fabAccept.setVisibility(View.INVISIBLE);
                            fabDeny.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(context, R.string.no_call_to_answer, toastDuration).show();
                        }
                    }
                });

        fabDeny.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context = getApplicationContext();
                        if (CallHandler.getCall() != null) {
                            CallHandler.rejectCall();
                            Toast.makeText(context, R.string.call_rejected, toastDuration).show();
                            fabAccept.setVisibility(View.INVISIBLE);
                            fabDeny.setVisibility(View.INVISIBLE);
                        } else {
                            Toast.makeText(context, R.string.no_call_to_reject, toastDuration).show();
                        }
                    }
                });

        Log.d(TAG, "FireBase token: " + FirebaseInstanceId.getInstance().getToken());
    }

    public static void checkPermissions(Activity activity, String permission, int requestCode){
        if (ActivityCompat.checkSelfPermission(
                activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
        }
    }

    public void updateContactListView(){
        ContactInfoUpdater.updateListView(callerInfoListView,accountInfoListView, caseInfoListView, AlertScreen.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
}