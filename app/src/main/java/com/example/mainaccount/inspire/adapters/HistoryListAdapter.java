package com.example.mainaccount.inspire.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.model.SigninActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.mainaccount.inspire.model.BaseActivity.userIntent;

/**
 *  Classname: HistoryListAdapter.java
 *  Version 1
 *  Date: 19 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class HistoryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> historyList;
    public static final String MyHistoryPREFERENCES = "MyHistoryPrefs";
    SharedPreferences sharedPreferencesHistory;
    SharedPreferences.Editor editorHistory;
    Intent intent;

    public HistoryListAdapter(Context mContext, List<String> historyList){
        this.mContext = mContext;
        this.historyList = historyList;
        sharedPreferencesHistory = mContext.getSharedPreferences(MyHistoryPREFERENCES, MODE_PRIVATE);
        editorHistory = sharedPreferencesHistory.edit();
    }

    public void setIntent(Intent intent){
        this.intent = intent;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.history_list, null);
        final TextView historyTV = (TextView) v.findViewById(R.id.tv_history);
        historyTV.setText(historyList.get(position).toString());

        Button deleteAllBtn = (Button) v.findViewById(R.id.delete_all_btn);
        deleteAllBtn.setVisibility(View.INVISIBLE);

        if(position == 0){
            deleteAllBtn.setVisibility(View.VISIBLE);
        }


        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // delete all favorite records from SharedPreferences storage
                    editorHistory.clear();
                    editorHistory.commit();

                    // updtate ListView of change in data
                    historyList.clear();
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Notification history has been deleted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "You must be signed in to delete notification history!\n" +
                            "Redirecting to sign in...", Toast.LENGTH_LONG).show();
                    userIntent = intent;
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500); // Launch login Activity after Toast message has run
                                Intent i = new Intent(mContext, SigninActivity.class);
                                viewGroup.getContext().startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                }

            }
        });

        return v;
    }
}
