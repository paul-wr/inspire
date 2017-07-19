package com.example.mainaccount.inspire.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.Calendar;

import static com.example.mainaccount.inspire.broadcasts.NotificationReceiver.author;
import static com.example.mainaccount.inspire.broadcasts.NotificationReceiver.text;

/**
 *  Classname: NotificationDetails.java
 *  Version 1
 *  Date: 28 Jun 2017
 *  @author Paul Wrenn, x15020029
 */

public class NotificationDetails extends BaseActivity {
    TextView t;
    private String textString;
    private String authorString;
    private String fullGemData;
    public static final String MyFavPREFERENCES = "MyFavPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Calendar c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        setTitle("Inspire gem details");
        t = (TextView) findViewById(R.id.textView3);
        Button addBtn = (Button) findViewById(R.id.add_to_fav_btn);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyFavPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        String dateString = "Saved date: "+date+"/"+month+"/"+year+" - "+hour+":"+minutes+":"+seconds;

        textString = "\""+text+"\"";

        authorString = " ~ "+author;
        // send notification text to TextView
        t.setText(textString);
        fullGemData = textString+authorString+"\n\n"+dateString;

        // store notification to favorites in shared preferences using unique string as key and value
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(fullGemData, fullGemData);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Gem added to Favorites list!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onPause(){
        super.onPause();
        finish();
    }

    // onRestart() method call refreshes user login data in the menu
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
