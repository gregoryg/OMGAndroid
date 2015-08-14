package com.example.ggrubbs.omgandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

/**
 * Created by ggrubbs on 8/14/2015.
 */
public class DetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// tell the activity which XML layout is right
	setContentView(R.layout.activity_detail);
	// enable the "up" button for more navigation options
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	// access the imageview from XML
	ImageView imageView = (ImageView) findViewById(R.id.img_cover);
    }
}

