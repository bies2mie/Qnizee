package com.badrul.qnitibox.old;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.badrul.qnitibox.Config;
import com.badrul.qnitibox.ErrorPage2;
import com.badrul.qnitibox.FeedbackPage;
import com.badrul.qnitibox.LoginPage;
import com.badrul.qnitibox.R;

public class MenuType extends AppCompatActivity {

	Calendar startDate;
	Calendar calendar1;
	Calendar calendar2;
	Calendar calendar3;
	Date x;
	String startTime = "00:00:00";;
	String stopTime = "19:59:00";
	Button breakfast;
	Button lunch;
	Button dinner;
	Button exit;
	TextView menuTest;
	TextView timeTest;
	String dayName;
	LocalTime target;
	Calendar orderCal;
	String orderDate;
	String orderTime;
	int hour;
	String curTime;
	String currentDate;
	ImageButton feedback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTheme(R.style.AppTheme);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu_type);

		feedback = (ImageButton) findViewById(R.id.feedbackbtn);
		exit = (Button) findViewById(R.id.exit);
		breakfast = (Button) findViewById(R.id.breakfastBtn);
		lunch = (Button) findViewById(R.id.lunchBtn);
		dinner = (Button) findViewById(R.id.dinnerBtn);
		Button prv = findViewById(R.id.prevorder);


		prv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent i = new Intent(MenuType.this, LoginPage.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
			}
		});
		// menuTest = (TextView)findViewById(R.id.testDate);
		// timeTest = (TextView)findViewById(R.id.testTime);

		startDate = Calendar.getInstance();
		// startDate.set(2018, Calendar.OCTOBER, 9);
		/*
		 * LocalDate date = LocalDate.now(); DayOfWeek dow =
		 * date.getDayOfWeek();
		 * 
		 * dayName = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		 */

		/*
		 * try { startTime = "00:00:00"; Date time1 = new
		 * SimpleDateFormat("HH:mm:ss").parse(startTime); calendar1 =
		 * Calendar.getInstance(); calendar1.setTime(time1);
		 * 
		 * stopTime = "19:59:00"; Date time2 = new
		 * SimpleDateFormat("HH:mm:ss").parse(stopTime); calendar2 =
		 * Calendar.getInstance(); calendar2.setTime(time2);
		 * calendar2.add(Calendar.DATE, 1);
		 * 
		 * String someRandomTime = "01:00:00"; Date d = new
		 * SimpleDateFormat("HH:mm:ss").parse(someRandomTime); calendar3 =
		 * Calendar.getInstance(); calendar3.setTime(d);
		 * calendar3.add(Calendar.DATE, 1);
		 * 
		 * x = calendar3.getTime();
		 * 
		 * } catch (ParseException e) { e.printStackTrace(); }
		 */
		// target = LocalTime.now();
		// hour = target.getHour();

		Calendar currTime = Calendar.getInstance();
		hour = currTime.get(Calendar.HOUR_OF_DAY);

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
		curTime = sdf.format(currTime.getTime());

		currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

		feedback.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent i = new Intent(MenuType.this, FeedbackPage.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});

		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(MenuType.this, HowToMenu.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				// finish();
			}
		});

		breakfast.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && (hour >= 8 && hour < 18)) { // x.after(calendar1.getTime())
																											// &&
																											// x.before(calendar2.getTime()))
																											// {

					sharedpref();

					Intent i = new Intent(MenuType.this, MondayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, TuesdayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, WednesdayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();
					Intent i = new Intent(MenuType.this, ThursdayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, FridayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SaturdayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SundayBreakfast.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				}

				else {

					Intent i = new Intent(MenuType.this, ErrorPage2.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					finish();
				}
			}
		});

		lunch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && (hour >= 8 && hour < 18)) { // x.after(calendar1.getTime())
																											// &&
																											// x.before(calendar2.getTime()))
																											// {

					sharedpref();

					Intent i = new Intent(MenuType.this, MondayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, TuesdayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, WednesdayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, ThursdayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, FridayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SaturdayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && (hour >= 8 && hour < 18)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SundayLunch.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

					/*
					 * menuTest.setText("SATURDAY"); DateTimeFormatter formatter
					 * = DateTimeFormatter.ISO_TIME;
					 * timeTest.setText(formatter.format(target));
					 */

				}

				else {

					Intent i = new Intent(MenuType.this, ErrorPage2.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					finish();
				}
			}
		});

		dinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) && (hour >= 8 && hour < 21)) { // x.after(calendar1.getTime())
																											// &&
																											// x.before(calendar2.getTime()))
																											// {

					sharedpref();

					Intent i = new Intent(MenuType.this, MondayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) && (hour >= 8 && hour < 21)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, TuesdayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) && (hour >= 8 && hour < 21)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, WednesdayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) && (hour >= 8 && hour < 21)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, ThursdayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) && (hour >= 8 && hour < 21)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, FridayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) && (hour >= 8 && hour < 21)) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SaturdayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

				} else if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && hour >= 8 && hour < 21) {

					sharedpref();

					Intent i = new Intent(MenuType.this, SundayDinner.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					// finish();

					/*
					 * menuTest.setText("SATURDAY"); DateTimeFormatter formatter
					 * = DateTimeFormatter.ISO_TIME;
					 * timeTest.setText(formatter.format(target));
					 */

					/*
					 * (target.isAfter( LocalTime.parse(startTime)) &&
					 * target.isBefore( LocalTime.parse(stopTime))))
					 */
				}

				else {

					Intent i = new Intent(MenuType.this, ErrorPage2.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					finish();
				}
			}
		});
	}

	public void sharedpref() {

		SharedPreferences sharedPreferences = MenuType.this.getSharedPreferences(Config.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);

		// Creating editor to store values to shared preferences
		SharedPreferences.Editor editor = sharedPreferences.edit();

		// Adding values to editor

		editor.putString(Config.ORDER_TIME, curTime);
		editor.putString(Config.ORDER_DATE, currentDate);

		// Saving values to editor
		editor.commit();
	}
}
