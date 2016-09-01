package com.example.conor.whichprep;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class PrepositionInfo extends AppCompatActivity {
    MyDbHandler dbhandler;
    Bundle bundle;
    TextView preposition;
    TextView description;
    TextView example;
    TextView history;
    private String prep;
    String userHistory;
    private ArrayList<String> prepInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preposition_info);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        preposition = (TextView) findViewById(R.id.prep);
        description = (TextView) findViewById(R.id.desc);
        example = (TextView) findViewById(R.id.example);
        history = (TextView) findViewById(R.id.history);

        dbhandler = new MyDbHandler(this,null,null,0);
        bundle = getIntent().getExtras();
        prep = bundle.getString("prep");
        prepInfo = dbhandler.getPrepInfo(prep);

        userHistory = prepInfo.get(4) + " / " + prepInfo.get(3);
        preposition.setText("\" " + prepInfo.get(0) + " \"");
        description.setText(prepInfo.get(1));
        example.setText(prepInfo.get(2));
        history.setText(userHistory + " correct");
    }
}
