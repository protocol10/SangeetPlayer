package com.akshay.sangeetplayer.data;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GenreHandler extends Thread{

	Handler h;
	ArrayList<String> genre_data;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		genre_data=new ArrayList<String>();
		AlbumsArtistDB a=new AlbumsArtistDB();
		genre_data=a.getGenre_list();
		
		for (int i = 0; i < genre_data.size(); i++) {
			Message msg=new Message();
			Bundle b=new Bundle();
			String name=genre_data.get(i);
			Log.d("",name);
			try {
				Thread.sleep(250);
			} catch (Exception e) {
				// TODO: handle exception
			}
			b.putString("genre", name);
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
