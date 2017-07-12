package com.example.mainaccount.inspire;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mainaccount on 12/07/2017.
 */

public class FavoriteListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> gemList;
    public static final String MyFavPREFERENCES = "MyFavPrefs";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public FavoriteListAdapter(Context mContext, List<String> gemList) {
        this.mContext = mContext;
        this.gemList = gemList;
        sharedPreferences = mContext.getSharedPreferences(MyFavPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
        tvDate = (TextView) v.findViewById(R.id.tv_date);
        tvFav = (TextView) v.findViewById(R.id.tv_fav);
        // set views
        tvFav.setText(gemList.get(position).toString());
        final String delete = gemList.get(position).toString();


        Button deleteFavBtn = (Button) v.findViewById(R.id.delete_fav_btn);
        Button deleteAllBtn = (Button) v.findViewById(R.id.delete_all_btn);
        deleteAllBtn.setVisibility(View.INVISIBLE);

        if(position == 0){
            deleteAllBtn.setVisibility(View.VISIBLE);
        }

        deleteFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete favorite record from storage
                sharedPreferences.edit().remove(delete).commit();
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // delete all favorite records from storage
                editor.clear();
                editor.commit();
            }
        });

        return v;
    }

}
