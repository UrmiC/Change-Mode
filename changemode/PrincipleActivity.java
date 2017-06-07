package com.techfortsoft.changemode;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class PrincipleActivity extends Activity {
	private final String DEFAULT = "";
	private String ringMode, silentMode, vibrateMode, location;
	TextView titleFirsNname, titleSecondName;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	EditText ringField, silentField, vibrateField, locationField;
	Button saveButton, storeButton;
	Switch toggleSwitch;
	PackageManager packageManager;
	ComponentName componentName;
	boolean modeSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_principle);
		preferences = getSharedPreferences("modes", MODE_PRIVATE);
		editor = preferences.edit();
		modeSetting = preferences.getBoolean("mode_setting", false);
		toggleSwitch = (Switch) findViewById(R.id.switch1);
		if (modeSetting)
			toggleSwitch.setChecked(true);
		else
			toggleSwitch.setChecked(false);

		ringField = (EditText) findViewById(R.id.editText1);
		silentField = (EditText) findViewById(R.id.editText2);
		vibrateField = (EditText) findViewById(R.id.editText3);
		locationField = (EditText) findViewById(R.id.editText4);
		saveButton = (Button) findViewById(R.id.btnsave);
		storeButton = (Button) findViewById(R.id.buttonstore);

		titleFirsNname = (TextView) findViewById(R.id.textView1);
		titleSecondName = (TextView) findViewById(R.id.textView2);
		Typeface face = Typeface
				.createFromAsset(getAssets(), "fonts/FORTE.TTF");
		titleFirsNname.setTypeface(face);
		titleSecondName.setTypeface(face);

		ringMode = preferences.getString("ring_key", DEFAULT);
		silentMode = preferences.getString("silent_key", DEFAULT);
		vibrateMode = preferences.getString("vibrate_key", DEFAULT);
		location = preferences.getString("locationkey", DEFAULT);
		ringField.setText(ringMode);
		silentField.setText(silentMode);
		vibrateField.setText(vibrateMode);
		locationField.setText(location);

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ringMode = ringField.getText().toString().trim();
				silentMode = silentField.getText().toString().trim();
				vibrateMode = vibrateField.getText().toString().trim();
				location = locationField.getText().toString().trim();
				if (ringMode.equals(""))
					ringField.setError("EMPTY FIELD!");
				else if (silentMode.equals(""))
					ringField.setError("EMPTY FIELD!");
				else if (vibrateMode.equals(""))
					ringField.setError("EMPTY FIELD!");
				else {
					editor = preferences.edit();
					editor.putString("ring_key", ringMode);
					editor.putString("silent_key", silentMode);
					editor.putString("vibrate_key", vibrateMode);
					editor.putString("locationkey", location);
					editor.commit();
					Message.showMessage(getApplicationContext(),
							"KEYS SAVED SUCCESSFULLY");
				}
			}
		});

		storeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ringMode = ringField.getText().toString().trim();
				silentMode = silentField.getText().toString().trim();
				vibrateMode = vibrateField.getText().toString().trim();
				location = locationField.getText().toString().trim();

				StringBuffer sb = new StringBuffer();

				sb.append("Silent key: " + ringMode).append("\n")
						.append("Ring key: " + silentMode).append("\n")
						.append("Vibate key: " + vibrateMode)
						.append("location" + location);

				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "youremail@yahoo.com" });
				email.putExtra(Intent.EXTRA_SUBJECT, "subject");
				email.putExtra(Intent.EXTRA_TEXT, sb.toString());
				email.setType("message/rfc822");
				startActivity(Intent.createChooser(email,
						"Choose an Email client :"));

			}
		});

		toggleSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				packageManager = getPackageManager();
				componentName = new ComponentName(getApplicationContext(),
						ModeChangeReceiver.class);
				if (toggleSwitch.isChecked()) {
					packageManager.setComponentEnabledSetting(componentName,
							PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
							PackageManager.DONT_KILL_APP);
					Message.showMessage(getApplicationContext(),
							"SERVICE ENABLED!");
					editor.putBoolean("mode_setting", true);
					editor.commit();
				} else {
					packageManager.setComponentEnabledSetting(componentName,
							PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
							PackageManager.DONT_KILL_APP);
					Message.showMessage(getApplicationContext(),
							"SERVICE DISABLED!");
					editor.putBoolean("mode_setting", false);
					editor.commit();
				}
			}
		});
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
