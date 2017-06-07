package com.techfortsoft.changemode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends Activity {
	private final String DEFAULT = "";
	private String ringMode, silentMode, vibrateMode,locationkey;
	TextView titleFirsNname, titleSecondName;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		titleFirsNname = (TextView) findViewById(R.id.textView1);
		titleSecondName = (TextView) findViewById(R.id.textView2);
		Typeface face = Typeface
				.createFromAsset(getAssets(), "fonts/FORTE.TTF");
		titleFirsNname.setTypeface(face);
		titleSecondName.setTypeface(face);
		preferences = getSharedPreferences("modes", MODE_PRIVATE);
		//preferences = getSharedPreferences("modes", MODE_PRIVATE);
		editor = preferences.edit();
		ringMode = preferences.getString("ring_key", DEFAULT);
		silentMode = preferences.getString("silent_key", DEFAULT);
		vibrateMode = preferences.getString("vibrate_key", DEFAULT);
		locationkey=preferences.getString("locationkey", DEFAULT);
		if (ringMode.equals(DEFAULT) || silentMode.equals(DEFAULT)
				|| vibrateMode.equals(DEFAULT)||locationkey.equals(DEFAULT)) {
			
			editor.putString("ring_key", "ring");
			editor.putString("silent_key", "silent");
			editor.putString("vibrate_key", "vibrate");
			editor.putString("locationkey", "location");
			editor.commit();
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this,
						HomeActivity.class));
			}
		}, 3000);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
}
