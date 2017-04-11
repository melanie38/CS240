package com.example.melanie.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Person> allPersons;
    private static ArrayList<Event> allEvents;

    public MainActivity(){}

    public static void setAllPersons(ArrayList<Person> setOfPersons) { allPersons = setOfPersons; }
    public static void setAllEvents(ArrayList<Event> setOfEvents) { allEvents = setOfEvents; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
        if (true) {
            fragment = new MapFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }
}
