package com.akshay.sangeetplayer;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchDemo extends Activity implements TextWatcher {

	
	ArrayList<HashMap<String, String>> songs_data;
	private ListView songs_list;
	EditText search_text;
	SimpleAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_demo);
		songs_list=(ListView) findViewById(R.id.searchList);
		search_text=(EditText) findViewById(R.id.search_text);
		search_text.addTextChangedListener(this);
		songs_data=new ArrayList<HashMap<String,String>>();
		String[] from={"songsTitle"};
		int[] to={R.id.tracks_name};
		
		adapter=new SimpleAdapter(this, songs_data, android.R.layout.simple_list_item_1, from, to);
		songs_list.setAdapter(adapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_demo, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

}
