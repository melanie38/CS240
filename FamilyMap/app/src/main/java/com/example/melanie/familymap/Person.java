package com.example.melanie.familymap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by Melanie on 04/08/16.
 */
public class Person {

    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouse;
    private String father;
    private String mother;


    public Person(String descendant, String personId, String firstName, String lastName, String gender, String spouse, String father, String mother) {
        this.descendant = descendant;
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.spouse = spouse;
        this.father = father;
        this.mother = mother;
    }

    public Person(){}


    public void setDescendant(String descendant) { this.descendant = descendant; }

    public void setPersonId(String personID) { this.personId = personID; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setGender(String gender) { this.gender = gender; }

    public void setSpouse(String spouse) { this.spouse = spouse; }

    public String getPersonId() { return personId; }

    public String getDescendant() { return descendant; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getGender() { return gender; }

    public String getSpouse() { return spouse; }

    public static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

}
