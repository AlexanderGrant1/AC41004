package com.team8.potatodoctor.Adapters;

import android.content.Context;
<<<<<<< HEAD
=======
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
>>>>>>> 49a6e515221470020e4beb26a9d5e3fbd6158a46

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.TutorialEntity;
import com.team8.potatodoctor.Models.Repositories.TutorialRepository;

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
