package com.badrul.qnitibox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderMenu extends AppCompatActivity implements OnItemSelectedListener {

	public static final String CONFIRMORDER_URL = "http://gmartbox.cvmall.my/apps/gmartorder.php";
	public static final String EMAIL_IDV_URL = "http://atsventures.com/mail/indmailer.php";

	public static final String KEY_MENUTYPE = "menuType";
	public static final String KEY_MENUDAY = "menuDay";
	public static final String KEY_ORDER_DATE = "orderDate";
	public static final String KEY_ORDER_TIME = "orderTime";
	public static final String KEY_CARDNUM = "userCard";
	public static final String KEY_PHONE = "phoneID";
	public static final String KEY_NAME = "nameID";
	public static final String KEY_MENUQTT = "userQtt";
	public static final String KEY_MENUSTATUS = "orderStatus";
	public static final String KEY_LOCATION = "puLocation";
	public static final String KEY_EMAIL = "emailID";
	public static final String KEY_USERID = "userID";


	String userID;
	String nameID;
	String phoneID;
	String emailID;
	String matrixID;
	String menuType;
	String menuDay;
	String orderDate;
	String orderTime;
	// String emailID;
	String locat = "";
	String myStatus = "Processing";
	private PopupWindow pwindo;
	final Context context = this;
	int id = 0;
	Button nextBtn;
	ImageButton exit;
	ImageButton staffOrder;
	TextView menuTypeD;
	TextView menuDayD;
	EditText cardNum;
	EditText qttNum;
	Spinner sp;
	List<String> list;
	ArrayAdapter<String> adp;
	Boolean loggedIn;
	// int result = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_menu);

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

		loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
		//If we will get true
		if(loggedIn==false){
			//We will start the Profile Activity
			Intent intent = new Intent(OrderMenu.this, LoginPage.class);
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

		nextBtn = findViewById(R.id.nextBtn);
		exit = findViewById(R.id.exit);
		// staffOrder =(ImageButton) findViewById(R.id.stafforder);
		menuTypeD = findViewById(R.id.menuTypeDisplay);
		menuDayD = findViewById(R.id.menuDayDisplay);
		cardNum = (EditText) findViewById(R.id.cardNum);
		qttNum = (EditText) findViewById(R.id.qttNum);
		sp = (Spinner) findViewById(R.id.spinner);
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
		menuTypeD.setText(menuType + "   For   ");

		RadioGroup locationType = (RadioGroup) findViewById(R.id.radioType);

		locationType.clearCheck();
		locationType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				if (i == R.id.inasis) {

					 switch(menuType) 
				        { 
				         case "Breakfast":
						list = new ArrayList<String>();
						list.add("Pejabat Inasis MAS (Pick Up: 7.30 AM)");
						list.add("Pejabat Inasis TNB (Pick Up: 7.40 AM)");
						list.add("Pejabat Inasis Tradewind (Pick Up: 7.50 AM)");
						list.add("Pejabat Inasis Proton (Pick Up: 8.00 AM)");
						list.add("Pejabat AM University Inn (Pick Up: 8.10 AM)");
						list.add("Pejabat Inasis Petronas (Pick Up: 8.20 AM)");
						list.add("Pejabat Inasis Grantt (Pick Up: 8.30 AM)");
						list.add("Pejabat Inasis Sime Darby (Pick Up: 8.40 AM)");
						list.add("Pejabat Inasis TM (Pick Up: 8.50 AM)");
						list.add("Pusat Dobi MISC (Pick Up: 9.00 AM)");
						list.add("Pejabat Inasis Muamalat (Pick Up: 9.10 AM)");
						list.add("Pusat Dobi YAB (Pick Up: 9.20 AM)");
						list.add("Pejabat Inasis Bank Rakyat (Pick Up: 9.35 AM)");
						list.add("Pusat Dobi SME (Pick Up: 9.45 AM)");

						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);
						
					break;
					
				         case "Lunch":
						list = new ArrayList<String>();
						list.add("Pejabat Inasis MAS (Pick Up: 11.00 AM)");
						list.add("Pejabat Inasis TNB (Pick Up: 11.10 AM)");
						list.add("Pejabat Inasis Tradewind (Pick Up: 11.20 AM)");
						list.add("Pejabat Inasis Proton (Pick Up: 11.30 AM)");
						list.add("Pejabat AM University Inn (Pick Up: 11.40 AM)");
						list.add("Pejabat Inasis Petronas (Pick Up: 11.50 AM)");
						list.add("Pejabat Inasis Grantt (Pick Up: 12.00 PM)");
						list.add("Pejabat Inasis Sime Darby (Pick Up: 12.10 PM)");
						list.add("Pejabat Inasis TM (Pick Up: 12.20 PM)");
						list.add("Pusat Dobi MISC (Pick Up: 12.30 PM)");
						list.add("Pejabat Inasis Muamalat (Pick Up: 12.40 PM)");
						list.add("Pusat Dobi YAB (Pick Up: 12.50 PM)");
						list.add("Pejabat Inasis Bank Rakyat (Pick Up: 1.05 PM)");
						list.add("Pusat Dobi SME (Pick Up: 1.15 PM)");
						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);
						
						break;

				        case "Dinner":
						list = new ArrayList<String>();
						list.add("Pejabat Inasis MAS (Pick Up: 6.00 PM)");
						list.add("Pejabat Inasis TNB (Pick Up: 6.10 PM)");
						list.add("Pejabat Inasis Tradewind (Pick Up: 6.20 PM)");
						list.add("Pejabat Inasis Proton (Pick Up: 6.30 PM)");
						list.add("Pejabat AM University Inn (Pick Up: 6.40 PM)");
						list.add("Pejabat Inasis Petronas (Pick Up: 6.50 PM)");
						list.add("Pejabat Inasis Grantt (Pick Up: 7.00 PM)");
						list.add("Pejabat Inasis Sime Darby (Pick Up: 7.10 PM)");
						list.add("Pejabat Inasis TM (Pick Up: 7.20 PM)");
						list.add("Pusat Dobi MISC (Pick Up: 7.30 PM)");
						list.add("Pejabat Inasis Muamalat (Pick Up: 7.40 PM)");
						list.add("Pusat Dobi YAB (Pick Up: 7.50 PM)");
						list.add("Pejabat Inasis Bank Rakyat (Pick Up: 8.05 PM)");
						list.add("Pusat Dobi SME (Pick Up: 8.15 PM)");
						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);
					}

				} else if (i == R.id.acade) {

					switch(menuType) 
			        { 
			         case "Breakfast":

						list = new ArrayList<String>();

						list.add("Bus Stop DKG 5 (Pick Up: 7.30 AM)");
						list.add("DKG 6/Pusat Asasi (Pick Up: 7.40 AM)");
						list.add("Bus Stop DKG 1 (Pick Up: 8.00 AM)");
						list.add("Pejabat AM Othman Yeob Abdullah (Pick Up: 8.20 AM)");
						list.add("Library Foyer (Pick Up: 8.30 AM)");
						list.add("Pejabat AM SOIS (Pick Up: 8.45 AM)");
						list.add("Business Centre Taman Uni (Pick Up: 9.00 AM)");
						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);
						
						break;

			         case "Lunch":

						list = new ArrayList<String>();

						list.add("Bus Stop DKG 5 (Pick Up: 11.00 AM)");
						list.add("DKG 6/Pusat Asasi (Pick Up: 1.10 AM)");
						list.add("Bus Stop DKG 1 (Pick Up: 11.30 AM)");
						list.add("Pejabat AM Othman Yeob Abdullah (Pick Up: 11.50 AM)");
						list.add("Library Foyer (Pick Up: 12.00 PM)");
						list.add("Pejabat AM SOIS (Pick Up: 12.15 PM)");
						list.add("Business Centre Taman Uni (Pick Up: 12.30 PM)");
						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);
						
						break;

			         case "Dinner":

						list = new ArrayList<String>();

						list.add("Bus Stop DKG 5 (Pick Up: 6.00 PM)");
						list.add("DKG 6/Pusat Asasi (Pick Up: 6.10 PM)");
						list.add("Bus Stop DKG 1 (Pick Up: 6.30 PM)");
						list.add("Pejabat AM Othman Yeob Abdullah (Pick Up: 6.50 PM)");
						list.add("Library Foyer (Pick Up: 7.00 PM)");
						list.add("Pejabat AM SOIS (Pick Up: 7.15 PM)");
						list.add("Business Centre Taman Uni (Pick Up: 7.30 PM)");
						adp = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, list);
						adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sp.setAdapter(adp);

					}
				}

			}
		});

		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				

				final String myCard = cardNum.getText().toString().trim();
				final String myQtt = qttNum.getText().toString().trim();
				final int result = Integer.parseInt(myQtt);

				if (locat == "") {
					Toast.makeText(getApplicationContext(), "Please select pickup location",
							Toast.LENGTH_LONG).show();
				} else if (result < 1) {
					Toast.makeText(getApplicationContext(), "Please enter minimum 1 order",
							Toast.LENGTH_LONG).show();
				} else if (result > 5) {
					Toast.makeText(getApplicationContext(),
							"Cannot order more than 5. Anything higher please use Event ordering",
							Toast.LENGTH_LONG).show();
				}
				/*
				 * else if (myCard.length() < 5 ||myCard.length() >
				 * 10) { Toast.makeText(getApplicationContext(),
				 * "Please enter proper ATS Card number!",
				 * Toast.LENGTH_LONG).show(); }
				 */
				else

				try {
					// We need to get the instance of the LayoutInflater
					LayoutInflater inflater = (LayoutInflater) OrderMenu.this
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

									

										StringRequest stringRequest = new StringRequest(Request.Method.POST,
												CONFIRMORDER_URL, new Response.Listener<String>() {
													@Override
													public void onResponse(String response) {
														Toast.makeText(OrderMenu.this, response, Toast.LENGTH_LONG)
																.show();
														Intent i = new Intent(OrderMenu.this, MenuType.class);
														i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
														startActivity(i);
														finish();
													}
												}, new Response.ErrorListener() {
													@Override
													public void onErrorResponse(VolleyError error) {
														Toast.makeText(OrderMenu.this, error.toString(),
																Toast.LENGTH_LONG).show();
													}
												}) {
											@Override
											protected Map<String, String> getParams() {
												Map<String, String> params = new HashMap<String, String>();
												params.put(KEY_MENUTYPE, menuType);
												params.put(KEY_MENUDAY, menuDay);
												params.put(KEY_ORDER_DATE, orderDate);
												params.put(KEY_ORDER_TIME, orderTime);
												params.put(KEY_CARDNUM, myCard);
												params.put(KEY_MENUQTT, myQtt);
												params.put(KEY_MENUSTATUS, myStatus);
												params.put(KEY_LOCATION, locat);
												params.put(KEY_USERID, userID);
												return params;
											}

										};

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

				Intent i = new Intent(OrderMenu.this, MenuType.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});

		/*
		 * staffOrder.setOnClickListener(new View.OnClickListener() { public
		 * void onClick(View view) {
		 * 
		 * Intent i = new Intent(OrderMenu.this, StaffOrder.class);
		 * i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(i);
		 * finish(); } });
		 */

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		locat = parent.getSelectedItem().toString();

		// Showing selected spinner item
		Toast.makeText(parent.getContext(), "Selected: " + locat, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Toast.makeText(arg0.getContext(), "Please Select Your Pickup Location", Toast.LENGTH_LONG).show();

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

		StringRequest stringRequest = new StringRequest(Request.Method.POST, EMAIL_IDV_URL,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// Toast.makeText(OrderMenu.this,response,Toast.LENGTH_LONG).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(OrderMenu.this, error.toString(), Toast.LENGTH_LONG).show();
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
				params.put(KEY_LOCATION, locat);
				params.put(KEY_EMAIL, emailID);
				return params;
			}

		};

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);
	}
}