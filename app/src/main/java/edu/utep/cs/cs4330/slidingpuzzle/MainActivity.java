package edu.utep.cs.cs4330.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
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

        /**
        tiles[0].setId(getResources().getIdentifier("d00","drawable", getPackageName()));
        tiles[1].setId(getResources().getIdentifier("d01","drawable", getPackageName()));
        tiles[2].setId(getResources().getIdentifier("d02","drawable", getPackageName()));
        tiles[3].setId(getResources().getIdentifier("d10","drawable", getPackageName()));
        tiles[4].setId(getResources().getIdentifier("d11","drawable", getPackageName()));
        tiles[5].setId(getResources().getIdentifier("d12","drawable", getPackageName()));
        tiles[6].setId(getResources().getIdentifier("d20","drawable", getPackageName()));
        tiles[7].setId(getResources().getIdentifier("d21","drawable", getPackageName()));
        tiles[8].setId(getResources().getIdentifier("d22","drawable", getPackageName()));

        tiles[8].setEmpty(true);
         **/

        Drawable[] d = images();

        tiles[0].drawable = d[0];
        tiles[1].drawable = d[1];
        tiles[2].drawable = d[2];
        tiles[3].drawable = d[3];
        tiles[4].drawable = d[4];
        tiles[5].drawable = d[5];
        tiles[6].drawable = d[6];
        tiles[7].drawable = d[7];
        tiles[8].drawable = d[8];

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

    private Drawable[] images(){
        Drawable[] images = new Drawable[9];

        Drawable original = squareImage();

        Bitmap bitmap = ((BitmapDrawable)original).getBitmap();

        int chunkLenght = 10;
        int w = chunkLenght;
        int h = chunkLenght;
        int index = 0;


        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                Bitmap b = Bitmap.createBitmap(bitmap,x,y,w,h);
                images[index] = new BitmapDrawable(getResources(),b);
                index++;
                h += h;
            }
            w += w;
        }

        return images;
    }

    private Drawable squareImage(){
        Drawable original = getDrawable(R.drawable.dogg);

        Rect bounds = original.copyBounds();
        int size;

        if(bounds.width() < bounds.height()){
            size = bounds.width();
        }else{
            size = bounds.height();
        }

        size = 1000;

        Bitmap bitmap = ((BitmapDrawable)original).getBitmap();
        Drawable squared = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap,size,size,true));

        return squared;
    }


}
