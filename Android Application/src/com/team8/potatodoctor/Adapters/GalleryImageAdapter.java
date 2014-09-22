package com.team8.potatodoctor.Adapters;

import java.net.URI;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;

// Refer to: http://www.learn-android-easily.com/2013/07/android-gallery-view-example.html
@SuppressWarnings("deprecation")
public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;
	private PestEntity pest;
    
	//Constructor
    public GalleryImageAdapter(Context context, PestEntity pest) 
    {
        mContext = context;
        this.pest = pest;
    }
    
    //Required Method
	@Override
	public int getCount() {
		return pest.getPhotos().size();
	}

	//Required Method
	@Override
	public Object getItem(int position) {
		return position;
	}

	//Required Method
	@Override
	public long getItemId(int position) {
		return position;
	}

	//Required Method
	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		ImageView i = new ImageView(mContext);

        i.setImageURI(Uri.parse(pest.getPhotos().get(index).getName()));
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
