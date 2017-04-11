package edu.byu.cs.imageeditor.studentCode;

/**
 * Created by melanie on 02/05/2016.
 */
public class Pixel {
    private int[] colorValues;

    public void setPix (int[] RGB) {
        colorValues = RGB;
    }
    public int[] getRGB() {
        return colorValues;
    }
    public int redValue() {
        return colorValues[0];
    }
    public int greenValue() {
        return colorValues[1];
    }
    public int blueValue() {
        return colorValues[2];
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            str.append(colorValues[i]);
            str.append('\n');
        }
        return str.toString();
    }
}
