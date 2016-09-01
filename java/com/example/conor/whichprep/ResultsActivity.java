package com.example.conor.whichprep;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {
    private MyDbHandler dbhandler;
    private  Button q1;
    private  Button q2;
    private  Button q3;
    private  Button q4;
    private  Button q5;
    private  Button q6;
    private  Button q7;
    private  Button q8;
    private  Button q9;
    private  Button q10;
    private  ImageButton finish;
    private  int questionNum;

    @Override
    public void onBackPressed() {   //Ensures user cant go back to settings once a quiz 'session' has started
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        dbhandler = new MyDbHandler(this, null, null, 0);
        q1 = (Button) findViewById(R.id.q1);
        q2 = (Button) findViewById(R.id.q2);
        q3 = (Button) findViewById(R.id.q3);
        q4 = (Button) findViewById(R.id.q4);
        q5 = (Button) findViewById(R.id.q5);
        q6 = (Button) findViewById(R.id.q6);
        q7 = (Button) findViewById(R.id.q7);
        q8 = (Button) findViewById(R.id.q8);
        q9 = (Button) findViewById(R.id.q9);
        q10 = (Button) findViewById(R.id.q10);
        finish = (ImageButton) findViewById(R.id.finish);

        if(!dbhandler.getResult(1))
            q1.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(2))
            q2.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(3))
            q3.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(4))
            q4.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(5))
            q5.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(6))
            q6.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(7))
            q7.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(8))
            q8.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(9))
            q9.setBackgroundColor(Color.parseColor("#e74c3c"));
        if(!dbhandler.getResult(10))
            q10.setBackgroundColor(Color.parseColor("#e74c3c"));



        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 1;
                startPostQuiz(view);
            }
        });

        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 2;
                startPostQuiz(view);
            }
        });

        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 3;
                startPostQuiz(view);
            }
        });

        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 4;
                startPostQuiz(view);
            }
        });

        q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 5;
                startPostQuiz(view);
            }
        });

        q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 6;
                startPostQuiz(view);
            }
        });

        q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 7;
                startPostQuiz(view);
            }
        });

        q8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                questionNum = 8;
                startPostQuiz(view);
            }
        });

        q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 9;
                startPostQuiz(view);
            }
        });

        q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionNum = 10;
                startPostQuiz(view);
            }
        });
    }

    public void startPostQuiz(View view) {
        Intent i = new Intent(this, PostQuizActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("questionNum", questionNum);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void backToHome(View view){

        //add num_used and num_correct here??
        dbhandler.resetSentences();
        startActivity(new Intent(this, HomeActivity.class));
    }
}

