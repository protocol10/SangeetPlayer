package com.akshay.sangeetplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

public class ArtistAlbum extends Activity implements OnItemClickListener {

	ListView lv;
	ArrayAdapter<String> artist_album;
	ArrayList<String> art_album;
	HashSet<String> s;
	MediaMetadataRetriever mretriver=new MediaMetadataRetriever();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_album);
		Bundle b=getIntent().getExtras();
		s=new HashSet<String>();
		String name=b.getString("artist_name");
		lv=(ListView) findViewById(R.id.listView1);
		art_album=new ArrayList<String>();
		
		
		if(savedInstanceState==null)
		{
			art_album=getList(name);
			artist_album=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, art_album);
			lv.setAdapter(artist_album);
			
		}
		else
		{
			art_album=(ArrayList<String>) savedInstanceState.getSerializable("list");
			artist_album=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, art_album);
			lv.setAdapter(artist_album);
			
		}
			
		lv.setOnItemClickListener(this);
		
				
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", art_album);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState!=null){
			art_album=(ArrayList<String>) savedInstanceState.getSerializable("list");
		}
	}
	
	private ArrayList<String> getList(String name1) {
		// TODO Auto-generated method stub
		ArrayList<String> album=new ArrayList<String>();
		for(int i=0;i<MainActivity.universal.size();i++){
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String artist=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
				if(name1!=""||name1!=null)
				{
					if(name1.equals(artist))
					{
						//String song=MainActivity.universal.get(i).get("songsTitle");
						s.add(artist);
					}
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		album.addAll(s);
		return album;
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.artist_album, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int index, long id) {
		// TODO Auto-generated method stub
		Intent i=new Intent(this,AlbumArtistSongs.class);
		Bundle b=new Bundle();
		String name=art_album.get(index);
		i.putExtra("name", name);
		i.putExtras(b);
		startActivity(i);
		
	}

}
