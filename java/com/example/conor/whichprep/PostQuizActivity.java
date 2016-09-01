package com.example.conor.whichprep;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostQuizActivity extends AppCompatActivity {

    Bundle bundle;
    int questionNum;
    ArrayList<String> results;
    MyDbHandler dbhandler;
    String question;
    TextView questionView;
    String sentence;
    TextView sentenceView;
    Button correctPrep;
    Button userPrep;
    String correct;
    String userChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_quiz);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbhandler = new MyDbHandler(this, null, null, 0);
        bundle = getIntent().getExtras();
        questionNum = bundle.getInt("questionNum");

        results = dbhandler.displayResult(questionNum);
        correctPrep = (Button) findViewById(R.id.correctPrep);
        userPrep = (Button) findViewById(R.id.userPrep);
        correct =  results.get(1);
        userChoice = results.get(2);

        if(results.get(1).equals(results.get(2))){
            userChoice = correct;
        }

        correctPrep.setText(correct);
        userPrep.setText(userChoice);

        questionView = (TextView) findViewById(R.id.questionView);
        question = "Question " + (questionNum);
        questionView.setText(question);

        sentence = results.get(0);
        sentenceView = (TextView) findViewById(R.id.sentenceView);
        sentenceView.setText(sentence);

        if (!(correct.equals(userChoice)))
            userPrep.setBackgroundColor(Color.parseColor("#e74c3c"));

    }


    public void getCorrectPrepInfo(View view) {
        Intent i = new Intent(this, PrepositionInfo.class);
        Bundle bundle = new Bundle();
        bundle.putString("prep", correct);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void getUserPrepInfo(View view) {
        Intent i = new Intent(this, PrepositionInfo.class);
        Bundle bundle = new Bundle();
        bundle.putString("prep", userChoice);
        i.putExtras(bundle);
        startActivity(i);
    }
}