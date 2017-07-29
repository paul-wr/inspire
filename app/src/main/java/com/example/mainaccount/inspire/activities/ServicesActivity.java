package com.example.mainaccount.inspire.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.example.mainaccount.inspire.HelpfulService;
import com.example.mainaccount.inspire.R;
import com.example.mainaccount.inspire.adapters.ServiceListAdapter;
import com.example.mainaccount.inspire.model.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends BaseActivity {
    private ListView lvService;
    private ServiceListAdapter adapter;
    private List<HelpfulService> mServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        lvService = (ListView) findViewById(R.id.service_list);
        mServiceList = new ArrayList<>();

        mServiceList.add(new HelpfulService(1, "Pieta House", "6 Main Street, Lower Lucan, Co Dublin", "016715551","info@pietahouse.ie", "http://www.pieta.ie/"));
        mServiceList.add(new HelpfulService(2, "Dublin Simon Community", "1-2 Cope Street, Dublin 2, Dublin", "0838579446", "info@pietahouse.ie", "http://www.simon.ie/"));
        mServiceList.add(new HelpfulService(3, "Well Woman Centre", "25 Capel Street, Dublin 1, Co Dublin", "018749243", "info@wellwomancentre.ie", "http://wellwomancentre.ie/services/"));
        mServiceList.add(new HelpfulService(4, "Acu Well", "Office 4, 126 Ranelagh Village, Dublin 6", "0877788591", "info@acuwell.ie", "http://www.acuwell.ie/"));
        mServiceList.add(new HelpfulService(5, "HSE Counselling and Advisory Services", "Block F, Edward Court, Edward Street, Tralee, Kerry", "0667184968", "Dolores.tiernan@HSE.ie", "http://www.hse.ie"));
        mServiceList.add(new HelpfulService(6, "Western Area Drugs Service", "64 Dominick St, Galway, Galway", "091561299", "drugs.services@hse.ie", "http://www.hse.ie/"));

        adapter = new ServiceListAdapter(getApplicationContext(), mServiceList);
        lvService.setAdapter(adapter);

    }
}
