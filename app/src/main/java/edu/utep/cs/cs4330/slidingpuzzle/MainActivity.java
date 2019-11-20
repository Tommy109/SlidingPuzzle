/**
 * Author: Tomas Chagoya
 */


package edu.utep.cs.cs4330.slidingpuzzle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    static final int REQUESET_TAKE_PHOTO = 1;

    private GridView gridView;
    private SlidingPuzzle puzzle;
    private int size = 3;
    private Tile[] tiles;


    private File photoFile;

    private Button button;
    private EditText editText;
    private Button photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.grid_view);
        gridView.setVerticalScrollBarEnabled(false);
        button = findViewById(R.id.button_view);
        editText = findViewById(R.id.edit_text);
        photoButton = findViewById(R.id.photo_button);

        photoButton.setOnClickListener(view -> {
            dispatchTakePictureIntent();

        });

        button.setOnClickListener(view ->{
            newGame(null);
            puzzle.shuffle();
        });

        setGame();

        /** Get image **/
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.annie);
        fixImage(bitmap);

    }



    public void setGame(){
        puzzle = new SlidingPuzzle(size);
        puzzle.generate();

        gridView.setNumColumns(size);

        tiles = puzzle.getTiles();


        TileAdapter tileAdapter = new TileAdapter(this, puzzle.getTiles());

        puzzle.shuffle();
        gridView.setAdapter(tileAdapter);



        gridView.setOnItemClickListener((adapterView,view,position,l) -> {

                puzzle.move(position);
                tileAdapter.notifyDataSetChanged();

                if(puzzle.isWin()){
                    displayWin();
                }
            });

    }

    public void newGame(Bitmap bitmap){

        try {
            size = Integer.parseInt(editText.getText().toString());
        }catch(NumberFormatException e){
            e.getStackTrace();
            size = 3;
        }

        if(size < 2){
            size = 3;
        }

        setGame();

        /** Get static image if it wasn't called by camera button**/
        if(bitmap == null)
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dogg);


        fixImage(bitmap);
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

    private void fixImage(Bitmap bitmap){
        /**
         * Scaled image based on phone screen size
         */
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels ;

        /** Scale image to fit phone screen **/
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap,screenWidth,screenWidth,false);

        /** Split image into individual images and store **/
        Bitmap[] bitmaps = splitBitmap(scaled,size,size);

        /** Save each piece to each tile**/
        for(int i = 0; i < this.size*this.size; i++){
            tiles[i].setBitmap(bitmaps[i]);
        }

        /** Make the last tile always empty and blank **/
        tiles[size*size-1].getBitmap().eraseColor(Color.WHITE);
        tiles[size*size-1].setEmpty(true);

        //Toast.makeText(this,"SW: " + dpWidth + "   SH: " + dpHeight, Toast.LENGTH_LONG).show();
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(takePictureIntent.resolveActivity((getPackageManager())) != null){
            photoFile = null;

            try{
                photoFile = createImageFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(
                        this,"edu.utep.cs.cs4330.slidingpuzzle",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUESET_TAKE_PHOTO);
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESET_TAKE_PHOTO){
            if(resultCode == RESULT_OK){
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);

                newGame(bitmap);
            }
        }
    }

    private File createImageFile() throws IOException{

        String photoPath;
        String timeSttamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeSttamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jgp",
                storageDir
        );

        photoPath = image.getAbsolutePath();
        return image;
    }


}
