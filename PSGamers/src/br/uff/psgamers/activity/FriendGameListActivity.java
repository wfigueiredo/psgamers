package br.uff.psgamers.activity;

import java.io.IOException;
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

public class FriendGameListActivity extends Activity implements OnItemClickListener {

	private Resources resources;
	
	private ProgressDialog progressDialog;
	private ListView gameListView;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_list);
		
		resources = getResources();
		
		PlayStationNetworkClient psnClient = ((PSNApplication) getApplication()).getPsnClient();
		String friendPsnId = getIntent().getExtras().getString(Constants.EXTRA_PSN_ID);
		
		List<PsnGameData> clientFriendGameList = null;
		
		try {
			clientFriendGameList = psnClient.getClientFriendGameList(friendPsnId);
		} catch (IOException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_IOEXCEPTION, e.getMessage());
		} 
		catch (PlayStationNetworkException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_EXCEPTION, e.getMessage());
		} 
		catch (PlayStationNetworkLoginException e) {
			Log.e(Constants.LOG_TAG_PSGAMERS_PSN_LOGIN_EXCEPTION, e.getMessage());
		}
		
		gameListView = (ListView) findViewById(R.id.gameList);
		new GameListTask().execute(clientFriendGameList);
		gameListView.setOnItemClickListener(this);
	}
	
	private class GameListTask extends AsyncTask<List<PsnGameData>, Void, GameListAdapter> {

		@Override
		protected void onPreExecute() {
			progressDialog = UIUtils.createProgressDialog(FriendGameListActivity.this, resources.getString(R.string.retrieving_game_info));
			progressDialog.show();
		}
		
		@Override
		protected GameListAdapter doInBackground(List<PsnGameData>... psnGameList) {
			return new GameListAdapter(FriendGameListActivity.this, psnGameList[0]);
		}
		
		@Override
		protected void onPostExecute(GameListAdapter gameListAdapter) {
			gameListView.setAdapter(gameListAdapter);
			progressDialog.dismiss();
		}
	}

	/**
	 * TODO: Feature not yet implemented.
	 * 
	 * 	For some weird reason, all trophy list for a friend is coming with 
	 *  isReceived() = false. Needs to be checked out in future lib releases.
	 */
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
//		PsnGameData psnGameData = (PsnGameData) parent.getItemAtPosition(position);
//		PlayStationNetworkClientUK psnClientUK = ((PSNApplication) getApplication()).getPsnClientUK();
//		PsnTrophyList<PsnTrophyData> trophyList = null;
//		
//		try {
//			trophyList = psnClientUK.getFriendTrophyList(psnGameData);
////			trophyList = (PsnTrophyList<PsnTrophyData>) psnGameData.getTrophyList(psnClientUK);
//		} catch (IOException e) {
//			Log.e(Constants.LOG_TAG_PSGAMERS_ERROR, e.getMessage());
//		} catch (PlayStationNetworkException e) {
//			Log.e(Constants.LOG_TAG_PSGAMERS_ERROR, e.getMessage());
//		}
//		
//		Intent intent = new Intent(Constants.ACTION_ACTIVITY_TROPHY_LIST);
//		intent.putExtra(Constants.EXTRA_GAME_ID, psnGameData.getGameId());
//		intent.putExtra(Constants.EXTRA_GAME_TITLE, psnGameData.getName());
//		intent.putExtra(Constants.EXTRA_GAME_IMAGE, psnGameData.getGameImage());
//		((PSNApplication) getApplication()).setPsnTrophyList(trophyList);
//		
//		startActivity(intent);
	}
}