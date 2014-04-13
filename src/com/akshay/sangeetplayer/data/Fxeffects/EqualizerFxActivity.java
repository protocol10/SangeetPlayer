package com.akshay.sangeetplayer.data.Fxeffects;

import java.util.ArrayList;
import java.util.List;

import com.akshay.sangeetplayer.R;
import com.akshay.sangeetplayer.service.IMedia;
import com.akshay.sangeetplayer.service.MediaService;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;


public class EqualizerFxActivity extends Activity implements  OnItemSelectedListener {

	int presetIndex;
	IMedia mservice=null;
	List<String> preset;
	Spinner presets_view;
	SeekBar band1,band2,band3,band4,band5;
	TextView tv1,tv2,tv3,tv4,tv5;
	boolean isbound=false;
	int a,b,c,d,e,index1=15;
	Intent i;
	int avalue,bvalue,cvalue,dvalue,evalue;
	ArrayAdapter<String> presets_item;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equalizer);
		init();
		addPresets();
		initService();
		presets_item=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, preset);
		presets_view.setAdapter(presets_item);
		presets_view.setOnItemSelectedListener(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(isbound)
		{
			unbindService(conn);
			isbound=false;
		}
	}
	private void initService() {
		// TODO Auto-generated method stub
		if(mservice==null)
		{
			i=new Intent(this,MediaService.class);
			bindService(i, conn, BIND_AUTO_CREATE);
		}
	}
	ServiceConnection conn=new ServiceConnection() {
		
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
			try {
				mservice.setPreset(0);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	private void addPresets() {
		// TODO Auto-generated method stub
		preset.add(0, "Normal");
		preset.add(1,"Classical");
		preset.add(2,"Dance");
		preset.add(3,"Flat");
		preset.add(4,"Folk");
		preset.add(5,"HeavyMetal");
		preset.add(6,"HipHop");
		preset.add(7,"Jazz");
		preset.add(8,"Pop");
		preset.add(9,"Rock");
		
	}

	protected void setPreset(int j) {
		// TODO Auto-generated method stub
		switch (j) {
		case 0:
			try {
				mservice.setPreset(0);
				a=18;
				b=15;
				c=15;
				d=15;
				e=18;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				band5.setProgress(e);
				tv5.setText(e-index1+"db");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				mservice.setPreset(1);
				a=20;
				b=18;
				c=13;
				d=19;
				e=19;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				band5.setProgress(e);
				tv5.setText(e-index1+"db");
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				a=21;
				b=15;
				c=17;
				d=19;
				e=16;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");
				mservice.setPreset(2);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				a=15;
				b=15;
				c=15;
				d=15;
				e=15;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				
				mservice.setPreset(3);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			try {
				a=18;
				b=15;
				c=15;
				d=17;
				e=14;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				
				mservice.setPreset(4);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				a=19;
				b=16;
				c=24;
				d=18;
				e=15;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				mservice.setPreset(5);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 6:
			try {
				
				a=20;
				b=18;
				c=15;
				d=16;
				e=18;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				mservice.setPreset(6);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 7:
			try {
				a=19;
				b=17;
				c=13;
				d=17;
				e=15;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				mservice.setPreset(7);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 8:
			try {
				a=16;
				b=17;
				c=20;
				d=16;
				e=13;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				
				mservice.setPreset(8);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 9:
			try {
				a=20;
				b=18;
				c=14;
				d=18;
				e=20;
				band1.setProgress(a);
				tv1.setText(a-index1+"db");
				band2.setProgress(b);
				tv2.setText(b-index1+"db");
				band3.setProgress(c);
				tv3.setText(c-index1+"db");
				band4.setProgress(d);
				tv4.setText(d-index1+"db");
				
				band5.setProgress(e);
				tv5.setText(e-index1+"db");

				
				mservice.setPreset(9);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			
			break;
		}

	}

	private void init() {
		// TODO Auto-generated method stub
		presets_view=(Spinner) findViewById(R.id.Equalizer_presets);
		band1=(SeekBar) findViewById(R.id.preset_band1);
		band2=(SeekBar) findViewById(R.id.preset_band2);
		band3=(SeekBar) findViewById(R.id.preset_band3);
		band4=(SeekBar) findViewById(R.id.preset_band4);
		band5=(SeekBar) findViewById(R.id.preset_band5);
		preset=new ArrayList<String>();
		
		tv1=(TextView) findViewById(R.id.Band1_text);
		tv2=(TextView) findViewById(R.id.Band2s_tex);
		tv3=(TextView) findViewById(R.id.Band3_text);
		tv4=(TextView) findViewById(R.id.Band4_text);
		tv5=(TextView) findViewById(R.id.Band5_text);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.equalizer_fx, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int index,
			long id) {
		// TODO Auto-generated method stub
		setPreset(index);
			}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		setPreset(0);
	}

	

		

}