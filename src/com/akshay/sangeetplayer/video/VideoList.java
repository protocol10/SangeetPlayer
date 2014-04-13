package com.akshay.sangeetplayer.video;

import java.util.ArrayList;
import java.util.HashMap;

import com.akshay.sangeetplayer.PlayerContol;
import com.akshay.sangeetplayer.R;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class VideoList extends Activity implements Callback, OnItemClickListener {

	
	ArrayList<HashMap<String, String>> tracks_list;
	//public static ArrayList<HashMap<String, String>> universal=new ArrayList<HashMap<String,String>>();
	SimpleAdapter adapter;
	ListView lv;
	Intent i;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_list);
		lv=(ListView) findViewById(R.id.video_list);
		tracks_list=new ArrayList<HashMap<String,String>>();
		String[] from={"songsTitle"};
		int[] to={R.id.video_name};
		
		if(savedInstanceState==null)
		{
			adapter=new SimpleAdapter(this, tracks_list, R.layout.video_row, from, to);
			lv.setAdapter(adapter);
			Log.i("SaveInstnace","null");
			VideoHandler v=new VideoHandler();
			v.setHandler(new Handler(this));
			v.start();

		}
		else
		{
			tracks_list=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
			adapter=new SimpleAdapter(this, tracks_list, R.layout.video_row, from, to);
			lv.setAdapter(adapter);
			
			
		}
		
		lv.setOnItemClickListener(this);
		Intent serviceIntent=new Intent(this,PlayerContol.class);
		startService(serviceIntent);
		
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", tracks_list);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
		if(savedInstanceState!=null)
		{
			tracks_list=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_list, menu);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		HashMap<String, String> map=(HashMap<String, String>) msg.obj;
		tracks_list.add(map);
		lv.setAdapter(adapter);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
		// TODO Auto-generated method stub
		Intent i=new Intent(this,Video_Player.class);
		Bundle b=new Bundle();
		int songIndex=index;
		String path=tracks_list.get(index).get("songsPath");
		Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
		i.putExtra("index", songIndex);
		i.putExtra("list", tracks_list);
		//i.putExtra("path", path);
		i.putExtras(b);
		startActivity(i);
		
	}

}
