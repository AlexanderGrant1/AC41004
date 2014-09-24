package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.team8.potatodoctor.database_objects.IDatabaseObject;

// Refer to: http://www.learn-android-easily.com/2013/07/android-gallery-view-example.html
@SuppressWarnings("deprecation")
public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;
	private IDatabaseObject dbItem;
    
	//Constructor
    public GalleryImageAdapter(Context context, IDatabaseObject dbItem) 
    {
        mContext = context;
        this.dbItem = dbItem;
    }
    
    //Required Method
	@Override
	public int getCount() {
		return dbItem.getPhotos().size();
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
		
        i.setImageURI(Uri.parse(dbItem.getPhotos().get(index).getFullyQualifiedPath()));
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
