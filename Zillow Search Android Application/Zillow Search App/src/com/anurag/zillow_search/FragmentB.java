package com.anurag.zillow_search;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FragmentB extends Fragment{

	
	View view = null;
	ImageView graph = null;
	Bitmap img1 = null;
	static Bitmap one_yr;
	static Bitmap five_yr;
	static Bitmap ten_yr;
	static Bitmap []zillowGraph;
	static int graphIndex = 0;
	JSONObject jsonObj;
	static String zillowId = "";
	static String url_one;
	static String url_five;
	static String url_ten;
	Button btnPrev;
	Button btnNext;
	ImageView prev;
	ImageView next;
	TextView graphDesc1;
	TextView graphDesc2;
	Boolean imgAvail = false;
	TextView footer1;
	TextView footer2;
	static String fullAddr = "";
	public FragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_b, container, false);
		//btnNext = (Button)view.findViewById(R.id.btnNext);
		//btnPrev= (Button)view.findViewById(R.id.btnPrev);
		prev = (ImageView)view.findViewById(R.id.prevb);
		next = (ImageView)view.findViewById(R.id.next);
		graphDesc1 = (TextView)view.findViewById(R.id.graphDesc1);
		graphDesc2 = (TextView)view.findViewById(R.id.graphDesc2);
		zillowGraph = new Bitmap[3];
		graph = (ImageView) view.findViewById(R.id.graph);
		prev.setOnClickListener(new OnClickListener() {
		 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(graphIndex == 2 ){
					//Toast.makeText(getActivity(), "Prevclicked !!!", Toast.LENGTH_SHORT).show();
						graphIndex--;
						graph.setImageBitmap(five_yr);
						graphDesc1.setText("Historical Zestimates for past 5 years");
				}else if(graphIndex == 1 ){
					//Toast.makeText(getActivity(), "Prevclicked !!!", Toast.LENGTH_SHORT).show();
					graphIndex--;
					graph.setImageBitmap(one_yr);
					graphDesc1.setText("Historical Zestimates for past 1 year");
					
			}
			}
		});
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "Nextclicked !!!", Toast.LENGTH_SHORT).show();
				graphDesc2.setText(fullAddr);
				if(graphIndex == 0 ){
					//Toast.makeText(getActivity(), "Prevclicked !!!", Toast.LENGTH_SHORT).show();
						graphIndex++;
						graph.setImageBitmap(five_yr);
						graphDesc1.setText("Historical Zestimates for past 5 years");
				}else if(graphIndex == 1 ){
					//Toast.makeText(getActivity(), "Prevclicked !!!", Toast.LENGTH_SHORT).show();
					graphIndex++;
					graph.setImageBitmap(ten_yr);
					graphDesc1.setText("Historical Zestimates for past 10 years");
				
			}
		}
		});
		//graph = (ImageView)view.findViewById(R.id.graph);
		//img1 = null;
		
			
			//url = 	"http://api.androidhive.info/images/sample.jpg";
			
			
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		footer1 = (TextView)view.findViewById(R.id.footer1);
		footer1.setText(Html.fromHtml(
	            "Use is subject to " +
	            "<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of use</a>"));
		footer1.setMovementMethod(LinkMovementMethod.getInstance());
		
		footer2 = (TextView)view.findViewById(R.id.footer2);
		footer2.setText(Html.fromHtml(
	            "<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate?</a>"));
		footer2.setMovementMethod(LinkMovementMethod.getInstance());
		String zillow_details = getActivity().getIntent().getExtras().getString("zillow_object");
		try {
			jsonObj = new JSONObject(zillow_details);
			fullAddr = 	jsonObj.getString("street")+
					jsonObj.getString("city")+
					jsonObj.getString("state")+
					jsonObj.getString("zipcode");
			graphDesc2.setText(fullAddr);
			zillowId = jsonObj.getString("one_year");
			if(zillowId.equals("")){
				
				
				one_yr = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.notavailable);	
				five_yr = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.notavailable);
				ten_yr = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.notavailable);				
				graph.setImageBitmap(one_yr);			
				graphDesc1.setText("Historical Zestimates for past 1 year");
				
			}else{
				zillowId=zillowId.split("zpid=")[1];
				url_one = "http://www.zillow.com/app?chartDuration=1year&chartType=partner&height=300&" +
						"page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillowId;
				
				//Toast.makeText(this.getActivity(), url_one,Toast.LENGTH_LONG) .show();
				url_five = "http://www.zillow.com/app?chartDuration=5years&chartType=partner&height=300&" +
						"page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillowId;
				//Toast.makeText(this.getActivity(), url_five,Toast.LENGTH_LONG) .show();
				url_ten = "http://www.zillow.com/app?chartDuration=10years&chartType=partner&height=300&" +
						"page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillowId;
				//Toast.makeText(this.getActivity(), url_ten,Toast.LENGTH_LONG) .show();
				
				
					new GetZillowImage().execute(url_one, "1").get();
					//Toast.makeText(getActivity(), "IMG1: "+msg, Toast.LENGTH_SHORT).show();
					new GetZillowImage().execute(url_five, "5").get();
					//Toast.makeText(getActivity(), "IMG2: "+msg, Toast.LENGTH_SHORT).show();
					new GetZillowImage().execute(url_ten, "10").get();
				//	Toast.makeText(getActivity(), "IMG3: "+msg, Toast.LENGTH_SHORT).show();
					graph.setImageBitmap(one_yr);
					graphDesc1.setText("Historical Zestimates for past 1 year");
					graphDesc2.setText(fullAddr);
					
			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this.getActivity(),"EXCEPTION11",Toast.LENGTH_LONG) .show();
			e.printStackTrace();
		}catch(Exception e){
			Toast.makeText(this.getActivity(),"EXCEPTION22",Toast.LENGTH_LONG) .show();
		}
		
	}
}


class GetZillowImage extends AsyncTask<String, String, String>{

	String jsonObj = "empty";
	
	@Override
	protected String doInBackground(String... arg0){
		// TODO Auto-generated method stub
		String msg = "";
		try {
			URL url = new URL(arg0[0]);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			if(arg0[1].equals("1")){
				FragmentB.one_yr = BitmapFactory.decodeStream(input);
				//FragmentB.zillowGraph[0] = BitmapFactory.decodeStream(input);
			}else if(arg0[1].equals("5")){
				FragmentB.five_yr = BitmapFactory.decodeStream(input);
				//FragmentB.zillowGraph[1] = BitmapFactory.decodeStream(input);
			}else if(arg0[1].equals("10")){
				FragmentB.ten_yr = BitmapFactory.decodeStream(input);
				//FragmentB.zillowGraph[2] = BitmapFactory.decodeStream(input);
			}
			
			msg = "Success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg = "fail";
			e.printStackTrace();
		}
		return msg;
	}
}
