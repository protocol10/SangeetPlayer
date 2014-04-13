package com.akshay.sangeetplayer.net;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.IBinder;

public class NetworkService extends Service implements OnCompletionListener, OnErrorListener, OnBufferingUpdateListener {

	private MediaPlayer mp=new MediaPlayer();
	Intent bufferIntent;
	public static final String BROADCAST_BUFFER="com.akshay.sangeetplayer.broadcastbuffer";
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		mp.setOnCompletionListener(this);
		mp.setOnErrorListener(this);
		mp.setOnBufferingUpdateListener(this);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
