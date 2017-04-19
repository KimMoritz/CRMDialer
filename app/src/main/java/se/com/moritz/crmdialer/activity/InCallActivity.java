package se.com.moritz.crmdialer.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;
import se.com.moritz.crmdialer.phonecall.CallHandler;
import se.com.moritz.crmdialer.R;

public class InCallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer2);
        chronometer.start();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallHandler.disconnectCall();
                chronometer.stop();
                finish();
            }
        });
    }

}
