package com.example.serviceproject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private final String URL = "http://10.0.2.1/wifeeye/discovery.php?q=wife";
    private ArrayList<Service> services;
    
    private ListView list;
    ServiceAdapter service;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTaskRunner().execute(URL);
        
        

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
