package com.badrul.qnitibox.old;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.badrul.qnitibox.R;

public class HowToMenu extends AppCompatActivity {

	Button backBtn;
	ImageButton btnShare;
	String content = "https://play.google.com/store/apps/details?id=com.badrul.mealsnjoy";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_howtomenu);

		btnShare = (ImageButton) findViewById(R.id.sharebtn);
		backBtn = (Button) findViewById(R.id.backBtn);

		btnShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// final String barcode =barID.getText().toString().trim();
				// if (barcode.equals("")) {
				// Toast.makeText(getApplicationContext(),"Please scan the code
				// before sharing!",
				// Toast.LENGTH_LONG).show();
				// } else {
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, content);
				sendIntent.setType("text/plain");
				// sendIntent.setPackage("com.whatsapp");
				startActivity(sendIntent);
				// }
			}

		});

		backBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent i = new Intent(HowToMenu.this, MenuType.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});
	}
}
