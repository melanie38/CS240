package edu.byu.cs.imageeditor.studentCode;

import java.io.BufferedReader;
//import java.io.FileNotFoundException;

/**
 * Created by melanie on 02/05/2016.
 */
public class EditImage implements IImageEditor {
    private Pixel[][] image;
    private String type = new String();
    private int row = 0;
    private int col = 0;
    private int maxVal = 0;
    private static final int red = 0;
    private static final int green = 1;
    private static final int blue = 2;

    private int setNewRGBVal (int colorDiff) {
        int newRGB = colorDiff + 128;
        if (newRGB < 0) {
            newRGB = 0;
        }
        else if (newRGB > 255) {
            newRGB = 255;
        }
        return newRGB;
    }

    public void load(BufferedReader bufferedReader){
        try {
            type = bufferedReader.readLine();
            if (type != null) {
                String str = bufferedReader.readLine();

                // set number of row and columns
                String[] line = bufferedReader.readLine().split("\\s+");

                col = Integer.parseInt(line[0]);
                row = Integer.parseInt(line[1]);
                image = new Pixel[row][col];

                // get the maxVal
                maxVal = Integer.parseInt(bufferedReader.readLine());

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        // get the RGB values
                        int[] RGB = {0,0,0};
                        RGB[red] = Integer.parseInt(bufferedReader.readLine());
                        RGB[green] = Integer.parseInt(bufferedReader.readLine());
                        RGB[blue] = Integer.parseInt(bufferedReader.readLine());
                        // create a nex pixel
                        Pixel px = new Pixel();
                        px.setPix(RGB);
                        // add the new pixel to the image
                        image[i][j] = px;
                    }
                }
            }
        }
        catch (Exception e){
            System.out.println("Could not find file");
        }
    }
    public String invert() {
        Pixel[][] tempImage = new Pixel[row][col];
        StringBuilder imageStr = new StringBuilder(type + '\n' + col + " " + row + '\n' + maxVal + '\n');
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int[] oldRGB = image[i][j].getRGB();
                int[] newRGB = new int[3];
                newRGB[red] = 255 - oldRGB[red];
                newRGB[green] = 255 - oldRGB[green];
                newRGB[blue] = 255 - oldRGB[blue];
                Pixel px = new Pixel();
                px.setPix(newRGB);
                tempImage[i][j] = px;
                imageStr.append(tempImage[i][j].toString());
            }
        }
        return imageStr.toString();
    }
    public String grayscale() {
        Pixel[][] tempImage = new Pixel[row][col];
        StringBuilder imageStr = new StringBuilder(type + '\n' + col + " " + row + '\n' + maxVal + '\n');
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int[] oldRGB = image[i][j].getRGB();
                int[] newRGB = new int[3];
                int newRGBVal = (oldRGB[red] + oldRGB[green] + oldRGB[blue]) / 3;
                newRGB[red] = newRGBVal;
                newRGB[green] = newRGBVal;
                newRGB[blue] = newRGBVal;
                Pixel px = new Pixel();
                px.setPix(newRGB);
                tempImage[i][j] = px;
                imageStr.append(tempImage[i][j].toString());
            }
        }
        return imageStr.toString();
    }
    public String emboss() {
        Pixel[][] tempImage = new Pixel[row][col];
        StringBuilder imageStr = new StringBuilder(type + '\n' + col + " " + row + '\n' + maxVal + '\n');
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int newRGBVal;
                int[] oldRGB = image[i][j].getRGB();
                int[] newRGB = new int[3];
                if (i == 0 || j == 0) {
                    newRGB[red] = 128;
                    newRGB[green] = 128;
                    newRGB[blue] = 128;
                    Pixel px = new Pixel();
                    px.setPix(newRGB);
                    tempImage[i][j] = px;
                    imageStr.append(tempImage[i][j].toString());
                }
                else {
                    int redDiff = oldRGB[red] - image[i - 1][j - 1].redValue(); // 0
                    int greenDiff = oldRGB[green] - image[i - 1][j - 1].greenValue(); // -255
                    int blueDiff = oldRGB[blue] - image[i - 1][j - 1].blueValue(); // -127
                    if (Math.abs(redDiff) > Math.abs(greenDiff) && Math.abs(redDiff) > Math.abs(blueDiff)) {
                        // redDiff is the greatest diff
                        newRGBVal = setNewRGBVal(redDiff);
                    }
                    else if (Math.abs(greenDiff) > Math.abs(redDiff) && Math.abs(greenDiff) > Math.abs(blueDiff)) {
                        // greenDiff is the greatest diff
                        newRGBVal = setNewRGBVal(greenDiff);
                    }
                    else if (Math.abs(blueDiff) > Math.abs(redDiff) && Math.abs(blueDiff) > Math.abs(greenDiff)) {
                        // blueDiff is the greatest diff
                        newRGBVal = setNewRGBVal(blueDiff);
                    }
                    else if (Math.abs(redDiff) == Math.abs(greenDiff) || Math.abs(redDiff) == Math.abs(blueDiff)) {
                        // use redDiff
                        newRGBVal = setNewRGBVal(redDiff);
                    }
                    else if (Math.abs(greenDiff) == Math.abs(blueDiff)) {
                        // use greenDiff
                        newRGBVal = setNewRGBVal(greenDiff);
                    }
                    else {
                        // use blueDiff
                        newRGBVal = setNewRGBVal(blueDiff);
                    }
                    newRGB[red] = newRGBVal;
                    newRGB[green] = newRGBVal;
                    newRGB[blue] = newRGBVal;
                    Pixel px = new Pixel();
                    px.setPix(newRGB);
                    tempImage[i][j] = px;
                    imageStr.append(tempImage[i][j].toString());
                }
            }
        }
        return imageStr.toString();
    }
    public String motionblur(int blurLength) {
        Pixel[][] tempImage = new Pixel[row][col];
        StringBuilder imageStr = new StringBuilder(type + '\n' + col + " " + row + '\n' + maxVal + '\n');
        for (int i = 0; i < row; i ++) {
            for (int j = 0; j < col; j++) {
                int[] oldRGB = image[i][j].getRGB();
                int[] newRGB = new int[3];
                int newRed = oldRGB[red]; // 255
                int newGreen = oldRGB[green]; // 0
                int newBlue = oldRGB[blue]; // 255
                if (blurLength + j > col) {
                    // update color up to col
                    for (int n = (j+1); n < col; n++){
                        newRed += image[i][n].redValue();
                        newGreen += image[i][n].greenValue();
                        newBlue += image[i][n].blueValue();
                    }
                    newRed = newRed / (col - j);
                    newGreen = newGreen / (col - j);
                    newBlue = newBlue / (col - j);
                    newRGB[red] = newRed;
                    newRGB[green] = newGreen;
                    newRGB[blue] = newBlue;
                    Pixel px = new Pixel();
                    px.setPix(newRGB);
                    tempImage[i][j] = px;
                    imageStr.append(tempImage[i][j].toString());
                }
                else {
                    for (int n = (j+1); n < j + blurLength; n++) {
                        newRed += image[i][n].redValue();
                        newGreen += image[i][n].greenValue();
                        newBlue += image[i][n].blueValue();
                    }
                    newRed = newRed / blurLength;
                    newGreen = newGreen / blurLength;
                    newBlue = newBlue / blurLength;
                    newRGB[red] = newRed;
                    newRGB[green] = newGreen;
                    newRGB[blue] = newBlue;
                    Pixel px = new Pixel();
                    px.setPix(newRGB);
                    tempImage[i][j] = px;
                    imageStr.append(tempImage[i][j].toString());
                }
            }
        }
        return imageStr.toString();
    }
}

