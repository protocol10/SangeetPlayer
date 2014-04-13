package com.akshay.sangeetplayer.data;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlbumDB extends Thread {

	
	Handler h;
	ArrayList<String> albums_name;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		albums_name=new ArrayList<String>();
		AlbumsArtistDB a=new AlbumsArtistDB();
		Log.i("AlbumsArtiss", "initialized");
		albums_name=a.getAlbum();
		
		
		for (int i = 0; i < albums_name.size(); i++) {
			Message msg=new Message();
			Bundle b=new Bundle();
			String name=albums_name.get(i);
			Log.d("",name);
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				// TODO: handle exception
			}
			b.putString("album", name);
			msg.setData(b);
			h.sendMessage(msg);
			Log.d("Message", name);
		}
		
		
	}

	public Handler getHandler() {
		return h;
	}

	public void setHandler(Handler h) {
		this.h = h;
	}
}
