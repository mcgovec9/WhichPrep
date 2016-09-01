package com.example.conor.whichprep;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    public static String url = "";

    public void startBlog(){
        startActivity(new Intent(this, blogWebActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] blogs = {"Building the Front End", "Idea for the WebScraper","Creating the WebScraper"}; //Add others

        //Takes string array and converts into listview
        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, blogs);
        ListView myListView = (ListView) findViewById(R.id.blogList);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        url = String.valueOf(parent.getItemAtPosition(position));

                        if(url == "Building the Front End")
                            url = "http://blogs.computing.dcu.ie/wordpress/conormcg/2016/03/14/building-the-front-end/";
                        else if(url == "Idea for the WebScraper")
                            url = "http://blogs.computing.dcu.ie/wordpress/conormcg/2016/03/14/creating-the-webscraper/";
                        else if(url == "Creating the WebScraper")
                            url = "http://blogs.computing.dcu.ie/wordpress/conormcg/2016/03/15/creating-the-webscraper-2/";

                        startBlog();
                    }
                }

        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
