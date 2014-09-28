package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.team8.potatodoctor.database_objects.IDatabaseObject;

/**
 * Adapter for Image Gallery, extracts the Image from the database. 
 * 
 * Referenced from: http://www.learn-android-easily.com/2013/07/android-gallery-view-example.html
 */
@SuppressWarnings("deprecation")
public class GalleryImageAdapter extends BaseAdapter
{
	private Context mContext;
	private IDatabaseObject dbItem;
    	
    /**
     * Initialises an instance of the GalleryImageAdapter
     * 
     * @param context The Context of the application.
     * @param dbItem The database object to read Image from.
     */
    public GalleryImageAdapter(Context context, IDatabaseObject dbItem) 
    {
        mContext = context;
        this.dbItem = dbItem;
    }
       
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return dbItem.getPhotos().size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return position;
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		ImageView i = new ImageView(mContext);
        i.setImageURI(Uri.parse(dbItem.getPhotos().get(index).getFullyQualifiedPath()));
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
