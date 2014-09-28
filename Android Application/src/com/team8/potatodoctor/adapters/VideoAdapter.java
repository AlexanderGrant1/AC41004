package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.TutorialRepository;

/**
 * Adapter that adds new Videos from the database to the Grid View.
 */
public class VideoAdapter extends ImageAdapter {
	private Context context;
	
	/**
	 * Instantiates a new instance of the VideoAdapter class.
	 * 
	 * @param context
	 */
	public VideoAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}
	
	/* (non-Javadoc)
	 * @see com.team8.potatodoctor.adapters.ImageAdapter#addItems()
	 */
	@Override
	void addItems() {  
		TutorialRepository tutorialRepository = new TutorialRepository(context);
		for(TutorialEntity tutorial : tutorialRepository.getAllTutorials())
		{
			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(tutorial.getFullyQualifiedPath(), MediaStore.Images.Thumbnails.MINI_KIND);
			items.add(new Item(tutorial.getName(),thumb)); 
		}
	}
 
}
