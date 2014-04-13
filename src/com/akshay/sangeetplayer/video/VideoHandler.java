package com.akshay.sangeetplayer.video;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class VideoHandler extends Thread{

	Handler h;
	String name;
	String track_name;
	String src;
	ArrayList<HashMap<String, String>> tracks_data=new ArrayList<HashMap<String,String>>();
	HashMap<String, String> song_map;
	String path=Environment.getExternalStorageDirectory().getPath();
	MediaMetadataRetriever mretriver=new MediaMetadataRetriever();
	public void retrivePath(String dir)
	{
		File dir1=new File(dir);
		String s[]=dir1.list();
		for(int i=0;i<s.length;i++)
		{
			try {
				File child=new File(dir1+"/"+s[i]);
				
				if(child.isDirectory())
				{
					retrivePath(dir1+"/"+s[i]);
				}
				else if((child.isFile())&&(s[i].endsWith(".mp4")||(s[i].endsWith(".webm")||(s[i].endsWith(".flv")))))
				{
					song_map=new HashMap<String, String>();
					name=child.getName();
					src=child.getPath();
					mretriver.setDataSource(src);
					track_name=mretriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
					if(track_name!=null)
					{
						name=track_name;
					}
					song_map.put("songsTitle", name);
					song_map.put("songsPath", src);
					Log.d("Name", name);
					Log.d("Path",src);
					tracks_data.add(song_map);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			retrivePath(path);
			for(int i=0;i<tracks_data.size();i++)
			{
				Message m=new Message();
				try {
					Thread.sleep(110);
				} catch (Exception e) {
					// TODO: handle exception
				}
				m.obj=tracks_data.get(i);
				h.sendMessage(m);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}


	public Handler getHandler() {
		return h;
	}


	public void setHandler(Handler h) {
		this.h = h;
	}

	
}
