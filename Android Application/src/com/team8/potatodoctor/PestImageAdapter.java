package com.team8.potatodoctor;

import android.content.Context;

public class PestImageAdapter extends ImageAdapter {
	
	public PestImageAdapter(Context context) {
		super(context);
		addItems();
		
	}

	@Override
	void addItems() {
		items.add(new Item("Pest",       R.drawable.ic_placeholder));
        items.add(new Item("Potato",   		R.drawable.ic_placeholder));
        items.add(new Item("Spud", 			R.drawable.ic_placeholder));
        items.add(new Item("Spuddy", 		R.drawable.ic_placeholder));
        items.add(new Item("Pomme de Terre",     R.drawable.ic_placeholder));
		
	}

}
