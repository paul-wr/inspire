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
 *  Classname: FavoriteListAdapter.java
 *  Version 1
 *  Date: 12 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class FavoriteListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> gemList;
    public static final String MyFavPREFERENCES = "MyFavPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String delete;
    Intent intent;

    public FavoriteListAdapter(Context mContext, List<String> gemList) {
        this.mContext = mContext;
        this.gemList = gemList;
        sharedPreferences = mContext.getSharedPreferences(MyFavPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIntent(Intent intent){
        this.intent = intent;
    }

    @Override
    public int getCount() {
        return gemList.size();
    }

    @Override
    public Object getItem(int position) {
        return gemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.favorite_list, null);
        TextView tvFav, tvDate;
        tvFav = (TextView) v.findViewById(R.id.tv_fav);
        // set views
        tvFav.setText(gemList.get(position).toString());
        delete = gemList.get(position).toString();

        Button deleteFavBtn = (Button) v.findViewById(R.id.delete_fav_btn);
        Button deleteAllBtn = (Button) v.findViewById(R.id.delete_all_btn);
        deleteAllBtn.setVisibility(View.INVISIBLE);

        if(position == 0){
            deleteAllBtn.setVisibility(View.VISIBLE);
        }

        deleteFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // delete favorite record from SharedPreferences storage
                    editor.remove(delete);
                    editor.commit();
                    // updtate ListView of change in data
                    gemList.remove(position);
                    notifyDataSetChanged();
                    // inform user of change
                    Toast.makeText(mContext, "Favorite deleted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "You must be signed in to delete a favorite!\n" +
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


        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // delete all favorite records from SharedPreferences storage
                    editor.clear();
                    editor.commit();

                    // updtate ListView of change in data
                    gemList.clear();
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Favorites list deleted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "You must be signed in to delete favorites!\n" +
                            "Redirecting to sign in...", Toast.LENGTH_LONG).show();
                    userIntent = intent;
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500); // Launch login Activity after Toast message has run
                                Intent intent = new Intent(mContext, SigninActivity.class);
                                viewGroup.getContext().startActivity(intent);
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
