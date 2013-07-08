package com.example.myandroidapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CalendarInputActivity extends Activity implements OnClickListener {
	private Button back;
	private Button submitCredentials;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setContentView(R.layout.calendarinput);	
	    
	}
	@Override
	public void onClick(View v) {
		
		if(v==submitCredentials)
		{
			
		}
		
		if(v==back)
		{
            Intent myIntent = new Intent(v.getContext(), CalendarEntriesActivity.class);
            startActivityForResult(myIntent, 0);
			return;
		}
		
	}
	
}
