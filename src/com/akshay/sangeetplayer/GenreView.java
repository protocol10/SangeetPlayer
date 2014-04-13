package com.akshay.sangeetplayer;

import java.util.ArrayList;

import com.akshay.sangeetplayer.data.GenreHandler;

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

public class GenreView extends Activity implements OnItemClickListener, Callback {

	ArrayList<String> genre_list;
	ListView list_view;
	ArrayAdapter<String> adapter;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_genre_view);
		list_view=(ListView) findViewById(R.id.genre_list_view);
		genre_list=new ArrayList<String>();
		list_view.setOnItemClickListener(this);
		
		if(savedInstanceState==null)
		{
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genre_list);
			list_view.setAdapter(adapter);
			GenreHandler g=new GenreHandler();
			g.setHandler(new Handler(this));
			g.start();
		}
		else
		{
			genre_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
			adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genre_list);
			list_view.setAdapter(adapter);
		}
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", genre_list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState!=null){
			genre_list=(ArrayList<String>) savedInstanceState.getSerializable("list");
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.genre_view, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
		// TODO Auto-generated method stub
		String genre_name=genre_list.get(index);
		Intent i=new Intent(this,GenreSongs.class);
		Bundle b=new Bundle();
		Log.d("Album name",genre_name);
		i.putExtra("name", genre_name);
		i.putExtras(b);
		startActivity(i);
		
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle b=msg.getData();
		genre_list.add(b.getString("genre"));
		list_view.setAdapter(adapter);
		return false;
	}
	

}
