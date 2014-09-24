package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.database_objects.TutorialEntity;
import com.team8.potatodoctor.models.repositories.TutorialRepository;

public class VideoAdapter extends ImageAdapter {
	private Context context;
	public VideoAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

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
