package edu.byu.cs.superasteroids.model;

/**
 * Created by mlalahar on 5/16/16.
 *
 * An array of asteroids. Should not be empty.
 */
public class Asteroids {
    private AsteroidType[] astero;

    public Asteroids() {
        astero = new AsteroidType[0];
    }

    public void setAstero(AsteroidType[] astero) {
        this.astero = astero;
    }
}
