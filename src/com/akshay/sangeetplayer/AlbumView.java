package com.akshay.sangeetplayer;

import java.util.ArrayList;
import java.util.HashMap;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AlbumView extends Activity implements OnItemClickListener {

	ListView lv;
	ArrayAdapter<HashMap<String, String>> albums_item;
	
	SimpleAdapter adapter;
	String name;
	ArrayList<HashMap<String, String>> album_songs=new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String, String>> album_data=new ArrayList<HashMap<String,String>>();
	MediaMetadataRetriever mretriver=new MediaMetadataRetriever();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_view);
		lv=(ListView) findViewById(R.id.album_result);
		
		String[] from={"songsTitle"};
		int[] to={R.id.album_song_name};
		
			
		Bundle b=getIntent().getExtras();
		name=b.getString("name");
		if(savedInstanceState==null)
		{
			album_data=getList(name);
			adapter=new SimpleAdapter(this, album_data, R.layout.album_view_row, from, to);
			lv.setAdapter(adapter);
			
		}
		else
		{
			adapter=new SimpleAdapter(this, album_data, R.layout.album_view_row, from, to);
			lv.setAdapter(adapter);
			
		}
		
		albums_item=new ArrayAdapter<HashMap<String,String>>(this, android.R.layout.simple_list_item_1, album_data);
		lv.setOnItemClickListener(this);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", album_data);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState!=null){
			album_data=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
		}
	}
	private ArrayList<HashMap<String, String>> getList(String name2) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> track=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<MainActivity.universal.size();i++)
		{
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String album=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
				
				if(name2!=null||name2=="")
				{
					if(name2.equals(album))
					{
						String song=MainActivity.universal.get(i).get("songsTitle");
						HashMap<String, String> map=MainActivity.universal.get(i);
						track.add(map);
						Log.d("song_name",song);
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return track;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.album_view, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
		// TODO Auto-generated method stub
		
		int songIndex=index;
		Intent i=new Intent(this,PlayerContol.class);
		Bundle b=new Bundle();
		i.putExtra("list", album_data);
		i.putExtra("index", songIndex);
		i.putExtra("Name", "Album");
		i.putExtras(b);
		startActivity(i);
		
	}

}
