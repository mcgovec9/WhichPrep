package com.example.conor.whichprep;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StatsMainActivity extends AppCompatActivity {
    Bundle bundle;
    ArrayList<String> prepList;
    MyDbHandler dbHandler;
    ListView weakPreps;
    ListView fairPreps;
    ListView strongPreps;
    ListAdapter adapter1;
    ListAdapter adapter2;
    ListAdapter adapter3;
    String preposition;
    int level;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHandler = new MyDbHandler(this,null,null,0);
        bundle = getIntent().getExtras();
        level = bundle.getInt("level");
        prepList = dbHandler.getPreps(0,50,level);
        adapter1 = new ArrayAdapter<>(this, R.layout.list_item, prepList);
        weakPreps = (ListView) findViewById(R.id.weakPreps);
        weakPreps.setAdapter(adapter1);

        weakPreps.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        preposition = (String) weakPreps.getItemAtPosition(position);
                        getPrepInfo(view);
                    }
                }

        );

        prepList = dbHandler.getPreps(51, 75,level);
        fairPreps = (ListView) findViewById(R.id.fairPreps);
        adapter2 = new ArrayAdapter<>(this, R.layout.list_item, prepList);
        fairPreps.setAdapter(adapter2);
        fairPreps.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        preposition = (String) fairPreps.getItemAtPosition(position);
                        getPrepInfo(view);
                    }
                }

        );

        prepList = dbHandler.getPreps(76, 100,level);
        strongPreps = (ListView) findViewById(R.id.strongPreps);
        adapter3 = new ArrayAdapter<>(this, R.layout.list_item, prepList);
        strongPreps.setAdapter(adapter3);
        strongPreps.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        preposition = (String) strongPreps.getItemAtPosition(position);
                        getPrepInfo(view);
                    }
                }

        );
    }

    public void getPrepInfo(View view) {
        Intent i = new Intent(this, PrepositionInfo.class);
        Bundle bundle = new Bundle();
        bundle.putString("prep", preposition);
        i.putExtras(bundle);
        startActivity(i);
    }
}
