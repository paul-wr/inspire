package com.example.mainaccount.inspire.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mainaccount.inspire.HelpfulService;
import com.example.mainaccount.inspire.R;

import java.util.List;


/**
 *  Classname: ServiceListAdapter.java
 *  Version 1
 *  Date: 8 Jul 2017
 *  @author Paul Wrenn, x15020029
 */

public class ServiceListAdapter extends BaseAdapter {
    private Context mContext;
    private List<HelpfulService> serviceList;

    public ServiceListAdapter(Context mContext, List<HelpfulService> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @Override
    public int getCount() {
        return serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.service_list, null);
        TextView tvName = (TextView) v.findViewById(R.id.tv_name);
        TextView tvAddress = (TextView) v.findViewById(R.id.tv_address);
        // set views
        tvName.setText(serviceList.get(position).getName());
        tvAddress.setText(serviceList.get(position).getAddress());

        // launch services website link on onClick event
        ImageButton webBtn = (ImageButton) v.findViewById(R.id.webButton);
        webBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(serviceList.get(position).getUrl()));
                viewGroup.getContext().startActivity(intent);
            }
        });

        // send email on onClick event to services email address
        ImageButton emailBtn = (ImageButton) v.findViewById(R.id.emailButton);
        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + serviceList.get(position).getEmail()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact through Inspire App helpful services listings:");
                viewGroup.getContext().startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        ImageButton phoneBtn = (ImageButton) v.findViewById(R.id.phone_button);
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+serviceList.get(position).getPhoneNo()));
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                viewGroup.getContext().startActivity(phoneIntent);
            }
        });
        return v;
    }
}
