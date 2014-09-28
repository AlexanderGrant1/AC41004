package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.database_objects.PestEntity;
import com.team8.potatodoctor.models.repositories.PestRepository;

/**
 * Adapter that reads Pest objects from the database and adds it to the Grid View.
 */
public class PestImageAdapter extends ImageAdapter {
	private Context context;
	
	/**
	 * Instantiates a new instance of the PestImageAdapter class.
	 * 
	 * @param context The Context of the application.
	 */
	public PestImageAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

	/* (non-Javadoc)
	 * @see com.team8.potatodoctor.adapters.ImageAdapter#addItems()
	 */
	@Override
	void addItems() {
		PestRepository pestRepository = new PestRepository(context);
		for(PestEntity pest : pestRepository.getAllPests())
		{
			if(pest.getPhotos().size() == 0)
			{
				items.add(new Item(pest.getName(), R.drawable.ic_default));
			}
			else
			{
				items.add(new Item(pest.getName(), Uri.parse(pest.getPhotos().get(0).getFullyQualifiedPath())));
			}
		}
	}

}
