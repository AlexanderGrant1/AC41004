package com.team8.adapters;

import com.team8.potatodoctor.R;
import com.team8.potatodoctor.DatabaseObjects.PestEntity;
import com.team8.potatodoctor.Models.DatabaseManager;
import com.team8.potatodoctor.Models.PestRepository;
import com.team8.potatodoctor.R.drawable;

import android.content.Context;

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
			items.add(new Item(pest.getName(), R.drawable.ic_placeholder));
		}
	}

}
