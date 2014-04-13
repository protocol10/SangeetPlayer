package com.akshay.sangeetplayer.video;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.akshay.sangeetplayer.PlayerContol;
import com.akshay.sangeetplayer.R;
import com.akshay.sangeetplayer.service.MediaService;
import com.akshay.sangeetplayer.video.fileexp.DirectoryChooserDialog;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnTimedTextListener;
import android.media.MediaPlayer.TrackInfo;
import android.media.TimedText;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;




@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class Video_Player extends Activity implements OnClickListener, Callback, OnPreparedListener, OnSeekBarChangeListener, OnCompletionListener, OnErrorListener, OnTimedTextListener {

	
	private String m_chosenDir="";
	private boolean m_newFolderEnabled=true;
	TextView title,total_duration,start_duration,subtitle;
	static MediaPlayer mediaPlayer;
	SurfaceView surface_view;
	Button play,next,previous;
	String path;
	SeekBar progress;
	int index=0;
	int position=0;
	String path_text;
	ArrayList<HashMap<String, String>> tracks;
	private Handler seekHandler=new Handler();
	SurfaceHolder holder;
	Handler h=new Handler();
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_video__player);
		play=(Button) findViewById(R.id.play);
		next=(Button) findViewById(R.id.next);
		previous=(Button) findViewById(R.id.back);
		progress=(SeekBar) findViewById(R.id.seekBar1);
		
		
		play.setTextColor(Color.YELLOW);
		next.setTextColor(Color.YELLOW);
		previous.setTextColor(Color.YELLOW);
		
		title=(TextView) findViewById(R.id.Title);
		subtitle=(TextView) findViewById(R.id.subtitle1);
		start_duration=(TextView) findViewById(R.id.startTime);
		total_duration=(TextView) findViewById(R.id.TotalDuration);
		progress.setOnSeekBarChangeListener(this);
		play.setOnClickListener(this);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (index < (tracks.size() - 1)) {
					
					startVideoPlayback(index + 1);
					index += 1;

				} else {
					// Limit Exceeded play the First song
					startVideoPlayback(0);
					index = 0;
				}

			}
		} );
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (index > 0) {
					// previous song
					startVideoPlayback(index - 1);
					index -= 1;

				} else {
					// play the back song if limit exceeded from last
					startVideoPlayback(tracks.size() - 1);
					index = tracks.size() - 1;
				}

			}
		});
		
		Bundle b=getIntent().getExtras();
		//src=b.getString("src");
		//path=b.getString("path");
		index=b.getInt("index");
		tracks=(ArrayList<HashMap<String, String>>) b.getSerializable("list");
		mediaPlayer=new MediaPlayer();
		if(savedInstanceState==null){
			
		}else
		{
			tracks=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
			position=savedInstanceState.getInt("duration");
			index=savedInstanceState.getInt("index");
			try {
				mediaPlayer.seekTo(position);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			surface_view=(SurfaceView) findViewById(R.id.video_surface);
			surface_view.setKeepScreenOn(true);
			holder=surface_view.getHolder();
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			
		}
		surface_view=(SurfaceView) findViewById(R.id.video_surface);
		surface_view.setKeepScreenOn(true);
		holder=surface_view.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		
	}
	
	

	
	
	Runnable r=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			/*progress.setProgress(mediaPlayer.getCurrentPosition());
			seekHandler.postDelayed(this, 100);*/
			updateProgress();
		}
	};

	private void updateProgress()
	{
		//seekHandler.removeCallbacks(r);
		long current_position = 0;
		long total_duartion1=0;
		try
		{
			progress.setProgress(mediaPlayer.getCurrentPosition());
			current_position=mediaPlayer.getCurrentPosition();
			total_duartion1=mediaPlayer.getDuration();
			
		}
		catch(IllegalStateException e)
		{
			
		}
		start_duration.setText(milliSecondsToTimer(current_position));
		total_duration.setText(milliSecondsToTimer(total_duartion1));
		start_duration.setTextColor(Color.YELLOW);
		total_duration.setTextColor(Color.YELLOW);
	//	start_duration.setText(""+mediaPlayer.getCurrentPosition());
		seekHandler.postDelayed(r, 100);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video__player, menu);
		
		return true;
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.subtitle:
			DirectoryChooserDialog directoryChooserDialog = new DirectoryChooserDialog(Video_Player.this,new DirectoryChooserDialog.ChosenDirectoryListener() 
            {
                
				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				@Override
                public void onChosenDir(String chosenDir) 
                {
                    m_chosenDir = chosenDir;
                    Toast.makeText(
                    Video_Player.this, "Chosen directory: " + 
                      chosenDir, Toast.LENGTH_LONG).show();
                   try {
						mediaPlayer.addTimedTextSource(m_chosenDir, MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
						 int textTrackIndex = findTrackIndexFor(
		        					TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
		        			if (textTrackIndex >= 0) {
		        				mediaPlayer.selectTrack(textTrackIndex);
		        			} else {
		        				Log.w("AKSHAY", "Cannot find text track!");
		        			}
		        		mediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
							
							@Override
							public void onTimedText(MediaPlayer mp, final TimedText text) {
								// TODO Auto-generated method stub
								if(text!=null)
								{
									Log.i("AKSHAY",""+text);
									h.post(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											Log.i("AKSHAY","TEXT reading");
											subtitle.setText(text.getText());
											subtitle.setTextColor(Color.YELLOW);
										}
									});
									
								}
								else{
									subtitle.setText("");
								}
							}
						});
		                    
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                                        
                }
                
            }); 
			// Toggle new folder button enabling
            directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
            // Load directory chooser dialog for initial 'm_chosenDir' directory.
            // The registered callback will be called upon final directory selection.
            directoryChooserDialog.chooseDirectory(m_chosenDir);
            m_newFolderEnabled = ! m_newFolderEnabled;
            //mediaPlayer.setOnTimedTextListener(this);
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private int findTrackIndexFor(int mediaTrackType, TrackInfo[] trackInfo) {
		int index = -1;
		for (int i = 0; i < trackInfo.length; i++) {
			Log.d("AKSHAY", "" + trackInfo[i].getTrackType());
			if (trackInfo[i].getTrackType() == mediaTrackType) {
				return i;
			}
		}
		return index;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.play:
				if(mediaPlayer.isPlaying()){
					//startVideoPlayback(index);
					mediaPlayer.pause();
					play.setText("play");
				}
				else{
					if (mediaPlayer != null) {
						// resume the song if paused
						mediaPlayer.start();
						play.setText("pause");

					}
				}
			break;
		default:
			break;
		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		// TODO Auto-generated method stub
		this.holder=surfaceholder;
		if(mediaPlayer!=null)
		{
			mediaPlayer.setDisplay(surfaceholder);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		startVideoPlayback(index);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		seekHandler.removeCallbacks(r);
		mediaPlayer.release();	
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void startVideoPlayback(int index) {
		// TODO Auto-generated method stub
			
		try {
			if(mediaPlayer.isPlaying())
			{
				mediaPlayer.stop();
				mediaPlayer.reset();
				
			}
			else
			{
				mediaPlayer.reset();
				
			}

			String path=tracks.get(index).get("songsPath");
			//mediaPlayer.seekTo(position);
			mediaPlayer.setDisplay(holder);
			
			mediaPlayer.setDataSource(path);
			/*mediaPlayer.addTimedTextSource("", MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
			int textTrackIndex = findTrackIndexFor(
 					TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
 			if (textTrackIndex >= 0) {
 				mediaPlayer.selectTrack(textTrackIndex);
 			} else {
 				Log.w("AKSHAY", "Cannot find text track!");
 			}
			mediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
				
				@Override
				public void onTimedText(MediaPlayer mp, final TimedText text) {
					// TODO Auto-generated method stub
					if(text!=null)
					{
						Log.i("AKSHAY",""+text);
						h.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.i("AKSHAY","TEXT reading");
								subtitle.setText(text.getText());
								subtitle.setTextColor(Color.YELLOW);
							}
						});
						
					}
				}
			});*/
			/*Sample code for manual entry*/
			/*mediaPlayer.addTimedTextSource("/storage/sdcard/sub.srt", MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
			 int textTrackIndex = findTrackIndexFor(
 					TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
 			if (textTrackIndex >= 0) {
 				mediaPlayer.selectTrack(textTrackIndex);
 			} else {
 				Log.w("AKSHAY", "Cannot find text track!");
 			}*/
 			String name=getAutoSub(path);
 			Log.i("AKSHAY", name);
 			/*if(name==null)
 			{
 				Toast.makeText(getApplicationContext(), "No Subtitle Found", Toast.LENGTH_SHORT).show();
 			}*/
			title.setText(tracks.get(index).get("songsTitle"));
			title.setTextColor(Color.YELLOW);
			/**/		
			play.setText("pause");
			//total_duration.setText(""+mediaPlayer.getDuration());
			//progress.setMax(mediaPlayer.getDuration());
			mediaPlayer.prepare();
			mediaPlayer.setOnErrorListener(this);
			mediaPlayer.setOnPreparedListener(this);
			/*mediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
				
				@Override
				public void onTimedText(MediaPlayer mp, final TimedText text) {
					// TODO Auto-generated method stub
					Log.i("AKSHAY", "Timed Text Listener");
					if(text!=null)
					{
						Log.i("AKSHAY",""+text);
						h.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								Log.i("AKSHAY","TEXT reading");
								subtitle.setText(text.getText());
							}
						});
						
					}
				}
			});*/
			mediaPlayer.setOnCompletionListener(this);
			//play.setEnabled(false);
			//mediaPlayer.start();
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
		
		//play.setVisibility(View.GONE);
		
		//
		
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onPrepared(MediaPlayer arg0) {progress.setMax(mediaPlayer.getDuration());
		// TODO Auto-generated method stub
		progress.setMax(mediaPlayer.getDuration());
		mediaPlayer.start();
		updateProgress();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(new Intent(Video_Player.this, MediaService.class));
		//mediaPlayer.release();
	}

	@Override
	public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		
		if(fromUser)
		{
			mediaPlayer.seekTo(progress);
		}
		if(mediaPlayer==null)
		{
			sb.setProgress(0);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		progress.setProgress(0);
		
		if (index < (tracks.size() - 1)) {
			startVideoPlayback(index + 1);
			index += 1;

		} else {
			// Limit Exceeded play the First song
			startVideoPlayback(0);
			index = 0;
		}

	}
	
	
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putSerializable("list", tracks);
		outState.putInt("duration", mediaPlayer.getCurrentPosition());
		outState.putInt("index", index);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		tracks=(ArrayList<HashMap<String, String>>) savedInstanceState.getSerializable("list");
		position=savedInstanceState.getInt("duration");
		index=savedInstanceState.getInt("index");
	}
	
	public String milliSecondsToTimer(long milliseconds){
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		//seekHandler.removeCallbacks(r);
		//mediaPlayer.release();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public String getAutoSub(String src)
	{
		File file=new File(src);
		Log.i("src",file.getPath());
		/*Code for getting the power directory eg:/home/akshay/*/
		String absolutePath=file.getPath();
		String filePath=absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
		File newFilePath=new File(filePath);
		String s[]=newFilePath.list();
		/*End of that code*/
		String name=absolutePath.substring(0,absolutePath.lastIndexOf("."))+".srt"; 
		Log.i("AKSHAY srt", name);
		for(int i=0;i<s.length;i++)
		{
			try {
				File child=new File(newFilePath+"/"+s[i]);
				if(child.isFile()&&s[i].endsWith(".srt"))
				{
					if(name.equals(child.getPath()))
					{
						Log.i("AKSHAY", name);
						Log.i("AKSHAY",child.getPath());
						mediaPlayer.addTimedTextSource(child.getPath(), MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
						int textTrackIndex = findTrackIndexFor(
	        					TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo());
	        			if (textTrackIndex >= 0) {
	        				mediaPlayer.selectTrack(textTrackIndex);
	        			} else {
	        				Log.w("AKSHAY", "Cannot find text track!");
	        			}
	        		
						mediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
							
							@Override
							public void onTimedText(MediaPlayer mp, final TimedText text) {
								// TODO Auto-generated method stub
								if(text!=null)
								{
									Log.i("AKSHAY",""+text);
									h.post(new Runnable() {
										
										@Override
										public void run() {
											// TODO Auto-generated method stub
											Log.i("AKSHAY","TEXT reading");
											subtitle.setText(text.getText());
											subtitle.setTextColor(Color.YELLOW);
										}
									});
									
								}
								else{
									subtitle.setText("");
								}
							}
						});
						
					}
					else
					{
						
					}
				}
				
			} catch (Exception e) {
				// TODO: handlmsge exception
			}
		}
		
		return name;
	}

	@Override
	public void onTimedText(MediaPlayer mp, final TimedText text) {
		// TODO Auto-generated method stub
		if(text!=null)
		{
			Log.i("AKSHAY",""+text);
			h.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.i("AKSHAY","TEXT reading");
					subtitle.setText(text.getText());
					subtitle.setTextColor(Color.YELLOW);
				}
			});
			
		}
		else{
			subtitle.setText("");
		}
	
	}
	
	
}
