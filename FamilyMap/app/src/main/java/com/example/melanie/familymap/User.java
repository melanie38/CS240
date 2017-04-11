package com.example.melanie.familymap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by Melanie on 04/08/16.
 */
public class User extends Person {

    public static User SINGLETON;

    private String authentificationToken;

    public User(){}

    public static User getInstance(){
        if (SINGLETON == null) {
            SINGLETON = new User();
        }
        return SINGLETON;
    }

    public boolean importUserLogin(InputStreamReader responseBody) {

        try {

            JSONObject toExtract = new JSONObject(makeString(responseBody));

            authentificationToken = (String) toExtract.get("Authorization");
            setPersonId((String) toExtract.get("personId"));

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean importPersonId(InputStreamReader responseBody) {

        try {

            JSONObject toExtract = new JSONObject(makeString(responseBody));

            setFirstName((String) toExtract.get("firstName"));
            setLastName((String) toExtract.get("lastName"));

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void setAuthentificationToken(String authentificationToken) { this.authentificationToken = authentificationToken;}

    public String getAuthentificationToken() { return authentificationToken; }

}
