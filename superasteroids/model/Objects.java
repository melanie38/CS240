package edu.byu.cs.superasteroids.model;

/**
 * Created by mlalahar on 5/16/16.
 *
 * This object is an array of string. These strings represent the path to the image file for a
 * background object.Can be empty.
 */
public class Objects {

    private int id;
    private String path;

    public Objects() {
        path = new String();
        id = 0;
    }

    public void setPaths(String path) {
        this.path = path;
    }
    public void setId(int id) {this.id = id;}
    public String getPath() { return path;}
    public int getId() {return id;}

    public void draw() {

    }

}
