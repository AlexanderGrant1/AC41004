package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.database_objects.TuberEntity;
import com.team8.potatodoctor.models.repositories.TuberRepository;

public class TuberImageAdapter extends ImageAdapter {
	private Context context;
	public TuberImageAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

	@Override
	void addItems() {
		TuberRepository tuberRepository = new TuberRepository(context);
		for(TuberEntity tuberSymptom : tuberRepository.getAllTubers())
		{
			if(tuberSymptom.getPhotos().size() == 0)
			{
				items.add(new Item(tuberSymptom.getName(), R.drawable.ic_default));
			}
			else
			{
				items.add(new Item(tuberSymptom.getName(), Uri.parse(tuberSymptom.getPhotos().get(0).getFullyQualifiedPath())));
			}
		}
	}

}
