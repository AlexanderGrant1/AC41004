package com.team8.potatodoctor.Adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.Adapters.ImageAdapter.Item;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.Models.Repositories.TuberRepository;

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
		for(TuberSymptomEntity tuberSymptom : tuberRepository.getAllTubers())
		{
			if(tuberSymptom.getPhotos().size() == 0)
			{
				items.add(new Item(tuberSymptom.getName(), R.drawable.ic_default));
			}
			else
			{
				items.add(new Item(tuberSymptom.getName(), Uri.parse(tuberSymptom.getPhotos().get(0).getName())));
			}
		}
	}

}
