package com.akshay.sangeetplayer.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.akshay.sangeetplayer.MainActivity;

import android.media.MediaMetadataRetriever;
import android.util.Log;



public class AlbumsArtistDB{
	
	Set<String> s=new HashSet<String>();
	MediaMetadataRetriever mretriver=new MediaMetadataRetriever();
	
	public ArrayList<String> getAlbum()
	{
		ArrayList<String> albums_name=new ArrayList<String>();
		for(int i=0;i<MainActivity.universal.size();i++)
		{
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String name=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
				if(name==null||name=="")
				{
					s.add("UnknownAlbums");
					Log.i("Artist name","UnknownAlbum");
				}
				else
				{
					s.add(name);
					Log.d("Album name ::",name);
				}
					
			} catch (Exception e) {
				// TODO: handle exception
			}	
		}
		albums_name.addAll(s);
		s.clear();
		return albums_name;
	}
	
	
	public ArrayList<String> getArtist()
	{
		ArrayList<String> artist_name=new ArrayList<String>();
		
		for(int i=0;i<MainActivity.universal.size();i++)
		{
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String name=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
				if(name==null||name=="")
				{
					s.add("Unknown Artist");
					Log.i("Artist name","UnknownArtist");
					
				}
				else
				{
					s.add(name);
					Log.i("Artist name ::",name);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		artist_name.addAll(s);
		s.clear();
		return artist_name;
	}
	
	public ArrayList<String> getGenre_list()
	{
		ArrayList<String> genre_name=new ArrayList<String>();

		for(int i=0;i<MainActivity.universal.size();i++)
		{
			try {
				mretriver.setDataSource(MainActivity.universal.get(i).get("songsPath"));
				String name=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
				if(name==null||name=="")
				{
					s.add("Unknown Genre");
					Log.i("Artist name","UnknownArtist");
					
				}
				else
				{
					s.add(name);
					Log.i("Genre name::",name);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		genre_name.addAll(s);
		s.clear();
		return genre_name;
	
	
	}

}
