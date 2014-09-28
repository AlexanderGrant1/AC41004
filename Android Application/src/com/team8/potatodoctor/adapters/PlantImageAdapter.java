package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.database_objects.PlantLeafEntity;
import com.team8.potatodoctor.models.repositories.PlantLeafRepository;

/**
 * Adapter that reads Plant/Leaf objects from the database and adds it to the Grid View.
 */
public class PlantImageAdapter extends ImageAdapter {
	private Context context;
	
	/**
	 * Instantiates a new instance of the PlantImageAdapter class.
	 * 
	 * @param context The Context of the application.
	 */
	public PlantImageAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

	/* (non-Javadoc)
	 * @see com.team8.potatodoctor.adapters.ImageAdapter#addItems()
	 */
	@Override
	void addItems() {
		PlantLeafRepository plantLeafRepository = new PlantLeafRepository(context);
		for(PlantLeafEntity plantLeaf : plantLeafRepository.getAllPlantLeafs())
		{
			if(plantLeaf.getPhotos().size() == 0)
			{
				items.add(new Item(plantLeaf.getName(), R.drawable.ic_default));
			}
			else
			{
				items.add(new Item(plantLeaf.getName(), Uri.parse(plantLeaf.getPhotos().get(0).getFullyQualifiedPath())));
			}
			
		}
	}

}
