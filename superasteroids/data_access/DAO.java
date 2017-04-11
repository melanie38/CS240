package edu.byu.cs.superasteroids.data_access;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.byu.cs.superasteroids.importer.MyGameDataImporter;
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
 * Created by mlalahar on 5/16/16.
 *
 * Data Access Object. Enables loading data from the database into model objects. Will also be used
 * by the data importer to populate the database from JSON game files.
 */
public class DAO {

    private SQLiteDatabase db;
    public static final DAO SINGLETON = new DAO();


    private DAO() { db = null; }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean addObject(String path) {

        ContentValues values = new ContentValues();
        values.put("path", path);

        long id = db.insert("objects", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add AsteroidType objects to the database.
     * object inserts the new AsteroidType object in the database.
     * @return true if the AsteroidType is added, false if it's not.
     */
    public boolean addAsteroidType(String image, int imageWidth, int imageHeight, String name,
                                   String type) {

        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("imageWidth", imageWidth);
        values.put("imageHeight", imageHeight);
        values.put("name", name);
        values.put("type", type);

        long id = db.insert("asteroidType", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add Level objects to the database.
     * object insert the new Level object in the database.
     * @return true if the level is added, false if it's not.
     */
    public boolean addLevel(int number, String title, String hint, int width, int height, String music) {

        ContentValues values = new ContentValues();
        values.put("number", number);
        values.put("title", title);
        values.put("hint", hint);
        values.put("width", width);
        values.put("height", height);
        values.put("music", music);

        long id = db.insert("level", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add LevelObjects objects to the database.
     * object inserts the nex LevelObject object in the database.
     * @return true if the LevelObject is added, false if it's not.
     */
    public boolean addLevelObject(int index, String position, int objectId, int scale) {

        ContentValues values = new ContentValues();
        values.put("id", index);
        values.put("position", position);
        values.put("objectId", objectId);
        values.put("scale", scale);

        long id = db.insert("levelObject", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean addLevelAsteroid(int index, int number, int asteroidId) {

        ContentValues values = new ContentValues();
        values.put("id", index);
        values.put("number", number);
        values.put("asteroidId", asteroidId);

        long id = db.insert("levelAsteroid", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add MainBody objects to the database.
     */
    public boolean addMainBody(String cannonAttach, String engineAttach, String extraAttach,
                               String image, int imageWidth, int imageHeight) {

        ContentValues values = new ContentValues();
        values.put("cannonAttach", cannonAttach);
        values.put("engineAttach", engineAttach);
        values.put("extraAttach", extraAttach);
        values.put("image", image);
        values.put("imageWidth", imageWidth);
        values.put("imageHeight", imageHeight);

        long id = db.insert("mainBody", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add Cannon objects to the database.
     * object inserts the new Cannon object in the database.
     * @return true if the Cannon object is added, false otherwise.
     */
    public boolean addCannon(String attachPoint, String emitPoint, String image, int imageWidth,
                             int imageHeight, String attackImage, int attackImageWidth,
                             int attackImageHeight, String attackSOUND, int damage) {
        ContentValues values = new ContentValues();
        values.put("attachPoint", attachPoint);
        values.put("emitPoint", emitPoint);
        values.put("image", image);
        values.put("imageWidth", imageWidth);
        values.put("imageHeight", imageHeight);
        values.put("attackImage", attackImage);
        values.put("attackImageWidth", attackImageWidth);
        values.put("attackImageHeight", attackImageHeight);
        values.put("attackSound", attackSOUND);
        values.put("damage", damage);

        long id = db.insert("cannon", null, values);
        if (id >= 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add ExtraPart objects to the database.
     * object insert the new ExtraPart object in the database.
     * @return true if the ExtraPart object is added, false otherwise.
     */
    public boolean addExtraPart(String attachPoint, String image, int imageWidth, int imageHeight) {
        ContentValues values = new ContentValues();
        values.put("attachPoint", attachPoint);
        values.put("image", image);
        values.put("imageWidth", imageWidth);
        values.put("imageHeight", imageHeight);

        long id = db.insert("extraPart", null, values);
        if (id >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add Engine objects to the database.
     * object inserts the new Engine object in the database.
     * @return true if the Engine object is added, false otherwise.
     */
    public boolean addEngine(int baseSpeed, int baseTurnRate, String attachPoint, String image,
                             int imageWidth, int imageHeight) {

        ContentValues values = new ContentValues();
        values.put("baseSpeed", baseSpeed);
        values.put("baseTurnRate", baseTurnRate);
        values.put("attachPoint", attachPoint);
        values.put("image", image);
        values.put("imageWidth", imageWidth);
        values.put("imageHeight", imageHeight);

        long id = db.insert("engine", null, values);
        if (id >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add PowerCore objects to the database.
     * object isnerts the new PowerCore object in the database.
     * @return true if the PowerCore object is added, false otherwise.
     */
    public boolean addPowerCore(int cannonBoost, int engineBoost, String image) {
        ContentValues values = new ContentValues();
        values.put("cannonBoost", cannonBoost);
        values.put("engineBoost", engineBoost);
        values.put("image", image);

        long id = db.insert("powerCore", null, values);
        if (id >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Objects> getObjects() {

        final String SQL = "select * from objects";

        ArrayList<Objects> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            int i = 1;
            while (!cursor.isAfterLast()) {
                Objects object = new Objects();

                object.setPaths(cursor.getString(1));
                object.setId(i);

                i++;

                result.add(object);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }
    public ArrayList<AsteroidType> getAsteroidType() {

        final String SQL = "select * from asteroidType";

        ArrayList<AsteroidType> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            int i = 1;
            while (!cursor.isAfterLast()) {
                AsteroidType asteroidType = new AsteroidType();

                asteroidType.setName(cursor.getString(1));
                asteroidType.setImage(cursor.getString(2));
                asteroidType.setImageWidth(cursor.getInt(3));
                asteroidType.setImageHeight(cursor.getInt(4));
                asteroidType.settype(cursor.getString(5));
                asteroidType.setId(i);

                i++;

                result.add(asteroidType);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        return result;
    }

    public ArrayList<Level> getLevels(){

        final String SQL = "select * from level";

        ArrayList<Level> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Level level = new Level();

                level.setNumber(cursor.getInt(1));
                level.setTitle(cursor.getString(2));
                level.setHint(cursor.getString(3));
                level.setWidth(cursor.getInt(4));
                level.setHeight(cursor.getInt(5));
                level.setMusic(cursor.getString(6));
                level.setLevelObjects(getLevelObjects(cursor.getInt(1)));
                level.setLevelAsteroids(getLevelAsteroids(cursor.getInt(1)));

                result.add(level);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    public ArrayList<LevelObject> getLevelObjects(int index) {

        final String SQL = "select * from levelObject where id = ?";

        ArrayList<LevelObject> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{Integer.toString(index)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LevelObject levelObject = new LevelObject();

                levelObject.setPosition(AsteroidGame.converts(cursor.getString(1)));
                levelObject.setObjectId(cursor.getInt(2));
                levelObject.setScale(cursor.getDouble(3));

                result.add(levelObject);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    public ArrayList<LevelAsteroid> getLevelAsteroids(int index) {

        final String SQL = "select * from levelAsteroid where id = ?";

        ArrayList<LevelAsteroid> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{Integer.toString(index)});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LevelAsteroid levelAsteroid = new LevelAsteroid();

                levelAsteroid.setNumber(cursor.getInt(1));
                levelAsteroid.setAsteroidId(cursor.getInt(2));

                result.add(levelAsteroid);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    public ArrayList<MainBody> getMainBodies(){

        final String SQL = "select * from mainBody";

        ArrayList<MainBody> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MainBody mainBody = new MainBody();

                mainBody.setCannonAttach(AsteroidGame.converts(cursor.getString(1)));
                mainBody.setEngineAttach(AsteroidGame.converts(cursor.getString(2)));
                mainBody.setExtraAttach(AsteroidGame.converts(cursor.getString(3)));
                mainBody.setImage(cursor.getString(4));
                mainBody.setImageWidth(cursor.getInt(5));
                mainBody.setImageHeight(cursor.getInt(6));

                result.add(mainBody);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    public ArrayList<Cannon> getCannons() {

        final String SQL = "select * from cannon";

        ArrayList<Cannon> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cannon cannon = new Cannon();

                cannon.setPosition(AsteroidGame.converts(cursor.getString(1)));
                cannon.setEmitPoint(AsteroidGame.converts(cursor.getString(2)));
                cannon.setImage(cursor.getString(3));
                cannon.setImageWidth(cursor.getInt(4));
                cannon.setImageHeight(cursor.getInt(5));
                cannon.setAttackImage(cursor.getString(6));
                cannon.setAttackImageWidth(cursor.getInt(7));
                cannon.setAttackImageHeight(cursor.getInt(8));
                cannon.setAttackSound(cursor.getString(9));
                cannon.setDamage(cursor.getInt(10));

                result.add(cannon);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    public ArrayList<ExtraPart> getExtraParts(){

        final String SQL = "select * from extraPart";

        ArrayList<ExtraPart> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExtraPart extraPart = new ExtraPart();

                extraPart.setPosition(AsteroidGame.converts(cursor.getString(1)));
                extraPart.setImage(cursor.getString(2));
                extraPart.setImageWidth(cursor.getInt(3));
                extraPart.setImageHeight(cursor.getInt(4));

                result.add(extraPart);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    public ArrayList<Engine> getEngine(){

        final String SQL = "select * from engine";

        ArrayList<Engine> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Engine engine = new Engine();

                engine.setBaseSpeed(cursor.getInt(1));
                engine.setBaseTurnRate(cursor.getInt(2));
                engine.setPosition(AsteroidGame.converts(cursor.getString(3)));
                engine.setImage(cursor.getString(4));
                engine.setImageWidth(cursor.getInt(5));
                engine.setImageHeight(cursor.getInt(6));

                result.add(engine);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }
    public ArrayList<PowerCore> getPowerCore(){

        final String SQL = "select * from powerCore";

        ArrayList<PowerCore> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(SQL, new String[]{});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PowerCore powerCore = new PowerCore();

                powerCore.setCannonBoost(cursor.getInt(1));
                powerCore.setEngineBoost(cursor.getInt(2));
                powerCore.setImage(cursor.getString(3));

                result.add(powerCore);

                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return result;
    }

    public void deleteTables() {
        db.delete("objects", null, null);
        db.delete("asteroidType", null, null);
        db.delete("level", null, null);
        db.delete("levelObject", null, null);
        db.delete("levelAsteroid", null, null);
        db.delete("mainBody", null, null);
        db.delete("cannon", null, null);
        db.delete("extraPart", null, null);
        db.delete("engine", null, null);
        db.delete("powerCore", null, null);
    }
}
