package edu.byu.cs.superasteroids.data_access;

import android.database.sqlite.*;
import android.content.Context;

/**
 * Created by Melanie on 20/05/16.
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "asteroidGame.sqlite";
    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Creates the tables necessary in the database
     * @param db the database being used
     */
    public void onCreate(SQLiteDatabase db) {

        // drop all the table if they already exist
        db.execSQL("DROP TABLE IF EXISTS objects");
        db.execSQL("DROP TABLE IF EXISTS asteroidType");
        db.execSQL("DROP TABLE IF EXISTS level");
        db.execSQL("DROP TABLE IF EXISTS levelObject");
        db.execSQL("DROP TABLE IF EXISTS levelAsteroid");
        db.execSQL("DROP TABLE IF EXISTS mainBody");
        db.execSQL("DROP TABLE IF EXISTS cannon");
        db.execSQL("DROP TABLE IF EXISTS extraPart");
        db.execSQL("DROP TABLE IF EXISTS engine");
        db.execSQL("DROP TABLE IF EXISTS powerCore");

        db.execSQL(object_table);
        db.execSQL(asteroidType_table);
        db.execSQL(level_table);
        db.execSQL(levelObject_table);
        db.execSQL(levelAsteroid_table);
        db.execSQL(mainBody_table);
        db.execSQL(cannon_table);
        db.execSQL(extraPart_table);
        db.execSQL(engine_table);
        db.execSQL(powerCore_table);
    }

    /**
     * Not currently implemented
     * @param db the database being used
     * @param oldVersion the old version number of the database
     * @param newVersion the new version number of the database
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    public static final String object_table =
            "CREATE TABLE objects" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   path TEXT NOT NULL" +
                    ")";

    public static final String asteroidType_table =
            "CREATE TABLE asteroidType" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   name TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL," +
                    "   type TEXT NOT NULL" +
                    ")";

    public static final String level_table =
            "CREATE TABLE level" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   number INTEGER NOT NULL," +
                    "   title TEXT NOT NULL," +
                    "   hint TEXT NOT NULL," +
                    "   width INTEGER NOT NULL," +
                    "   height INTEGER NOT NULL," +
                    "   music TEXT NOT NULL" +
                    ")";

    public static final String levelObject_table =
            "CREATE TABLE levelObject" +
                    "(" +
                    "   id INTEGER NOT NULL," +
                    "   position TEXT NOT NULL," +
                    "   objectId INTEGER NOT NULL," +
                    "   scale FLOAT NOT NULL" +
                    ")";

    public static final String levelAsteroid_table =
            "CREATE TABLE levelAsteroid" +
                    "(" +
                    "   id INTEGER NOT NULL," +
                    "   number INTEGER NOT NULL," +
                    "   asteroidId INTEGER NOT NULL" +
                    ")";

    public static final String mainBody_table =
            "CREATE TABLE mainBody" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   cannonAttach TEXT NOT NULL," +
                    "   engineAttach TEXT NOT NULL," +
                    "   extraAttach TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL" +
                    ")";

    public static final String cannon_table =
            "CREATE TABLE cannon" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   attachPoint TEXT NOT NULL," +
                    "   emitPoint TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL," +
                    "   attackImage TEXT NOT NULL," +
                    "   attackImageWidth INTEGER NOT NULL," +
                    "   attackImageHeight INTEGER NOT NULL," +
                    "   attackSound TEXT NOT NULL," +
                    "   damage INTEGER NOT NULL" +
                    ")";

    final String extraPart_table =
            "CREATE TABLE extraPart" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   attachPoint TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL" +
                    ")";

    public static final String engine_table =
            "CREATE TABLE engine" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   baseSpeed INTEGER NOT NULL," +
                    "   baseTurnRate INTEGER NOT NULL," +
                    "   attachPoint TEXT NOT NULL," +
                    "   image TEXT NOT NULL," +
                    "   imageWidth INTEGER NOT NULL," +
                    "   imageHeight INTEGER NOT NULL" +
                    ")";

    public static final String powerCore_table =
            "CREATE TABLE powerCore" +
                    "(" +
                    "   id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "   cannonBoost INTEGER NOT NULL," +
                    "   engineBoost INTEGER NOT NULL," +
                    "   image TEXT NOT NULL" +
                    ")";
}
