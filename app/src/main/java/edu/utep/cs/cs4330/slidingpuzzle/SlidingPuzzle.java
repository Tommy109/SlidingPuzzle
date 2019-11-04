package edu.utep.cs.cs4330.slidingpuzzle;

import android.media.Image;

public class SlidingPuzzle {

    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private Tile[] tiles;
    private int size;

    public Tile[] getTiles(){
        return tiles;
    }

    public SlidingPuzzle(int size){
        this.size = size;
        tiles = new Tile[size*size];
    }

    public boolean isWin(){
        for(int i = 0; i < tiles.length; i++){
            if(tiles[i].getPosition() != i)
                return false;
        }
        return true;
    }

    public boolean inBounds(int position){
        return (position >= 0) && (position < size*size);
    }

    public void shuffle(){
        for(int i = tiles.length - 1; i > 0; i--){
            double j = Math.floor(Math.random() * (i+1));

            Tile temp = tiles[i];
            tiles[i] = tiles[(int)j];
            tiles[(int)j] = temp;
        }
    }

    public void move(int i){
        Tile temp;

        if(isLeftEmpty(i)){
            temp = tiles[i-1];
            tiles[i-1] = tiles[i];
            tiles[i] = temp;
        }
        else if(isRightEmpty(i)){
            temp = tiles[i+1];
            tiles[i+1] = tiles[i];
            tiles[i] = temp;
        }
        else if(isDownEmpty(i)){
            temp = tiles[i+size];
            tiles[i+size] = tiles[i];
            tiles[i] = temp;
        }
        else if(isUpEmpty(i)){
            temp = tiles[i-size];
            tiles[i-size] = tiles[i];
            tiles[i] = temp;
        }
    }

    private boolean isDownEmpty(int position){
        int down = position+size;

        if(!inBounds(down))
            return false;

        Tile downTile = tiles[down];

        if(downTile != null && downTile.isEmpty())
            return true;

        return false;
    }

    private boolean isUpEmpty(int position){
        int up = position-size;

        if(!inBounds(up))
            return false;

        Tile upTile = tiles[up];

        if(upTile != null && upTile.isEmpty())
            return true;

        return false;
    }

    private boolean isRightEmpty(int position){
        int right = position+1;

        if(!inBounds(right))
            return false;

        Tile rightTile = tiles[right];

        if(rightTile != null && rightTile.isEmpty())
            return true;

        return false;
    }

    private boolean isLeftEmpty(int position){
        int left = position-1;

        if(!inBounds(left))
            return false;

        Tile leftTile = tiles[left];

        if(leftTile != null && leftTile.isEmpty())
            return true;

        return false;
    }


    /** Generate tiles for testing **/
    public void generate(){
        for(int i = 0; i < size*size; i++){
            tiles[i] = new Tile(i);
        }
    }
}
