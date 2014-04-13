package com.akshay.sangeetplayer.data;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ArtistDB extends Thread {

	
	Handler h;
	ArrayList<String> artist_data;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		artist_data=new ArrayList<String>();
		AlbumsArtistDB a=new AlbumsArtistDB();
		artist_data=a.getArtist();
		
		for (int i = 0; i < artist_data.size(); i++) {
			Message msg=new Message();
			Bundle b=new Bundle();
			String name=artist_data.get(i);
			Log.d("",name);
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				// TODO: handle exception
			}
			b.putString("artist", name);
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
