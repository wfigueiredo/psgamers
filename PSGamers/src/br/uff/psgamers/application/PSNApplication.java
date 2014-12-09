package br.uff.psgamers.application;

import java.util.List;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import br.uff.psgamers.constant.Constants;

import com.krobothsoftware.psn.client.PlayStationNetworkClient;
import com.krobothsoftware.psn.model.PsnGameData;
import com.krobothsoftware.psn.model.PsnTrophyData;

public class PSNApplication extends Application {
	
	private PlayStationNetworkClient psnClient;
	private List<PsnTrophyData> psnTrophyList;
	private List<PsnGameData> clientGameList;
	
	private static PSNApplication instance;
	
	private static Context appContext;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(Constants.LOG_TAG_PSGAMERS_INFO, "PSNApplication instance created.");
		appContext = (this);
		instance = (this);
//		executeLinkedAccountCheck(appContext);
		
//		-----------------------------------------------------------------------------------------------------
//		TODO: Future changes will re-initialize, if needed, the 'PlayStationNetworkClientUK' variable, via
//			  linkedAccountCheck() [email, password], in order to support scenarios which the app is wiped out 
//			  of the memory by the OS scheduler.
//		-----------------------------------------------------------------------------------------------------
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		this.psnClient = null;
	}
	
	public static PSNApplication getInstance() {
		
		if (instance == null) {
			throw new IllegalStateException("Application not created yet!");
		}
		
		return instance;
	}
	
//	private void executeLinkedAccountCheck(Context context) {
//		
//		boolean accountLinkedToDevice = PSNUserPreferences.getLinkedAccountPreference();
//		
//		if (accountLinkedToDevice) {
//			
//			Intent intent = new Intent(Constants.ACTION_FETCH_DATA_HOLDER);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			appContext.startActivity(intent);
//		}
//	}
	
	public static Context getAppContext() {
		return appContext;
	}
	
	// ---------------------------------- getters and setters for main app attributes ----------------------------------
	
	public void setPsnClientUK(PlayStationNetworkClient psnClient) {
		this.psnClient = psnClient;
	}
	
	public PlayStationNetworkClient getPsnClient() {
		return psnClient;
	}

	public List<PsnTrophyData> getPsnTrophyList() {
		return psnTrophyList;
	}

	public void setPsnTrophyList(List<PsnTrophyData> psnTrophyData) {
		this.psnTrophyList = psnTrophyData;
	}

	public List<PsnGameData> getClientGameList() {
		return clientGameList;
	}

	public void setClientGameList(List<PsnGameData> clientGameList) {
		this.clientGameList = clientGameList;
	}
}