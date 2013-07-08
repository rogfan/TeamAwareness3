package com.example.myandroidapp;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetEventsTask extends AsyncTask<String, Void, Void> 
{
	private String response = "NONIX";
	private CalendarEntriesActivity mainAct;
	private JSONObject jsonObj;
	private String name;
	

	 JSONArray creds = null;
	public GetEventsTask(CalendarEntriesActivity ma)
	{
		 mainAct = ma;
	}
	 @Override
    protected Void doInBackground(String... params) 
    {
		
		 response = params[0];
    	// Get Token
    	Log.v("GetEventsTask: ", params[0]);
    
    	
    	// Make Connection
		DefaultHttpClient httpclient = new DefaultHttpClient();
		 httpclient.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS,false);
		 HttpGet httpget = new HttpGet("https://www.googleapis.com/calendar/v3/calendars/teamawan@gmail.com/events?key=AIzaSyD_cCVHHHernTGnbrj-I68Zf4tUHCrlnlc");
		//	httpget.addHeader("Authorization", "Bearer " + accessToken);
	
        // Execute Request
       HttpResponse postResponse = null;
       boolean noConnection = false;
/**
 * Maybe a loop that waits until the postResponse-Object receives correct data.
 */
		try {
			postResponse = httpclient.execute(httpget);
			// MainActivity.log.setText(params[0]);
			
		} catch (ClientProtocolException e) {
			Toast.makeText(this.mainAct, "ClientProtocolExc", Toast.LENGTH_LONG).show();
			Log.v("ClientProtocolException: ", "ClientProtocolException");
		} catch (IOException e) {
			Log.v("IOException by httpGet", "IOException by httpGet");
			noConnection = true;
	
		}
///////////////////////////////////////POSTRESPONSE INITIALIZATION END////////////////////////////////////////////////		
    
        if(!noConnection)
        {
        	Log.v("Init HTTP entitiy", "Init HTTP entitiy");
       HttpEntity entity = postResponse.getEntity();
        response = "no response";
        
        Log.v("HTTPENTITY INITED","HTTPENTITY INITED");
        

        	try {
				response = EntityUtils.toString(entity);
				Log.v("GetEventsTask: ", "Passing json to CalendarEntriesActivity");
				mainAct.parseJsonForEntries(response);
				
			} catch (ParseException e) {
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
		return null;
    }

	 
	 
	 
    
    @Override
    protected void onPostExecute(Void result) 
    {
  // 	 MainActivity.log.setText(response);
	 
       // Log.v("Antwort vom Server: ", response);
    }

}