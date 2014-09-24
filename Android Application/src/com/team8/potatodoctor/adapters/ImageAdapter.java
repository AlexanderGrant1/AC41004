package com.team8.potatodoctor.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team8.potatodoctor.R;

public abstract class ImageAdapter extends BaseAdapter {
    public List<Item> items = new ArrayList<Item>();
    private LayoutInflater inflater;

    public ImageAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        
    }

    abstract void addItems();
    
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
    	return items.get(i).drawableId;
    }

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

        if(item.imageUri != null)
        {
        	picture.setImageURI(item.imageUri);
        }
        else if(item.bitmap != null)
        {
        	picture.setImageBitmap(item.bitmap);
        }
        else
        {
        	picture.setImageResource(item.drawableId);
        }
        
        name.setText(item.name);

        return v;
    }

    class Item {
        final String name;
        final Uri imageUri;
        final int drawableId;
        final Bitmap bitmap;

        Item(String name, Uri imageUri) {
            this.name = name;
            this.imageUri = imageUri;
            this.drawableId = 0;
            this.bitmap = null;
        }
        
        Item(String name, int drawableId)
        {
        	this.name = name;
        	this.imageUri = null;
        	this.drawableId = drawableId;
        	this.bitmap = null;
        }
        
        Item(String name, Bitmap bitmap)
        {
        	this.name = name;
        	this.imageUri = null;
        	this.drawableId = 0;
        	this.bitmap = bitmap;
        }
    }
}