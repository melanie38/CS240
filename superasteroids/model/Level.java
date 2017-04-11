package edu.byu.cs.superasteroids.model;

import java.util.ArrayList;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing a Level.
 */
public class Level {
    private int number;
    private String title;
    private String hint;
    private int width;
    private int height;
    private String music;
    private ArrayList<LevelObject> levelObjects;
    private ArrayList<AsteroidType> levelAsteroids;
    private long id;

    public Level(){}

    public void setNumber(int number) {
        this.number = number;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setMusic(String music) {
        this.music = music;
    }
    public void setLevelObjects(ArrayList<LevelObject> levelObjects) { this.levelObjects = levelObjects; }
    public void setLevelAsteroids(ArrayList<LevelAsteroid> levelAsteroids) {

        this.levelAsteroids = new ArrayList<>();

        for (int i = 0; i < levelAsteroids.size(); i++) {

            int index = levelAsteroids.get(i).getAsteroidId();

            for (int j = 0; j < levelAsteroids.get(i).getNumber(); j++) {

                AsteroidType asteroid = new AsteroidType();
                AsteroidType toExtract = AsteroidGame.retriveAsteroid(index);

                asteroid.setImage(toExtract.getImage());
                asteroid.setImageWidth(toExtract.getImageWidth());
                asteroid.setImageHeight(toExtract.getImageHeight());

                this.levelAsteroids.add(asteroid);
            }

        }

    }
    public void setID(long id) {this.id = id;}

    public int getNumber(){return number;}
    public String getTitle(){return title;}
    public String getHint(){return hint;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public String getMusic(){return music;}
    public ArrayList<LevelObject> getLevelObjects(){return levelObjects;}
    public ArrayList<AsteroidType> getLevelAsteroids(){return levelAsteroids;}
    public long getId(){return id;}

    public void update(double elapsedTime) {
        for (AsteroidType asteroid : levelAsteroids) {
            asteroid.update(elapsedTime);
        }
    }

    public void draw() {
        for (int i = 0; i < levelObjects.size(); i++) {
            levelObjects.get(i).draw();
        }
        for (int j = 0; j < levelAsteroids.size(); j++) {
            levelAsteroids.get(j).draw();
        }
    }

    public void clear() {
        number = 0;
        title = null;
        hint = null;
        width = 0;
        height = 0;
        music = null;
        levelObjects.clear();
        levelAsteroids.clear();
        id = -1;
    }
}
