package com.example.conor.whichprep;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class StatsActivity extends AppCompatActivity {
    Button practice;
    Button level1;
    Button level2;
    Button level3;
    Button level4;
    Intent i;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        i = new Intent(this, StatsMainActivity.class);
        bundle = new Bundle();
        practice = (Button) findViewById(R.id.practiceBtn);
        level1 = (Button) findViewById(R.id.lev1Btn);
        level2 = (Button) findViewById(R.id.lev2Btn);
        level3 = (Button) findViewById(R.id.lev3Btn);
        level4 = (Button) findViewById(R.id.lev4Btn);
        // MyDbHandler dbHandler = new MyDbHandler(this, null, null, 1);
       // String sentence = dbHandler.databaseToString();

    }

    public void startPracticeQuiz(View view){
        Intent i;
            i = new Intent(this, QuizActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("answerSuggestions", "true");
        bundle.putString("time", "60");
        bundle.putBoolean("practice", true);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void showLevel1(View view){
        bundle.putInt("level", 1);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void showLevel2(View view){
        bundle.putInt("level", 2);
        i.putExtras(bundle);
        startActivity(i);
    }
    public void showLevel3(View view){
        bundle.putInt("level", 3);
        i.putExtras(bundle);
        startActivity(i);
    }
    public void showLevel4(View view){
        bundle.putInt("level", 4);
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
