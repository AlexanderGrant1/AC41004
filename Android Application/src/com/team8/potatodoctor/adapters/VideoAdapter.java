package com.team8.potatodoctor.adapters;

import android.content.Context;

import com.team8.potatodoctor.R;

public class VideoAdapter extends ImageAdapter {
	
	public VideoAdapter(Context context) {
		super(context);
		addItems();
		
	}

	@Override
	void addItems() {
		items.add(new Item("Kit Test Video",     R.drawable.ic_placeholder));
        items.add(new Item("Leaf Press Video",   R.drawable.ic_placeholder));
        items.add(new Item("The last video",   	 R.drawable.ic_placeholder));
	}

}
