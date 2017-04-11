package edu.byu.cs.superasteroids.model;

/**
 * Created by mlalahar on 5/16/16.
 *
 * Contains information describing the asteroid in a level.
 */
public class LevelAsteroid {
    private int number;
    private int asteroidId;
    private AsteroidType asteroidType;

    public LevelAsteroid(){}

    public void setNumber(int number) {
        this.number = number;
    }
    public void setAsteroidId(int asteroidId) {
        this.asteroidId = asteroidId;
    }
    public void setAsteroidType(AsteroidType asteroidType) { this.asteroidType = asteroidType; }

    public int getNumber(){return number;}
    public int getAsteroidId(){return asteroidId;}
    public AsteroidType getAsteroidType() { return asteroidType; }


    public void update(double elapsedTime) {
        asteroidType.update(elapsedTime);
    }

    public void draw() {
            asteroidType.draw();
    }
}
