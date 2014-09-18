package com.team8.potatodoctor;

import java.io.File;

import com.team8.adapters.PlantImageAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GridviewItemActivity extends Activity {
	
	TextView tv;
	//LinearLayout gridLinearLayout;
	
	
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview_item_layout);
		
		String type = "";
		int position = 0;
		
	    Bundle extras = getIntent().getExtras();
	    if(extras !=null)
	    {
	    	type = extras.getString("Type");
	    	position = extras.getInt("Position");
	    }
	    
	    //gridLinearLayout = (LinearLayout)findViewById(R.id.gridLinearLayout);
	    
	    
	    tv.setMovementMethod(new ScrollingMovementMethod());
	    
	    HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		LinearLayout topLinearLayout = (LinearLayout) findViewById(R.id.gridLinearLayout);
		
		topLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
	    for(int i = 0; i < 10; i++)
	    {
	    	final ImageView imageView = new ImageView (this);
	    	
	    	imageView.setTag(i);
	    	imageView.setImageResource(R.drawable.ic_pest);
	    	topLinearLayout.addView(imageView);
	    	imageView.setOnClickListener(new OnClickListener()
	    	{
	    		public void onClick(View v)
	    		{
	    			Log.e("Tag",""+imageView.getTag());
	    		}
	    	});
	    	
	    }
	    
	    scrollView.addView(topLinearLayout);
	    
	}

        
        
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		/*
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		*/
		return true;
	}
	
}
