package com.example.conor.whichprep;

public class Preposition {
    private String _prepname;
    private int _level;
    private String _french;
    private String _spanish;
    private String _german;
    private String _description;
    private String _example;
    private int _num_used;
    private int _num_correct;


    public Preposition(){
    }

    public Preposition(String prepname, int level, String french, String spanish, String german, String description, String example, int num_used, int num_correct) {
        this._prepname  = prepname;
        this._level     = level;
        this._french    = french;
        this._spanish   = spanish;
        this._german    = german;
        this._description = description;
        this._example = example;
        this._num_used = num_used;
        this._num_correct = num_correct;

    }

    public String get_example() {
        return _example;
    }

    public void set_example(String _example) {
        this._example = _example;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_french() {
        return _french;
    }

    public void set_french(String _french) {
        this._french = _french;
    }

    public String get_german() {
        return _german;
    }

    public void set_german(String _german) {
        this._german = _german;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public String get_prepname() {
        return _prepname;
    }

    public void set_prepname(String _prepname) {
        this._prepname = _prepname;
    }

    public String get_spanish() {
        return _spanish;
    }

    public void set_spanish(String _spanish) {
        this._spanish = _spanish;
    }

    public int get_num_correct() {
        return _num_correct;
    }

    public void set_num_correct(int _num_correct) {
        this._num_correct = _num_correct;
    }

    public int get_num_used() {
        return _num_used;
    }

    public void set_num_used(int _num_used) {
        this._num_used = _num_used;
    }
}
