package com.conceptappsworld.wellbeing.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager
				= (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public boolean isNetWorking(){
		boolean isNet = false;
		ConnectivityManager conMgr = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info= conMgr.getActiveNetworkInfo();

		if(conMgr.getActiveNetworkInfo() != null &&
				conMgr.getActiveNetworkInfo().isAvailable() &&
				conMgr.getActiveNetworkInfo().isConnected()){
			isNet = true;
		}else {
			isNet = false;
		}
		return isNet;
	}
}
