package com.team8.potatodoctor.adapters;

import com.team8.potatodoctor.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

// Refer to: http://www.learn-android-easily.com/2013/07/android-gallery-view-example.html
public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;

	//Array of images for the image gallery.
	private Integer[] mImageIds = {
		R.drawable.ic_shiba1,
		R.drawable.ic_shiba2,
		R.drawable.ic_shiba3,
		R.drawable.ic_shiba4,
	};
    
	//Constructor
    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }
    
    //Required Method
	@Override
	public int getCount() {
		return mImageIds.length;
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

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
