package com.team8.potatodoctor.adapters;

import android.content.Context;
import android.net.Uri;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.Models.Repositories.PestRepository;

public class PestImageAdapter extends ImageAdapter {
	
	private Context context;
	public PestImageAdapter(Context context) {
		super(context);
		this.context = context;
		addItems();
		
	}

	@Override
	void addItems() {
		PestRepository pestRepository = new PestRepository(context);
		for(PestEntity pest : pestRepository.getAllPests())
		{
			if(pest.getPhotos().size() == 0)
			{
				items.add(new Item(pest.getName(), R.drawable.ic_placeholder));
			}
			else
			{
				items.add(new Item(pest.getName(), Uri.parse(pest.getPhotos().get(0).getName())));
			}
		}
	}

}
