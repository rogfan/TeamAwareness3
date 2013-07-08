package com.example.myandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	int viewChanger = 0;
	TextView textView;
	Button textButton;
	Button editButton;
	TextView color;
	TextView statusMessage;
	TextView time;
	TextView nameOver;
	DummyProfile dp = new DummyProfile();
	
	
	Button switcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	
		
		Bundle extras = getIntent().getExtras();

		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);	

		color = (TextView)findViewById(R.id.StatusColor);
		statusMessage = (TextView)findViewById(R.id.StatusMessage);
		time = (TextView)findViewById(R.id.TimeVal);
		switcher=(Button)findViewById(R.id.switcher);
		switcher.setOnClickListener((android.view.View.OnClickListener) this);

		
		nameOver = (TextView)findViewById(R.id.overView);
		textView = (TextView)findViewById(R.id.httpText);
		
		if (extras != null) {

		    this.nameOver.setText(extras.getString("name"));
		    this.time.setText(extras.getString("time"));
		    this.statusMessage.setText(extras.getString("statusMessage"));
		    this.textView.setText(extras.getString("annotation"));
		    color.setBackgroundColor(Color.parseColor(extras.getString("color")));
		
		}
		
	
	}

	public void onClick(View v)
	{
		if(v==switcher)
		{
			//dp.loadADummy(this);
            Intent myIntent = new Intent(v.getContext(), CalendarEntriesActivity.class);
            startActivityForResult(myIntent, 0);
            this.finish();
            return;
		}
	}	
}
