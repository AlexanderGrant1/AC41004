package com.team8.potatodoctor.adapters;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;
import com.team8.potatodoctor.R.drawable;
import com.team8.potatodoctor.adapters.ImageAdapter.Item;

import android.content.Context;

public class PlantImageAdapter extends ImageAdapter {
	private Context context;
	public PlantImageAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

	@Override
	void addItems() {
		PlantLeafRepository plantLeafRepository = new PlantLeafRepository(context);
		for(PlantLeafSymptomsEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			items.add(new Item(plantLeaf.getName(), R.drawable.ic_placeholder));
		}
	}

}