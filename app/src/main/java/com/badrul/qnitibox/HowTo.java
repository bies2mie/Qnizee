package com.badrul.qnitibox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class HowTo extends Activity {

	Button backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_how_to);

		backBtn = (Button) findViewById(R.id.backBtn);

		backBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				Intent i = new Intent(HowTo.this, MenuType.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});
	}
}
