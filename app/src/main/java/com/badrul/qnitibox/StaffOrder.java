package com.badrul.qnitibox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StaffOrder extends AppCompatActivity implements OnItemSelectedListener {

	public static final String CONFIRMORDER_URL = "http://gmartbox.cvmall.my/apps/stafforder.php";
	public static final String EMAIL_EVENT_URL = "http://atsventures.com/mail/eventmailer.php";

	public static final String KEY_MENUTYPE = "menuType";
	public static final String KEY_MENUDAY = "menuDay";
	public static final String KEY_ORDER_DATE = "orderDate";
	public static final String KEY_ORDER_TIME = "orderTime";
	public static final String KEY_PHONE = "userNotel";
	public static final String KEY_CARDNUM = "userCard";
	public static final String KEY_NAME = "userName";
	public static final String KEY_MATRIXNUM = "userMatrix";
	public static final String KEY_MENUQTT = "userQtt";
	public static final String KEY_MENUSTATUS = "orderStatus";
	public static final String KEY_LOCATION = "puLocation";
	public static final String KEY_PUTIME = "puTime";
	public static final String KEY_EMAIL = "emailID";
	public static final String KEY_USERID = "userID";

	String menuType;
	String menuDay;
	String orderDate;
	String orderTime;

	String userID;
	String nameID;
	String phoneID;
	String emailID;
	String matrixID;
	// String locat;
	String myStatus = "Processing";
	private PopupWindow pwindo;
	final Context context = this;
	int id = 0;
	Button nextBtn;
	ImageButton exit;
	TextView menuTypeD;
	TextView menuDayD;
	EditText cardNum;
	EditText qttNum;
	EditText puLocat;
	Spinner sp;
	List<String> list;
	ArrayAdapter<String> adp;
	// int result = 0;
	TextView chooseTime;
	TimePickerDialog timePickerDialog;
	Calendar calendar;
	int currentHour;
	int currentMinute;
	String amPm;
	String pickupTime = "";
	Boolean loggedIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_staff_order);

		SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		menuType = sharedPreferences.getString(Config.MENU_TYPE, "Not Available");
		menuDay = sharedPreferences.getString(Config.MENU_DAY, "Not Available");
		orderDate = sharedPreferences.getString(Config.ORDER_DATE, "Not Available");
		orderTime = sharedPreferences.getString(Config.ORDER_TIME, "Not Available");
		userID = sharedPreferences.getString(Config.USER_ID2,"0");
		nameID = sharedPreferences.getString(Config.NAME_ID2, "Not Available");
		phoneID = sharedPreferences.getString(Config.PHONE_ID2, "Not Available");
		emailID = sharedPreferences.getString(Config.EMAIL_ID2, "Not Available");
		matrixID = sharedPreferences.getString(Config.MATRIX_ID2, "Not Available");

		nextBtn = findViewById(R.id.nextBtn);
		exit = findViewById(R.id.exit);
		menuTypeD = findViewById(R.id.menuTypeDisplay);
		menuDayD = findViewById(R.id.menuDayDisplay);
		cardNum = findViewById(R.id.cardNum);
		qttNum = findViewById(R.id.qttNum);
		puLocat = findViewById(R.id.puLocat);
		//chooseTime = (TextView) findViewById(R.id.puTime);

		loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

		//If we will get true
		if(loggedIn==false){
			//We will start the Profile Activity
			Intent intent = new Intent(StaffOrder.this, LoginPage.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		}

		TextView showName = findViewById(R.id.nameText);
		TextView showPhone = findViewById(R.id.phoneNum);
		TextView showEmail = findViewById(R.id.emailIDtxt);
		TextView showMatrix = findViewById(R.id.matrixNum);

		showName.setText(nameID);
		showEmail.setText(emailID);
		showMatrix.setText(matrixID);
		showPhone.setText(phoneID);
		
		sp = findViewById(R.id.spinner);
		sp.setOnItemSelectedListener(this);

		Date dt = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		// to convert Date to String, use format method of SimpleDateFormat
		// class.
		String strDate = dateFormat.format(dt);

		menuDayD.setText(strDate);
		menuTypeD.setText("Event: " + menuType + "   For   ");
		
		switch(menuType) 
        { 
         case "Breakfast":
        	 
			list = new ArrayList<String>();
			list.add("8.00 AM");
			list.add("9.00 AM");
			list.add("10.00 AM");

			adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
			adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(adp);
			break;
			
         case "Lunch":
			list = new ArrayList<String>();
			list.add("11.00 AM");
			list.add("12.00 PM");
			list.add("1.00 PM");
			list.add("2.00 PM");

			adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
			adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(adp);
			break;
			
         case "Dinner":
			list = new ArrayList<String>();
			list.add("6.00 PM");
			list.add("7.00 PM");
			list.add("8.00 PM");
		
			adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
			adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sp.setAdapter(adp);
		}

		/*chooseTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				calendar = Calendar.getInstance();
				currentHour = calendar.get(Calendar.HOUR_OF_DAY);
				currentMinute = calendar.get(Calendar.MINUTE);

				timePickerDialog = new TimePickerDialog(StaffOrder.this, new TimePickerDialog.OnTimeSetListener() {

					@Override
					public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
						// TODO Auto-generated method stub
						if (hourOfDay >= 12) {
							amPm = "PM";
						} else {
							amPm = "AM";
						}
						chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes));
						pickupTime = chooseTime.getText().toString();
					}
				}, currentHour, currentMinute, false);

				timePickerDialog.show();
			}
		});*/

		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				final String myCard = cardNum.getText().toString().trim();
				final String myQtt = qttNum.getText().toString().trim();
				final String myLocat = puLocat.getText().toString().trim();

				final int result = Integer.parseInt(myQtt);


				 if (pickupTime == "") {
					Toast.makeText(getApplicationContext(), "Please select the pickup time!",
							Toast.LENGTH_LONG).show();
				} else if (myLocat.length() < 5) {
					Toast.makeText(getApplicationContext(), "Please enter with more than 5 characters in Location",
							Toast.LENGTH_LONG).show();
				} else if (result < 6) {
					Toast.makeText(getApplicationContext(),
							"Please enter minimum 6 orders, Anything lower please use Individual ordering",
							Toast.LENGTH_LONG).show();
				}
				/*
				 * else if (result >50) {
				 * Toast.makeText(getApplicationContext(),
				 * "Cannot order more than 50",
				 * Toast.LENGTH_LONG).show(); } else if
				 * (myCard.length() < 5 ||myCard.length() > 10) {
				 * Toast.makeText(getApplicationContext(),
				 * "Please enter proper ATS Card number!",
				 * Toast.LENGTH_LONG).show(); }
				 */
				else

				try {
					// We need to get the instance of the LayoutInflater
					LayoutInflater inflater = (LayoutInflater) StaffOrder.this
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.popup_confirmationorder,
							(ViewGroup) findViewById(R.id.popup_element));
					pwindo = new PopupWindow(layout, 700, 1000, true);
					pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
					Button cancel = (Button) layout.findViewById(R.id.cancelBtn);
					Button confirm = (Button) layout.findViewById(R.id.confirmBtn);

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							pwindo.dismiss();
						}
					});

					confirm.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							
								try {
									if (Settings.Global.getInt(getContentResolver(), Global.AUTO_TIME) == 0) {

										Toast.makeText(getApplicationContext(),
												"Please set Automatic Date & Time to ON in the Settings",
												Toast.LENGTH_LONG).show();

										startActivityForResult(
												new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
									} else if (Settings.Global.getInt(getContentResolver(),
											Global.AUTO_TIME_ZONE) == 0) {

										Toast.makeText(getApplicationContext(),
												"Please set Automatic Time Zone to ON in the Settings",
												Toast.LENGTH_LONG).show();

										startActivityForResult(
												new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
									}
									final ProgressDialog loading = ProgressDialog.show(StaffOrder.this,"Please Wait","Contacting Server",false,false);
									

										StringRequest stringRequest = new StringRequest(Request.Method.POST,
												CONFIRMORDER_URL, new Response.Listener<String>() {
													@Override
													public void onResponse(String response) {

														loading.dismiss();
														Toast.makeText(StaffOrder.this, response, Toast.LENGTH_LONG)
																.show();
														Intent i = new Intent(StaffOrder.this, MenuType.class);
														i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
														startActivity(i);
														finish();
														
													}
												}, new Response.ErrorListener() {
													@Override
													public void onErrorResponse(VolleyError error) {
														loading.dismiss();
														if (error instanceof TimeoutError || error instanceof NoConnectionError) {
															Toast.makeText(StaffOrder.this,"No internet . Please check your connection",
																	Toast.LENGTH_LONG).show();
														}
														else{

															Toast.makeText(StaffOrder.this, error.toString(), Toast.LENGTH_LONG).show();
														}
													}
												}) {
											@Override
											protected Map<String, String> getParams() {
												Map<String, String> params = new HashMap<String, String>();
												params.put(KEY_MENUTYPE, menuType);
												params.put(KEY_MENUDAY, menuDay);
												params.put(KEY_ORDER_DATE, orderDate);
												params.put(KEY_ORDER_TIME, orderTime);
												params.put(KEY_USERID, userID);
												params.put(KEY_CARDNUM, myCard);
												params.put(KEY_MENUQTT, myQtt);
												params.put(KEY_MENUSTATUS, myStatus);
												params.put(KEY_LOCATION, myLocat);
												params.put(KEY_PUTIME, pickupTime);
												return params;
											}

										};

									stringRequest.setRetryPolicy(new DefaultRetryPolicy(
											30000,
											DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
											DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

										RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
										requestQueue.add(stringRequest);

										emailer();
										notifyMe();
									}

								 catch (SettingNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent i = new Intent(StaffOrder.this, MenuType.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});

	}

	@TargetApi(24)
	public void notifyMe() {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

	    int notificationId = 1;
	    String channelId = "channel-01";
	    String channelName = "Channel Name";
	    int importance = NotificationManager.IMPORTANCE_HIGH;

	    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
	        NotificationChannel mChannel = new NotificationChannel(
	                channelId, channelName, importance);
	        notificationManager.createNotificationChannel(mChannel);
	    }

	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setContentTitle("Order Notifications")
	            .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Your order has been recorded \n\nPlease pay during delivery. Check your email for order details"))
	            .setContentText("Your order has been recorded");

	    Intent notifyIntent = new Intent(this, MenuType.class);
	 // Set the Activity to start in a new, empty task
	 notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
	                     | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	 // Create the PendingIntent
	 PendingIntent notifyPendingIntent = PendingIntent.getActivity(
	         this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
	    );
	    mBuilder.setContentIntent(notifyPendingIntent);

	    notificationManager.notify(notificationId, mBuilder.build());

	}

	public void emailer() {

		final String myQtt = qttNum.getText().toString().trim();
		final String myLocat = puLocat.getText().toString().trim();

		final ProgressDialog loading = ProgressDialog.show(StaffOrder.this,"Please Wait","Contacting Server",false,false);

		StringRequest stringRequest = new StringRequest(Request.Method.POST, EMAIL_EVENT_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {

						loading.dismiss();
						// Toast.makeText(IndvOrder.this,response,Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						loading.dismiss();
						if (error instanceof TimeoutError || error instanceof NoConnectionError) {
							Toast.makeText(StaffOrder.this,"No internet . Please check your connection",
									Toast.LENGTH_LONG).show();
						}
						else{

							Toast.makeText(StaffOrder.this, error.toString(), Toast.LENGTH_LONG).show();
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put(KEY_MENUTYPE, menuType);
				params.put(KEY_MENUDAY, menuDay);
				params.put(KEY_ORDER_DATE, orderDate);
				params.put(KEY_ORDER_TIME, orderTime);
				params.put(KEY_PHONE, phoneID);
				params.put(KEY_NAME, nameID);
				params.put(KEY_MENUQTT, myQtt);
				params.put(KEY_MENUSTATUS, myStatus);
				params.put(KEY_LOCATION, myLocat);
				params.put(KEY_PUTIME, pickupTime);
				params.put(KEY_EMAIL, emailID);
				return params;
			}

		};

		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				30000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		pickupTime = parent.getSelectedItem().toString();
		Toast.makeText(parent.getContext(), "Selected: " + pickupTime, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Toast.makeText(arg0.getContext(), "Please Select Your Pickup Time", Toast.LENGTH_LONG).show();
		
	}
}