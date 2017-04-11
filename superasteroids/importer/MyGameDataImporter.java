package edu.byu.cs.superasteroids.importer;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.media.Image;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.superasteroids.base.MyGameDelegate;
import edu.byu.cs.superasteroids.data_access.DAO;
import edu.byu.cs.superasteroids.data_access.DbOpenHelper;
import edu.byu.cs.superasteroids.model.AsteroidGame;
import edu.byu.cs.superasteroids.model.AsteroidType;
import edu.byu.cs.superasteroids.model.Asteroids;
import edu.byu.cs.superasteroids.model.Cannon;
import edu.byu.cs.superasteroids.model.Engine;
import edu.byu.cs.superasteroids.model.ExtraPart;
import edu.byu.cs.superasteroids.model.Level;
import edu.byu.cs.superasteroids.model.LevelAsteroid;
import edu.byu.cs.superasteroids.model.LevelObject;
import edu.byu.cs.superasteroids.model.MainBody;
import edu.byu.cs.superasteroids.model.Objects;
import edu.byu.cs.superasteroids.model.PowerCore;

/**
 * Created by Melanie on 21/05/16.
 */
public class MyGameDataImporter implements IGameDataImporter {

    public MyGameDataImporter(){}

    @Override
    public boolean importData(InputStreamReader dataInputReader) {

        try {

            DAO.SINGLETON.deleteTables();
            AsteroidGame.SINGLETON.clear();

            JSONObject root = new JSONObject(makeString(dataInputReader));
            root = root.getJSONObject("asteroidsGame");

            addObjects(root.getJSONArray("objects"));
            addAsteroids(root.getJSONArray("asteroids"));
            addLevels(root.getJSONArray("levels"));
            addMainBodies(root.getJSONArray("mainBodies"));
            addCannons(root.getJSONArray("cannons"));
            addExtraParts(root.getJSONArray("extraParts"));
            addEngines(root.getJSONArray("engines"));
            addPowerCores(root.getJSONArray("powerCores"));

            return true;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String makeString(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();
        char[] buf = new char[512];

        int n = 0;
        while ((n = reader.read(buf)) > 0) {
            sb.append(buf, 0, n);
        }

        return sb.toString();
    }

    private void addObjects (JSONArray objects) throws JSONException {

        for (int i = 0; i < objects.length(); i++) {
             DAO.SINGLETON.addObject(objects.getString(i));
        }

    }
    private void addAsteroids (JSONArray asteroids) throws JSONException {

        for (int i = 0; i < asteroids.length(); i++) {
            JSONObject asteroid = asteroids.getJSONObject(i);

            String name = asteroid.getString("name");
            String image = asteroid.getString("image");
            int imageWidth = asteroid.getInt("imageWidth");
            int imageHeight = asteroid.getInt("imageHeight");
            String type = asteroid.getString("type");

            DAO.SINGLETON.addAsteroidType(image, imageWidth, imageHeight, name, type);
        }
    }
    private void addLevels (JSONArray levels) throws JSONException {

        for (int i = 0; i < levels.length(); i ++) {
            JSONObject level = levels.getJSONObject(i);

            int number = level.getInt("number");
            String title = level.getString("title");
            String hint = level.getString("hint");
            int width = level.getInt("width");
            int height = level.getInt("height");
            String music = level.getString("music");

            JSONArray objects = level.getJSONArray("levelObjects");

            for (int j = 0; j < objects.length(); j++) {
                JSONObject details = objects.getJSONObject(j);

                String position = details.getString("position");
                int objectId = details.getInt("objectId");
                int scale = details.getInt("scale");

                DAO.SINGLETON.addLevelObject(number, position, objectId, scale);
            }

            JSONArray asteroids = level.getJSONArray("levelAsteroids");

            for (int k = 0; k < asteroids.length(); k++) {
                JSONObject details = asteroids.getJSONObject(k);

                int numberAsteroids = details.getInt("number");
                int asteroidId = details.getInt("asteroidId");

                DAO.SINGLETON.addLevelAsteroid(number, numberAsteroids, asteroidId);
            }

            DAO.SINGLETON.addLevel(number, title, hint, width, height, music);
        }
    }
    private void addMainBodies (JSONArray mainBodies) throws JSONException {

        for (int i = 0; i < mainBodies.length(); i++) {
            JSONObject mainBody = mainBodies.getJSONObject(i);

            String cannonAttach = mainBody.getString("cannonAttach");
            String engineAttach = mainBody.getString("engineAttach");
            String extraAttach = mainBody.getString("extraAttach");
            String image = mainBody.getString("image");
            int imageWidth = mainBody.getInt("imageWidth");
            int imageHeight = mainBody.getInt("imageHeight");

            DAO.SINGLETON.addMainBody(cannonAttach, engineAttach, extraAttach, image, imageWidth, imageHeight);
        }
    }
    private void addCannons (JSONArray cannons) throws JSONException {

        for (int i = 0; i < cannons.length(); i++) {
            JSONObject cannon = cannons.getJSONObject(i);

            String attachPoint = cannon.getString("attachPoint");
            String emitPoint = cannon.getString("emitPoint");
            String image = cannon.getString("image");
            int imageWidth = cannon.getInt("imageWidth");
            int imageHeight = cannon.getInt("imageHeight");
            String attackImage = cannon.getString("attackImage");
            int attackImageWidth = cannon.getInt("attackImageWidth");
            int attackImageHeight = cannon.getInt("attackImageHeight");
            String attackSound = cannon.getString("attackSound");
            int damage = cannon.getInt("damage");

            DAO.SINGLETON.addCannon(attachPoint, emitPoint, image, imageWidth, imageHeight, attackImage, attackImageWidth, attackImageHeight, attackSound, damage);
        }
    }
    private void addExtraParts (JSONArray extraParts) throws JSONException {

        for (int i = 0; i < extraParts.length(); i++) {
            JSONObject extraPart = extraParts.getJSONObject(i);

            String attachPoint = extraPart.getString("attachPoint");
            String image = extraPart.getString("image");
            int imageWidth = extraPart.getInt("imageWidth");
            int imageHeight = extraPart.getInt("imageHeight");

            DAO.SINGLETON.addExtraPart(attachPoint, image, imageWidth, imageHeight);
        }
    }
    private void addEngines (JSONArray engines) throws JSONException {

        for (int i = 0; i < engines.length(); i++) {
            JSONObject engine = engines.getJSONObject(i);

            int baseSpeed = engine.getInt("baseSpeed");
            int baseTurnRate = engine.getInt("baseTurnRate");
            String attachPoint = engine.getString("attachPoint");
            String image = engine.getString("image");
            int imageWidth = engine.getInt("imageWidth");
            int imageHeight = engine.getInt("imageHeight");

            DAO.SINGLETON.addEngine(baseSpeed, baseTurnRate, attachPoint, image, imageWidth, imageHeight);
        }
    }
    private void addPowerCores (JSONArray powerCores) throws JSONException {

        for (int i = 0; i < powerCores.length(); i++) {
            JSONObject powerCore = powerCores.getJSONObject(i);

            int cannonBoost = powerCore.getInt("cannonBoost");
            int engineBoost = powerCore.getInt("engineBoost");
            String image = powerCore.getString("image");

            DAO.SINGLETON.addPowerCore(cannonBoost, engineBoost, image);
        }
    }
}
