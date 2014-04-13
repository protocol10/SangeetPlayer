package com.akshay.sangeetplayer.data.Fxeffects;

import java.util.ArrayList;
import java.util.List;

public class Presets {

	List<String> presetlist=new ArrayList<String>();
	
	public List<String> getPreset()
	{
		
		presetlist.add("Normal");
		return presetlist;
	}
}
