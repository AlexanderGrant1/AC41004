package com.team8.potatodoctor.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team8.potatodoctor.R;

/**
 * Adapter that takes the icons for categories and adds them to the Grid View.
 */
public class ImageAdapterMain extends BaseAdapter {
    private List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    /**
     * Instantiates a new instance of the ImageAdapterMain class.
     * 
     * @param context The Context of the application/object.
     */
    public ImageAdapterMain(Context context) {
        inflater = LayoutInflater.from(context);

        items.add(new Item("Pests",       			R.drawable.ic_pest));
        items.add(new Item("Plant/Leaf Symptoms",   R.drawable.ic_leaf));
        items.add(new Item("Tuber Symptoms", 		R.drawable.ic_tuber));
        items.add(new Item("Tutorials", 			R.drawable.ic_video));
        
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int i) {
        return items.get(i).drawableId;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if(v == null) {
            v = inflater.inflate(R.layout.grid_layout, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
            v.setPadding(5, 5, 5, 5);
        }

        picture = (ImageView)v.getTag(R.id.picture);
        name = (TextView)v.getTag(R.id.text);

        Item item = (Item)getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    /**
     * Creates a new Item to add to the Adapter.
     */
    private class Item {
        final String name;
        final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}