package com.akshay.sangeetplayer;

import java.util.ArrayList;
import java.util.HashMap;

import com.akshay.sangeetplayer.data.TracksDB;
import com.akshay.sangeetplayer.net.StreamInput;
import com.akshay.sangeetplayer.video.VideoList;



import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements Callback, OnItemClickListener {

	
	ArrayList<HashMap<String, String>> tracks_list;
	public static ArrayList<HashMap<String, String>> universal=new ArrayList<HashMap<String,String>>();
	SimpleAdapter adapter;
	ListView lv;
	Intent i;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tracks_list=new ArrayList<HashMap<String,String>>();
		lv=(ListView) findViewById(R.id.tracks_row_view);
		String[] from={"songsTitle"};
		int[] to={R.id.tracks_name};
		//adapter=new SimpleAdapter(this, tracks_list, R.layout.tracks_row, from, to);
		//lv.setAdapter(adapter);
		
		if(savedInstanceState==null)
		{
			adapter=new SimpleAdapter(this, tracks_list, R.layout.tracks_row, from, to);
			lv.setAdapter(adapter);
			Log.i("SaveInstnace","null");
			TracksDB t=new TracksDB();
			t.setHandler(new Handler(this));
			t.start();
		}
		else
		{
			tracks_list=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
			Log.i("SaveInstnace","not null, size : " + tracks_list.size());
			adapter=new SimpleAdapter(this, tracks_list, R.layout.tracks_row, from, to);
			lv.setAdapter(adapter);
		}
		lv.setOnItemClickListener(this);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", tracks_list);
	}
	
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		restore(savedInstanceState);
	}
	
	@SuppressWarnings("unchecked")
	private void restore(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(savedInstanceState!=null)
		{
			tracks_list=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		HashMap<String, String> map=(HashMap<String, String>) msg.obj;
		tracks_list.add(map);
		universal.add(map);
		lv.setAdapter(adapter);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
		// TODO Auto-generated method stub
		
		int songIndex=index;
		//String name=tracks_list.get(songIndex).get("songsPath");
		i=new Intent(this,PlayerContol.class);
		Bundle b=new Bundle();
		//i.putExtra("src", name);
		Toast.makeText(getApplicationContext(), tracks_list.get(index).get("songsName"), Toast.LENGTH_SHORT).show();
		i.putExtra("index", songIndex);
		i.putExtra("list", tracks_list);
		i.putExtra("Name", "Tracks");
		i.putExtras(b);
		startActivity(i);		
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.album_item:
			i=new Intent(this,Albums.class);
			startActivity(i);
			break;
		case R.id.artist_item:
			i=new Intent(this,Artist.class);
			startActivity(i);
			break;
		case R.id.network:
			//showNetworkDialog();
			i=new Intent(this, StreamInput.class);
			startActivity(i);
			break;
		case R.id.genre:
			i=new Intent(this, GenreView.class);
			startActivity(i);
			break;
		case R.id.video_item:
			i=new Intent(this, VideoList.class);
			startActivity(i);
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	final Context context=this;
	@SuppressWarnings("unused")
	private void showNetworkDialog() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflator=LayoutInflater.from(context);
		View pop_up=layoutInflator.inflate(R.layout.dialog, null);
		AlertDialog.Builder alert=new AlertDialog.Builder(context);
		alert.setView(pop_up);
		
		final EditText input=(EditText)pop_up.findViewById(R.id.input);
		
		alert.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog a=alert.create();
		a.show();
	}

}
