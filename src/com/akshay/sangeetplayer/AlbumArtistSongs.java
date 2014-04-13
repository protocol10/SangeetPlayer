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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AlbumArtistSongs extends Activity implements OnItemClickListener {

	
	ListView lv;
	Intent i;
	
	SimpleAdapter adapter;
	String name;
	ArrayList<HashMap<String, String>> album_songs=new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String, String>> album_artist_data=new ArrayList<HashMap<String,String>>();
	MediaMetadataRetriever mretriver=new MediaMetadataRetriever();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_artist_songs);
		lv=(ListView) findViewById(R.id.artist_album_songs);
		String[] from={"songsTitle"};
		int[] to={R.id.artist_tracks_name};
		
		Bundle b=getIntent().getExtras();
		name=b.getString("name");
		
		if(savedInstanceState==null)
		{
			album_artist_data=getList(name);
			adapter=new SimpleAdapter(this, album_artist_data, R.layout.artist_album_song_row, from, to);
			lv.setAdapter(adapter);
		}
		else
		{
			album_artist_data=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
			adapter=new SimpleAdapter(this, album_artist_data, R.layout.artist_album_song_row, from, to);
			lv.setAdapter(adapter);
		}
		
		lv.setOnItemClickListener(this);
	
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", album_artist_data);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		album_artist_data=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
	}
	
	private ArrayList<HashMap<String, String>> getList(String name2) {
		// TODO Auto-generated method stub
		ArrayList<HashMap<String, String>> track=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<MainActivity.universal.size();i++)
		{
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String album=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
				
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
		getMenuInflater().inflate(R.menu.album_artist_songs, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view , int index, long id) {
		// TODO Auto-generated method stub
		
		String name=album_artist_data.get(index).get("songsTitle");
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
		int songIndex=index;
		//String name=tracks_list.get(songIndex).get("songsPath");
		Intent i=new Intent(this,PlayerContol.class);
		Bundle b=new Bundle();
		//i.putExtra("src", name);
		Toast.makeText(getApplicationContext(), album_artist_data.get(index).get("songsName"), Toast.LENGTH_SHORT).show();
		i.putExtra("index", songIndex);
		i.putExtra("list", album_artist_data);
		i.putExtra("Name", "Tracks");
		i.putExtras(b);
		startActivity(i);
	}

}
