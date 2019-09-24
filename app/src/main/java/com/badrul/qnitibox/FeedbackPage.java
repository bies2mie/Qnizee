package com.badrul.qnitibox;

import java.util.HashMap;
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
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Global;
import android.provider.Settings.SettingNotFoundException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackPage extends AppCompatActivity {

	public static final String FEEDBACK_URL = "http://gmartbox.cvmall.my/apps/gmatfeedback.php";
	public static final String KEY_PHONE = "userNotel";
	public static final String KEY_NAME = "userName";
	public static final String KEY_MATRIXNUM = "userMatrix";
	public static final String KEY_FEEDBACK = "userFeedback";

	EditText name;
	EditText phone;
	EditText matrixNum;
	EditText feedbackTxt;
	Button nextBtn;
	Button back;
	int id = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_page);

		nextBtn = (Button) findViewById(R.id.nextBtn);
		back = (Button) findViewById(R.id.back);

		name = (EditText) findViewById(R.id.nameText);
		phone = (EditText) findViewById(R.id.phoneNum);
		matrixNum = (EditText) findViewById(R.id.matrixNum);
		feedbackTxt = (EditText) findViewById(R.id.feedbackTxt);

		nextBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				final String username = name.getText().toString().trim();
				final String myPhone = phone.getText().toString().trim();
				final String myMatrix = matrixNum.getText().toString().trim();
				final String myFeedback = feedbackTxt.getText().toString().trim();

				if (username.length() < 5) {
					Toast.makeText(getApplicationContext(), "Please enter minimum 5 character in Name!",
							Toast.LENGTH_LONG).show();
				} else if (myPhone.length() < 10 || myPhone.length() > 15) {
					Toast.makeText(getApplicationContext(), "Please enter proper phone number!", Toast.LENGTH_LONG)
							.show();
				} else if (myFeedback.length() < 10) {
					Toast.makeText(getApplicationContext(), "Please enter minimum 10 character", Toast.LENGTH_LONG)
							.show();
				} else
					try {
						if (Settings.Global.getInt(getContentResolver(), Global.AUTO_TIME) == 0) {

							Toast.makeText(getApplicationContext(),
									"Please set Automatic Date & Time to ON in the Settings", Toast.LENGTH_LONG).show();

							startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
						} else if (Settings.Global.getInt(getContentResolver(), Global.AUTO_TIME_ZONE) == 0) {

							Toast.makeText(getApplicationContext(),
									"Please set Automatic Time Zone to ON in the Settings", Toast.LENGTH_LONG).show();

							startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
						} else {

							StringRequest stringRequest = new StringRequest(Request.Method.POST, FEEDBACK_URL,
									new Response.Listener<String>() {
										@Override
										public void onResponse(String response) {
											Toast.makeText(FeedbackPage.this, response, Toast.LENGTH_LONG).show();
										}
									}, new Response.ErrorListener() {
										@Override
										public void onErrorResponse(VolleyError error) {
											Toast.makeText(FeedbackPage.this, error.toString(), Toast.LENGTH_LONG)
													.show();
										}
									}) {
								@Override
								protected Map<String, String> getParams() {
									Map<String, String> params = new HashMap<String, String>();

									params.put(KEY_PHONE, myPhone);
									params.put(KEY_NAME, username);
									params.put(KEY_MATRIXNUM, myMatrix);
									params.put(KEY_FEEDBACK, myFeedback);

									return params;
								}

							};

							RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
							requestQueue.add(stringRequest);

							notifyMe();
						}
					} catch (SettingNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent i = new Intent(FeedbackPage.this, MenuType.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});

	}

	@TargetApi(24)
	public void notifyMe() {
		NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

	    int notificationId = 1;
	    String channelId = "channel-01";
	    String channelName = "Channel Name";
	    int importance = NotificationManager.IMPORTANCE_HIGH;

	    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
	        NotificationChannel mChannel = new NotificationChannel(
	                channelId, channelName, importance);
	        notificationManager.createNotificationChannel(mChannel);
	    }

	    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), channelId)
	            .setSmallIcon(R.drawable.ic_launcher)
	            .setAutoCancel(true)
	            .setContentTitle("Order Notifications")
	            .setContentText("Your feedback has been recorded.");

	    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getBaseContext());
	    stackBuilder.addNextIntent(getIntent());
	    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	    );
	    mBuilder.setContentIntent(resultPendingIntent);

	    notificationManager.notify(notificationId, mBuilder.build());

	}
}
