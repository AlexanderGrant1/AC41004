package com.team8.potatodoctor.Adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PlantLeafSymptomsEntity;
import com.team8.potatodoctor.Models.Repositories.PlantLeafRepository;

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
			if(plantLeaf.getPhotos().size() == 0)
			{
				items.add(new Item(plantLeaf.getName(), R.drawable.ic_placeholder));
			}
			else
			{
				items.add(new Item(plantLeaf.getName(), Uri.parse(plantLeaf.getPhotos().get(0).getName())));
			}
			
		}
	}

}
