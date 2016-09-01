package com.example.conor.whichprep;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class AltQuizActivity extends AppCompatActivity {
    private Bundle bundle;
    private String question;
    private String sentence;
    private String correctPrep;
    private String userChoice;
    private int levelSelected;
    private String time;
    private long seconds;
    private MyDbHandler dbhandler;
    private TextView questionView;
    private TextView sentenceView;
    private CountDownTimer countdown;
    private TextView timer;
    private ImageButton btnSpeak;
    private ImageButton next;
    private EditText input;
    private SpeechRecognizer sr;
    private int count;
    private boolean nextButtonPressed = false;



    @Override
    public void onBackPressed() {   //Ensures user cant go back to settings once a quiz 'session' has started
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        levelSelected = bundle.getInt("level");
        time = bundle.getString("time") + "000";

        setContentView(R.layout.activity_quiz_alt);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        dbhandler = new MyDbHandler(this,null,null,1);
        count = ((MyApplication) getApplicationContext()).getCount();
        questionView = (TextView)findViewById(R.id.questionView);
        question = "Question " + (count);
        questionView.setText(question);

        sentence = dbhandler.prepareSentence(levelSelected, false);
        sentenceView = (TextView)findViewById(R.id.sentenceView);
        sentenceView.setText(sentence);

        correctPrep = dbhandler.getPrep(sentence);
        timer = (TextView)findViewById(R.id.timer);
        seconds = Long.parseLong(time) + 1000;
        timer.setText(time);
        next = (ImageButton) findViewById(R.id.nextBtn);

        input = (EditText) findViewById(R.id.editText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnSpeak)
                {
                    countdown.cancel();
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
                    sr.startListening(intent);
                }
            }
        });

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
                if (userChoice == null) {
                    Toast.makeText(MyApplication.getAppContext(), "You have not entered any input!", Toast.LENGTH_SHORT).show();
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

    public void activityCounter(int count){
        if(count != 10) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        else {
            startActivity(new Intent(AltQuizActivity.this, ResultsActivity.class));
            //start postQuiz Activity
        }
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params) {
        }
        public void onBeginningOfSpeech() {
        }
        public void onRmsChanged(float rmsdB) {
        }
        public void onBufferReceived(byte[] buffer) {
        }
        public void onEndOfSpeech() {
        }
        public void onError(int error) {
        }
        public void onResults(Bundle results) {
            String speech = "";
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (String word : data){
                if (dbhandler.getAllPreps().contains(word))
                    speech = word;
            }
            input.setText(speech);
            userChoice = speech + " ";
        }
        public void onPartialResults(Bundle partialResults) {
        }
        public void onEvent(int eventType, Bundle params) {
        }
    }

}

