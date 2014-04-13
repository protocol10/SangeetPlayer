package com.akshay.sangeetplayer.net;

import java.io.IOException;

import com.akshay.sangeetplayer.R;
import com.akshay.sangeetplayer.service.IMedia;
import com.akshay.sangeetplayer.service.MediaService;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class StreamInput extends Activity implements OnPreparedListener, OnSeekBarChangeListener {

	final Context context=this;
	Intent serviceIntent;
	private SeekBar seekbar;
	private int seekmax;
	boolean mbroadcastIsRegistered;
	private static int songended=0;
	Intent intent;
	boolean isbound=false;
	IMedia mservice=null;
	String url;
	String name1;
	public static final String BROADCAST_SEEKBAR="com.akshay.sangeetplayer.sendseekbar";
	public MediaPlayer mp=new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stream_input);
		showNetworkDialog();
		mp.setOnPreparedListener(this);
		seekbar=(SeekBar) findViewById(R.id.seekBar1);
		seekbar.setOnSeekBarChangeListener(this);
		intent =new Intent(BROADCAST_SEEKBAR);
	}

	private void showNetworkDialog() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflator=LayoutInflater.from(context);
		View pop_up=layoutInflator.inflate(R.layout.dialog, null);
		AlertDialog.Builder alert=new AlertDialog.Builder(context);
		alert.setTitle("Network Strem");
		alert.setMessage("Please enter the link for streaming");
		alert.setView(pop_up);
		
		final EditText input=(EditText)pop_up.findViewById(R.id.input);
		
		alert.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			//	init();
				//intent =new Intent(BROADCAST_SEEKBAR);
				//String name=getURL(input.getText().toString());
				name1="http://spreadmp3.com/indian/Aashiqui%202%20(2013)-190Kbps%20-%202%20(www.SpreadMp3.com)/01%20-%20Tum%20Hi%20Ho%20(www.Spreadmp3.com).mp3";
				//mp=new MediaPlayer();
				try {
					mp.setDataSource(name1);
					mp.prepareAsync();
					
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				Toast.makeText(getApplicationContext(), name1, Toast.LENGTH_SHORT).show();
				
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

	
	public String getURL(String URL)
	{
		return URL;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stream_input, menu);
		return true;
	}

	protected void playSong(String name12) {
		// TODO Auto-generated method stub
		try {
			mservice.playStream(name1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		mp.start();
		
	}
	
	
	
	@Override
	public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		/*if(fromUser)
		{
			int seekpos=sb.getProgress();
			intent.putExtra("seekpos", seekpos);
			sendBroadcast(intent);
		}*/
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
}
