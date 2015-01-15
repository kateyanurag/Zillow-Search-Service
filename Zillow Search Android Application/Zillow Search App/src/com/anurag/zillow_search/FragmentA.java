package com.anurag.zillow_search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.anurag.zillow_search.R.id;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.FacebookDialog.ShareDialogBuilder;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.media.Image;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.Face;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FragmentA extends Fragment{

	View view = null;
	Button btn = null;
	ListView detailsList = null;
	List<ZillowEntry> list;
	JSONObject jsonObj = null;
	Facebook fb;
	int count = 0;
	static String fullAddr = "";
	static String lastSoldPrice = "";
	static String overallChange30Days = "";
	static String homeDetails = "";
	TextView footer1;
	TextView footer2;
	private UiLifecycleHelper uiHelper;
	public FragmentA() {
		// Required empty public constructor
	}

	@SuppressLint("InflateParams") @SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_a, container, false);
		detailsList = (ListView)view.findViewById(R.id.detailsList);
		list = new ArrayList<ZillowEntry>();
		View zillowView = LayoutInflater.from(getActivity()).inflate(R.layout.zillow_item_view, null);
		ImageView fbShare = (ImageView)zillowView.findViewById(R.id.itemSign);
		fbShare.setOnClickListener(new OnClickListener() {
			
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*detailsList.setAdapter(new ArrayAdapter<ZillowEntry>(getActivity(), 
				android.R.layout.simple_list_item_1, list
				));*/
		
		
		//
			
		return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String zillow_details = getActivity().getIntent().getExtras().getString("zillow_object");
		//Toast.makeText(this.getActivity(), "ZILLOW DETAILS: "+zillow_details ,Toast.LENGTH_LONG) .show();
		View zillowView = LayoutInflater.from(getActivity()).inflate(R.layout.zillow_item_view, null);
		
		ImageView fbShare = (ImageView)view.findViewById(R.id.itemSign);
		footer1 = (TextView)view.findViewById(R.id.footer1);
		footer1.setText(Html.fromHtml(
	            "Use is subject to " +
	            "<a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of use</a>"));
		footer1.setMovementMethod(LinkMovementMethod.getInstance());
		
		footer2 = (TextView)view.findViewById(R.id.footer2);
		footer2.setText(Html.fromHtml(
	            "<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate?</a>"));
		footer2.setMovementMethod(LinkMovementMethod.getInstance());
		
		try {
			jsonObj = new JSONObject(zillow_details);
			//Toast.makeText(this.getActivity(), "year_built"+ jsonObj.getString("year_built") ,Toast.LENGTH_LONG) .show();
			populateZillowArrayList();
			populateZillowListView();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this.getActivity(), "ERRORORORO!!" ,Toast.LENGTH_LONG) .show();
			e.printStackTrace();
		}
	
		
	}
	
	private String check(String s){
		if(s.equals("") || s.equals("Unknown") || s.equals("0 sq. ft.") || s.equals("$.00") || s.equals(".00") || s.equals("$.00 - $.00"))
			return "N/A";
		return s;
	}
	
	public void populateZillowArrayList(){
		try{
			String reg = getString(R.string.reg);
			list.add(new ZillowEntry("See more details on Zillow:",R.drawable.fbshare, ""));
			fullAddr	 = 	jsonObj.getString("street")+
								jsonObj.getString("city")+
								jsonObj.getString("state")+
								jsonObj.getString("zipcode");
			homeDetails = jsonObj.getString("homedetails");
			String url = "<a href=" +  homeDetails + "\">" + fullAddr  +"</a>";
			list.add(new ZillowEntry(Html.fromHtml(url)));
			list.add(new ZillowEntry("Property Type:",-1,check(jsonObj.getString("property_type"))));
			list.add(new ZillowEntry("Lot Size:",-1,check(jsonObj.getString("lot_size"))));
			list.add(new ZillowEntry("Finished Area:",-1,check(jsonObj.getString("finished_area"))));
			list.add(new ZillowEntry("Bathrooms:",-1,check(jsonObj.getString("bathrooms"))));
			list.add(new ZillowEntry("Bedrooms:",-1,check(jsonObj.getString("bedrooms"))));
			
			list.add(new ZillowEntry("Tax Assesment Year:",-1,check(jsonObj.getString("taxAssessmentYear"))));
			list.add(new ZillowEntry("Tax Assesment:",-1,check(jsonObj.getString("taxAssessment"))));
			lastSoldPrice = jsonObj.getString("last_sold_price");
			if(lastSoldPrice.equals("$.00")){
				lastSoldPrice = "N/A";
			}
			lastSoldPrice = "Last Sold Price: " + lastSoldPrice;
			list.add(new ZillowEntry("Last Sold Price:",-1,check(jsonObj.getString("last_sold_price"))));
			list.add(new ZillowEntry("Last Sold Date:",-1,check(jsonObj.getString("last_sold_date"))));
			
			list.add(new ZillowEntry("Zestimate"+ reg +" Property Estimate as of 04-Nov-2014:",
					-1,check(jsonObj.getString("property_estimate"))));
			overallChange30Days = jsonObj.getString("overall_change");
			String var = jsonObj.getString("overall_change");
			if(!check(var).equals("N/A")){
				if(var.contains("-")){
					var = var.split("-")[1];
					list.add(new ZillowEntry("30 days Overall Change:",R.drawable.down,"$"+var));
				}else{
					list.add(new ZillowEntry("30 days Overall Change:",R.drawable.up,"$"+var));
				}
			}else{
				list.add(new ZillowEntry("30 days Overall Change:",-1,"N/A"));
			}
			
			list.add(new ZillowEntry("All time Property Range:",-1,check(jsonObj.getString("property_range"))));
			list.add(new ZillowEntry("Rent Zestimate"+ reg +" Valuation as of 03-Nov-2014:",
					-1,check(jsonObj.getString("rent_estimate"))));
			
			var = jsonObj.getString("rent_change");
			if(!check(var).equals("N/A")){
				if(var.contains("-")){
					var = var.split("-")[1];
					list.add(new ZillowEntry("30 days Rent Change:",R.drawable.down,"$"+var));
				}else{
					list.add(new ZillowEntry("30 days Rent Change:",R.drawable.up,"$"+var));
				}
			}else{
				list.add(new ZillowEntry("30 days Rent Change:",-1,"N/A"));
			}
			
			
			list.add(new ZillowEntry("All time Rent Change:",-1,check(jsonObj.getString("rent_range"))));
			
		}catch(Exception e){
			Toast.makeText(getActivity(), "Exception",Toast.LENGTH_LONG) .show();
		}
		
		
	}
	private void populateZillowListView() {
		// TODO Auto-generated method stub
		ArrayAdapter<ZillowEntry> adapter = new ZillowListAdapter();
		
		detailsList.setAdapter(adapter);
		}
	
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        uiHelper = new UiLifecycleHelper(getActivity(), callback);
	        uiHelper.onCreate(savedInstanceState);
	    }
	 
	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	    }
	 
	    @Override
	    public void onResume() {
	        super.onResume();
	        uiHelper.onResume();
	    }
	 
	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        uiHelper.onSaveInstanceState(outState);
	    }
	 
	    @Override
	    public void onPause() {
	        super.onPause();
	        uiHelper.onPause();
	    }
	 
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        uiHelper.onDestroy();
	    }

		private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
			
			@Override
			public void onError(PendingCall pendingCall, Exception error, Bundle data) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), " ERROR !",Toast.LENGTH_LONG) .show();
			}
			
			@Override
			public void onComplete(PendingCall pendingCall, Bundle data) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), " Complete ",Toast.LENGTH_LONG) .show();
			}
		};
		
		private Session.StatusCallback callback = new Session.StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				// TODO Auto-generated method stub
				 onSessionStateChange(session, state, exception);
			}

			
		};
		
		private void onSessionStateChange(Session session,
				SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			 if (state.isOpened()) {
				 Toast.makeText(getActivity(), " Logged in",Toast.LENGTH_LONG) .show();
		        } else if (state.isClosed()) {
		        	Toast.makeText(getActivity(), " logged out",Toast.LENGTH_LONG) .show();
		        }
			
		}
		
		private void fbPost(){
		/*	if(FacebookDialog.canPresentShareDialog(getActivity(), FacebookDialog.ShareDialogFeature.SHARE_DIALOG)){
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
                .setName("Titolo")
                .setLink("http://developer.neosperience.com/android")
                .setDescription("Hello from Neosperience Developer")
                .setPicture("http://lh3.googleusercontent.com/-P4JBVTv_kSI/AAAAAAAAAAI/AAAAAAAAAAs/bZptjIhkWu4/s265-c-k-no/photo.jpg")
                .build();
        uiHelper.trackPendingDialogCall(shareDialog.present());
			}*/
			
			String APP_ID = "783448755034766";  //tujha app id
            //    String APP_ID = getString(R.string.APP_ID);
                final Facebook fb= new Facebook(APP_ID);
                
                if(fb.isSessionValid())
                {
                    Log.v("hello", APP_ID);    
                    try {
                        fb.logout(getActivity());
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                
                
                else
                {
                    fb.authorize(getActivity(), new Facebook.DialogListener() {
                        
      
                        
                        @Override
                        public void onFacebookError(FacebookError e) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "fberror", Toast.LENGTH_LONG).show();

                        }
                        
                        @Override
                        public void onError(DialogError e) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "onerror", Toast.LENGTH_LONG).show();

                        }
                        
                        @Override
                        public void onComplete(Bundle values) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "oncomplete", Toast.LENGTH_LONG).show();
    
                        }
                        
                        @Override
                        public void onCancel() {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "oncancel", Toast.LENGTH_LONG).show();
                        }
                    });
                
                
                
                    Bundle params = new Bundle();
                    params.putString("name",fullAddr);
                    params.putString("caption","Property information from Zillow.com");
                    String overallChange = "";
                    if(!overallChange30Days.equals("")){
                    	if(overallChange30Days.equals(".00")){
                    		overallChange = "30 Days Overall Change: "+ "N/A";
                    	}
                    	else if(!overallChange30Days.contains("-")){
                    		overallChange = "30 Days Overall Change: "+ "+$" + overallChange30Days;
                    	}else{
                    		overallChange = "30 days overall Change: " + overallChange30Days+"$";
                    	}
                    }
                    params.putString("description",lastSoldPrice + ",   " + overallChange);
                    params.putString("link",homeDetails);
                    
                    try {
						String zillowId = jsonObj.getString("one_year");
						//if(!zillowId.equals("")){
							zillowId=zillowId.split("zpid=")[1];
							if(!zillowId.equals("")){
								String url = "http://www.zillow.com/app?chartDuration=1year&chartType=partner&height=300&" +
										"page=webservice%2FGetChart&service=chart&showPercent=true&width=600&zpid="+zillowId;
								params.putString("picture",url);
							}
						//}
						
							
						
                    } catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                   
                    
                    fb.dialog(getActivity(), "feed", params,new DialogListener() {
                        
                        @Override
                        public void onFacebookError(FacebookError e) {
                            // TODO Auto-generated method stub
                        	  Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
                        }
                        
                        @Override
                        public void onError(DialogError e) {
                            // TODO Auto-generated method stub
                        	  Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        }
                        
                        @Override
                        public void onComplete(Bundle values) {
                            // TODO Auto-generated method stub
                            Toast.makeText(getActivity(), "Posted Story,ID\n:"+values.getString("post_id"), Toast.LENGTH_LONG).show();

                        }
                        
                        @Override
                        public void onCancel() {
                            // TODO Auto-generated method stub
                        	  Toast.makeText(getActivity(), "Post Cancelled", Toast.LENGTH_LONG).show();
                        }
                    });
                    
                
                
                
                
                
                }
            }
                
                

	
	
	private class ZillowListAdapter extends ArrayAdapter<ZillowEntry>{
		public ZillowListAdapter(){
			super(getActivity(), R.layout.zillow_item_view, list);
			
		}
		
		
		
		
		
		
		
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View itemView = convertView;
			if(itemView == null){
				//Toast.makeText(getActivity(), "itemView = null ",Toast.LENGTH_LONG) .show();
				itemView = 	getActivity().getLayoutInflater().inflate(R.layout.zillow_item_view, parent, false);
			}
			ZillowEntry zillowEntry = list.get(position);
					
				TextView label = (TextView)itemView.findViewById(R.id.itemLabel);
				ImageView sign = (ImageView)itemView.findViewById(R.id.itemSign);
				TextView itemValue = (TextView)itemView.findViewById(R.id.itemValue);
				
				if(position == 0){
					
					sign.setImageResource(zillowEntry.getImage());
					sign.setOnClickListener(new OnClickListener(){
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							new AlertDialog.Builder(getActivity())
						    .setTitle("Post to Facebook")
						    .setPositiveButton("Post Property details", new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // continue with delete
						        	fbPost();
						        }
						     })
						    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						        public void onClick(DialogInterface dialog, int which) { 
						            // do nothing
						        	Toast.makeText(getActivity(), "Post Cancelled", Toast.LENGTH_LONG).show();
						        }
						     })
						     .show();
							
						}
					});
					
					label.setText(zillowEntry.getLabel());
				} 
				if(position == 1){
					label = (TextView)itemView.findViewById(R.id.itemLabel);
					//Toast.makeText(getActivity(), "pos 1:"+count , Toast.LENGTH_LONG).show();
					label.setText(zillowEntry.getLink());
					label.setMovementMethod(LinkMovementMethod.getInstance());
					label.getLayoutParams().width=400;
					count++;
					
					
					
					
				}else{
					label.setText(zillowEntry.getLabel());
				}
				itemValue.setText(zillowEntry.getValue());
				sign.setImageResource(zillowEntry.getImage());
				
			
			 if(position % 2 == 0 && position != 0){  
				 
				 itemView.setBackgroundColor(Color.rgb(223, 255, 230));
			 }else {
				 itemView.setBackgroundColor(Color.rgb(255, 255, 255));
			 }
			
			
			return itemView;
		}

	}
		
}

