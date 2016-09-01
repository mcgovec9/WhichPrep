package com.example.conor.whichprep;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class QuizActivity extends AppCompatActivity {
    Bundle bundle;
    String correctPrep;
    String answerSuggestions;
    String question;
    String sentence;
    String userChoice;
    boolean practice;
    int levelSelected;
    String time;
    long seconds;
    ArrayList<String> prepList;
    MyDbHandler dbhandler;
    TextView questionView;
    TextView sentenceView;
    CountDownTimer countdown;
    TextView timer;
    ImageButton next;
    int count;
    boolean itemSelected = false;
    boolean nextButtonPressed = false;

    ListView answerList;
    ListAdapter myAdapter;

    @Override
    public void onBackPressed() {   //Ensures user cant go back to settings once a quiz 'session' has started
    }

    public void activityCounter(int count){
        if(count != 10) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else {
            startActivity(new Intent(QuizActivity.this, ResultsActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        answerSuggestions = bundle.getString("answerSuggestions");
        levelSelected = bundle.getInt("level");
        time = bundle.getString("time") + "000";
        practice = bundle.getBoolean("practice");
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        dbhandler = new MyDbHandler(this,null,null,1);
        count = ((MyApplication) getApplicationContext()).getCount();
        questionView = (TextView)findViewById(R.id.questionView);
        question = "Question " + (count);
        questionView.setText(question);

        sentence = dbhandler.prepareSentence(levelSelected, practice);
        sentenceView = (TextView)findViewById(R.id.sentenceView);
        sentenceView.setText(sentence);


        timer = (TextView)findViewById(R.id.timer);

        seconds = Long.parseLong(time) + 1000;
        timer.setText(time);
        next = (ImageButton) findViewById(R.id.nextBtn);

        prepList = dbhandler.getAnswerSuggestions(sentence, levelSelected);
        correctPrep = prepList.get(0);
        Collections.shuffle(prepList);
        myAdapter = new ArrayAdapter<>(this, R.layout.list_item, prepList);
        answerList = (ListView) findViewById(R.id.answerList);
        answerList.setAdapter(myAdapter);

        answerList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        itemSelected = true;
                        userChoice = (String) answerList.getItemAtPosition(position);
                        countdown.cancel();
                    }
                }

        );

        countdown = new CountDownTimer(seconds, 1000) {
            public void onTick(long millisUntilFinished) {
               timer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if(!nextButtonPressed)
                    dbhandler.addUserChoice(sentence, "No choice");
                    dbhandler.saveResult(correctPrep, "No choice");
                    activityCounter(count);
            }
        };
        countdown.start();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemSelected) {
                    Toast.makeText(MyApplication.getAppContext(), "You must select an option from the menu above", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbhandler.addUserChoice(sentence, userChoice);
                    dbhandler.setQuestionNum(sentence, count);
                    nextButtonPressed = true;
                    dbhandler.saveResult(correctPrep, userChoice);
                    activityCounter(count);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quit_quiz_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_quit){
            ((MyApplication) getApplicationContext()).setCount(0);
            countdown.cancel();
            dbhandler.resetSentences();
            startActivity(new Intent(this, HomeActivity.class));
        }
        return true;
    }
}
