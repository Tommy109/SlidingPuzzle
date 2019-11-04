package edu.utep.cs.cs4330.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private Tile[] tiles = Tile.generate(9);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.grid_view);
        gridView.setVerticalScrollBarEnabled(false);


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


        final TileAdapter tileAdapter = new TileAdapter(this, tiles);

        shuffle();

        gridView.setAdapter(tileAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tile tile = tiles[i];

                //check left
                if(inBounds(i-1) && tiles[i-1] != null && tiles[i-1].isEmpty()){
                    Tile temp = tiles[i-1];
                    tiles[i-1] = tile;
                    tiles[i] = temp;
                }
                //check right
                else if(inBounds(i+1) && tiles[i+1] != null && tiles[i+1].isEmpty()){
                    Tile temp = tiles[i+1];
                    tiles[i+1] = tile;
                    tiles[i] = temp;
                }
                //check up
                else if(inBounds(i-3) && tiles[i-3] != null && tiles[i-3].isEmpty()){
                    Tile temp = tiles[i-3];
                    tiles[i-3] = tile;
                    tiles[i] = temp;
                }
                //check down
                else if(inBounds(i+3) && tiles[i+3] != null && tiles[i+3].isEmpty()){
                    Tile temp = tiles[i+3];
                    tiles[i+3] = tile;
                    tiles[i] = temp;
                }

                tileAdapter.notifyDataSetChanged();

                if(isWin()){
                    Log.d("STATE", "WIN CONDITION");
                }

            }
        });

    }

    private boolean inBounds(int position){
        return (position >= 0) && (position < tiles.length);
    }


    private void shuffle(){
        for(int i = tiles.length - 1; i > 0; i--){
            double j = Math.floor(Math.random() * (i+1));

            Tile temp = tiles[i];
            tiles[i] = tiles[(int)j];
            tiles[(int)j] = temp;
        }
    }

    private boolean isWin(){
        for(int i = 0; i < tiles.length; i++){
            Log.d("isWin","getPosition = " + tiles[i].getPosition() + " i = " + i);
            if(tiles[i].getPosition() != i) {
                Log.d("isWin","getPosition = " + tiles[i].getPosition() + " i = " + i);
                return false;
            }
        }

        return true;
    }

}
