package com.techfortsoft.changemode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InstructionsActivity extends Activity {
	LinearLayout layout;
	TextView title, body, action;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_instructions);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/GEORGIA.TTF");
		layout = (LinearLayout) findViewById(R.id.layout);
		title = (TextView) findViewById(R.id.textView1);
		body = (TextView) findViewById(R.id.textView2);
		action = (TextView) findViewById(R.id.textView3);
		title.setTypeface(face);
		body.setTypeface(face);
		action.setTypeface(face);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(InstructionsActivity.this,
						PrincipleActivity.class));
			}
		});
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
}
