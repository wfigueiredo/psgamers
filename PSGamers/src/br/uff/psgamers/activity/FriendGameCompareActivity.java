package br.uff.psgamers.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.uff.psgamers.R;
import br.uff.psgamers.adapter.GameListAdapter;
import br.uff.psgamers.application.PSNApplication;
import br.uff.psgamers.constant.Constants;
import br.uff.psgamers.util.UIUtils;

import com.krobothsoftware.psn.PlayStationNetworkException;
import com.krobothsoftware.psn.PlayStationNetworkLoginException;
import com.krobothsoftware.psn.client.PlayStationNetworkClient;
import com.krobothsoftware.psn.model.PsnGameData;

public class FriendGameCompareActivity extends Activity implements OnItemClickListener{
	
	private Resources resources;
	
	private ProgressDialog progressDialog;
	private ListView gameCompareListView;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_compare);
		
		resources = getResources();
		
		PlayStationNetworkClient psnClient = ((PSNApplication) getApplication()).getPsnClient();
		String friendPsnId = getIntent().getExtras().getString(Constants.EXTRA_PSN_ID);
		
		List<PsnGameData> gameList = null;
		
		try {
			gameList = new ArrayList<PsnGameData>(psnClient.getClientFriendGameList(friendPsnId));
			
			for (PsnGameData clientGame : psnClient.getClientGameList()) {
				if (!gameList.contains(clientGame)) {
					gameList.add(clientGame);
				}
			} 
			
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
		} 
		catch (PlayStationNetworkException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
		} 
		catch (PlayStationNetworkLoginException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
		}
				
		gameCompareListView = (ListView) findViewById(R.id.gameList);
		new GameCompareTask().execute(gameList);
		gameCompareListView.setOnItemClickListener(this);
	}
	
	private class GameCompareTask extends AsyncTask<List<PsnGameData>, Void, GameListAdapter> {

		@Override
		protected void onPreExecute() {
			progressDialog = UIUtils.createProgressDialog(FriendGameCompareActivity.this, resources.getString(R.string.retrieving_game_info));
			progressDialog.show();
		}
		
		@Override
		protected GameListAdapter doInBackground(List<PsnGameData>... psnGameList) {
			return new GameListAdapter(FriendGameCompareActivity.this, psnGameList[0]);
		}
		
		@Override
		protected void onPostExecute(GameListAdapter gameListAdapter) {
			gameCompareListView.setAdapter(gameListAdapter);
			progressDialog.dismiss();
		}
	}
	
	/**
	 * TODO: Feature not yet implemented.
	 */
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
	}
}