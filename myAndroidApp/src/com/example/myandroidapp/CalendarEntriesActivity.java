package com.example.myandroidapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CalendarEntriesActivity extends Activity implements OnClickListener {
	private Button back;
	private static CalendarEntriesActivity entries;
	ListView workers;
	String[] values = new String[0];
	 Employee[] employees = new Employee[0];
	 private boolean fetchedFiles = false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    setContentView(R.layout.activity2layout);	
	    workers = (ListView)findViewById(R.id.listOfWorkers);
	    entries =  CalendarEntriesActivity.this;
	    int timeOut = 0;
	    boolean breakFetch = false;
	    GetEventsTask getEvents = new GetEventsTask(CalendarEntriesActivity.this);
	    getEvents.execute("Start task getEvents");
	    
	    while(!fetchedFiles)
	    {
	    	try {
				Thread.sleep(500);
				timeOut++;
				if(timeOut == 5)
				{
					breakFetch = true;
					Log.v("BROKE FETCH", "BROKE FETCH");
					break;
				}
			}
	    	catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//Log.v("CALENDARENTRIES WAITING", "WAITING FOR SIZE");
	    
	    }
	    
	    
	  //  Log.v("CALENDARENTRIES SIZE: ", "SIZE IS: "+this.employees.length);
	    
	    
	    if(!breakFetch)
	    {
	    	final String[] values = this.values; 
	    	final Employee[] employees = this.employees;
	

	    
	 //   if(values.length>0 && employees.length > 0)
	    {
	        final ArrayList<String> list = new ArrayList<String>();
	        for (int i = 0; i < employees.length; ++i) {
	          list.add(values[i]);
	    
	        }
	      
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	
	    
	    workers.setAdapter(adapter);

	     ////////////////////////////////////////////
	     
	     workers.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	         @Override
	         public void onItemClick(AdapterView<?> parent, final View view,
	             int position, long id) {
	           final String selected = (String) parent.getItemAtPosition(position);
	   
	           Employee item = null;
	           
	           for(int i=0; i<employees.length;i++)
	           {
	        	   if(employees[i].name.equals(selected.substring(0,selected.indexOf(':'))))
	        	   {
	        		   item = employees[i];
	        		   break;
	        	   }
	           }
	           
	           
	            Intent myIntent = new Intent(parent.getContext(), MainActivity.class);
	            if(item != null)
	            {
		            myIntent.putExtra("name",item.name);
		            myIntent.putExtra("annotation", item.annotation);
		            myIntent.putExtra("time",item.time);
		            myIntent.putExtra("statusMessage", item.statusMessage);
		            myIntent.putExtra("color",item.colorCode);
	            }
	            
	            startActivityForResult(myIntent, 0);
	            CalendarEntriesActivity.entries.finish();
	            return;

	         }

	       });
	     
	    }
	    
	}
	    
	   if(breakFetch)
	   {
		   Toast.makeText(this, "Cannot contact Calendar -> Closing App", Toast.LENGTH_LONG).show();
		   try {
			Thread.sleep(Toast.LENGTH_LONG+2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   this.finish();
		   
		  
	   }
	    	
	     ////////////////////////////////////////////////
	
	}

	@Override
	public void onClick(View v) {
		if(v == back)
		{
            Intent myIntent = new Intent(v.getContext(), MainActivity.class);
            myIntent.putExtra("nameValue","HEROLD");
            startActivityForResult(myIntent,0);
            this.finish();
            return;
		}			
	}
	
	public void parseJsonForEntries(String jsonString)
	{
		
		JSONObject jsonObj;
		JSONArray jsonArray;

	       try {
	           jsonObj = new JSONObject(jsonString);
	           jsonArray = jsonObj.getJSONArray("items");
	           
	           //System.out.println(jsonArray.length());
	           values = new String[jsonArray.length()];
	           employees = new Employee[jsonArray.length()];
	           for(int i=0; i<jsonArray.length();i++)
	           {
	        	   JSONObject picker = jsonArray.getJSONObject(i);
	        	   String name = picker.getString("summary");
	        
	        	   String availability = name.substring(2+name.indexOf(": "),name.length()).toLowerCase();
	        	   name = name.substring(0, name.indexOf(':'));
	        	   
	        	   String message = "";
	        	   if(!picker.isNull("description"))
	        		  message = picker.getString("description");
	        	 
	        	  
	        	   String color = "#ffff00";
	        	   Log.v("Name: "+name, availability);
	        	   if(availability.equals("teils anwesend"))
	        	   {
	        		   Log.v(name+" ist TEILWEISE DA: ", "YELLOW");
	        		   color = "#ffff00";
	        	   }
	        	   
	        	
	        		   
        		   if(availability.equals("nicht anwesend"))  
	        	   {
	        		   Log.v(name+" ist GAR NED DA: ", "REEEDDD");
	        		   color = "#ff0000";
	        	   }
	        	   
	        	  if(availability.equals("anwesend"))
	        	   {
	        		   Log.v(name+" ist VOLL DA: ", "GREEEN");
	         		   color = "#00ff00";
	        		  
	        	   }
	        	   
	        	   String timeBegin = picker.getJSONObject("start").getString("dateTime");
	        	   String timeEnd = picker.getJSONObject("end").getString("dateTime");
	        	   
		        	String date = timeBegin.substring(8,10)+"."+timeBegin.substring(5,7)+"."+timeBegin.substring(0,4);

	        	  // System.out.println(timeBegin.substring(1+timeBegin.indexOf('T')).substring(0,5));
	        	   timeBegin = timeBegin.substring(1+timeBegin.indexOf('T')).substring(0,5);
	        	   timeEnd = timeEnd.substring(1+timeEnd.indexOf('T')).substring(0,5);
	        	   
		
	        	
	        	   employees[i]=new Employee(name, availability, message,timeBegin+"-"+timeEnd+" "+date, color);
	        	   values[i] = name+": "+date;
	        	   
	           }
	  
	         this.fetchedFiles = true;
	           
	       }
	       
	       catch (JSONException e) {
	          e.printStackTrace();
	       }
		
		
		
	}
}
