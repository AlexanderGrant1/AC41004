package com.team8.potatodoctor.Adapters;

import android.content.Context;

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
			items.add(new Item(tutorial.getName(), R.drawable.ic_default));
		}
	}

}
