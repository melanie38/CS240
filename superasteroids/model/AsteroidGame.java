package edu.byu.cs.superasteroids.model;

import android.graphics.PointF;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.data_access.DAO;

/**
 * Created by mlalahar on 5/16/16.
 *
 * This object holds all of the data for the Asteroids game configuration.
 */
public class AsteroidGame {

    public static AsteroidGame SINGLETON;

    private Ship ship;
    private Level currentLevel;
    private ArrayList<MainBody> listOfMainBodies;
    private ArrayList<Cannon> listOfCannons;
    private ArrayList<Engine> listOfEngines;
    private ArrayList<ExtraPart> listOfExtraParts;
    private ArrayList<PowerCore> listOfPowerCores;
    private ArrayList<Level> listOfLevels;
    private ArrayList<Objects> listOfObjects;
    private static ArrayList<AsteroidType> listOfAsteroids;

    private AsteroidGame(){

        listOfMainBodies = DAO.SINGLETON.getMainBodies();
        listOfCannons = DAO.SINGLETON.getCannons();
        listOfEngines = DAO.SINGLETON.getEngine();
        listOfExtraParts = DAO.SINGLETON.getExtraParts();
        listOfPowerCores = DAO.SINGLETON.getPowerCore();
        listOfAsteroids = DAO.SINGLETON.getAsteroidType();
        listOfObjects = DAO.SINGLETON.getObjects();
        listOfLevels = DAO.SINGLETON.getLevels();

        ship = new Ship();
    }

    public static AsteroidGame getInstance(){
        if (SINGLETON == null) {
            SINGLETON = new AsteroidGame();
        }
         return SINGLETON;
    }

    public void populateLevelObject() {
        ArrayList<LevelObject> levelObjects = currentLevel.getLevelObjects();

        for (int i = 0; i < levelObjects.size(); i++) {
            int index = levelObjects.get(i).getObjectId();
            levelObjects.get(i).setObject(retriveObject(index));
        }
    }

    public Objects retriveObject(int index){
        for (int i = 0; i < listOfObjects.size(); i++) {
            if (listOfObjects.get(i).getId() == index) {
                return listOfObjects.get(i);
            }
        }
        return null;
    }

    public void populateLevelAsteroid() {
        ArrayList<AsteroidType> levelAsteroid = currentLevel.getLevelAsteroids();

        for (AsteroidType asteroid : levelAsteroid) {

            asteroid.setPosition(Movable.generateRandomPosition());
            asteroid.setBounds(asteroid.generateBounds());
            asteroid.setRotation(asteroid.generateAngle());

        }
    }

    public static AsteroidType retriveAsteroid(int index){
        for (int i = 0; i < listOfAsteroids.size(); i++) {
            if (listOfAsteroids.get(i).getId() == index) {
                return listOfAsteroids.get(i);
            }
        }
        return null;
    }

    public Ship getShip() { return ship; }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public String getImage() { return ship.getImage(); }

    public ArrayList<MainBody> getListOfMainBodies() {
        return listOfMainBodies;
    }

    public ArrayList<Cannon> getListOfCannons() {
        return listOfCannons;
    }

    public ArrayList<Engine> getListOfEngines() {
        return listOfEngines;
    }

    public ArrayList<ExtraPart> getListOfExtraParts() {
        return listOfExtraParts;
    }

    public ArrayList<PowerCore> getListOfPowerCores() {
        return listOfPowerCores;
    }

    public ArrayList<Level> getListOfLevels() { return listOfLevels;}


    public void setShip(Ship ship){
        this.ship = ship;
    }

    public void setCurrentLevel(Level currentLevel) {this.currentLevel = currentLevel;}


    public static PointF converts(String positionSTR) { // move to the singleton

        String[] tempSTR = positionSTR.split(",");

        int x = Integer.parseInt(tempSTR[0]);
        int y = Integer.parseInt(tempSTR[1]);

        return new PointF(x,y);
    }

    public void clear() {

        ship.clear();
        ship = null;

        if (currentLevel != null) {
            currentLevel.clear();
            currentLevel = null;
        }

        listOfMainBodies.clear();
        listOfCannons.clear();
        listOfEngines.clear();
        listOfExtraParts.clear();
        listOfPowerCores.clear();
        listOfLevels.clear();
        listOfObjects.clear();
        listOfAsteroids.clear();

        SINGLETON = null;
    }
}
