package com.example.serviceproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

    private final String URL = "http://192.168.173.1/wifeeye/discovery.php?q=wife";
    private ArrayList<Service> services;
    
    private ListView listv;
    private MainActivity mainRef;
    ServiceAdapter serviceAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        
        services = new ArrayList<Service>();
        mainRef = this;
        new AsyncTaskRunner().execute(URL);
        
        listv = (ListView) findViewById(R.id.list);
        
        
  	    serviceAdapter = new ServiceAdapter(mainRef, services);
        
        listv.setAdapter(serviceAdapter);
        
        listv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Toast.makeText(mainRef, "Smoking", Toast.LENGTH_LONG).show();
				
				
				Service s = services.get(position);
				
				Uri uri = Uri.parse(s.getAddress());
				
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				Intent browserChooserIntent = Intent.createChooser(intent , "Choose VLC PLEASE");
				 
				PackageManager packageManager = getPackageManager();
				List<ResolveInfo> activities = packageManager.queryIntentActivities(browserChooserIntent,PackageManager.MATCH_DEFAULT_ONLY);
				boolean isIntentSafe = activities.size() > 0;
				
				if ( isIntentSafe ){
					Log.e("OK? ",  s.getAddress());
					startActivity(browserChooserIntent);
				}
				else{
					Log.e("WOW ",  s.getAddress());
				}
			}
		});
       

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onItemClick(int mPosition) {
        //add click operation

    }


    private class AsyncTaskRunner extends AsyncTask<String, Void, JSONArray> {

    	 // private String resp;

    	  @Override
    	  protected JSONArray doInBackground(String... params) {

	            URL url = null;
				try {
					url = new URL(params[0]);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

              return loadJSON(url);
    	  }

          @Override
          protected void onPostExecute(JSONArray jsonData) {
              
        	  int lenght = jsonData.length();
        	  
        	  
        	  for ( int i = 0; i < lenght; i++ ){
        		  
        		  try {
        			  
					JSONObject jService = jsonData.getJSONObject(i);
					Service service = new Service(
							 jService.getString("serviceName"),
							 jService.getString("description"),
							 jService.getString("location"),
							 jService.getString("locationType")
							);
					services.add(service);
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	  }
        	 
              
        	  
          }


        public JSONArray loadJSON(URL url) {

            HttpURLConnection con = null;
            BufferedReader in = null;
            

			try {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				System.out.println("HAbeeb Error1");
				int responseCode = con.getResponseCode();
				System.out.println("HAbeeb Erro2");
				 System.out.println("\nSending 'GET' request to URL : " + url);
		         System.out.println("Response Code : " + responseCode);
		            
		            
		         in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		         String inputLine;
		         StringBuffer response = new StringBuffer();

		         while ((inputLine = in.readLine()) != null) {
		              response.append(inputLine);
		         }
		         in.close();
		       //print result
		            System.out.println(response.toString());
		         return new JSONArray(response.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("HAbeeb Error");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
           
            
        }

    }
}
