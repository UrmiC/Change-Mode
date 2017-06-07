package com.techfortsoft.changemode;


import java.util.List;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ModeChangeReceiver extends BroadcastReceiver {
	final SmsManager smsManager = SmsManager.getDefault();
	private final String DEFAULT = "";
	private String ringMode, silentMode, vibrateMode,location;
	SharedPreferences preferences;
	AudioManager am;
Context context;
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context=context;
		final Bundle bundle = intent.getExtras();
		preferences = context.getSharedPreferences("modes",
				Context.MODE_PRIVATE);
		ringMode = preferences.getString("ring_key", DEFAULT);
		silentMode = preferences.getString("silent_key", DEFAULT);
		vibrateMode = preferences.getString("vibrate_key", DEFAULT);
		location=preferences.getString("locationkey", DEFAULT);
		Message.showMessage(context, ringMode+"\n"+silentMode+"\n"+vibrateMode);
		am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		try {
			if (bundle != null) {
				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String message = currentMessage.getDisplayMessageBody();
					String receivedMessage = getFirstWord(message);
					String smssender=currentMessage.getDisplayOriginatingAddress();
					int status = changeMode(receivedMessage);
					switch (status) {
					case 1:
						Message.showMessage(context, "MODE CHANGED : RING");
						break;
					case 2:
						Message.showMessage(context, "MODE CHANGED : SILENT");
						break;
					case 3:
						Message.showMessage(context, "MODE CHANGED : VIBRATE");
						break;
					default:
						break;
					}
					
					
			GPSTracker		gps = new GPSTracker(context);

					double lat = gps.latitude;
					double lon = gps.longitude;
				String	text = "Location : "+lat+":"+lon+""+getCompleteAddressString(lat ,lon);
Toast.makeText(context, text,100).show();
					// text=number;
					System.out.println("Get LAt and Long : "+ text);
if(receivedMessage.equalsIgnoreCase(location)){
					SmsManager smsManager = SmsManager.getDefault();
				
					smsManager.sendTextMessage(
									smssender,
									null,
									text,
									null,
									null);

}

//Toast.makeText(getApplicationContext(),"SMS Sent!",Toast.LENGTH_LONG).show();


					
					
				}
			}
		} catch (Exception e) {
			Log.e("MODE ERROR", e.toString());
		}

	}

	private int changeMode(String receivedMessage) {
		if (receivedMessage.equalsIgnoreCase(ringMode)) {
			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			return 1;
		} else if (receivedMessage.equalsIgnoreCase(silentMode)) {
			am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			return 2;
		} else if(receivedMessage.equalsIgnoreCase(vibrateMode)){
			am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			return 3;
		}
		return 0;
	}

	private String getFirstWord(String text) {
		if (text.indexOf(' ') > -1) {
			return text.substring(0, text.indexOf(' '));
		} else {
			return text;
		}
	}
	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }
     }
