package br.uff.psgamers.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;

public class ConnectivityHelper {
	
	private Context context;
	
	public ConnectivityHelper(Context context) {
		
		this.context = context;
	}
	
	public boolean isDeviceOnline() {
		
		boolean isOnline;
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) .isConnectedOrConnecting() || 
			cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {
			
			isOnline = true;
		}
		else {
			
			isOnline = false;
		}
		
		return isOnline;
	}
}