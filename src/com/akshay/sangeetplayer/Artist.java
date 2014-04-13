package com.akshay.sangeetplayer;

import java.util.ArrayList;

import com.akshay.sangeetplayer.data.ArtistDB;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Artist extends Activity implements Callback, OnItemClickListener {

	ListView lv;
	ArrayList<String> artist_list;
	ArrayAdapter<String> artist_items;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist);
		lv=(ListView) findViewById(R.id.artist_list_view);
		artist_list=new ArrayList<String>();
		
		lv.setOnItemClickListener(this);
		
		if(savedInstanceState==null)
		{
			artist_items=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, artist_list);
			lv.setAdapter(artist_items);
			ArtistDB a=new ArtistDB();
			a.setHandler(new Handler(this));
			a.start();
		}
		else{
			artist_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
			artist_items=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, artist_list);
			lv.setAdapter(artist_items);
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", artist_list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		artist_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artist, menu);
		return true;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle b=msg.getData();	
		artist_list.add(b.getString("artist"));
		lv.setAdapter(artist_items);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
		// TODO Auto-generated method stub
		String artist_name=artist_list.get(index);
		Intent i=new Intent(this,ArtistAlbum.class);
		Bundle b=new Bundle();
		
		Log.d("Artist Name", artist_name);
		i.putExtra("artist_name", artist_name);
		i.putExtras(b);
		startActivity(i);
	}

}
