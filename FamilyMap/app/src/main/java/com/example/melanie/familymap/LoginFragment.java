package com.example.melanie.familymap;

import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LoginFragment extends Fragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText serverHostEditText;
    private EditText serverPostEditText;
    private Button signInButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        usernameEditText = (EditText) view.findViewById(R.id.usernameed);
        passwordEditText = (EditText) view.findViewById(R.id.passworded);
        serverHostEditText = (EditText) view.findViewById(R.id.serverhosted);
        serverPostEditText = (EditText) view.findViewById(R.id.serverposted);

        signInButton = (Button)view.findViewById(R.id.signInButton);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadButtonClicked();
                }
            });

        return view;
    }

    private void downloadButtonClicked() {
        try {
            //resetViews();

            DownloadTask task = new DownloadTask();

            task.execute(new URL("http://"
                    + serverHostEditText.getText().toString()
                    + ":"
                    + serverPostEditText.getText().toString()
                    + "/user/login"));
        }
        catch (MalformedURLException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
    }


    public class DownloadTask extends AsyncTask<URL, Void, Boolean> {

        String postData = "{\n username:\""
                + usernameEditText.getText().toString()
                + "\"," + "\n password:\""
                + passwordEditText.getText().toString()
                + "\"}";

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (!aBoolean) {
                Toast.makeText(getContext(), "LoginFragment failed", Toast.LENGTH_SHORT).show();
            }
            else {

                String firstName = User.SINGLETON.getFirstName();
                String lastName = User.SINGLETON.getLastName();

                Toast.makeText(getContext(), "first name: " + firstName + "\nlast name: " + lastName, Toast.LENGTH_SHORT).show();
            }
        }

        public boolean importPerson(InputStreamReader responseBody) {

            ArrayList<Person> setOfPersons = new ArrayList<>();

            try {

                JSONObject root = new JSONObject(Person.makeString(responseBody));

                JSONArray data = root.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject person = data.getJSONObject(i);

                    String descendant = person.getString("descendant");
                    String personId = person.getString("personID");
                    String firstName = person.getString("firstName");
                    String lastName = person.getString("lastName");
                    String gender = person.getString("gender");
                    String spouse = null;
                    try {
                        spouse = person.getString("spouse");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String father = null;
                    try {
                        father = person.getString("father");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String mother = null;
                    try {
                        mother = person.getString("mother");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setOfPersons.add(new Person(descendant, personId, firstName, lastName, gender, spouse, father, mother));

                }

                MainActivity.setAllPersons(setOfPersons);

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;

        }
        public boolean importEvent(InputStreamReader responseBody) {

            ArrayList<Event> setOfEvents = new ArrayList<>();

            try {

                JSONObject root = new JSONObject(Person.makeString(responseBody));

                JSONArray data = root.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject person = data.getJSONObject(i);

                    String eventID = person.getString("eventID");
                    String personID = person.getString("personID");
                    String latitude = person.getString("latitude");
                    String longitude = person.getString("longitude");
                    String country = person.getString("country");
                    String city = person.getString("city");
                    String description = person.getString("description");
                    String year = person.getString("year");
                    String descendant = person.getString("descendant");

                    setOfEvents.add(new Event(eventID, personID, latitude, longitude, country, city, description, year, descendant));

                }

                MainActivity.setAllEvents(setOfEvents);

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;

        }

        public boolean getUserLogin(URL... urls) {

            try {
                HttpURLConnection connection;
                connection = (HttpURLConnection) urls[0].openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.connect();

                OutputStream requestBody = connection.getOutputStream();
                requestBody.write(postData.getBytes());
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get response body input stream
                    InputStream responseBody = connection.getInputStream();
                    User.getInstance();

                    if (!User.SINGLETON.importUserLogin(new InputStreamReader(responseBody))) {
                        return false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        public boolean getPersonId() {
            try {

                // TODO: change the URL to serverHoseEditText:serverPostHostEditText
                URL url = new URL("http://192.168.0.101:8080/person/"
                        + User.SINGLETON.getPersonId());

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.addRequestProperty("Authorization", User.SINGLETON.getAuthentificationToken());
                connection.connect();

                OutputStream requestBody = connection.getOutputStream();
                requestBody.write(postData.getBytes());
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get response body input stream
                    InputStream responseBody = connection.getInputStream();

                    if (!User.SINGLETON.importPersonId(new InputStreamReader(responseBody))) {
                        return false;
                    }

                }

            } catch (Exception e) {
                Log.e("HttpClient", e.getMessage(), e);
                return false;
            }

            return true;
        }
        public boolean getAllPersons() {

            try {

                // TODO: change the URL to serverHoseEditText:serverPostHostEditText
                URL url = new URL("http://192.168.0.101:8080/person/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.addRequestProperty("Authorization", User.SINGLETON.getAuthentificationToken());
                connection.connect();

                OutputStream requestBody = connection.getOutputStream();
                requestBody.write(postData.getBytes());
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get response body input stream
                    InputStream responseBody = connection.getInputStream();

                    if (!importPerson(new InputStreamReader(responseBody))) {
                        return false;
                    }

                }

            } catch (Exception e) {
                Log.e("HttpClient", e.getMessage(), e);
                return false;
            }

            return true;
        }
        public boolean getAllEvents() {

            try {

                // TODO: change the URL to serverHoseEditText:serverPostHostEditText
                URL url = new URL("http://192.168.0.101:8080/event/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.addRequestProperty("Authorization", User.SINGLETON.getAuthentificationToken());
                connection.connect();

                OutputStream requestBody = connection.getOutputStream();
                requestBody.write(postData.getBytes());
                requestBody.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get response body input stream
                    InputStream responseBody = connection.getInputStream();

                    if (!importEvent(new InputStreamReader(responseBody))) {
                        return false;
                    }

                }

            } catch (Exception e) {
                Log.e("HttpClient", e.getMessage(), e);
                return false;
            }

            return true;
        }

        protected Boolean doInBackground(URL... urls) {

            if (!getUserLogin(urls)) { // load the personId and the auth-token
                return false;
            }
            if (!getPersonId()) { // load the first name and the last name
                return false;
            }
            if (!getAllPersons()) { // load all the persons
                return false;
            }
            if (!getAllEvents()) { // load all the events
                return false;
            }

            return true;

        }
    }

    public void getPerson(InputStream responseBody) {
        // parse(responseBody);
    }

    public void getEvent(InputStream responseBody) {
        // parse(responseBody);
    }

    // create another async task that will be a get

    public String getUserName() {
        return usernameEditText.getText().toString();
    }
}
