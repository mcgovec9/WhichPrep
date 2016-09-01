package com.example.conor.whichprep;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class PreQuiz extends AppCompatActivity {
    RadioGroup rg;
    int pos;
    ToggleButton toggle;
    int levelSelected;
    String answerSuggestions = "";
    String time = "30";
    MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_quiz);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        answerSuggestions = "true";
        levelSelected = 1;
        dbHandler = new MyDbHandler(this,null,null,0);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                pos=rg.indexOfChild(findViewById(checkedId));

                switch (pos)
                {
                    case 0 :
                        levelSelected = 1;
                        break;
                    case 1 :
                        levelSelected = 2;
                        break;
                    case 2 :
                        levelSelected = 3;
                        break;
                    case 3 :
                        levelSelected = 4;
                        break;
                }
            }
        });

        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                answerSuggestions =  String.valueOf(buttonView.isChecked());
            }
        });


        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView timer = (TextView)findViewById(R.id.timer);
        seekBar.setMax(60);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < 20 ){                 //20 seconds is minimum
                    timer.setText("20");
                    time = "20";
                }
                else {
                    timer.setText(String.valueOf(progress));
                    time = String.valueOf(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void startQuiz(View view){
        Intent i;
        if(answerSuggestions.equals("true"))
             i = new Intent(this, QuizActivity.class);
        else
             i = new Intent(this, AltQuizActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("level", levelSelected);
        bundle.putString("answerSuggestions", answerSuggestions);
        bundle.putString("time", time);
        bundle.putBoolean("practice", false);
        i.putExtras(bundle);
        dbHandler.resetSentences();
        startActivity(i);
    }
}

