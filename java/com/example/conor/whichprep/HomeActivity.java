package com.example.conor.whichprep;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private PendingIntent pendingIntent;
    SharedPreferences prefs = null;

    @Override
    public void onBackPressed() {   //Ensures user cant go back to settings once a quiz 'session' has started
    }

    public void start() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at 12:00 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY * 2, pendingIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            Intent alarmIntent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
            start();
            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        prefs = getSharedPreferences("com.example.conor.whichprep", MODE_PRIVATE);

        MyDbHandler dbHandler = new MyDbHandler(this, null, null, 1);
        //Only run on first initialization of application
        if (dbHandler.isPrepEmpty()){
            dbHandler.addPrep(new Preposition("aboard", 2, "à bord", "a bordo", "auf, an Bord", "on or into (a ship, aircraft, train, or other vehicle).", "the plane crashed, killing all 158 people aboard", 0, 0));
            dbHandler.addPrep(new Preposition("about", 1, "au sujet de", "alrededor de, como, acia", "ungefähr, circa, über", "on the subject of; concerning. | Used to indicate movement within a particular area", "I was thinking about you", 0, 0));
            dbHandler.addPrep(new Preposition("above", 1, "au-dessus de", "sobre, encima de", "über", "in or to a higher place than; over | more in quantity or number than", "a display of fireworks above the town", 0, 0));
            dbHandler.addPrep(new Preposition("across", 1, "à travers, en croix", "sobre, cruzando, en frente", "über, andere", "from one side to the other | on the other side of", "I ran across the street", 0, 0));
            dbHandler.addPrep(new Preposition("after", 1, "après", "después de", "nach, aufgrund", "behind in place or position | later in time than", "she went out, shutting the door after her", 0, 0));
            dbHandler.addPrep(new Preposition("against", 1,"contre", "contra", "gegen", "in opposition to | in contact with ", "the fight against crime", 0, 0));
            dbHandler.addPrep(new Preposition("along", 		1,"le long de, avec, sur", "a lo largo, al lado", "entlang, neben", "moving in a constant direction on (a more or less horizontal surface)", "We were driving along the beach ", 0, 0));
            dbHandler.addPrep(new Preposition("alongside", 	2,"à côté de", "junto a", "entlang", "close to the side of; next to", "she was sitting alongside him", 0, 0));
            dbHandler.addPrep(new Preposition("amid", 		2,"au milieu de", "entre, en medio de", "in der mitte, zwischen", "surrounded by; in the middle of", "our dream home, set amid magnificent rolling countryside", 0, 0));
            dbHandler.addPrep(new Preposition("amidst", 	2,"au milieu de, parmi", "entre, en medio de", "DE", "surrounded by; in the middle of", "our dream home, set amid magnificent rolling countryside", 0, 0));
            dbHandler.addPrep(new Preposition("among", 		1,"au milieu de, parmi", "entre, en medio de", "zwischen, bei", "situated more or less centrally in relation to (several other things) | being a member or members of (a larger set)", "flowers hidden among the roots of the trees |a British woman was among the 54 victims of the disaster", 0, 0));
            dbHandler.addPrep(new Preposition("amongst", 	1,"au milieu de, parmi", "en medio de, entre", "DE", "situated more or less centrally in relation to (several other things) | being a member or members of (a larger set)", "flowers hidden among the roots of the trees |a British woman was among the 54 victims of the disaster", 0, 0));
            dbHandler.addPrep(new Preposition("around", 	1,"autour de", "alrededor, en torno a", "um, durch, circa", "on every side of | in or to many places throughout (a community or locality)", "the hotel is built around a courtyard | cycling around the village", 0, 0));
            dbHandler.addPrep(new Preposition("as", 		1,"comme, aussi", "como", "", "during the time of being (the thing specified) | used to refer to the function or character that someone or something has", "he had often been ill as a child | it came as a shock", 0, 0));
            dbHandler.addPrep(new Preposition("astride", 	2,"à califourchon sur, à cheval sur", "a horcajadas sobre, a lo largo de", "mit gespreizten beinen, breitbeinig", "with a leg on each side of | extending across", "he was sitting astride the bike | the port stands astride an international route", 0, 0));
            dbHandler.addPrep(new Preposition("at", 		1,"à", "en, a", "zu, um, in", "expressing location or arrival in a particular place or position | expressing the time when an event takes place", "the event was at Wembley | the children go to bed at nine o'clock ", 0, 0));
            dbHandler.addPrep(new Preposition("atop", 		2,"en haut, au sommet", "encima de, sobre", "auf, Über", "on the top of", "the statue is perched atop the Great Tower", 0, 0));
            dbHandler.addPrep(new Preposition("before", 	1,"avant", "antes de, delante de", "vor", "during the period of time preceding (a particular event or time) | in front of", "she had to rest before dinner | Mary stood before her, panting", 0, 0));
            dbHandler.addPrep(new Preposition("behind", 	1,"derrière", "detrás de, atrás de", "hinter, hinterher", "at or to the far side of (something), typically so as to be hidden by it | following or further back than (another member of a moving group)", "the recording machinery was kept behind screens | we were stuck behind a tractor", 0, 0));
            dbHandler.addPrep(new Preposition("below", 		1,"sous, en dessous de", "debajo de, abajo de", "unter, unterhalb", "at a lower level or layer than | extending underneath", "just below the pocket was a stain | the tunnel below the crags", 0, 0));
            dbHandler.addPrep(new Preposition("beneath", 	1,"sous,au-dessous de", "debajo de, bajo", "unter, hinter", "extending or directly underneath", "a 3-mile tunnel beneath the Alps", 0, 0));
            dbHandler.addPrep(new Preposition("beside", 	1,"à côté de, près de", "junto a, al lado de", "neben", "at the side of; next to", "he sat beside me in the front seat", 0, 0));
            dbHandler.addPrep(new Preposition("between", 	1,"entre", "entre", "zwischen", "at, into, or across the space separating (two objects or regions) | in the period separating (two points in time)", "the border between Mexico and the United States | they snack between meals", 0, 0));
            dbHandler.addPrep(new Preposition("beyond", 	2,"au-delà de", "más allá de, más de", "hinter, nach", "at or to the further side of | happening or continuing after (a specified time, stage, or event)", "he pointed to a spot beyond the trees | training beyond the age of 14", 0, 0));
            dbHandler.addPrep(new Preposition("by", 		1,"près de, à côté de", "por, junto a", "bei, um, mit", "indicating the means of achieving something", "malaria can be controlled by attacking the parasite", 0, 0));
            dbHandler.addPrep(new Preposition("circa", 		2,"vers, aux alentours de", "hacia, alrededor de", "ungefähr, zirka", "(often preceding a date) approximately", "the church was built circa 1860", 0, 0));
            dbHandler.addPrep(new Preposition("concerning", 2,"sur, concernant", "relativa a, sobre", "bezüglich, betreffs", "on the subject of or in connection with; about", "we are given little information concerning matters of national security", 0, 0));
            dbHandler.addPrep(new Preposition("despite", 	1,"malgré", "a pesar de, pese a", "trotz, obwohl", "without being affected by; in spite of", "he remains a great leader despite age and infirmity", 0, 0));
            dbHandler.addPrep(new Preposition("down", 		1,"descendre de, un peu plus loin", "abajo, bajar", "runter", "from a higher to a lower point of (something) | throughout (a period of time)", "up and down the stairs | we have done him many favours down the years", 0, 0));
            dbHandler.addPrep(new Preposition("during", 	1,"pendant", "durante", "Über, bei", "throughout the course or duration of (a period of time) | at a particular point in the course of", "the restaurant is open during the day | the stabbing took place during a row at a party", 0, 0));
            dbHandler.addPrep(new Preposition("except", 	1,"sauf", "excepto", "bis auf, außer", "not including; other than", "I was naked except for my shorts", 0, 0));
            dbHandler.addPrep(new Preposition("excluding", 	2,"sans compter, hors", "excluyendo", "", "not taking someone or something into account; except", "the holiday cost £180, excluding accommodation", 0, 0));
            dbHandler.addPrep(new Preposition("following", 	2,"après, suite à", "después, tras", "im Anschluss, infolge", "coming after or as a result of", "police are hunting for two men following a robbery in the area", 0, 0));
            dbHandler.addPrep(new Preposition("for", 		1,"pour", "para, por", "für", "intended to be given to", "There's a phone message for you", 0, 0));
            dbHandler.addPrep(new Preposition("from", 		1,"de, depuis", "desde, de", "von, aus, an", "indicating the point in space at which a journey, motion, or action starts | indicating the point in time at which a particular process, event, or activity starts", "she began to walk away from him | the show will run from 10 a.m. to 2 p.m", 0, 0));
            dbHandler.addPrep(new Preposition("given", 		1,"étant donné", "dado/a", "angesichts, in Anbetracht", "taking into account", "given the difficulty of the task, they did a good job", 0, 0));
            dbHandler.addPrep(new Preposition("in", 		1,"dans, en, à", "en", "im, ins", "expressing the situation of something that is or appears to be enclosed or surrounded by something else | expressing a period of time during which an event happens or a situation remains the case", "I'm living in London | they met in 1885", 0, 0));
            dbHandler.addPrep(new Preposition("including", 	2,"y compris, parmi", "incluso, incluyendo", "einschließlich, inklusive", "containing as part of the whole being considered", "languages including french and spanish", 0, 0));
            dbHandler.addPrep(new Preposition("inside", 	1,"dans, à l'intérieur de", "dentro, adentro", "im, innerhalb", "situated within the confines of (something) | in less than (the period of time specified)", "a radio was playing inside the flat | the oven will have paid for itself inside 18 months", 0, 0));
            dbHandler.addPrep(new Preposition("into", 		2,"dans", "a, en", "in rein, in", "expressing movement or action with the result that someone or something becomes enclosed or surrounded by something else", "cover the bowl and put it into the fridge", 0, 0));
            dbHandler.addPrep(new Preposition("like", 		1,"comme", "como", "wie", "having the same characteristics or qualities as; similar to | used to draw attention to the nature of an action or event", "he used to have a car like mine | I apologize for coming over unannounced like this", 0, 0));
            dbHandler.addPrep(new Preposition("minus", 		2,"moins, sans", "menos, sin", "minus, ohne", "with the subtraction of |(of temperature) below zero by ", "what's ninety three minus seven | minus 40 degrees centigrade", 0, 0));
            dbHandler.addPrep(new Preposition("near", 		1,"près de, proche de", "cerca de", "bei sich, in der Nähe", "at or to a short distance away from (a place) | a short period of time from", "the car park near the sawmill | near the end of the war", 0, 0));
            dbHandler.addPrep(new Preposition("notwithstanding", 2,"malgré", "a pesar de, pese a", "trotz, entgegen, ungeachtet", "in spite of", "notwithstanding the evidence, the consensus is that the jury will not reach a verdict", 0, 0));
            dbHandler.addPrep(new Preposition("of", 		1,"de", "de", "von, zum", "expressing the relationship between a scale or measure and a value | expressing the relationship between a part and a whole", "an increase of 5%", 0, 0));
            dbHandler.addPrep(new Preposition("off", 		1,"de, pas", "de, quitado", "vom", "moving away and often down from", "he rolled off the bed", 0, 0));
            dbHandler.addPrep(new Preposition("on", 		1,"sur, au bord de", "en, sobre", "auf, an, im", "physically in contact with and supported by (a surface) | forming a distinctive or marked part of the surface of", "on the table was a water jug | a scratch on her arm", 0, 0));
            dbHandler.addPrep(new Preposition("onto", 		2,"sur", "en, sobre", "auf", "used to show movement into or on a particular place", "I slipped as I stepped onto the platform", 0, 0));
            dbHandler.addPrep(new Preposition("opposite", 	2,"en face de, face à", "enfrente", "gegenüber", "in a position on the other side of a specific area from; facing", "they sat opposite one another", 0, 0));
            dbHandler.addPrep(new Preposition("outside", 	1,"à l'extérieur de", "hacia afuera, fuera", "draußen, außerhalb", "situated or moving beyond the confines or boundaries of", "there was a boy outside the door", 0, 0));
            dbHandler.addPrep(new Preposition("over", 		1,"au-dessus de, sur, par-dessus", "por encima de, sobre", "über, auf", "above someone or something", "he jumped over the wall", 0, 0));
            dbHandler.addPrep(new Preposition("past", 		1,"après, au delà de", "más allá", "hinter, überschreiten", "to or on the further side of", "He drove past the crossroads", 0, 0));
            dbHandler.addPrep(new Preposition("pending", 	2,"en attendant", "esperando, aguardando", "bis", "waiting for something to happen or take place", "he was working on a pending case", 0, 0));
            dbHandler.addPrep(new Preposition("per", 		1,"par", "por, para", "je, pro, per", "for each (used with units to express a rate)", "the petrol station charges €1.29 per litre", 0, 0));
            dbHandler.addPrep(new Preposition("plus", 		1,"plus", "más", "plus, und", "with the addition of", "two plus four is six", 0, 0));
            dbHandler.addPrep(new Preposition("pro", 		2,"pour, en faveur de", "a favor de", "für", "in favour of.", "members have voted pro strike", 0, 0));
            dbHandler.addPrep(new Preposition("re", 		2,"au sujet de", "sobre, con respecto a", "bezüglich, hinsichtlich", "about or concerning something.", "I saw the officer re the incident", 0, 0));
            dbHandler.addPrep(new Preposition("regarding", 	2,"à propos de, au sujet de, concernant", "en relación de, sobre", "bezüglich, hinsichtlich", "with respect to; concerning", "your recent letter regarding the above proposal", 0, 0));
            dbHandler.addPrep(new Preposition("respecting", 2,"concernant", "respecto a", "in dieser Hinsicht, hinsichtlich", "with reference or regard to", "she had worries respecting her car", 0, 0));
            dbHandler.addPrep(new Preposition("round", 		2,"autour de, à", "alrededor, a la vuelta", "um, hinter", "variant of around", "the moon goes round the earth", 0, 0));
            dbHandler.addPrep(new Preposition("since", 		1,"depuis", "desde", "seit", "The period of time between the time mentioned", "she has suffered from depression since she was sixteen", 0, 0));
            dbHandler.addPrep(new Preposition("than", 		1,"que", "que", "als, in Vergleich Zu", "Introducing the second element in a comparison", "he was much smaller than his son", 0, 0));
            dbHandler.addPrep(new Preposition("through", 	2,"à travers, par", "a través de", "durch, Über", "moving in one side and out the other side of ", "he stepped through the doorway", 0, 0));
            dbHandler.addPrep(new Preposition("thru", 		2,"à, jusqu'à", "desde, hasta", "bis", "informal spelling of through.", "", 0, 0));
            dbHandler.addPrep(new Preposition("throughout", 2,"tout au long de, durant", "durante, a través de", "durchweg, ganz", "from beginning to end of an event or period of time", "he sat quietly throughout the movie", 0, 0));
            dbHandler.addPrep(new Preposition("till", 		2,"jusqu'à", "hasta", "bis", "less formal way of saying until.", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("to", 		1,"à, vers", "a, hacia", "zu, zum, auf", "used for expressing direction or motion or direction toward something", "He walked to the shop | He was kind to her", 0, 0));
            dbHandler.addPrep(new Preposition("toward", 	2,"vers, en direction de, face à", "hacia, a, en dirección a", "zu, zum, in Richtung", "in the direction of", "I walked toward the front door", 0, 0));
            dbHandler.addPrep(new Preposition("towards", 	2,"vers, en direction de, face à", "hacia, a, en dirección a", "zu, zum, in Richtung", "in the direction of", "he turned towards the hallway", 0, 0));
            dbHandler.addPrep(new Preposition("under",		1,"sous, moins de", "bajo", "unter, hinter", "Directly below something", "He sat under the tree", 0, 0));
            dbHandler.addPrep(new Preposition("underneath", 1,"sous", "bajo", "unter", "situated directly below something else", "The kitchen is right underneath the bedroom", 0, 0));
            dbHandler.addPrep(new Preposition("unlike", 	2,"contrairement à", "a diferencia de", "anders als", "different from; not similar to", "they were unlike anything ever seen before", 0, 0));
            dbHandler.addPrep(new Preposition("until", 		1,"jusqu'à", "hasta", "bis, vor", "up to the point in time or the event mentioned.", "He went to bed and slept until morning", 0, 0));
            dbHandler.addPrep(new Preposition("up", 		1,"en haut de, au bout de", "arriba", "runter, rauf, auf", "from a lower to a higher point on something", "she climbed up a flight of steps", 0, 0));
            dbHandler.addPrep(new Preposition("upon", 		2,"sur", "sobre, tras", "auf", "more formal term for on", "it was based upon two principles", 0, 0));
            dbHandler.addPrep(new Preposition("versus", 	2,"contre, par rapport à", "frente, contra", "in Gegenüberstellung, kontra", "against especially in sports and legal use", "The blue team versus the red team", 0, 0));
            dbHandler.addPrep(new Preposition("via", 		2,"via, par", "por, mediante, vía", "per, via, über", "by means of", "a file sent via electronic mail", 0, 0));
            dbHandler.addPrep(new Preposition("with", 		1,"avec, qui a", "con, de", "mit, von", "accompanied by another person or thing", "she went with her friend", 0, 0));
            dbHandler.addPrep(new Preposition("within", 	2,"dans, à l'intérieur de", "dentro de, en", "innerhalb", "inside (something).", "the spread of fire within the building", 0, 0));
            dbHandler.addPrep(new Preposition("without", 	2,"sans", "sin", "ohne", "in the absence of", "he went without her", 0, 0));
            dbHandler.addPrep(new Preposition("according to", 3,"selon, d'après", "según, de acuerdo con", "DE", "as stated by or in", "according to the invitation it starts at eight", 0, 0));
            dbHandler.addPrep(new Preposition("ahead of", 3,"en avance sur son, penser à tout", "", "DE", "in front of or before", "she walked ahead of him along the corridor", 0, 0));
            dbHandler.addPrep(new Preposition("along with", 3,"ainsi que, avec", "junto con", "Zusätzlich zu, mit", "in company with or at the same time as", "he was chosen along with some others", 0, 0));
            dbHandler.addPrep(new Preposition("apart from", 3,"à part", "excepto", "DE", "except for", "everyone was sleeping apart from the baby", 0, 0));
            dbHandler.addPrep(new Preposition("as for", 3,"quant à", "con respecto a", "DE", "with regard to", "as for you, you better hurry up", 0, 0));
            dbHandler.addPrep(new Preposition("aside from", 3,"à part", "aparte de", "DE", "except for", "aside from the c in maths, he made all A's this term", 0, 0));
            dbHandler.addPrep(new Preposition("as per", 3,"conformément à, suivant", "según,en lo que hace a", "DE", "as usual", "I changed the design as per suggestion of my boss", 0, 0));
            dbHandler.addPrep(new Preposition("as to", 3,"concernant, quant à", "en cuanto", "DE", "with respect to; concerning", "As to the lab’s upcoming experiment, we’ll just have to wait and see.", 0, 0));
            dbHandler.addPrep(new Preposition("as well as", 4,"ainsi que", "además de", "und, so wie", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("away from", 3,"loin de", "lejos de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("because of", 3,"à cause de", "por causa de, por", "DE", "by reason of", "they moved here because of the baby", 0, 0));
            dbHandler.addPrep(new Preposition("but for", 3,"sans", "", "", "except for", "we would have reached the summit but for the weather", 0, 0));
            dbHandler.addPrep(new Preposition("by means of", 4,"au moyen de, à travers", "por medio de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("close to", 3,"", "ES", "DE", "almost or very nearly", "he spent close to 30 years in prison", 0, 0));
            dbHandler.addPrep(new Preposition("contrary to", 3,"au contraire", "al contrario", "entgegen, sich nicht vereinbaren lassen", "conflicting with", "contrary to her expectations, she enjoyed the atmosphere", 0, 0));
            dbHandler.addPrep(new Preposition("depending on", 3,"en fonction de, selon", "dependiendo de", "DE", "determined by the conditions followed", "it will make 8-10 burgers,depending on size", 0, 0));
            dbHandler.addPrep(new Preposition("due to", 3,"en raison de, à cause de", "debido a, por el", "DE", "because of; owing to.", "he had to withdraw due to a knee injury", 0, 0));
            dbHandler.addPrep(new Preposition("except for", 3,"sauf, excepté", "excepto, menos", "DE", "not including, other than", "everyone was gone gone except for me", 0, 0));
            dbHandler.addPrep(new Preposition("forward of", 3,"avant", "adelante", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("further to", 3,"suite à", "con relación a", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in addition to", 4,"en plus de", "además de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in between", 3,"entre", "entre", "zwischen", "a state or position that is in the middle between two other things", "in between the cold and hot weather", 0, 0));
            dbHandler.addPrep(new Preposition("in case of", 4,"en cas de", "en caso de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in face of", 4,"", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in favour of", 4,"", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in front of", 4,"devant", "enfrente, delante", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in lieu of", 4,"à la place de", "en reemplazo de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("in spite of", 4,"malgré, en dépit de", "a pesar de, pese a", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("instead of", 3,"plutôt que, au lieu de", "en lugar de, en vez de", "DE", "as an alternative", "I will walk to work instead of driving ", 0, 0));
            dbHandler.addPrep(new Preposition("in view of", 4,"au vu de", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("irrespective of", 3,"indépendamment de", "independientemente", "ungeachtet", "regardless of", "child benefit is paid irrespective of income levels", 0, 0));
            dbHandler.addPrep(new Preposition("near to", 3,"près de, proche de", "cerca de, a punto de", "DE", "similar to", "a shape near to the original", 0, 0));
            dbHandler.addPrep(new Preposition("next to", 3,"", "ES", "DE", "beside", "we sat next to each other", 0, 0));
            dbHandler.addPrep(new Preposition("on account of", 4,"à cause de, en raison de", "debido a", "aufgrund von", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("on behalf of", 4,"de la part de, au nom de", "de parte de, en nombre de", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("on to", 3,"FR", "ES", "DE", "moving to a location on (the surface of something)", "they went up onto the ridge", 0, 0));
            dbHandler.addPrep(new Preposition("on top of", 4,"au sommet de, en haut de", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("opposite of", 3,"aux antipodes de", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("other than", 3,"à part, hormis", "además de, aparte de", "DE", "except", "he claims not to own anything other than his home", 0, 0));
            dbHandler.addPrep(new Preposition("out of", 3,"hors de", "ES", "DE", "indicating the source,(from)", "a bench made out of fallen tree oak", 0, 0));
            dbHandler.addPrep(new Preposition("outside of", 3,"à l'extérieur de, hors de", "fuera de, en la parte de afuera de", "DE", "beyond the boundaries of", "a village 20 miles outside of New York", 0, 0));
            dbHandler.addPrep(new Preposition("owing to", 3,"à cause de, en raison de", "debido a", "DE", "because of or on account of.", "their late arrival was owing to the weather", 0, 0));
            dbHandler.addPrep(new Preposition("preparatory to", 3,"none", "ES", "DE", "as a preparation for", "she applied her makeup preparatory to leaving", 0, 0));
            dbHandler.addPrep(new Preposition("prior to", 3,"avant de", "antes de, previo a", "DE", "before a particular time or event.", "she visited me on the day prior to her death", 0, 0));
            dbHandler.addPrep(new Preposition("regardless of", 3,"en dépit de, au mépris de", "a pesar de, sin tener en cuenta", "DE", "without regard or consideration for.", "the allowance is paid regardless of age or income", 0, 0));
            dbHandler.addPrep(new Preposition("save for", 3,"FR", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("thanks to", 3,"grâce à", "gracias a", "DE", "as a result of; due to.", "it's thanks to you that he's in this mess", 0, 0));
            dbHandler.addPrep(new Preposition("together with", 3,"associé à", "acompañados con, junto con", "DE", "as well as; along with", "their meal arrived,together with some red wine", 0, 0));
            dbHandler.addPrep(new Preposition("up against", 3,"contre, face à", "viéndose las caras con, enfrentado a", "DE", "close to or in contact with", "crowds pressed up against the police barricades", 0, 0));
            dbHandler.addPrep(new Preposition("up to", 3,"capable, en état", "hasta, depender de", "DE", "to indicate a limit or boundary", "up to 50,000 copies a month", 0, 0));
            dbHandler.addPrep(new Preposition("up until", 3,"FR", "ES", "DE", "description", "example sentence here", 0, 0));
            dbHandler.addPrep(new Preposition("with reference to", 4,"FR", "ES", "DE", "in relation to; as regards", "war can only be explained with reference to complex social factors", 0, 0));
            dbHandler.addPrep(new Preposition("with regard to", 4,"au sujet de, concernant", "ES", "DE", "as concerns; with respect to.", "he made inquiries with regard to his friend", 0, 0));
        }
    }

    public void startPreQuiz(View view){
        startActivity(new Intent(this, PreQuiz.class));
    }

    public void startAbout(View view){
        startActivity(new Intent(this, AboutActivity.class));
    }

    public void startStats(View view){
        startActivity(new Intent(this,StatsActivity.class));
    }
}
