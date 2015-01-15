package com.anurag.zillow_search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.anurag.zillow_search.R.color;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint.Join;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends Activity {

	static boolean chk = false;
	static String zillow_addr;
	static String zillow_city;
	static String zillow_state;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //final Button btn = (Button)findViewById(R.id.btn);
       final EditText address = (EditText)findViewById(R.id.addr_val);
       final EditText city = (EditText)findViewById(R.id.city_val);
       final Spinner state = (Spinner)findViewById(R.id.spinner1);
       final TextView addr_err = (TextView)findViewById(R.id.addr_err);
       final TextView city_err = (TextView)findViewById(R.id.city_err);
       final TextView state_err = (TextView)findViewById(R.id.stateErr);
       final TextView zerror1 = (TextView)findViewById(R.id.zerror1);
       final TextView zerror2 = (TextView)findViewById(R.id.zerror2);
       final ImageView searchIcon = (ImageView)findViewById(R.id.search);
       
       zerror1.setTextColor(Color.parseColor("#00000000"));
       zerror2.setTextColor(Color.parseColor("#00000000"));
       
       address.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
			//Toast.makeText(getApplicationContext(), "Text"+address.getText(), Toast.LENGTH_SHORT).show();
			if(s.toString().trim().equals("")){
				addr_err.setTextColor(Color.parseColor("#FF0000"));
			}else if(!s.toString().trim().equals("")){
				addr_err.setTextColor(Color.parseColor("#00000000"));
			}
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	});
       
       
       city.addTextChangedListener(new TextWatcher() {
   		
   		@Override
   		public void onTextChanged(CharSequence s, int start, int before, int count) {
   			// TODO Auto-generated method stub
   			
   			//Toast.makeText(getApplicationContext(), "Text"+city.getText(), Toast.LENGTH_SHORT).show();
   			if(s.toString().trim().equals("")){
   				city_err.setTextColor(Color.parseColor("#FF0000"));
   			}else{
   				city_err.setTextColor(Color.parseColor("#00000000"));
   			}
   		}
   		
   		@Override
   		public void beforeTextChanged(CharSequence s, int start, int count,
   				int after) {
   			// TODO Auto-generated method stub
   			
   		}
   		
   		@Override
   		public void afterTextChanged(Editable s) {
   			// TODO Auto-generated method stub
   			
   		}
   	});
       
       
       state.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
			if(chk && arg0.getItemAtPosition(arg2).toString().equals("Select State")){
				state_err.setTextColor(Color.parseColor("#FF0000"));
			}else if(! (chk && arg0.getItemAtPosition(arg2).toString().equals("Select State"))){
				state_err.setTextColor(Color.parseColor("#00000000"));
			}
			chk = true;
			//state_err.setTextColor(Color.parseColor("#FF0000"));
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
       
       
        searchIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				zerror1.setTextColor(Color.parseColor("#00000000"));
			    zerror2.setTextColor(Color.parseColor("#00000000"));
				Boolean flag = true;
				zillow_addr = address.getText().toString();
				zillow_city = city.getText().toString();
				zillow_state = state.getItemAtPosition(state.getSelectedItemPosition()).toString();
				//Toast.makeText(getApplicationContext(), zillow_addr+zillow_city+zillow_state, Toast.LENGTH_LONG).show();
				if(zillow_addr.trim().equals("")){
					addr_err.setTextColor(Color.parseColor("#FF0000"));
					flag = false;
				}
				if(zillow_city.trim().equals("")){
					city_err.setTextColor(Color.parseColor("#FF0000"));
					flag = false;
				}
				if(zillow_state.equals("Select State")){
					state_err.setTextColor(Color.parseColor("#FF0000"));
					flag = false;
				}
				
				if(flag){
					String addr_parts[] = zillow_addr.split(" ");
					zillow_addr="";
					for(int i=0;i<addr_parts.length;i++){
						zillow_addr += addr_parts[i];
						if(i!=addr_parts.length-1)
							zillow_addr += "+";
					}
					
					String city_parts[] = zillow_city.split(" ");
					zillow_city="";
					for(int i=0;i<city_parts.length;i++){
						zillow_city += city_parts[i];
						if(i!=city_parts.length-1)
							zillow_city += "+";
					}
					
					Toast.makeText(getApplicationContext(), "Getting details from Zillow", Toast.LENGTH_SHORT).show();
					
					String msg = findZillowDetails(zillow_addr, zillow_city, zillow_state);
					
					if(msg.equals("error")){
						zerror1.setTextColor(Color.parseColor("#FFFFFF"));
					    zerror2.setTextColor(Color.parseColor("#FFFFFF"));
					}
					//Toast.makeText(getApplicationContext(), "Heyasss" + "  city: "+zillow_city, Toast.LENGTH_LONG).show();
				}	
			}
		});
        
        
        
    }
    
    public String findZillowDetails(String addr, String city, String state){
    	//Toast.makeText(getApplicationContext(), "In start of func", Toast.LENGTH_LONG).show();
    	GetZillowDetails getDetails = new GetZillowDetails();
    	String msg = "error";
    	//Toast.makeText(getApplicationContext(), "one", Toast.LENGTH_LONG).show();
    	Log.d("One", "******************one");
    	try {
			String zillow_obj = getDetails.execute(getApplicationContext()).get();
		//	Toast.makeText(getApplicationContext(), "two", Toast.LENGTH_LONG).show();
			Log.d("One", "***************two");
			JSONObject jsonObj = new JSONObject(zillow_obj);
			Log.d("One", "**************************three");
			if(jsonObj.getString("message").equals("ERROR")){
				msg = "error";
			}else{
				msg = "ok";
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), TabbedActivity.class);
				intent.putExtra("zillow_object", zillow_obj);
				startActivity(intent);	
			}
		//	Toast.makeText(getApplicationContext(), "four", Toast.LENGTH_LONG).show();
			Log.d("One", "*****************************four");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("One", "*****************************ERRRRRRRRRR1");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("One", "*****************************ERRRRRRRRR2");
		} 
    	Log.d("One", "*****************************five");
    	return msg;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
}

  class GetZillowDetails extends AsyncTask<Context, String, String>{

	Context ctx;
	String jsonObj = "empty";
	Activity activity;
	@Override
	protected String doInBackground(Context... arg0){
		// TODO Auto-generated method stub
		Log.d("One", "*****************************async 1");
		ctx = arg0[0];
		try{
			String url = "http://cs-server.usc.edu:24106/assign8/zillow.php?addr="+SearchActivity.zillow_addr+
					"&city="+SearchActivity.zillow_city+"&state="+SearchActivity.zillow_state;
			Log.d("One", "*****************************async 2");
			HttpClient http = new DefaultHttpClient();
			HttpGet http_get = new HttpGet(url);
			HttpResponse http_resp = http.execute(http_get);
			HttpEntity http_ent = http_resp.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(http_ent.getContent()));
			jsonObj = br.readLine();
			Log.d("One", "*****************************async 3");
			
		}catch(Exception e){
			jsonObj = e.getMessage();
			Log.d("One", "*****************************async ERRRRRR");
		}
		//publishProgress(jsonObj);
		Log.d("One", "*****************************async 4");
		return jsonObj;
	}
}
