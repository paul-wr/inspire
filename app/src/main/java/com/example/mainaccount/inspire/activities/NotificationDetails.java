package com.example.mainaccount.inspire.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.Date;

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
    String textString;
    String authorString;
    public static final String MyFavPREFERENCES = "MyFavPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Date date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        t = (TextView) findViewById(R.id.textView3);
        Button addBtn = (Button) findViewById(R.id.add_to_fav_btn);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyFavPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        date = new Date();
        setTitle("Inspire gem details");

        textString = "\""+text+"\"";

        authorString = " ~ "+author;
        // send notification text to TextView
        t.setText(textString);

        // store notification to favorites in shared preferences using unique string as key and value
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(textString+authorString+"\n\n"+"Saved date: "+date.toString(),
                        textString+authorString+"\n\n"+"Saved date: "+date.toString());
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
