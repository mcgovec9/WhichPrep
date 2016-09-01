package com.example.conor.whichprep;

public class TestSentence {
    private String _sentence;
    private String _prep;
    private String _choice;
    private int _level;
    private int _used;
    private int _questionNum;

    public TestSentence(){}

    public TestSentence(String _sentence, String _prep, int _level, int _used, String _choice, int _questionNum){
        this._level = _level;
        this._prep = _prep;
        this._sentence = _sentence;
        this._used = _used;
        this._choice = _choice;
        this._questionNum = _questionNum;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public String get_sentence() {
        return _sentence;
    }

    public void set_sentence(String _sentence) {
        this._sentence = _sentence;
    }

    public String get_prep() {
        return _prep;
    }

    public void set_prep(String _prep) {
        this._prep = _prep;
    }

    public int get_used() {
        return _used;
    }

    public void set_used(int _used) {
        this._used = _used;
    }

    public String get_choice() {
        return _choice;
    }

    public void set_choice(String _choice) {
        this._choice = _choice;
    }

    public int get_questionNum() {
        return _questionNum;
    }

    public void set_questionNum(int _questionNum) {
        this._questionNum = _questionNum;
    }
}
