package com.example.conor.whichprep;


import android.os.AsyncTask;
import android.widget.Toast;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebScraper extends AsyncTask<Void, Void, Void>{
    static Calendar c = Calendar.getInstance();
    static SimpleDateFormat df = new SimpleDateFormat("yyyy/MMM/dd/");
    MyDbHandler dbHandler = new MyDbHandler(MyApplication.getAppContext(), null, null, 1);
    String url = "http://www.theguardian.com/";

    public ArrayList<String> getLinks(){
        try
        {
            ArrayList<String> topics = new ArrayList<>();
            topics.add("sport");
            topics.add("lifeandstyle");
            topics.add("environment");
            topics.add("tv-and-radio");
            topics.add("music");
            topics.add("film");
            topics.add("technology");

            ArrayList<String> urls = new ArrayList<>();
            for (String topic: topics) {

                Document doc = Jsoup.connect(updateLinkDate(url + topic + "/")).get();
                Elements links = doc.select(".fc-item__title > a");
                for (Element link : links) {
                    if(!link.absUrl("href").contains("/live/")
                            && !link.absUrl("href").contains("/video/")
                            && !link.absUrl("href").contains("/picture/")
                            && !link.absUrl("href").contains("/blog/")
                            && !link.absUrl("href").contains("/gardening-blog/")
                            && !link.absUrl("href").contains("/womens-blog/")
                            && !link.absUrl("href").contains("/filmblog/")
                            && !link.absUrl("href").contains("/gallery/")
                            && !link.absUrl("href").contains("/audio/")
                            && !link.absUrl("href").contains("/shortcuts/")
                            && !link.absUrl("href").contains("/planet-oz/")
                            && !link.absUrl("href").contains("/business/")
                            && !link.absUrl("href").contains("/world/")
                            && !link.absUrl("href").contains("/lostinshowbiz/")) //filter out wrong url types
                        urls.add(link.absUrl("href"));
                }
            }
            return urls;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    public String updateLinkDate(String url){
        c.add(Calendar.DATE, -1);
        String formattedDate = df.format(c.getTime());
        return url + formattedDate;

    }

    public int numWords(String sentence){
        int wordCount = 0;
        int i = 0;
        while (i != sentence.length() - 1) {
            if (sentence.charAt(i) == ' ') {
                wordCount++;
                i++;
            } else
                i++;
        }
        return wordCount + 1;
    }


    public void splitString(String text){
        if(!(text == null)) {
            String[] parts = text.split("\\. ");
            for (String sentence : parts) {
                if (numWords(sentence) < 18 && numWords(sentence) > 6)
                    dbHandler.getSentence(sentence);
            }
        }
    }


    public static String getArticle(String url){
        try
        {
            Document doc = Jsoup.connect(url).get();
            Elements article = doc.select("div[class=content__article-body from-content-api js-article__body");
            return article.text();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void getSentences(){
        for(String link: getLinks()) {
            splitString(getArticle(link));
        }
    }


    @Override
    protected void onPreExecute() {
        Toast.makeText(MyApplication.getAppContext(), "Webscraper has begun", Toast.LENGTH_SHORT).show();

    }

    protected Void doInBackground(Void... params) {
        dbHandler.deleteSentences();
        getSentences();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(MyApplication.getAppContext(), "Webscraper has completed", Toast.LENGTH_SHORT).show();
    }

}