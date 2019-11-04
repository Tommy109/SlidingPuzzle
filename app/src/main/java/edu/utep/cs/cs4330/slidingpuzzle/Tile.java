package edu.utep.cs.cs4330.slidingpuzzle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;


import java.util.Random;

/**
 * TODO
 * Change drawable to just having a path to the file. The idea is this:
 * 1) Get image (using camera, internet, in the phone itself, etc)
 * 2) Cut the image and store the pieces in the phone (delete files when user uninstalls app)
 *      -We don't want the image/images in memory (use of limited resources)
 * 3) Tile attribute of Drawable type changes to String for path to image.
 */
public class Tile {

    private int id;
    private int position;
    private boolean isEmpty;

    Bitmap bitmap;


    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getPosition() { return position; }

    public boolean isEmpty(){
        return isEmpty;
    }

    public Tile(int position, int id){
        this.position = position;
        this.id = id;
        this.isEmpty = false;
    }

    public Tile(int position){
        this(position,-1);
        isEmpty = false;
    }

    @NonNull
    @Override
    public String toString() {
        return Integer.toString(getPosition());
    }





}
