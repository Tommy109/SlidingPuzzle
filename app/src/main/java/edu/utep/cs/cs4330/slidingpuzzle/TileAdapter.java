package edu.utep.cs.cs4330.slidingpuzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class TileAdapter extends BaseAdapter {

    private final Context context;
    private final Tile[] tiles;

    public TileAdapter(Context context, Tile[] tiles){
        this.context = context;
        this.tiles = tiles;
    }

    @Override
    public int getCount() {
        return tiles.length;
    }

    @Override
    public Object getItem(int position) {
        return tiles[position];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Tile tile = tiles[position];

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.tile_view,null);
        }

        ImageView imageView = view.findViewById(R.id.image_view);

        //imageView.setBackgroundResource(tile.getId());
        imageView.setImageDrawable(tile.drawable);

        return view;
    }
}
