package com.example.conor.whichprep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Objects;

public class MyDbHandler extends SQLiteOpenHelper {

    //DB name and version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "main.db";

    //DB Tables
    public static final String TABLE_PREPOSITION = "preposition";
    public static final String TABLE_TESTSENTENCE = "testsentence";

    //PREPOSITION table
    private static final String COLUMN_PREPNAME = "prepname";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_FRENCH = "french";
    private static final String COLUMN_SPANISH = "spanish";
    private static final String COLUMN_GERMAN = "german";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_EXAMPLE = "example";
    private static final String COLUMN_NUM_USED = "num_used";
    private static final String COLUMN_NUM_CORRECT = "num_correct";



    //TESTSENTENCE table. WILL TAKE LEVEL AND PREPNAME FROM ABOVE
    private static final String COLUMN_SENTENCE = "sentence";
    private static final String COLUMN_USED = "used";
    private static final String COLUMN_CHOICE = "choice";
    private static final String COLUMN_QUESTIONNUM = "questionNum";



    //Table create statements
    private static final String CREATE_TABLE_PREPOSITION = "CREATE TABLE "
            + TABLE_PREPOSITION + "("
            + COLUMN_PREPNAME + " TEXT PRIMARY KEY,"
            + COLUMN_LEVEL + " INTEGER,"
            + COLUMN_FRENCH + " TEXT,"
            + COLUMN_SPANISH + " TEXT,"
            + COLUMN_GERMAN + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_EXAMPLE + " TEXT,"
            + COLUMN_NUM_USED + " INTEGER,"
            + COLUMN_NUM_CORRECT + " INTEGER" + ");";

    private static final String CREATE_TABLE_TESTSENTENCE = "CREATE TABLE "
            + TABLE_TESTSENTENCE + "("
            + COLUMN_SENTENCE + " TEXT PRIMARY KEY,"
            + COLUMN_PREPNAME + " TEXT,"
            + COLUMN_LEVEL + " INTEGER, "
            + COLUMN_USED + " INTEGER, "
            + COLUMN_CHOICE + " TEXT,"
            + COLUMN_QUESTIONNUM + " INTEGER " + ");";


    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PREPOSITION);
        db.execSQL(CREATE_TABLE_TESTSENTENCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_PREPOSITION);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_TESTSENTENCE);
        onCreate(db);
    }

    //Method for adding preposition
    public void addPrep(Preposition prep) {
        ContentValues values = new ContentValues();
        if (!Objects.equals(prep.get_prepname(), "")) {
            values.put(COLUMN_PREPNAME, prep.get_prepname());
            values.put(COLUMN_LEVEL, prep.get_level());
            values.put(COLUMN_FRENCH, prep.get_french());
            values.put(COLUMN_SPANISH, prep.get_spanish());
            values.put(COLUMN_GERMAN, prep.get_german());
            values.put(COLUMN_DESCRIPTION, prep.get_description());
            values.put(COLUMN_EXAMPLE, prep.get_example());
            values.put(COLUMN_NUM_USED, prep.get_num_used());
            values.put(COLUMN_NUM_CORRECT, prep.get_num_correct());
            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_PREPOSITION, null, values);
            db.close();
        }
    }


    public void addSentence(TestSentence sentence, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        if ((!Objects.equals(sentence.get_sentence(), "")) && !(sentence.get_sentence().equals(""))) {          //does this check to see it doesn't exist in table already??
            values.put(COLUMN_SENTENCE, sentence.get_sentence());
            values.put(COLUMN_PREPNAME, sentence.get_prep());
            values.put(COLUMN_LEVEL, sentence.get_level());
            values.put(COLUMN_USED, sentence.get_used());
            values.put(COLUMN_CHOICE, sentence.get_choice());
            values.put(COLUMN_QUESTIONNUM, sentence.get_questionNum());

            db.insert(TABLE_TESTSENTENCE, null, values);
        }
    }

    //Deletes sentences used in test
    public void deleteSentences() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TESTSENTENCE);
    }


    public void getSentence(String sentence) {
        TestSentence validSentence = new TestSentence("", "", 0, 0, "", 0);
        String prep = "";
        String refreshSentence = sentence;
        int level;
        SQLiteDatabase db = getReadableDatabase();
        String prepQuery = "SELECT " + COLUMN_PREPNAME + " FROM " + TABLE_PREPOSITION + " WHERE 1";

        Cursor c = db.rawQuery(prepQuery, null);
        c.moveToFirst();
        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("prepname")) != null) {
                prep = c.getString(c.getColumnIndex("prepname"));
                String levelQuery = "SELECT " + COLUMN_LEVEL + " FROM " + TABLE_PREPOSITION + " WHERE " + COLUMN_PREPNAME + " = '" + prep + "';";
                Cursor cursor = db.rawQuery(levelQuery, null);
                cursor.moveToFirst();

                level = cursor.getInt(cursor.getColumnIndex("level"));

                prep = " " + prep + " ";
                if (sentence.contains(prep)) {
                    sentence = sentence.replaceAll(prep, " ___ ");
                    validSentence.set_sentence(sentence);
                    validSentence.set_prep(prep.substring(1, prep.length()-1)); // Adds preposition to database without spaces on either end
                    validSentence.set_level(level);
                    addSentence(validSentence, db);
                    sentence = refreshSentence;
                }
                cursor.close();
            }
            c.moveToNext();
        }
        c.close();
        db.close();
    }



    public boolean isPrepEmpty() {
        SQLiteDatabase db = getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_PREPOSITION;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        return (icount == 0);
    }

    public String prepareSentence(int level, boolean practice) {
        String sentence = "";
        String prepname = "";
        ArrayList<String> weakPreps = getPreps(0, 50, 0);
        SQLiteDatabase db = getWritableDatabase();
        String query = "";
        if(level != 0)
            query = "SELECT * FROM " + TABLE_TESTSENTENCE + " WHERE " + COLUMN_LEVEL + "=" + level + " AND " + COLUMN_USED + "=0 ORDER BY RANDOM() LIMIT 1";
        else
            query = "SELECT * FROM " + TABLE_TESTSENTENCE + " WHERE " +  COLUMN_USED + "=0 ORDER BY RANDOM() LIMIT 1";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
                if (weakPreps.contains(prepname)) {
                    if (cursor.getString(cursor.getColumnIndex("sentence")) != null) {
                        sentence = cursor.getString(cursor.getColumnIndex("sentence"));
                        db.execSQL("UPDATE " + TABLE_TESTSENTENCE + " SET " + COLUMN_USED + "=1 WHERE " + COLUMN_SENTENCE + "='" + sentence + "'");
                        break;
                    }
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return sentence;
    }

    public void addUserChoice(String sentence, String userChoice) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TESTSENTENCE + " SET " + COLUMN_CHOICE + "='" + userChoice + "' WHERE " + COLUMN_SENTENCE + "='" + sentence + "'");
        db.close();
    }

    public ArrayList<String> getAllPreps() {
        ArrayList<String> allPreps = new ArrayList<>();
        String prepname = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_PREPNAME + " FROM " + TABLE_PREPOSITION;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
                allPreps.add(prepname);
            }
            cursor.moveToNext();
        }
        db.close();
        return allPreps;
    }

    public ArrayList<String> getAnswerSuggestions(String sentence, int level){
        String prepname = "";
        ArrayList<String> answers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_PREPNAME + " FROM " + TABLE_TESTSENTENCE + "  WHERE " + COLUMN_SENTENCE + "='" + sentence + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
                answers.add(prepname);
            }
            cursor.moveToNext();
        }

        query = "SELECT " + COLUMN_PREPNAME + " FROM " + TABLE_PREPOSITION + "  WHERE " + COLUMN_PREPNAME + "!='" + prepname + "' AND " + COLUMN_LEVEL + "=" + level + " ORDER BY RANDOM() LIMIT 2";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                answers.add(cursor.getString(cursor.getColumnIndex("prepname")));
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return answers;
    }

    public void saveResult(String prep, String userChoice){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_PREPOSITION + " SET " + COLUMN_NUM_USED + "= " + COLUMN_NUM_USED + " + " + 1 + " WHERE " + COLUMN_PREPNAME + "='" + prep + "'");
        if(userChoice.equals(prep))
            db.execSQL("UPDATE " + TABLE_PREPOSITION + " SET " + COLUMN_NUM_CORRECT + "= " + COLUMN_NUM_CORRECT + " + " + 1 + " WHERE " + COLUMN_PREPNAME + "='" + prep + "'");
        db.close();
    }

    public void setQuestionNum(String sentence, int num){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TESTSENTENCE + " SET " + COLUMN_QUESTIONNUM + "=" + num + " WHERE " + COLUMN_SENTENCE + "='" + sentence + "'");
        db.close();
    }

    public void resetSentences(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_TESTSENTENCE + " SET " + COLUMN_QUESTIONNUM + "=" + 0 + ", " + COLUMN_USED + " = "+ 0);
        db.close();
    }

    public boolean getResult(int num){
        String prepname = "";
        String userChoice = "";
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_PREPNAME + ", " + COLUMN_CHOICE + " FROM " + TABLE_TESTSENTENCE + " WHERE " + COLUMN_QUESTIONNUM + "=" + num + "";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
            }
            if (cursor.getString(cursor.getColumnIndex("choice")) != null) {
                userChoice = cursor.getString(cursor.getColumnIndex("choice"));
            }
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return prepname.equals(userChoice);
    }

    public ArrayList<String> getPreps(int low, int high, int level){
        ArrayList<String> prepositionList = new ArrayList<>();
        String prepname = "";
        int used = 0;
        int correct = 0;
        int percentage = -1;
        SQLiteDatabase db = getReadableDatabase();
        String query = "";

            query = "SELECT " + COLUMN_PREPNAME + ", " + COLUMN_NUM_USED + ", " + COLUMN_NUM_CORRECT + " FROM " + TABLE_PREPOSITION;
        if(level != 0)
            query += " WHERE " + COLUMN_LEVEL + "=" + level;


        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
            }
            if (cursor.getInt(cursor.getColumnIndex("num_used")) != 0) {
                used = cursor.getInt(cursor.getColumnIndex("num_used"));
            }
            if (cursor.getInt(cursor.getColumnIndex("num_correct")) != -1) {
                correct = cursor.getInt(cursor.getColumnIndex("num_correct"));
            }
            if(used != 0 && used >= 10)
                percentage = (correct*100) / used;
            if(percentage != -1) {
                if (percentage > low && percentage <= high) {
                    prepositionList.add(prepname);
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return prepositionList;
    }


    public ArrayList<String> displayResult(int num){
        ArrayList<String> results = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_SENTENCE + ", " + COLUMN_PREPNAME + ", " + COLUMN_CHOICE + " FROM " + TABLE_TESTSENTENCE + " WHERE " + COLUMN_QUESTIONNUM + "=" + num + "";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("sentence")) != null) {
                results.add(cursor.getString(cursor.getColumnIndex("sentence")));
            }
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                results.add(cursor.getString(cursor.getColumnIndex("prepname")));
            }
            if (cursor.getString(cursor.getColumnIndex("choice")) != null) {
                results.add(cursor.getString(cursor.getColumnIndex("choice")));
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return results;
    }


    public ArrayList<String> getPrepInfo(String prep){
        ArrayList<String> prepInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_PREPNAME + ", " + COLUMN_DESCRIPTION + ", " + COLUMN_EXAMPLE + ", " + COLUMN_NUM_USED + ", " + COLUMN_NUM_CORRECT + " FROM " + TABLE_PREPOSITION + " WHERE " + COLUMN_PREPNAME + "='" + prep + "'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepInfo.add(cursor.getString(cursor.getColumnIndex("prepname")));
            }
            if (cursor.getString(cursor.getColumnIndex("description")) != null) {
                prepInfo.add(cursor.getString(cursor.getColumnIndex("description")));
            }
            if (cursor.getString(cursor.getColumnIndex("example")) != null) {
                prepInfo.add(cursor.getString(cursor.getColumnIndex("example")));
            }
            if (cursor.getInt(cursor.getColumnIndex("num_used")) != -1) {
                prepInfo.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("num_used"))));
            }
            if (cursor.getInt(cursor.getColumnIndex("num_correct")) != -1) {
                prepInfo.add(Integer.toString(cursor.getInt(cursor.getColumnIndex("num_correct"))));
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return prepInfo;
    }

    public String getPrep(String sentence) {
        String prepname = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_PREPNAME + " FROM " + TABLE_TESTSENTENCE + " WHERE " + COLUMN_SENTENCE + "='" + sentence + "'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("prepname")) != null) {
                prepname = cursor.getString(cursor.getColumnIndex("prepname"));
                break;
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return prepname;
    }
}
