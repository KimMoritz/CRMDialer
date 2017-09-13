package se.com.moritz.crmdialer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import se.com.moritz.crmdialer.R;
import se.com.moritz.crmdialer.firebase_messaging.MyFirebaseInstanceIdService;
import se.com.moritz.crmdialer.phonecall.MyBroadCastReceiver;

public class StartScreenActivity extends AppCompatActivity {


    public static final int REQUEST_CODE_FOR_PHONE=1;
    public static final int REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE=2;
    public static final int REQUEST_CODE_FOR_CALL_PHONE=3;
    public static final String TAG = "StartScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TelephonyManager t = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        MyBroadCastReceiver.phoneNumber = t.getLine1Number();
        Log.i(TAG, "Firebase token:" + MyFirebaseInstanceIdService.getFirebaseToken());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call38();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call39();
            }
        });

        checkPermissions(this, android.Manifest.permission.READ_PHONE_STATE, REQUEST_CODE_FOR_PHONE);
        checkPermissions(this, android.Manifest.permission.CALL_PHONE, REQUEST_CODE_FOR_CALL_PHONE);
        checkPermissions(this, Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,
                REQUEST_CODE_FOR_BIND_TELECOM_CONNECTION_SERVICE);
    }

    public void call38() {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+4917268050038"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.CALL_PHONE},0);
            return;
        }
        startActivity(callIntent);/*
        Intent inCallActivityIntent = new Intent(StartScreenActivity.this, MakeCallActivity.class);
        startActivity(inCallActivityIntent);*/
    }

    public void call39() {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+4917268050039"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.CALL_PHONE},0);
            return;
        }
        startActivity(callIntent);
        /*Intent inCallActivityIntent = new Intent(StartScreenActivity.this, MakeCallActivity.class);
        startActivity(inCallActivityIntent);*/
    }

    public static void checkPermissions(Activity activity, String permission, int requestCode){
        if (ActivityCompat.checkSelfPermission(
                activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
        }
    }

}
