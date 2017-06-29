package com.example.mainaccount.inspire;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mainaccount.inspire.model.BaseActivity;

import static com.example.mainaccount.inspire.MyReceiver.text;


public class NotificationDetails extends BaseActivity {
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        t = (TextView) findViewById(R.id.textView3);

        // send static data to TextView
        t.setText(text);

    }
}
