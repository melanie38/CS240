package edu.byu.cs.superasteroids.model;

/**
 * Created by Melanie on 21/05/16.
 * Super class of objects that can be seen.
 */
public class Visible {

    private String image;
    private int imageWidth;
    private int imageHeight;

    private long id;

    public Visible(){}

    public void setImage(String image) {
        this.image = image;
    }
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }
    public void setId(long id) { this.id = id; }

    /**
     * This method will draw the visible object on the screen.
     */
    public void draw(){}

    /**
     * This method will update the visible object on the screen.
     */
    public void update(){}

    public String getImage(){return image;}

    public int getImageWidth(){return imageWidth;}

    public int getImageHeight(){return imageHeight;}

    public long getId() {
        return id;
    }

    public void clear() {

        image = null;
        imageWidth = 0;
        imageHeight = 0;
        id = -1;

    }
}
