package se.com.moritz.crmdialer.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Chronometer;

import se.com.moritz.crmdialer.R;

public class MakeCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer3);
        chronometer.start();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        PhoneStateListener callListener = new PhoneStateListener (){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if(state == TelephonyManager.CALL_STATE_RINGING) {

                }else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {

                }else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        };
        TelephonyManager mTM = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        mTM.listen(callListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

}
