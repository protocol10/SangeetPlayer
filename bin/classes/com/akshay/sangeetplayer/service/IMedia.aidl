package com.akshay.sangeetplayer.service;

interface IMedia{

	void playSong(in int index);
	
	void addList(in String name,in String path,in String album_name,in String artist_name);
	void previous();
	void next();
	void clearList();
	void setPreset(in int pindex);
	String getArtistName();
	String getTrackName();
	String getAlbumName();
	void pause();
	int getDuration();
	int currentPosition();
	void stop();
	void name();
	void playStream(in String stream);
	
}