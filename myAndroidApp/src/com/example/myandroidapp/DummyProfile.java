package com.example.myandroidapp;

import android.graphics.Color;

public class DummyProfile { 
private int viewDummy=0;
	public void loadADummy(MainActivity a)
	{
		String colorVal;
		String message;
		String statusMsg;
		String timeVal;
		
		switch(viewDummy)
		{
		case 0:
			colorVal="#ff0000";
			message="Ich bin ganztags unterwegs";
			statusMsg = "nicht verfügbar";
			timeVal = "08:00 - 17:00";
			break;
			
		case 1:
			colorVal="#ffaa00";
			message="Ich bin halbtags da. Ab 13:30 bin ich beim Kunden";
			statusMsg = "teilweise verfügbar";
			timeVal = "08:00 - 13:30";
			break;
			
		default:
			colorVal="#00ff00";
				message="Bin heute ganztags da";
				statusMsg = "voll verfügbar";
				timeVal = "08:00 - 17:00";
				break;
		
		}
		viewDummy++;
		a.textView.setText(message);
		a.color.setBackgroundColor(Color.parseColor(colorVal));
		a.statusMessage.setText(statusMsg);
		a.time.setText(timeVal);
		if(viewDummy == 3)
		{
			viewDummy=0;
		}	
	}

}
