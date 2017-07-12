package com.example.mainaccount.inspire;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.Date;

import static com.example.mainaccount.inspire.NotificationReceiver.author;
import static com.example.mainaccount.inspire.NotificationReceiver.text;


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

        textString = "\""+text+"\"";

        authorString = " ~ "+author;
        // send static data to TextView
        t.setText(textString);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(textString+authorString+"\n\n"+"Saved date: "+date.toString(),
                        textString+authorString+"\n\n"+"Saved date: "+date.toString());
                editor.commit();
            }
        });

    }

    // onRestart() method call refreshes user login data in the menu
    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
