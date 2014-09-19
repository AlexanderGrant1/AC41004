package com.team8.adapters;

import com.team8.adapters.ImageAdapter.Item;
import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.DatabaseObjects.TuberSymptomEntity;
import com.team8.potatodoctor.Models.DatabaseManager;
import com.team8.potatodoctor.Models.TuberRepository;
import com.team8.potatodoctor.R.drawable;

import android.content.Context;

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
			items.add(new Item(tuberSymptom.getName(), R.drawable.ic_placeholder));
		}
	}

}
