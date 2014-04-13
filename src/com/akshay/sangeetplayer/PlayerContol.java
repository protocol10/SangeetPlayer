package com.akshay.sangeetplayer;

import java.util.ArrayList;
import java.util.HashMap;

import com.akshay.sangeetplayer.data.Fxeffects.EqualizerFxActivity;
import com.akshay.sangeetplayer.service.IMedia;
import com.akshay.sangeetplayer.service.MediaService;

import android.os.Bundle;
import android.os.Handler;
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
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class PlayerContol extends Activity implements OnClickListener, OnSeekBarChangeListener {

	boolean state=false;
	boolean isbound=false;
	IMedia mservice=null;
	ArrayList<HashMap<String, String>> tracks;
	Button play,next,back;
	SeekBar seekbar;
	TextView tracks_text,albums_text,artist_text,current_time_text,total_time_text;
	String src;
	String name1;
	int index;
	private int seekmax;
	private static int songended=0;
	boolean mbroadcastIsRegistered;
	Intent serviceIntent;
	public static final String BROADCAST_SEEKBAR="com.akshay.sangeetplayer.sendseekbar";
	Intent intent;
	AlertDialog.Builder builder;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_contol);
	
		Bundle b=getIntent().getExtras();
		//src=b.getString("src");
		index=b.getInt("index");
		tracks=(ArrayList<HashMap<String, String>>) b.getSerializable("list");
		name1=b.getString("Name");
		Log.d("Activity", name1);
		tracks_text=(TextView) findViewById(R.id.tracks_name);
		albums_text=(TextView) findViewById(R.id.albums_name);
		artist_text=(TextView) findViewById(R.id.artist_name);
		current_time_text=(TextView) findViewById(R.id.current_time_text);
		total_time_text=(TextView) findViewById(R.id.total_time_text);
		
		play=(Button) findViewById(R.id.play_song);
		next=(Button) findViewById(R.id.next_song);
		back=(Button) findViewById(R.id.back_song);
		seekbar=(SeekBar) findViewById(R.id.seekBar1);
		play.setOnClickListener(this);
		next.setOnClickListener(this);
		back.setOnClickListener(this);
		seekbar.setOnSeekBarChangeListener(this);
		if(savedInstanceState!=null)
		{
			tracks_text.setText(savedInstanceState.getString("tracks_text"));
			albums_text.setText(savedInstanceState.getString("albums_text"));
			artist_text.setText(savedInstanceState.getString("artist_text"));
			tracks=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
			state=savedInstanceState.getBoolean("state");
		}
		else
		{
			
		}
		initService();
		
		intent =new Intent(BROADCAST_SEEKBAR);
		
		builder=new AlertDialog.Builder(PlayerContol.this);
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		state=true;
		outState.putString("track_text", tracks_text.getText().toString());
		outState.putString("albums_text", albums_text.getText().toString());
		outState.putString("artist_text", artist_text.getText().toString());
		outState.putBoolean("state", state);
		outState.putSerializable("list", tracks);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		
		tracks_text.setText(savedInstanceState.getString("tracks_text"));
		albums_text.setText(savedInstanceState.getString("albums_text"));
		artist_text.setText(savedInstanceState.getString("artist_text"));
		tracks=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
		savedInstanceState.getBoolean("state");
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent i;
		switch (item.getItemId()) {
		
		case R.id.effects_fx:
			i=new Intent(this,EqualizerFxActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	private BroadcastReceiver broadcastReciever=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent serviceintent) {
			// TODO Auto-generated method stub
			updateUI(serviceintent);
		}
	};
	
	private void initService() {
		// TODO Auto-generated method stub
		if(mservice==null)
		{
			Log.d("SERVICE", "BINDING TO SERVICE");
			serviceIntent=new Intent(this,MediaService.class);
			bindService(serviceIntent, sc, BIND_AUTO_CREATE);
		}
		
		
	}


	protected void updateUI(Intent serviceintent2) {
		// TODO Auto-generated method stub
		String counter=serviceintent2.getStringExtra("counter");
		String mediamax=serviceintent2.getStringExtra("mediamax");
		String strsongended=serviceintent2.getStringExtra("songended");
		int seekprogress=Integer.parseInt(counter);
		seekmax=Integer.parseInt(mediamax);
		songended=Integer.parseInt(strsongended);
		//
		seekbar.setMax(seekmax);
		seekbar.setProgress(seekprogress);
		current_time_text.setText(updateText(seekprogress));
		total_time_text.setText(updateText(seekmax));
		if(songended==1)
		{
			
		}
	}
	public String updateText(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";
 
        // Convert total duration into time
           int hours = (int)( milliseconds / (1000*60*60));
           int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
           int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
           // Add hours if there
           if(hours > 0){
               finalTimerString = hours + ":";
           }
 
           // Prepending 0 to seconds if it is one digit
           if(seconds < 10){
               secondsString = "0" + seconds;
           }else{
               secondsString = "" + seconds;}
 
           finalTimerString = finalTimerString + minutes + ":" + secondsString;
 
        // return timer string
        return finalTimerString;
    }

	
	
	
	ServiceConnection sc=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			mservice=null;
			isbound=false;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mservice=IMedia.Stub.asInterface(service);
			isbound=true;
			if(name1.equals("Notification"))
			{
				displayInfo();
				Log.d(name1,"Notification");
			}
			else
			{
				if(state==true)
				{
					//clear();
					//updateSongList();
					//playSong(index);
					//getMaxDuration();
				}
				else
				{
					clear();
					updateSongList();
					playSong(index);
					getMaxDuration();
				}
				
			}
			
			
			Log.i("Player Control","Service Connected");
		}
	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player_contol, menu);
		return true;
	}
	
	
	protected void getMaxDuration() {
		// TODO Auto-generated method stub
		try {
			seekbar.setMax(mservice.getDuration());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void clear() {
		// TODO Auto-generated method stub
		try {
			mservice.clearList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void playSong(int index2) {
		// TODO Auto-generated method stub
		try {
			
			//src=tracks.get(index).get("songsPath");
			mservice.playSong(index2);
			/*tracks_text.setText(mservice.getTrackName());
			albums_text.setText(mservice.getAlbumName());
			artist_text.setText(mservice.getArtistName());*/
			displayInfo();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registerReceiver(broadcastReciever,new IntentFilter(MediaService.BROADCAST_ACTION));
		mbroadcastIsRegistered=true;
	}


	protected void updateSongList() {
		// TODO Auto-generated method stub
		for(int i=0;i<tracks.size();i++)
		{
			String name=tracks.get(i).get("songsTitle");
			String src=tracks.get(i).get("songsPath");
			String artist_name=tracks.get(i).get("artistName");
			String album_name=tracks.get(i).get("albumName");
			try {
				Log.i("name",name);
				Log.i("src",src);
				Log.i("album_name",album_name);
				Log.i("arist_name", artist_name);
				mservice.addList(name, src, album_name, artist_name);
				Log.d("Service","addList");
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!mbroadcastIsRegistered)
		{
			registerReceiver(broadcastReciever, new IntentFilter(MediaService.BROADCAST_ACTION));
			mbroadcastIsRegistered=true;
		}
		
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(isbound)
		{
			unbindService(sc);
			isbound=false;
		}
		
		try {
			if(mbroadcastIsRegistered)
			{
				unregisterReceiver(broadcastReciever);
				mbroadcastIsRegistered=false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
	public void presetIndex(int index)
	{
		try {
			//startService(new Intent(this,MediaService.class));
			mservice.setPreset(index);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
		switch (view.getId()) {
		case R.id.play_song:
			try {
				mservice.pause();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case R.id.next_song:
			try {
				//mservice.next();
				showDialog1(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			break;
		case R.id.back_song:
			
			try {
				//mservice.previous();
				//displayInfo();
				showDialog1(2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	void displayInfo()
	{
		try {
			tracks_text.setText(mservice.getTrackName());
			albums_text.setText(mservice.getAlbumName());
			artist_text.setText(mservice.getArtistName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void onProgressChanged(SeekBar sb, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser)
		{
			int seekpos=sb.getProgress();
			intent.putExtra("seekpos", seekpos);
			sendBroadcast(intent);
		}
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		showDialog1(1);
		//finish();
	}
	
	
	
	public void showDialog1(int i)
	{
		int option=i;
		switch (option) {
		case 0:
			builder.setTitle("SangeetPlayer");
			builder.setMessage("Do you want to play the next song");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
					try {
						mservice.next();
						displayInfo();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
				}
			});
			builder.show();
			break;
		case 1:
			builder.setTitle("SangeetPlayer");
			builder.setMessage("Do you want to quit");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
					try {
						stopService(serviceIntent);
						finish();
				//		displayInfo();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
				}
			});
			builder.show();
			break;
		case 2:
			builder.setTitle("SangeetPlayer");
			builder.setMessage("Do you want to play the previouss song");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
					try {
						
						mservice.previous();
						displayInfo();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
				}
			});
			builder.show();
			
			break;
		default:
			break;
		}
	}
	
	
	

}
