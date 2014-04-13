package com.akshay.sangeetplayer;

import java.util.ArrayList;

import com.akshay.sangeetplayer.data.AlbumDB;

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


public class Albums extends Activity implements Callback, OnItemClickListener {

	ListView lv;
	
	ArrayList<String> albums_list;
	ArrayAdapter<String> adapter;	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_albums);
		
		lv=(ListView) findViewById(R.id.albums_list_view);
		
		albums_list=new ArrayList<String>();
		lv.setOnItemClickListener(this);
		if(savedInstanceState==null){
			AlbumDB d=new AlbumDB();
			d.setHandler(new Handler(this));
			d.start();
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albums_list);
			lv.setAdapter(adapter);
			
		}
		else{
			albums_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, albums_list);
			lv.setAdapter(adapter);
			
		}
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", albums_list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState!=null)
		{
			albums_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.albums, menu);
		return true;
	}



	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle b=msg.getData();
		albums_list.add(b.getString("album"));
		lv.setAdapter(adapter);
		return true;
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
		// TODO Auto-generated method stub
		
		String album_name=albums_list.get(index);
		Intent i=new Intent(this,AlbumView.class);
		Bundle b=new Bundle();
		
		Log.d("Album name",album_name);
		
		i.putExtra("name", album_name);
		i.putExtras(b);
		startActivity(i);
		
	}

}
