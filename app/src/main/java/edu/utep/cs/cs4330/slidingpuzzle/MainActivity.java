package edu.utep.cs.cs4330.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private SlidingPuzzle puzzle;
    private int size = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.grid_view);
        gridView.setVerticalScrollBarEnabled(false);


        puzzle = new SlidingPuzzle(size);
        puzzle.generate();

        gridView.setNumColumns(size);

        Tile[] tiles = puzzle.getTiles();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dogg);

        int size = (bitmap.getWidth() < bitmap.getHeight()) ? bitmap.getWidth() : bitmap.getHeight();

        Bitmap scaled = Bitmap.createScaledBitmap(bitmap,size,size,true);
        Bitmap[] bitmaps = splitBitmap(scaled,3,3);

        for(int i = 0; i < 9; i++){
            tiles[i].bitmap = bitmaps[i];
        }

        tiles[8].bitmap.eraseColor(Color.WHITE);
        tiles[8].setEmpty(true);


        final TileAdapter tileAdapter = new TileAdapter(this, puzzle.getTiles());

        puzzle.shuffle();
        gridView.setAdapter(tileAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                puzzle.move(position);
                tileAdapter.notifyDataSetChanged();

                if(puzzle.isWin()){
                    displayWin();
                }
            }
        });

    }

    private void displayWin(){
        Toast.makeText(this,"You Win!",Toast.LENGTH_SHORT).show();
    }

    private Bitmap[] splitBitmap(Bitmap bitmap, int xCount, int yCount) {

        Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
        int width, height;

        width = bitmap.getWidth() / xCount;
        height = bitmap.getHeight() / yCount;

        for(int x = 0; x < xCount; ++x) {
            for(int y = 0; y < yCount; ++y) {

                bitmaps[x][y] = Bitmap.createBitmap(bitmap, x * width, y * height, width, height);
            }
        }

        Bitmap[] pieces = new Bitmap[xCount*yCount];
        int index = 0;

        for(Bitmap[] row: bitmaps){
            for(Bitmap col: row){
                pieces[index] = col;
                index++;
            }
        }

        return pieces;
    }


}
